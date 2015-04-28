/**
 * 
 */
package com.idove.web;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 服务端传向浏览器端的封装数据，
 * 第一项为程序执行状态，第二项为数据值或自定义信息。
 * @author gery
 * @date 2012-10-30
 */
@SuppressWarnings("serial")
public class FrontModel<K, V> extends LinkedHashMap<K, V> {
	
	public FrontModel() {
		super();
	}
	
	public FrontModel(int initialCapacity) {
		super(initialCapacity);
	}
	
	public FrontModel(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
	public FrontModel(Map<? extends K, ? extends V> m) {
		super(m);
	}	

}
