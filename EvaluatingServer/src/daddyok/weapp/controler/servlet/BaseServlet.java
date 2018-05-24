package daddyok.weapp.controler.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//[4]处理编码问题
        resp.setContentType("text/html;utf-8");
        req.setCharacterEncoding("utf-8");
        //1.获取 methodName 参数，它是用户想调用的方法名称
        String methodName = req.getParameter("method");
        Method method = null;
      //2.通过方法名称获取 Method 对象
        try {
            //[3]使其子类能够调用自身的私有方法
            method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("您调用的  " + methodName + "（） 方法不存在", e);
        }
        //3.通过 Method 对象来调用它
        try {
            method.setAccessible(true);
            String result = (String) method.invoke(this, req, resp);
            //[2]简化转发和重定向功能
            /*考虑到用 f、r 分别代替 forward、redirect 对他人阅读代码可能会造成阅读障碍
            故，在此并未进行替代*/
            if (result != null && !result.trim().isEmpty()) {
                String[] strings = result.split(":");
                //子类中的转发格式 forward:/index.jsp
                if (strings[0].equals("forward")) {
                    req.getRequestDispatcher(strings[1]).forward(req, resp);
                    //子类中的重定向格式 redirect:/index.jsp
                } else if (strings[0].equals("redirect")) {
                    resp.sendRedirect(req.getContextPath() + strings[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
  

}
