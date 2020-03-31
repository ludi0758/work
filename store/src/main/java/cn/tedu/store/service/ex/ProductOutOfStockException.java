package cn.tedu.store.service.ex;

/**
 * 商品库存超出限制的异常
 * @author syk
 *
 */
public class ProductOutOfStockException extends RuntimeException {

	private static final long serialVersionUID = 5474881212831659811L;

	public ProductOutOfStockException(){
		
	}

	public ProductOutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductOutOfStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductOutOfStockException(String message) {
		super(message);
	}

	public ProductOutOfStockException(Throwable cause) {
		super(cause);
	}
	
}
