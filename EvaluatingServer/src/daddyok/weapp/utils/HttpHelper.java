package daddyok.weapp.utils;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

public class HttpHelper {

	public static JSONObject doGet(String url) throws Exception{
		JSONObject json=null;
		//生成请求
		HttpGet httpGet=new HttpGet(url);
		//配置
		RequestConfig rconfig=RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		httpGet.setConfig(rconfig);
		//发起请求，获取响应信息
		//创建HttpClient
		CloseableHttpClient httpClient= HttpClients.createDefault();
		CloseableHttpResponse response=null;

		try {
			//发起请求
			response= httpClient.execute(httpGet);
			//判断状态
			if(response.getStatusLine().getStatusCode() ==HttpStatus.SC_OK)
			{
				//解析请求结果
				HttpEntity entity=response.getEntity();
				if(entity !=null)
				{
					String resultString=EntityUtils.toString(entity, "utf-8");
					System.out.println("Get 请求结果："+resultString);
					
					json=new JSONObject(resultString);
					
					if(json.isNull("errcode")) {
						return json;
					}else if (0 == json.getInt("errcode")) {
						return json;
					}else {
						System.out.println("request url=" + url + ",return value=");
						System.out.println(resultString);
						int errCode = json.getInt("errcode");
						String errMsg = json.getString("errmsg");
						throw new Exception("error code:"+errCode+", error message:"+errMsg); 
					}
				}
			}
	 		
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if (response != null) try {
				response.close();                     //释放资源

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/** 2.发起POST请求
	 * @desc ：
	 *  
	 * @param url   请求url
	 * @param data  请求参数（json）
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject doPost(String url, Object data) throws Exception {
		//1.生成一个请求
		HttpPost httpPost = new HttpPost(url);

		//2.配置请求属性
		//2.1 设置请求超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		httpPost.setConfig(requestConfig);
		//2.2 设置数据传输格式-json
		httpPost.addHeader("Content-Type", "application/json");
		//2.3 设置请求实体，封装了请求参数
		StringEntity requestEntity = new StringEntity(JSONObject.valueToString(data), "utf-8");
		httpPost.setEntity(requestEntity);

		//3.发起请求，获取响应信息	
		//3.1 创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {


			//3.3 发起请求，获取响应
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}

			//获取响应内容
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				//System.out.println("POST请求结果："+resultStr);

				//解析响应内容
				JSONObject result = new JSONObject(resultStr);

				if(result.isNull("errcode")) {
					return result;
				}else if (0 == result.getInt("errcode")) {
					return result;
				}else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInt("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();              //释放资源

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
