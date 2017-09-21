<%@page contentType="text/html; charset=utf-8" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>atm系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>
<h2>开户页面</h2>
<h3>${loginUser.userName }</h3>
<body>
    <form>
    	开户存款：<input type="text" id= "amount" name="amount"><br>
    		<input type="hidden" name="action" value="openAccount">
    		<input type="button" value="确定" onclick="openAccount();"> &nbsp;&nbsp;&nbsp;
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="/index.jsp">返回主页</a>
    </form>
    
    <script type="text/javascript">
    	function openAccount() {
    		var amount = $('#amount').val();
     		var rule = /^\+?[1-9][0-9]*$/;
     		
     		if (!rule.test(amount)) {
     			return;
     		}
     		
    		$.ajax({
                url:'/card/openAccount.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	amount:$('#amount').val(),
                },
                timeout:5000,    //超时时间
                dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                beforeSend:function(xhr){
                    console.log(xhr)
                    console.log('发送前')
                },
                success:function(data,textStatus,jqXHR){
                    var obj = data;
                    alert(obj.success);
                    
                    if (!data.success) {
                    	alert(data.message);
                    	return;
                    }
                    
                    window.location.href='/user/toUserCenter.do';
                    
                },
                error:function(xhr,textStatus){
                    console.log('错误')
                    console.log(xhr)
                    console.log(textStatus)
                },
                complete:function(){
                    console.log('结束')
                }
            });
    	}
    	
    </script>
</body>

<footer>
1997-2017@copy
</footer>

</html>