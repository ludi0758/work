package cn.tedu.store.util;
/**
 * 封装响应JSON对象的属性的类
 * @param <E> 响应给客户端的数据的类型
 */
public class JsonResult<E> {

	// 响应的标识，例如：使用1表示登录成功，使用2表示由于用户名不存在导致的登录失败
	private Integer state;
	// 操作失败/操作出错时的描述文字，例如：“登录失败，用户名不存在”
	private String message;
	// 操作成功时需要响应给客户端的数据
	private E data;

	public JsonResult() {
		super();
	}

	public JsonResult(Integer state) {
		super();
		this.state = state;
	}

	public JsonResult(Throwable e) {
		super();
		this.message = e.getMessage();
	}
	
	public JsonResult(Integer state, E data) {
	    super();
	    this.state = state;
	    this.data = data;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	
}
