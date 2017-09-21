<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>ATM系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>

<h1>用户中心</h1>
	
	<c:if test="${not empty loginUser }">
	<h3>VIP客户：${loginUser.userName }</h3>
	<img height="50px" width="50px" src="/read/readFile.do" onerror="javascript:this.src='/images/default.png'">
	</c:if>
	<a href="/upload/toUpload.do">更换头像</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="/card/toOpenAccount.do">开户</a>&nbsp;&nbsp;
	<a href="/user/logout.do">注销</a>
	
	<table id="xxx">
   	</table>
  
   	
	    <h1 id="pageInfo"></h1>
	    <a href="###" onclick="firstPage();">首页</a>&nbsp;&nbsp;
	    <a href="###" onclick="prePage();">上一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="nextPage();">下一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="LastPage();">尾页</a>
    
    <script type="text/javascript">
    var pageNum = 1;
	var lastPage = 0;
	
	listCards();

    function deleteCard(cardNum) {
		if (confirm("你确信要删除银行卡吗？")) {
			$.ajax({
                url:'/card/deleteCard.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	cardNum:cardNum
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
                    
                    alert('删除成功');
                    firstPage();
                    
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
		} else {
			alert('删除失败');
		}
	}
	
    	
    	function listCards() {
    		$.ajax({
                url:'/card/listCard.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	pageNum:pageNum
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
                    
                    var cards = data.data.cardList;
                    var msg = "<tr><td>ID</td><td>卡号</td><td>余额</td><td>银行卡状态</td><td>创建时间</td><td>操作时间</td><td>操作</td></tr>";
                    for (var i=0;i<cards.length;i++) {
                    	var card = cards[i];
                    	msg += '<tr>';
                    	msg += '<td>' + card.id + '</td>';
                    	msg += '<td>' + card.cardNum + '</td>';
                    	msg += '<td>' + card.balance + '</td>';
                    	msg += '<td>' + card.status + '</td>';
                    	msg += '<td>' + card.createTime + '</td>';
                    	msg += '<td>' + card.modifyTime + '</td>';
                    	msg += '<td><a href="/card/toCardDetail.do?cardNum='+ card.cardNum +'">详情</a></td>';
                    	msg += '<td><a href="###" onclick="deleteCard('+card.cardNum+');">删除</a></td>';
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
    		listCards();
    	}
    	
    	function LastPage() {
    		pageNum = lastPage;
    		listCards();
    	}
    	
    	function nextPage() {
    		if (pageNum + 1 > lastPage) {
    			return;
    		}
    		
    		pageNum += 1;
    		listCards();
    	}
    	
    	function prePage() {
    		if (pageNum - 1 <= 0) {
    			return;
    		}
    		
    		pageNum -= 1;
    		listCards();
    	}
    
    </script>
   
</body>

<footer>
1997-2017@copy
</footer>

</html>