/**
 * 
 */
package com.idove.web;



/**
 * 
 * 服务端请求执行状态代码 状态码一律为四位数字，起始值：1000 状态码分段： 1000：正常 2000：服务端执行异常 2101：用户名或密码错误
 * 2102：用户已存在 2103：注册失败2105：名称重复 2106：批发商名称已存在 2200：超出（总页数）范围
 * 2201：未找到符合条件的数据 
 * 
 * 3000：其他错误 5000：服务端异常
 * 
 * @author gery
 * @date 2012-10-30
 */
public enum ExecuteStatus {
	SUCCESS(new String[] {"1000", "成功"}),
	FAIL(new String[] {"2000", "系统繁忙，请稍候再试"}),
	JEDISCONNECTIONFAIL(new String[] {"2001", "jedis连接失败"}),
	DATAEXISTS(new String[] {"2002","数据已经存在"}),
	DATABINDEXCEPTION(new String[]{"2222","数据类型错误"}),
	
	USERNAMEORPASSWORDFALSE(new String[]{"2010","用户名或密码错误"}),
	USEREXISTS(new String[]{"2011","用户名已存在"}),
	CHANGESTOCKFAIL(new String[]{"2012","修改库存失败"}),
	UNDERSTOCK(new String[]{"2013","库存不足"}),
	ADDSELLFAIL(new String[]{"2014","添加单据失败"}),
	UPDATESELLFAIL(new String[]{"2015","修改单据失败"}),
	
	PARAMLOSE(new String[]{"2100","参数丢失"}),
	
	OTHER(new String[] {"3000", " 其他错误"}),
	URLERROR(new String[] {"3001", " 请求路径错误"}),
	SERVERERROR(new String[] {"5000", " 服务端异常"});
	
	private String[] value;

	private ExecuteStatus(String[] value) {
		this.value = value;
	}

//	public String[] getValue() {
//		return value;
//	}
	
	public Integer getCode() {
		return Integer.valueOf(value[0]);
	}
	
	public String getMsg() {
		return value[1];
	}
	
//	public static String getMsg(Object status) {
//		for(ExecuteStatus value : ExecuteStatus.values()) {
//			String[] obj = value.getValue();
//			if(status.toString().equals(obj[0])) {
//				return obj[1];
//			}
//		}
//		return null;
//	}
}
