package org.ants.tommy.utils;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.Result;

import com.google.gson.Gson;

public class ServiceResult {
	/// http 请求是否成功
	private boolean reqSuccess;
	/// 成功是为服务返回的数据, 失败时为错误信息
	private String resMsg;
	
	private Gson mGson = new Gson();
	
	ServiceResult(boolean isSuccess, String msg) {
		this.reqSuccess = isSuccess;
		this.resMsg = msg;
	}

	public boolean isReqSuccess() {
		return reqSuccess;
	}

	public void setReqSuccess(boolean reqSuccess) {
		this.reqSuccess = reqSuccess;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
	public Result getResult() {
		Result rlt = null;
		
		if (this.reqSuccess) {
			rlt = mGson.fromJson(this.resMsg, Result.class);
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), this.resMsg);
		}
		
		return rlt;
	}
}
