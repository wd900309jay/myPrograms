package daddyok.weapp.controler.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import daddyok.weapp.config.EnvConfig;
import daddyok.weapp.service.LoginService;


/**
 * Servlet implementation class LoginOnServlet
 */
@WebServlet("/LoginOnServlet")
public class LoginOnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginOnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//获取js_code
		String code=request.getParameter("code");
		//访问微信服务器获取Openid和sessionId
		String urlStr="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		urlStr =urlStr.replace("APPID", EnvConfig.APP_ID).replace("SECRET", EnvConfig.APP_SECRET).replace("JSCODE", code);
		System.out.println("url:"+urlStr);
		LoginService loginSer = new LoginService();
		PrintWriter out = response.getWriter(); 
		try {
			if(loginSer.SendGet(urlStr))
			{
				JSONObject thirdkey=loginSer.get_3rdSessionKey();
				//request.getRequestDispatcher("/comment.jsp?thirdkey="+thirdkey.toString()).forward(request,response);
				out.print(thirdkey);  
				out.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();  
		out = null;  
	}

}
