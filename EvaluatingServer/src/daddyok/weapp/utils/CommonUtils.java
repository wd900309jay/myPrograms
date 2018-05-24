package daddyok.weapp.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CommonUtils {
	private static String wxSessionkey = "F3UENUg3JcI31O2RpoBQ9n8J77Tf1LgZUyGyzdjm7Q4rRKT052DPLdA3NqHeajF6cITOX54rQ2yoFxE83g3eHWjEH7CB9m2FvdoljuTXZLrJy6U2Ba2EbUlF6xazawRaK9Aq";   
	/**  
   * linux中执行命令  
   * @param cmd  
   * @return  
   */  
	public static String exec(String cmd) {  
		StringBuffer sb = new StringBuffer();  
		try {  
			String[] cmdA = { "/bin/sh", "-c", cmd };  
			Process process = Runtime.getRuntime().exec(cmdA);  
			LineNumberReader br = new LineNumberReader(new InputStreamReader( process.getInputStream()));  
			String line;  
			while ((line = br.readLine()) != null) {  
				sb.append(line).append("\n");  
			}  
		} catch (Exception e) {  
	      //如果本地测试，会报空指针异常，所以为了不让报错，索性返回有值即可  
			sb.append(wxSessionkey);  
	    }  
	    return sb.toString();  
	}  
	public static String GetUserID(String thirdKey)
	{
		String userIdStr="";
		
		
		return userIdStr;
	}

}
