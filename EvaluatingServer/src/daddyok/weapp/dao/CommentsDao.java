package daddyok.weapp.dao;


import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.fabric.xmlrpc.base.Value;
import com.mysql.jdbc.PreparedStatement;

import daddyok.weapp.entity.CommentsEntity;



public class CommentsDao {
	CommentsEntity commentInfo = null;

	public JSONObject getCommentsFromDB(String sql) throws SQLException
	{
		System.out.println(sql);
		Connection conn=null;
		PreparedStatement psmt=null;
		List<CommentsEntity> InfoList=new ArrayList<CommentsEntity>();
		try {
			conn = DBDao.getConnection();
			psmt=(PreparedStatement)conn.prepareStatement(sql);
			ResultSet rSet=(ResultSet)psmt.executeQuery();
			System.out.println(rSet);
			while(rSet.next()){
				//System.out.println("这里是获取到数据");
				//评论内容
				String contentString = rSet.getString("Content");
				System.out.println("Content>>>>>>>>>"+contentString);
				//评论创建时间
				Timestamp Time = rSet.getTimestamp("create_time");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
				String createTime = df.format(Time);
				//评论星级
				int StarLevel = rSet.getInt("total_stars");
				System.out.println("total_stars>>>>>>>>>"+StarLevel);
				int EnvLevel = rSet.getInt("total_stars");
				System.out.println("env_stars>>>>>>>>>"+StarLevel);
				int DevLevel = rSet.getInt("total_stars");
				System.out.println("dev_stars>>>>>>>>>"+StarLevel);
				int AirLevel = rSet.getInt("total_stars");
				System.out.println("air_stars>>>>>>>>>"+StarLevel);
				//评论人得昵称
				String nickName = rSet.getString("nickName");
				//评论得点赞总数
				int LikeCount = rSet.getInt("like_count");
				System.out.println("nickName>>>>>>>>>"+nickName);
			
				//评论人得头像链接
				String avatarUrl = rSet.getString("avatarUrl");
				System.out.println("avatarUrl>>>>>>>>>"+avatarUrl);
				//
				int commentId = rSet.getInt("comment_id");
				System.out.println("commentId>>>>>>>>>"+commentId);
				
				
				commentInfo =  new CommentsEntity();
				commentInfo.setCommentId(commentId);
				commentInfo.setContent(contentString);
				commentInfo.setCreateTime(createTime);
				commentInfo.setStarLevel(StarLevel);
				commentInfo.setNickName(nickName);
				commentInfo.setAvataUrl(avatarUrl);
				commentInfo.setLikeCount(LikeCount);
				commentInfo.setEnvLevel(EnvLevel);
				commentInfo.setDevLevel(DevLevel);
				commentInfo.setAirLevel(AirLevel);
				
				InfoList.add(commentInfo);
			}
			conn.close();
			psmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(conn);
		}
		return WriteJsonObj(InfoList);
		
		
	}
	public JSONObject getStarLevelFromDB(String sql) throws SQLException
	{
		System.out.println(sql);
		Connection conn=null;
		PreparedStatement psmt=null;
		List<Float> InfoList=new ArrayList<Float>();
		try {
			conn = DBDao.getConnection();
			psmt=(PreparedStatement)conn.prepareStatement(sql);
			ResultSet rSet=(ResultSet)psmt.executeQuery();
			System.out.println(rSet);
			while(rSet.next()){
				//System.out.println("这里是获取到数据");
				
				float totalLevel = rSet.getFloat("total_stars");
				float envLevel = rSet.getFloat("env_stars");
				float devLevel = rSet.getFloat("dev_stars");
				float airLevel = rSet.getFloat("air_stars");
			
				
				InfoList.add(totalLevel);
				InfoList.add(envLevel);
				InfoList.add(devLevel);
				InfoList.add(airLevel);
			}
			conn.close();
			psmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(conn);
		}
		return WriteJsonfloatObj(InfoList);
		
		
	}
	
	public boolean InsertCommentToDB(String sql)
	{
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
	public JSONObject WriteJsonfloatObj(List<Float> infoList)
	{	
		JSONObject resJson= new JSONObject();
		try {
			
			if(infoList.isEmpty())
			{
		        System.out.println("数据库有问题");
			}
			
			resJson.put("starLevelInfo", infoList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resJson;
	}
	public JSONObject WriteJsonObj(List<CommentsEntity> infoList)
	{	
		JSONObject resJson= new JSONObject();
		try {
			
			if(infoList.isEmpty())
			{
		        System.out.println("数据库有问题");
			}
			
			resJson.put("commentsInfo", infoList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resJson;
	}
}
