package base;

/**@Description: TODO
 * @author: renkun
 * @date: 2018年12月21日上午8:25:19
 */
public enum Terms {

	/************** DbPool 初始化参数配置 **************/
	
	DbPool_Url("jdbc:mysql://localhost:3306/rbac?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true"),
	DbPool_UserName("root"),
	DbPool_Password("123456");
		
	private String values;

	private Terms(String values) {
		this.values = values;
	}

	public String getValues() {
		return values;
	}
}
