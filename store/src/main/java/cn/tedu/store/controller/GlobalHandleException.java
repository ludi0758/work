package cn.tedu.store.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileStateException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ProductException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;


@RestControllerAdvice
public class GlobalHandleException {
	
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public JsonResult<Void> handleException(Throwable e) {
		JsonResult<Void> result = new JsonResult<>(e);
		
		if (e instanceof UsernameDuplicateException) {
			result.setState(4000);
		} else if (e instanceof UserNotFoundException) {
			result.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			result.setState(4002);
		} else if (e instanceof AddressSizeLimitException) {
			result.setState(4003);
		} else if (e instanceof AddressNotFoundException) {
			result.setState(4004);
		} else if (e instanceof AccessDeniedException) {
			result.setState(4005);
		} else if (e instanceof ProductException) {
			result.setState(4006);
		} else if (e instanceof CartNotFoundException) {
			result.setState(4007);
		} else if (e instanceof InsertException) {
			result.setState(5000);
		} else if (e instanceof UpdateException) {
			result.setState(5001);
		} else if (e instanceof DeleteException) {
			result.setState(5002);
		} else if (e instanceof FileEmptyException) {
			result.setState(6000);
		} else if (e instanceof FileSizeException) {
			result.setState(6001);
		} else if (e instanceof FileTypeException) {
			result.setState(6002);
		} else if (e instanceof FileStateException) {
			result.setState(6003);
		} else if (e instanceof FileUploadException) {
			result.setState(6004);
		}
		
		return result;
	}

}

