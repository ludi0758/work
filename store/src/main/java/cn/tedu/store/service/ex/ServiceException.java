package cn.tedu.store.service.ex;

/**
 * 业务异常的基类
 * @author syk
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 980104530291206274L;

	public ServiceException(){
		
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
}