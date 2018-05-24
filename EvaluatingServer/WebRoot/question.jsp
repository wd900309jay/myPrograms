<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'question.jsp' starting page</title>
    
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
	 	String urlStr = (String)request.getAttribute("urlStr");
	 	if(urlStr.isEmpty())
	 	{
	 		urlStr="https://mp.weixin.qq.com/s?__biz=MzI5Mzk4MDE5MQ==&mid=100001197&idx=1&sn=b0612140e233244d223e0c1a137dcaa9&chksm=6c6894dd5b1f1dcb408fc7fdfcbb0693f1db7a87c93f5fbf7d82e217b814d7ba50bcf4213a68#rd";
	 	}
	  %>
  		<iframe src="<%=urlStr %>" width=100% height=100% scrolling="yes" frameborder="0"></iframe>
  </body>
</html>
