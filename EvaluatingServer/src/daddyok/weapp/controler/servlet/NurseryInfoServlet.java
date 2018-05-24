package daddyok.weapp.controler.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import daddyok.weapp.dao.NurseryDao;
import daddyok.weapp.entity.NurseriesInfo;

/**
 * Servlet implementation class NurseryInfoServlet
 */
@WebServlet("/NurseryInfoServlet")
public class NurseryInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NurseryInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html;charset=utf-8");          
        /* 设置响应头允许ajax跨域访问 */  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* 星号表示所有的异域请求都可以接受， */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String queryData = request.getParameter("queryData");
        if(queryData.equals("mapData"))
        {
        	
        }else if(queryData.equals("shopData")){
			
		}
        
        NurseryDao nurDao=new NurseryDao();
		List<NurseriesInfo> InfoList = nurDao.GetBasicInfo();
		JSONObject jObj= new JSONObject();
		PrintWriter out = response.getWriter(); 
	     //获取微信小程序get的参数值并打印
       
   
        System.out.println("queryData="+queryData);
        //返回值给微信小程序
		if(InfoList.isEmpty())
		{
	        out.write("数据库有问题");
		}else {
			jObj.put("nurseriesInfos", InfoList);
			//System.out.println(jObj.toString());
			out.write(jObj.toString());	
		}
		out.flush();
		out.close();
      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
