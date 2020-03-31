package cn.tedu.store.service.ex;

/**
 * 删除数据异常
 * @author syk
 *
 */
public class DeleteException extends RuntimeException {

	private static final long serialVersionUID = -8881103463792318580L;

	public DeleteException(){
		
	}

	public DeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteException(String message) {
		super(message);
	}

	public DeleteException(Throwable cause) {
		super(cause);
	}
	
}
