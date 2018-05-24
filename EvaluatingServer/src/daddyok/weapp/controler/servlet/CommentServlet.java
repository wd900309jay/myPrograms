package daddyok.weapp.controler.servlet;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import org.json.JSONObject;

import daddyok.weapp.service.CommentsService;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");

	    request.setCharacterEncoding("utf-8");
	    String action = request.getParameter("action");
	    JSONObject resJson=null;
	    PrintWriter out = response.getWriter(); 
	    CommentsService commentDeal= new CommentsService();
	    //获取商场所有评论信息
	    if(action.equals("getMarketComments"))
	    {
	    	String marketId = request.getParameter("marketId");
	    	
	    	try {
	    		String sqlStr = "select * from view_comment_detail where market_id='uniqueId'";
	    		sqlStr = sqlStr.replace("uniqueId", marketId);
			
	    		resJson = commentDeal.QueryComments(sqlStr);
				
	    		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	 
	    }
	    //查看个人评论信息
	    else if(action.equals("getUserComments"))
	    {
	    	String thirdKey = request.getParameter("thirdKey");
	    	System.out.println("thirdkey:"+thirdKey);
	    	try {
	    	
	    		/*根据thirdkey获取用户信息
	    		*1.CheckThirdKey()
	    		*2.GetUserId()
	    		*/
	    		String openid =commentDeal.GetOpenId(thirdKey);
	    		System.out.println("openId:"+openid);
	    		String sqlStr = "select * from view_comment_detail where open_id='openId'";
	    		sqlStr = sqlStr.replace("openId", openid);
			
	    		resJson = commentDeal.QueryComments(sqlStr);
				
				System.out.println("resJson>>>>>>>"+resJson);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    //发布评论内容
	    else if (action.equals("publishComment"))
	    {
	    	//我是谁
	    	String thirdKey = request.getParameter("thirdKey");
	    	//我评论哪个
	    	String marketId = request.getParameter("marketId");
	    	//我得评论信息
	    	String content = request.getParameter("content");
	    	System.out.println("content:"+content);
	    	//我得打分
	    	String totalStars = request.getParameter("totalStars");
	    	String envStars = request.getParameter("envStars");
	    	String devStars = request.getParameter("devStars");
	    	String airStars = request.getParameter("devStars");
	    	//String photoPathArray = request.getParameter("photoPathArray");
	    		/*根据thirdkey获取用户信息
	    		*1.CheckThirdKey()
	    		*2.GetUserId()
	    		*/
	    		//找到属于我的openid
	    		String openid =commentDeal.GetOpenId(thirdKey);
	    		System.out.println("openId:"+openid);
	    		
	    		
	    		String sqlStr=String.format("INSERT INTO comments_info(auth_openid,market_id,content,total_stars,env_stars,dev_stars,air_stars) VALUE('%s','%s','%s',%s,%s,%s,%s)", openid,marketId,content,totalStars,envStars,devStars,airStars);
	    		System.out.println("InsertSQL:"+sqlStr);
	    		resJson =new JSONObject();
	    		if(commentDeal.PublishComment(sqlStr)){
	    			
	    			resJson.put("publishResult", "success");
	    		}
	    		else {
	    			resJson.put("publishResult", "failure");
				}
		} 
	    else if (action.equals("giveThumbToComments"))
		{
	    	String commentIdArray = request.getParameter("commentIdArray");
	    	System.out.println("commentIdArray>>>>>>>"+commentIdArray);
//	    	String sqlStr="";
//	    	if(actionType.equals("like")){
//	    		sqlStr = String.format("UPDATE comments_info SET like_count=like_count+1 WHERE comment_id IN(%s)",commentIdArray);
//	    	}else if(actionType.equals("unlike")){
//	    		sqlStr = String.format("UPDATE comments_info SET like_count=like_count-1 WHERE comment_id IN(%s)",commentIdArray);
//			}
//	    	
//    		System.out.println("UpdateSQL:"+sqlStr);
//    		resJson =new JSONObject();
//    		if(commentDeal.PublishComment(sqlStr)){
//    			
//    			resJson.put("UpdateLikeCount", "success");
//    		}
//    		else {
//    			resJson.put("UpdateLikeCount", "failure");
//			}
		}
	    else if(action.equals("getStarLevel")){
			
	    	String marketId = request.getParameter("marketId");
	    	
	    	try {
		    	
	    		String sqlStr = "select * from view_comment_stars where market_id='marketId'";
	    		sqlStr = sqlStr.replace("marketId", marketId);
			
	    		resJson = commentDeal.QueryStarLevel(sqlStr);
				
				System.out.println("resJson>>>>>>>"+resJson);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    if(resJson.length()>0){
	    	out.print(resJson.toString());	
	    }
	    else {
	    	out.write("获取数据失败");	
		}
		out.flush();
		out.close();
	    

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
