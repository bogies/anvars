package domain;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import util.EncryptUtil;
import util.FileUtil;
import util.SqlUtil;

public class DoMainImpl{

	public Logger log = Logger.getLogger(this.toString());
	
	FileUtil fu = new FileUtil();
	SqlUtil su = new SqlUtil();
	
	public void getFile(String filePath, String req_method, String services_name) throws IOException {

		List<File> files = fu.getFileList(filePath);	
		log.info("待处理的文件个数为： " + files.size());

		Iterator<File> it = files.iterator();

		StringBuffer sb =new StringBuffer(48);
		sb.append("INSERT INTO resources(id, type, path, req_method, services_name) VALUES ");
		while (it.hasNext()) {

			File entry = it.next();
			String namePath = entry.getPath().replace(filePath, "").replace(File.separator, "-").replace(".jsp", "").toLowerCase();
			namePath = namePath.startsWith("-")?namePath.substring(1, namePath.length()):namePath;
			
			String md5Id = EncryptUtil.md5(namePath+req_method+services_name);
			if(!su.recordExist("SELECT COUNT(*) FROM resources WHERE id='"+md5Id+"'")) {
				
				sb.append("(");
				sb.append("'"+md5Id+"',");
				sb.append("'pages',");
				sb.append("'"+namePath+"',");
				sb.append("'"+req_method+"',");
				sb.append("'"+services_name+"'),");
			}
		}
		String insertSql = sb.toString();
		insertSql = insertSql.substring(0, insertSql.length()-1).replace(".jsp", "")+";";
		su.insertSql(insertSql, null);
	}
}
