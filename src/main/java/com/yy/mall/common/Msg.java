package com.yy.mall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回json的通用类
 *
 * @author crf
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Msg<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 响应吗
	 */
	private int status;
	/**
	 * 返回描述
	 */
	private String message;
	/**
	 * 同bean
	 */
	private T data;
	/**
	 * 用户不同个bean
	 */
	private Map<String, Object> dataBean;

	public Msg() {
	}

	public Msg(int status) {
		this.status = status;

	}

	public Msg(int status, String message) {
		this.status = status;
		this.message = message;

	}

	public Msg(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;

	}

	public Msg(String message) {
		this.message = message;
	}

	public Msg(T data) {
		this.data = data;
	}

	public Msg(String key, Object object) {
		if (dataBean == null) {
			dataBean = new HashMap<String, Object>();
		}

		dataBean.put(key, object);
	}

	public static <T> Msg<T> createSuccess(T data) {
		Msg<T> msg = new Msg<T>(data);
		ResponseCode success = ResponseCode.SUCCESS;
		msg.setStatus(success.getStatus());
		msg.setMessage(success.getMsg());
		return msg;
	}

	public static <T> Msg<T> createError(T data) {
		Msg<T> msg = new Msg<T>(data);
		ResponseCode error = ResponseCode.ERROR;
		msg.setStatus(error.getStatus());
		msg.setMessage(error.getMsg());
		return msg;
	}

	public static <T> Msg<T> createSuccessWithMessage(String message, T data) {
		Msg<T> msg = new Msg<T>(data);
		ResponseCode success = ResponseCode.SUCCESS;
		msg.setStatus(success.getStatus());
		msg.setMessage(message);
		return msg;
	}

	public static <T> Msg<T> createErrorWithMessage(String message, T data) {
		Msg<T> msg = new Msg<T>(data);
		ResponseCode error = ResponseCode.ERROR;
		msg.setStatus(error.getStatus());
		msg.setMessage(message);
		return msg;
	}

	/**
	 * 默认成功
	 *
	 * @return
	 */
	public static Msg addSuccessMessage() {
		ResponseCode success = ResponseCode.SUCCESS;
		return new Msg(success.getStatus(), success.getMsg());
	}

	/**
	 * 默认失败
	 *
	 * @return
	 */
	public static Msg addErrorMessage() {
		ResponseCode error = ResponseCode.ERROR;
		return new Msg(error.getStatus(), error.getMsg());
	}

	public static Msg addMessage(int status, String message) {
		return new Msg(status, message);
	}

	public static Msg addErrorMessage(String message) {
		return new Msg(100, message);
	}

	public static Msg createDataBean(String key, Object object) {
		return new Msg(key, object);
	}

	public static Msg addSuccessMessage(String message) {
		return new Msg(ResponseCode.SUCCESS.getStatus(), message);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Msg addSingleBean(T data) {
		this.data = data;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDataBean() {
		return dataBean;
	}

	public void putData(String key, Object object) {
		if (dataBean == null) {
			dataBean = new HashMap<String, Object>();
		}
		dataBean.put(key, object);
	}

	@JsonIgnore
	public boolean isSuccess() {
		return this.getStatus() == ResponseCode.SUCCESS.getStatus();
	}

}
		

