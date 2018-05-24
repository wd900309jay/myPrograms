package daddyok.weapp.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import daddyok.weapp.entity.NurseriesInfo;

public class NurseryDao {
	NurseriesInfo nInfo=null;
	private String sqlStr="";
	public List<NurseriesInfo> GetBasicInfo() {
		sqlStr="select * from nurseries_info";
		Connection conn=null;
		PreparedStatement psmt=null;
		List<NurseriesInfo> InfoList=new ArrayList<NurseriesInfo>();
		try {
			conn = DBDao.getConnection();
			psmt=(PreparedStatement)conn.prepareStatement(sqlStr);
			ResultSet rSet=(ResultSet)psmt.executeQuery();
			while(rSet.next()){
				//System.out.println("这里是获取到数据");
				String maket_name = rSet.getString("market_name");
				String detect_status = rSet.getString("detect_status");
				String langitude=rSet.getString("langitude");
				String latitude = rSet.getString("latitude");
				String market_address = rSet.getString("market_address");
				String market_phone_num=rSet.getString("market_phone_num");
				String advertorial_link = rSet.getString("advertorial_link");
				String uniqueId = rSet.getString("nursery_id");
				
				NurseriesInfo nInfo= new NurseriesInfo();
				nInfo.setMarket_name(maket_name);
				nInfo.setDetect_status(detect_status);
				nInfo.setLangitude(langitude);
				nInfo.setLatitude(latitude);
				nInfo.setMarket_address(market_address);
				nInfo.setMarket_phone_num(market_phone_num);
				nInfo.setAdvertorial_link(advertorial_link);
				nInfo.setUniqueId(uniqueId);
				
				InfoList.add(nInfo);
			}
			conn.close();
			psmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(conn);
			
		}
		return InfoList;
	}
	
}
