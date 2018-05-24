package daddyok.weapp.dao;

import java.sql.Connection;

import org.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

import daddyok.weapp.entity.UserInfo;

public class UserInfoDao {
	private UserInfo userInfoEntity = null;
	
	public void ParseUserInfo(String userInfo) {
		JSONObject jsonObj = new JSONObject(userInfo);
		userInfoEntity = UserInfo.BuildFromJson(jsonObj);
		String sqlStr="INSERT INTO user_info(open_id,nickName,city,province,country,avatarUrl,gender) VALUE('value_1','value_2','value_3','value_4','value_5','value_6',value_7) ON DUPLICATE KEY UPDATE nickName='value_2',city='value_3',province='value_4',country='value_5',avatarUrl='value_6',gender=value_7";
		sqlStr = sqlStr.replace("value_1", userInfoEntity.getOpenId())
				.replaceAll("value_2", userInfoEntity.getNickName())
				.replaceAll("value_3", userInfoEntity.getCity())
				.replaceAll("value_4", userInfoEntity.getCountry())
				.replaceAll("value_5", userInfoEntity.getProvince())
				.replaceAll("value_6", userInfoEntity.getAvatarUrl())
				.replaceAll("value_7", userInfoEntity.getGender().toString());
		System.out.println("UserInfo_sqlStr>>>>>"+sqlStr);
				
		if(!InsertUserInfoToDB(sqlStr))
		{
			System.out.println("用户数据入库出错");
		}
	
	}

	private boolean InsertUserInfoToDB(String sql) {
		
		boolean result = false;
		System.out.println(sql);
		Connection conn=null;
		PreparedStatement psmt=null;
		try {
			conn = DBDao.getConnection();
			psmt=(PreparedStatement)conn.prepareStatement(sql);
			
			if(psmt.executeUpdate(sql)>0){
				System.out.println("评论数据入库成功");
				result = true;
			}else {
				System.out.println("评论数据入库失败");
				result =  false;
			}
			conn.close();
			psmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(conn);
			
		}
		return result;
	} 
}
