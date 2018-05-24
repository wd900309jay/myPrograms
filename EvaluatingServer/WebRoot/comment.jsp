<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'comment.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head> 
	<body>
	 <% 
  	String thirdKey ="";
  	if(request.getParameter("thirdkey")!=null)
	  {
	  	thirdKey = request.getParameter("thirdkey");
	  }
	  %>
	  <p><%=thirdKey %></p>
<form action="CommentsInfo" method="Get">
  <p>thirdKey:<input type="text" name="thirdKey" value="<%=thirdKey%>"/></p>
   	<p>action:<input type="text" name="action" value="getUserComments"/></p>
   	<input type="submit" value="我的点评"/>
   	</form>
   	
   	<form action="CommentsInfo" method="Get">
	  <p>thirdKey:<input type="text" name="thirdKey" value="<%=thirdKey%>"/></p>
	   	<p>action:<input type="text" name="action" value="publishComment"/></p>
	   		<p>action:<input type="text" name="uniqueId" value="CDSC_001"/></p>
	   			<p>action:<input type="text" name="content" value="这是最好的商场"/></p>
	   			<p>action:<input type="text" name="starLevel" value="4"/></p>
	   	<input type="submit" value="发表"/>
   	</form>
	</body>
</html>
