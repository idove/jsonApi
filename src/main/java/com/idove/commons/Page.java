/**
 * 
 */
package com.idove.commons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页common类
 * @author gery
 * @date 2012-11-13
 */
public class Page {
	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Integer pageNo = 1;     // 第一页序号为1
	
	private Integer pageSize = 15;  // 默认页面大小
	
	private Long offset = -1L;            // 记录偏移量，分页查询的起始行
	
	private Long count = -1L;             // 未查询记录前，数据记录数
	
	private Integer totalPage = -1;       // -1 表示未进行过查询
	
	/**
	 * 客户端请求中以键值对的方式传递条件字段
	 */
	private Map<String, Object> condition = new HashMap<String, Object>();
	
	/**
	 * 客户端请求中以键值对的方式传递排序字段
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> orderBy = new LinkedMap();
	

	public Integer getPageNo() {
		return pageNo;
	}

	public Map<String, Object> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Map<String, Object> orderBy) {
		this.orderBy = orderBy;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		this.offset = Long.valueOf((this.pageNo - 1) * this.pageSize);
		
		if(this.offset < 0) {
			this.offset = 0L;
		}
		
		if(this.getCount() >= 0) {
			if(this.count % this.pageSize == 0) {
				this.totalPage = (int) (this.count / this.pageSize);
			} else {
				this.totalPage = (int) (this.count / this.pageSize) + 1;
			}
		}
		logger.debug("pageNo:" + this.pageNo + ", pageSize:" + this.pageSize + ", offset:" + this.offset);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
		if(this.count % this.pageSize == 0) {
			this.totalPage = (int) (this.count / this.pageSize);
		} else {
			this.totalPage = (int) (this.count / this.pageSize) + 1;
		}
		if (pageNo.intValue() > totalPage.intValue()) {
			setPageNo(totalPage);
		}
		logger.debug("count:" + this.count + ", totalPage:" + this.totalPage);
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

}
