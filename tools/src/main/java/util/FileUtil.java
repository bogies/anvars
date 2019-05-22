package util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil{
	/**
	 * 直接读html的文件 将文件内容读成String
	 * @param filePath 	文件路径
	 */
	public String readFileToString(String filePath) throws IOException{
		
		StringBuilder sb = new StringBuilder();
		
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		while (br.ready()) {	sb.append(br.readLine());	}
		br.close();		isr.close();		fis.close();
		// String str = new String(sb.toString().getBytes("utf-8"), "gbk");
		String str = sb.toString();
		str = str.substring(str.indexOf("<!--文书内容 开始 -->")==-1?0:str.indexOf("<!--文书内容 开始 -->"), 
							str.indexOf("<!--文书内容 结束 -->")==-1?0:str.indexOf("<!--文书内容 结束 -->"));
		if(!str.equals("")) 
			str = Jsoup.parse(str.replace("</p>","~jx~</p>")).text().replaceAll("~jx~", "\n");
		return str;
	}
	/**
	 * 直接读html的文件 将内容读成String
	 * @param filePath 	文件路径
	 */
	public synchronized String readFileHtmlToString(String filePath) throws IOException{
		
		StringBuilder sb = new StringBuilder();
		
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		while (br.ready()) {	sb.append(br.readLine());	}
		br.close();		isr.close();		fis.close();
		return sb.toString();
	}

	/**
	 * 直接读txt的文件 将内容读成String
	 * @param filePath 	文件路径
	 */
	public synchronized String readFileTxtToString(String filePath) throws IOException{
		
		StringBuilder sb = new StringBuilder();
		
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		while (br.ready()) {	sb.append(br.readLine());	}
		br.close();		isr.close();		fis.close();
		return sb.toString();
	}

	/**
	 * doc转换成html之后,html去样式并分段
	 *
	 * @param str
	 * @return
	 */
	public static String converHtmlToTxt(String str) {
		String regex = "<style type=\"text/css\">(.*?)</style>";// 正则替换style样式
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		String result = matcher.replaceAll("");
		result = result.replace("</p>", "~jx~</p>");
		return Jsoup.parse(result).text().replaceAll("~jx~", "\n");
	}

	/**
	 * @param folder 文件夹地址
	 */
	public Map<String, String> getFiles(String folder) {

		final Path path = Paths.get(folder);
		final Map<String, String> map = new HashMap<String, String>();

		SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				File every_file = file.toFile(); // 得到当前文件
//				if (every_file.getPath().replace(every_file.getName(), "").endsWith(File.separator + "1")) // 放入map集合
//				if (every_file.getPath().endsWith(".txt")) // 放入map集合
					map.put(every_file.getPath(), every_file.getName());
				return super.visitFile(file, attrs);
			}
		};
		try {
			Files.walkFileTree(path, finder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @param folder 文件夹地址
	 */
	public List<File> getFileList(String folder) {

		Path path = Paths.get(folder);
		final List<File> li = new ArrayList<File>();

		SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				File every_file = file.toFile(); // 得到当前文件
				if (every_file.getPath().endsWith(".jsp")) // 放入map集合
					li.add(every_file);
				return super.visitFile(file, attrs);
			}
		};
		try {
			Files.walkFileTree(path, finder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return li;
	}
	/**
	 * @param folder 文件夹地址
	 */
	public List<File> getTxtList(String folder) {

		Path path = Paths.get(folder);
		final List<File> li = new ArrayList<File>();

		SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				File every_file = file.toFile(); // 得到当前文件
//				if (every_file.getPath().replace(every_file.getName(), "").endsWith(File.separator + "1")) // 放入map集合
				if (every_file.getPath().endsWith(".txt")) // 放入map集合
					li.add(every_file);
				return super.visitFile(file, attrs);
			}
		};
		try {
			Files.walkFileTree(path, finder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return li;
	}
	Map<String, String> map = new HashMap<String, String>();
	/**
	 * @param files 文件夹地址
	 */
	public Map<String, String> getFilesMap(File[] files) {

		for (File f : files) {
			if(f.isDirectory())	getFilesMap(f.listFiles());
			else if(f.getName().endsWith(".html")) map.put(f.getName(), f.getPath());
		}
		return map;
	}
	public String getFileToString(String path) throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "";
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		br.close();
		return sb.toString();
	}

	/**
	 * @param source  源地址
	 * @param target  目标地址
	 * @param fileName	3906.html
	 */
	public synchronized void copyFile(String source, String target, String fileName) {
		
		String new_target = target + source.replace(":", "_");// 新的目的文件(兼容 windows、linux)
		String dir = new_target.replace(File.separator + fileName, "");

		if (!new File(dir).exists()) 	new File(dir).mkdirs();
		Path fromPath = Paths.get(source); // 相当于 c:\test\a.txt a.txt为需要复制的文件
		Path toPath = Paths.get(new_target); // 相当于 c:\test1\b.txt 。 b.txt无需存在
		if(new File(toPath.toString()).exists()) {
			System.err.println("warn   目标地址已存在该文件,toPath     "+toPath);
			return;
		}
		try {
			//使用这个方法必须先确保路径是存在的	copy 全路径+文件名称
//			Files.move(fromPath, toPath, StandardCopyOption.ATOMIC_MOVE);//	// 移动文件（即复制并删除源文件）
			Files.copy(fromPath, toPath);
		} catch (Exception e) {
			System.err.println("error   文件复制异常,fromPath     "+fromPath);
		}
	}
	/**
	 * 生成文件，追加内容
	 * @param fileName
	 * @param txt
	 * @param dir
	 */
	public synchronized void txtAppend(String fileName, String txt, String dir) {
		FileWriter fr = null;
		try {
			if (!new File(dir).exists()) 	new File(dir).mkdirs();
			String path = dir + File.separator + fileName + ".txt";
			if (!new File(dir).exists()) 	new File(dir).mkdirs();
			File file = new File(path);
			fr = new FileWriter(file,true);
			fr.write(txt);
			fr.close();
		} catch (Exception e) {
			return;
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (Exception e) {
					return;
				}
			}
		}
	}

	/**
	 * 根据txt内容获取案号
	 * @param neirong
	 * @return
	 */
	public String getCaseNo(String neirong) {
		String caseno = null;
		String regex = "((?<=\\s| |　| )[\\u4e00-\\u9fa5\\(\\)*]{1,12}刑不?诉[\\d\\D]{0,18}号(?=\\s| |　| ))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(neirong);
		
		while (m.find())	caseno = m.group(0);

		if(caseno.equals("")) {
			String[] txt = neirong.split("\n");
			for (int i = 0; i < 5 && i<txt.length; i++) {
				String tt = StringUtils.trimToEmpty(txt[i]).trim().replace(" ", "").replace(" ","");
				if(tt !=null && 
						((tt.contains("〔2013〕") || tt.contains("[2013]")
						  ||tt.contains("〔2014〕") || tt.contains("[2014]")
						  ||tt.contains("〔2015〕") || tt.contains("[2015]")
						  ||tt.contains("〔2016〕") || tt.contains("[2016]")
						  ||tt.contains("〔2017〕") || tt.contains("[2017]")
						  ||tt.contains("〔2018〕") || tt.contains("[2018]")) 
						 &&tt.contains("号"))) {
					caseno = tt;
					break;
				}
			}
		}
		return caseno;
	}
	public static void main(String[] args) {
		String aa = "                                     石大检公诉刑诉〔2018〕83号";
		String ab = StringUtils.trimToEmpty(aa).trim().replace(" ", "").replace(" ","");
		System.err.println(ab);
	}
	/**
	 * 根据txt内容获取案号
	 * @param neirong
	 * @return
	 */
	public static String getCaseNo_wwe(String neirong) {
		String caseno = "";
		//
		String regex = "((?<=\\s| |　| )[\\u4e00-\\u9fa5\\(\\)*]{1,12}刑不?诉[\\d\\D]{0,18}号(?=\\s| |　| ))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(neirong);
		while (m.find()) {
			caseno = m.group(0);
		}
		if(caseno.equals("")) {
			String[] txt = neirong.split("\n");
			for (int i = 0; i < 5; i++) {
				if(txt[i].contains(")")&&  txt[i].contains("(")) {
					caseno =  txt[i].trim();
					while (caseno.contains(" ") || caseno.contains("　")) {
						caseno = caseno.trim().replace(" ", "");
						caseno = caseno.trim().replace("　", "");
					}
					break;
				}
			}
		}
		return caseno;
	}
	
	 /**
    *
    * @param str 纯txt文本
    * @return
    */
	public String text2html(String str){
		String begin = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><style type=\"text/css\">.b1 {" +
              "        white-space-collapsing: preserve;" +
              "    }" +
              "" +
              "    .b2 {" +
              "        margin: 0.28125in 0.90555555in 0.9847222in 1.1027777in;" +
              "    }" +
              "" +
              "    .p0 {" +
              "        text-align: center;" +
              "        hyphenate: auto;" +
              "        font-family: Times New Roman;" +
              "        font-size: 22pt;" +
              "    }" +
              "" +
              "    .p1 {" +
              "        text-align: center;" +
              "        hyphenate: auto;" +
              "        font-family: 华文中宋;" +
              "        font-size: 16pt;" +
              "    }" +
              "" +
              "    .p2 {" +
              "        text-align: right;" +
              "        hyphenate: auto;" +
              "        font-family: Times New Roman;" +
              "        font-size: 16pt;" +
              "    }" +
              "" +
              "    .p3 {" +
              "        text-indent: 0.45in;" +
              "        text-align: justify;" +
              "        hyphenate: auto;" +
              "        font-family: 仿宋_GB2312;" +
              "        font-size: 16pt;" +
              "    } </style>" +
              "</head>" +
              "<body class=\"b1\">";
		String end = "</body></html>";
		StringBuilder sb = new StringBuilder();
		String content[] = str.split("\n");
		sb.append(begin);
		for (int i = 0; i < content.length; i++) {
			switch (i) {
	    	   case 0:
	    	   case 1:
	    	   case 2:	sb.append("<p class=\"p" + i + "\"><strong>"+content[i]+"</strong></p>");	break;
	    	   default: sb.append("<p class=\"p3\"><span>"+content[i]+"</span></p>");	break;
			}
		}
		sb.append(end);
		return sb.toString();
	}
}
