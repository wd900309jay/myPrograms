package daddyok.weapp.service;



import java.sql.SQLException;
import java.util.Set;

import javax.faces.flow.builder.ReturnBuilder;

import org.json.JSONObject;

import daddyok.weapp.dao.CommentsDao;
import daddyok.weapp.utils.RedisUtil;

public class CommentsService {

	public CommentsService() {
		// TODO Auto-generated constructor stub

	}
	public JSONObject QueryComments(String sql) throws SQLException {
		// TODO Auto-generated method stub
		//查询数据库
		CommentsDao commenstDao = new CommentsDao();
		return commenstDao.getCommentsFromDB(sql);

	}
	public JSONObject QueryStarLevel(String sql) throws SQLException {
		// TODO Auto-generated method stub
		//查询数据库
		CommentsDao commenstDao = new CommentsDao();
		return commenstDao.getStarLevelFromDB(sql);

	}
	public boolean PublishComment(String sql) 
	{
		// TODO Auto-generated method stub
				//查询数据库
		CommentsDao commenstDao = new CommentsDao();
		return commenstDao.InsertCommentToDB(sql);
	}
	public String GetOpenId(String thirdKey) 
	{
		String openid =RedisUtil.getInstance().hash().hget(thirdKey, "openid");
		System.out.println(openid);
		return openid;
	
	}
}
