package base;

/**@Description: TODO
 * @author: renkun
 * @date: 2018年12月21日上午8:25:19
 */
public enum Term {

	/************** DbPool 初始化参数配置 **************/
	
	DbPool_Url("jdbc:mysql://code.ku8.info:8081/jrbac?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true"),
	DbPool_UserName("root"),
	DbPool_Password("mc59LsQ^b,VEWR7q"),
		
	/************** resources Table 参数配置 **************/
	FILE_PATH("D:\\Gjy\\Project\\anvars\\RbacView\\src\\main\\webapp\\pages"),
	REQ_METHOD("get"),
	SERVICES_NAME("RbacView")
	;
	
	private String val;

	private Term(String val) {
		this.val = val;
	}

	public String val() {
		return val;
	}
}
