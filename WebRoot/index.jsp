<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="refresh" content="0;URL=index.do" />
	</head>
	
	<%
		String error = (String)request.getAttribute("LOGIN_ERROR");
	%>
	<%
		if (null != error && !"".equals(error.trim())) {
			request.setAttribute("LOGIN_ERROR", error);
		}
		request.getRequestDispatcher("welcome.do").forward(request, response);
	%>
	<body>
	</body>
</html>