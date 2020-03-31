package cn.tedu.store.service.ex;


/**
 * 用户找不到的异常
 */
public class UserNotFoundException extends ServiceException {

	private static final long serialVersionUID = 5156778717593377564L;

	public UserNotFoundException(){
		
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
	
}
