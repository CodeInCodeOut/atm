<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>ATM系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>

<h1>银行卡详情</h1>
<h3>VIP客户：${loginUser.userName }</h3>
<img height="50px" width="50px" src="/read/readFile.do" onerror="javascript:this.src='/images/default.png'"><br>

	
    卡号：${card.cardNum }  
    余额：${card.balance }
    
    <a href="/card/toDeposit.do?cardId=${card.id }&cardNum=${card.cardNum }">存款</a>
    <a href="/card/toDraw.do?cardId=${card.id }&cardNum=${card.cardNum }">取款</a>
    <a href="/card/toTransfer.do?cardId=${card.id }&cardNum=${card.cardNum }">转账</a>
    <a href="/card/toAsynTransfer.do?cardId=${card.id }&cardNum=${card.cardNum }">异步转账</a>
    <a href="/export/exportCardDetail.do?cardId=${card.id }&cardNum=${card.cardNum }">导出明细</a>
 
    
    <form id="serachForm">
		<input type="hidden" name="cardId" value="${card.id }">
		<input id= "cardNum" type="hidden" name="cardNum" value="${card.cardNum }">
	</form>
    
    <table id="xxx">
   	</table>
   	
	    <h1 id="pageInfo"></h1>
	    <a href="###" onclick="firstPage();">首页</a>&nbsp;&nbsp;
	    <a href="###" onclick="prePage();">上一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="nextPage();">下一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="LastPage();">尾页</a>
    
    <script type="text/javascript" charset="utf-8">
    	var pageNum = 1;
    	var lastPage = 0;
    	var cardNum = ${card.cardNum }
    	
    	listDetails();
    	
    	function listDetails() {
    		$.ajax({
                url:'/card/cardDetailList.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	pageNum:pageNum,
                	cardNum:$('#cardNum').val(),
                	
                },
                timeout:5000,    //超时时间
                dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                beforeSend:function(xhr){
                    console.log(xhr)
                    console.log('发送前')
                },
                success:function(data,textStatus,jqXHR){
                    
                    if (!data.success) {
                    	alert(data.message);
                    	return;
                    }
                    
                    var details = data.data.data;
                    var msg = "<tr><td>ID</td><td>卡号</td><td>交易金额</td><td>交易类型</td><td>交易描述</td><td>银行卡状态</td><td>交易时间</td></tr>";
                    for (var i=0;i<details.length;i++) {
                    	var detail = details[i];
                    	msg += '<tr>';
                    	msg += '<td>' + detail.id + '</td>';
                    	msg += '<td>' + detail.cardNum + '</td>';
                    	msg += '<td>' + detail.amount + '</td>';
                    	msg += '<td>' + detail.optType + '</td>';
                    	msg += '<td>' + detail.flowDesc + '</td>';
                    	msg += '<td>' + detail.status + '</td>';
                    	msg += '<td>' + detail.createTime + '</td>';
                    	msg += '</tr>';
                    }
                    
                    $('#xxx').html(msg);
                    lastPage = data.data.lastPage;
                    pageNum = data.data.pageNum;
                    
                    $('#pageInfo').html('当前页'+data.data.pageNum+'/'+data.data.lastPage);
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
    	
    	function firstPage() {
    		pageNum = 1;
    		listDetails();
    	}
    	
    	function LastPage() {
    		pageNum = lastPage;
    		listDetails();
    	}
    	
    	function nextPage() {
    		if (pageNum + 1 > lastPage) {
    			return;
    		}
    		
    		pageNum += 1;
    		listDetails();
    	}
    	
    	function prePage() {
    		if (pageNum - 1 <= 0) {
    			return;
    		}
    		
    		pageNum -= 1;
    		listDetails();
    	}
    
    </script>
    
</body>

<footer>
1997-2017@copy
</footer>

</html>