package cn.tedu.store.service.ex;

/**
 * 购物车找不到的基类
 * @author syk
 *
 */
public class CartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -290753401450618624L;

	public CartNotFoundException(){
		
	}

	public CartNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CartNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CartNotFoundException(String message) {
		super(message);
	}

	public CartNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
