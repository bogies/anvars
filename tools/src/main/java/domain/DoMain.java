package domain;

import java.io.IOException;

import base.Term;

public class DoMain{

	static DoMainImpl dmi = new DoMainImpl();
	
	public static void main(String[] args) throws IOException {

		dmi.getFile(Term.FILE_PATH.val(), Term.REQ_METHOD.val().toLowerCase(), Term.SERVICES_NAME.val());	//文件路径，API，GET，服务名
	}
}







