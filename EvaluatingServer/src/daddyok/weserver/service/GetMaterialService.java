package daddyok.weserver.service;


import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import daddyok.weapp.config.EnvConfig;
import daddyok.weapp.utils.HttpHelper;

public class GetMaterialService {
	private String accessToken;
	public String getAccessToken()
	{
		return this.accessToken;
	}
	public void setAccessToken() {
		
		String urlStr ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		urlStr = urlStr.replace("APPID", EnvConfig.SERVER_APP_ID).replace("APPSECRET", EnvConfig.SERVER_APP_SECRET);
		JSONObject jsonObject =new JSONObject();
		try {
			jsonObject = HttpHelper.doGet(urlStr);
			
			this.accessToken = jsonObject.getString("access_token");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public JSONObject getMaterialList() {
		String urlString = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
		
		urlString = urlString.replace("ACCESS_TOKEN",getAccessToken());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "news");
		jsonObject.put("offset", 20);
		jsonObject.put("count", 20);
		JSONObject jsonRes = new JSONObject();
		try {
			System.out.println("urlString>>>>>>>"+urlString);
			
			jsonRes =HttpHelper.doPost(urlString, jsonObject);

			//4.解析结果

			if (jsonRes != null) {

				//4.1 错误消息处理
				if (!jsonRes.isNull("errcode")) {
					int errCode = jsonRes.getInt("errcode");
					String errMsg = jsonRes.getString("errmsg");
					System.out.println("获取永久素材列表失败 "+"errcode:"+errCode+", errmsg:"+errMsg);		

					//4.2 新增成功
				} else {
					System.out.println("获取永久素材列表成功 ");
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonRes;
	
	}
	public String GetMaterialUrl() {
		String  urlStr = "";
		String  titleStr = "";
		String  media_id = "";
		JSONObject resJson=getMaterialList();
		//System.out.println("jsonObject:"+resJson.get("item"));
		JSONArray itemArray = resJson.getJSONArray("item");
		for (int i = 0; i < itemArray.length(); i++) {
		   JSONObject item=itemArray.getJSONObject(i);
		   
           //从数组里取出数组
		   JSONObject content = item.getJSONObject("content");
		   media_id =item.getString("media_id");
		   //从Content里面取出图文Item
		   JSONArray news_item =  content.getJSONArray("news_item");
		   for (int j = 0; j < news_item.length(); j++) {
			   JSONObject news=news_item.getJSONObject(j);
                //从数组里取出数组
			   titleStr = news.getString("title");
			   urlStr = news.getString("url");
			   System.out.println("title:["+titleStr+"],mediaId:["+media_id+"],url["+urlStr+"]");
		   }
		}
		return urlStr;
	}
    public void getReturnData(String urlString) throws UnsupportedEncodingException {  
    	 CloseableHttpClient httpClient = HttpClients.createDefault();  
         HttpGet httpGet = new HttpGet(urlString);  
         httpGet.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");  
         // 执行请求  
         HttpResponse response;  
         String line;  
         try {  
   
             response = httpClient.execute(httpGet);  
             HttpEntity httpEntity = response.getEntity();  
             BufferedReader bufferedReader = null;  
             bufferedReader = new BufferedReader(new InputStreamReader(  
                     httpEntity.getContent(), "utf-8"), 8 * 1024);  
             StringBuilder entityStringBuilder = new StringBuilder();  
             while ((line = bufferedReader.readLine()) != null) {  
                 entityStringBuilder.append(line + "\n");  
             }  
         //  System.out.println(entityStringBuilder.toString());  
         // appendMethodB("f:/中医基础理论.html",entityStringBuilder.toString());  
             savaFile("/usr/local/tomcat/webapps/myweb/WXServer.html",entityStringBuilder.toString(),"UTF-8");  
   
         } catch (ClientProtocolException e) {  
             e.printStackTrace();  
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
    }  
    /** 
     * 保存文件 
     * @param fileName 文件名称：绝对路径 
     * @param content 要保存的内容 
     * @param format 以某种格式保存文件 
     */  
    public static void savaFile(String fileName, String content,String format) {  
        BufferedWriter rd=null;  
        OutputStream out=null;  
        File file = new File(fileName);  
        try {  
            out = new FileOutputStream(file);  
            rd = new BufferedWriter(new OutputStreamWriter(out,format));  
            rd.write(content);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(null!=rd){  
                try {  
                    rd.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if(null!=out){  
                try {  
                    out.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        }  
    }  
  
    /** 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void appendMethodB(String fileName, String content) {  
        FileWriter writer=null;  
        try {  
            writer = new FileWriter(fileName, false);  
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            writer.write(content);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(null!=writer){  
                    writer.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
              
        }  
    }  
}