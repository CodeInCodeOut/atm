<%@page contentType="text/html; charset=utf-8" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>atm系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>
<h3>转账页面</h3>
<h3>VIP客户：${loginUser.userName }</h3>
		卡号：${card.cardNum }
		余额：${card.balance }
<body>
    <form>
    	转账金额：<input id="amount" type="text" name="amount"><br>
    	转入账户：<input id="inCardNum" type="text" name="inCardNum"><br>
    		<input type="hidden" name="action" value="transfer">
    		<input id="outCardNum" type="hidden" name="outCardNum" value="${card.cardNum }">
    		<input  id="userId" type="hidden" name="userId" value="${card.userId}">
    		<input type="button" value="确定" onclick="transfer();"> &nbsp;&nbsp;&nbsp;
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="/index.jsp">返回主页</a>
    </form>
    
     <script type="text/javascript">
    	function transfer() {
    		var cardNum = ${card.cardNum };
    		var userId=${card.userId};
    		$.ajax({
                url:'/card/transfer.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	amount:$('#amount').val(),
                	inCardNum:$('#inCardNum').val(),
                	userId:$('#userId').val(),
                	outCardNum:$('#outCardNum').val(),
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