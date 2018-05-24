package daddyok.weapp.service;



import org.json.JSONObject;

import daddyok.weapp.utils.CommonUtils;
import daddyok.weapp.utils.HttpHelper;
import daddyok.weapp.utils.RedisUtil;

public class LoginService {
	
	private String third_sessionKey;
	private String get3rdSession(){  
	    return CommonUtils.exec("head -n 80 /dev/urandom | tr -dc A-Za-z0-9 | head -c 168");  
	}  
	
	public boolean SendGet(String url) throws Exception
	{
		JSONObject jsonObj=null;
		try {
			jsonObj = HttpHelper.doGet(url);
			System.out.println("josn:"+jsonObj.toString());
			if(jsonObj != null)
			{
				//获取第三方随机会话密钥
				third_sessionKey = get3rdSession();
				
				//映射第三方密钥到openid和sessionkey的json对象
				String openid=jsonObj.getString("openid");
				String session_key = jsonObj.getString("session_key");
				RedisUtil.getInstance().hash().hset(third_sessionKey, "openid", openid);
				RedisUtil.getInstance().hash().hset(third_sessionKey, "session_key", session_key);
				return true;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}
	public JSONObject get_3rdSessionKey() {
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("thirdkey", third_sessionKey);
		return jsonObject;
	}
		
}
