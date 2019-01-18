package org.ants.common.exception;


/**
 * 服务异常类
 *
 * @author Li Jinhui
 * @since 2018/12/6
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 201812140852009876L;
    private int code;
    private String message;

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}