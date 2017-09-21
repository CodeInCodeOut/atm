<%@page contentType="text/html; charset=utf-8" %>


<html>

<head>
	<meta charset="utf-8" />
	<title>atm系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>

<h3>取钱页面</h3>
<h3>VIP客户：${loginUser.userName }</h3>
		卡号：${card.cardNum }
		余额：${card.balance }  
    <form action="/card/draw.do">
    	取钱金额：<input  id="amount" type="text" name="amount"><br>
    	<input  id="cardNum" type="hidden" name="cardNum" value="${card.cardNum }">
    	<input  id="userId" type="hidden" name="userId" value="${card.userId}">
   		<input type="hidden" name="action" value="draw">
   		<input type="button" value="确定" onclick="draw();"> &nbsp;&nbsp;&nbsp;
   		<a href="/index.jsp">返回</a>
    </form>
    <script type="text/javascript">
    	function draw() {
    		var cardNum = ${card.cardNum };
    		var amount = $('#amount').val();
     		var rule = /^\+?[1-9][0-9]*$/;
     		
     		if (!rule.test(amount)) {
     			return;
     		}
    		
    		$.ajax({
                url:'/card/draw.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	amount:$('#amount').val(),
                	cardNum:$('#cardNum').val(),
                	userId:$('#userId').val(),
                	
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
                    
                    window.location.href='/card/toCardDetail.do?cardNum=${card.cardNum}';
                    
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