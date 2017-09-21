<%@page contentType="text/html; charset=utf-8" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>note系统</title>
</head>

<body>
    <form name="upform" action="/photo/toUpPhoto.do" method="post" enctype="multipart/form-data"> 
		头像： <input type ="file" name="file1"/><br/>
		说明： <input type ="text" name="desc" /><br/> 
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="hidden" name="action" value="upPhoto">
		<input type="submit" value="上传" /> <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="reset" value="重置"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="/index.jsp">返回主页</a>
	</form> 
</body>

<footer>
1997-2017@copy
</footer>

</html>