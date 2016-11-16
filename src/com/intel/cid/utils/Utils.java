package com.intel.cid.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;

import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.service.UserService;
import com.opensymphony.xwork2.ActionContext;

public class Utils {

	public static final String USER_COOKIE = "user.cookie";

	public static String md5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static User isLogin(UserService userService) throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");

		if (user != null) {
			return user;
		}

		return checkCookie(userService);
	}

	public static User checkCookie(UserService userService) throws Exception {
		HttpServletRequest httpRequest = ServletActionContext.getRequest();

		Cookie[] cookies = httpRequest.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (!USER_COOKIE.equals(cookie.getName())) {
				continue;
			}

			String value = cookie.getValue();
			if (value != null && value.indexOf(',') != -1) {
				String[] split = value.split(",");
				String username = split[0];
				String password = split[1];
				User user = userService.queryUserByCookie(username, password);

				if (user.getUserId() != -1) {
					return user;
				}
			}
		}

		return null;
	}

	public static void addCookie(User user) {
		HttpServletResponse httpResponse = ServletActionContext.getResponse();

		Cookie cookie = new Cookie(USER_COOKIE, user.getUserName() + ","
				+ user.getPassword());
		cookie.setMaxAge(60 * 60 * 24 * 7);// save cookie days
		cookie.setPath("/iTMS/frameset/");
		httpResponse.addCookie(cookie);
	}

	public static void removeCookie(ActionContext context) {
		HttpServletResponse httpResponse = ServletActionContext.getResponse();

		Cookie cookie = new Cookie(USER_COOKIE, null);

		cookie.setMaxAge(0);
		cookie.setPath("/iTMS/frameset/");
		httpResponse.addCookie(cookie);
	}

	public synchronized static String htmlTextChange(String text) {
		text = text.replace("&", "&amp;");
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace("\"", "&quot;");
		text = text.replace("\n", "<br/>");

		return text;
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);

		boolean isDeleted = false;
		if (file.isFile()) {
			isDeleted = file.delete();
			if (!isDeleted) {
				return false;
			}
		} else {
			File files[] = file.listFiles();
			for (File f : files) {
				deleteFile(f.getAbsolutePath());
			}
			isDeleted = file.delete();
			if (!isDeleted) {
				return false;
			}
		}
		return isDeleted;
	}

	public static int getIntValue(String str) {
		int r = 0;
		if (str != null && str.length() != 0) {
			StringBuffer bf = new StringBuffer();

			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c >= '0' && c <= '9') {
					bf.append(c);
				} else if (c == ',') {
					continue;
				} else {
					if (bf.length() != 0) {
						break;
					}
				}
			}
			try {
				r = Integer.parseInt(bf.toString());
			} catch (Exception e) {
			}
		}
		return r;
	}

	public static String readFile(String path) {
		StringBuffer strBuffer = new StringBuffer(8 * 1024);

		File file = new File(path);
		if (!file.exists()) {
			return null;
		}

		InputStream in = null;
		try {
			in = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = null;
			while ((line = br.readLine()) != null) {
				strBuffer.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strBuffer.toString();
	}

	public static void writeFile(String path, String text) throws Exception {
		PrintWriter pw = new PrintWriter(new File(path));
		pw.write(text);
		pw.close();
	}

	public static String getProjectPath(ActionContext context) {
		ServletContext sc = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		return sc.getRealPath("/");
	}

	/**
	 * return the top path<br>
	 * e.g.: /a/b/c, return a
	 * 
	 * @param path
	 * @return
	 */
	public static String getTopPath(String path) {
		if (path == null) {
			return null;
		}

		path = path.trim();

		if (path.trim().length() == 0) {
			return "";
		}

		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}

		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				return path.substring(0, i);
			}
		}

		return path;
	}

	public static Document getDocument(File file) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(file);
	}

	public static Document getDocument(InputStream in) throws Exception {
		DocumentBuilderFactory docBuilderFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;

		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();

		doc = docBuilder.parse(in);

		return doc;
	}

	public static Document getDocument() throws Exception {
		DocumentBuilderFactory docBuilderFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();

		doc = docBuilder.newDocument();

		return doc;
	}

	public static String dateFormat(Date date, String pattern) {
		if (pattern == null) {
			pattern = Constant.DATE_FORMAT_DEFAULT;
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);

		return format.format(date);

	}

	public static long dateFormat(String dateStr, String pattern)
			throws ParseException {
		if (pattern == null) {
			pattern = Constant.DATE_FORMAT_DEFAULT;
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = format.parse(dateStr);
		return date.getTime();

	}

	public static long dateCompare(String srcDate, String desDate,
			String pattern) throws Exception {
		if (pattern == null) {
			pattern = Constant.DATE_FORMAT_DEFAULT;
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date1 = format.parse(srcDate);

		Date date2 = format.parse(desDate);

		return date1.getTime() - date2.getTime();
	}

	public static String dateFormat(long time, String pattern) {
		if (pattern == null) {
			pattern = Constant.DATE_FORMAT_DEFAULT;
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date();
		date.setTime(time);
		return format.format(date);

	}

	public static String renameFileAppendSuffix(String fileName, String suffix) {

		String[] names = fileName.split("\\.");

		return names[0] + "_" + suffix + "." + names[1];

	}

	public static boolean isNullORWhiteSpace(String str) {

		String regex = "^\\s*$";
		if (str == null || Pattern.matches(regex, str)) {
			return true;
		}
		return false;
	}

	public static void writeXmlToFile(Document doc, String fileName)
			throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();

		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		StreamResult result = new StreamResult(pw);
		transformer.transform(source, result);

	}

	public static String getStringFromArray(String[] array)  {

		if (array != null) {
			StringBuilder builder = new StringBuilder("");
			boolean flag = false;
			for (int i = 0; i < array.length; i++) {
				
				
				
				if (i>0 && !Utils.isNullORWhiteSpace(array[i])&& flag) {
					builder.append(",");
				}
				if (!isNullORWhiteSpace(array[i])) {
					builder.append(array[i]);	
					flag = true;
				}
				
				
				if (i == array.length - 1) {
					builder.append("");
				}
			}
			return builder.toString();
		} else {
			return "";
		}

	}

	 public static String replaceHTMLSpecialChars(String str)
	  {
	    StringBuffer buffer = new StringBuffer();
	    char temp;
	    for(int i = 0; i < str.length(); i++)
	    {
	      temp = str.charAt(i);
	      if(temp == '<')
	      {
	          buffer.append("&lt;");
	      }
	      else if(temp == '>')
	      {
	          buffer.append("&gt;");
	      }
	      else if(temp == '"')
	      {
	          buffer.append("&quot;");
	      }
	      else if(temp == '&')
	      {
	          buffer.append("&amp;");
	      }
	      else if(temp == ' ')
	      {
	          buffer.append("&nbsp;");
	      }
	      else if(temp == '\n')
	      {
	          buffer.append("<br/>");
	      }
	      else
	      {
	          buffer.append(temp);
	      }
	    }
	    return buffer.toString();
	  }
	
	public static void main(String[] args) throws Exception {
		System.out.println(dateFormat("2013-02-17", "yyyy-MM-dd"));
		System.out
				.println(dateFormat(System.currentTimeMillis(), "yyyy-MM-dd"));
		System.out
				.println(dateCompare("2013-02-17", "2013-02-17", "yyyy-MM-dd"));
		System.out.println(getStringFromArray(null));
		System.out.println(getStringFromArray(new String[] { "123", "", "234",
				"23" ,null}));
		System.out.println(getStringFromArray(new String[] { "", "", "234",
				"23" ,null}));
		System.out.println(getStringFromArray(new String[] { "", "", "",
				"" ,null}));
		System.out.println(getStringFromArray(new String[] { "", "", "234","","",
				"23" ,null,""}));
	}
}
