package cn.tedu.store.service.ex;

/**
 * 商品数据不存在的异常
 */
public class ProductException extends RuntimeException {

	private static final long serialVersionUID = 4658741945178711545L;

	public ProductException(){
		
	}

	public ProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductException(String message) {
		super(message);
	}

	public ProductException(Throwable cause) {
		super(cause);
	}
	
}
