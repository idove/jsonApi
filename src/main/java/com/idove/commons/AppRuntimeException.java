/**
 * 
 */
package com.idove.commons;

import com.idove.web.ExecuteStatus;

/**
 * 应用异常基类，应用中其他异常都应继承该异常，该异常为非检查性异常
 * @author gery
 * @date 2012-10-30
 */
@SuppressWarnings("serial")
public class AppRuntimeException extends RuntimeException {
	
	
	private ExecuteStatus status;  // 异常状态码

	public ExecuteStatus getStatus() {
		return status;
	}

	public void setStatus(ExecuteStatus status) {
		this.status = status;
	}

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(ExecuteStatus status) {
		this.status = status;
	}
	
	public AppRuntimeException(ExecuteStatus status, String message) {
		super(message);
		this.status = status;
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

}
