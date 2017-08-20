<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/messageStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function check(){
	if(window.confirm('本当にログアウトしますか？')){ // 確認ダイアログを表示
		return true; // 「OK」時は送信を実行
	}
	else{ // 「キャンセル」時の処理
		return false; // 送信を中止
	}
}
function disabledButton(btn){
	btn.disabled=true;
	btn.form.submit();
}
function CountDownLength( idn, str, mnum ) {
	   document.getElementById(idn).innerHTML = "残り" + (mnum - str.length) + "文字";
	}
</script>
<title>新規投稿</title>
</head>
<body>
	<div class="link">
	<a href="./" class="homeLink">ホームへ戻る</a>
		<form method="POST" onClick="return check()" style="display: inline">
			<a href="logout" class="logoutLink" style="float:right;">ログアウト</a>
		</form>
	</div>
	<div class="form">
	<h2>新規投稿</h2>
	<c:if test="${ not empty errorMessages }">
		<div style="color:red" class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<c:out value="${message}" /><br />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session" />
	</c:if>

	<form action="message" method="post" class="messageForm">

		<label for="title" ><b>件名</b></label><br />
		<input name="title" value="${message.title }" maxlength='30'/><h5 style="display: inline">（30文字以内で入力してください）</h5><br />

		<label for="category"><b>カテゴリーを入力または選択</b></label><br />
		<input name="category" value="${message.category }" maxlength='10' placeholder="カテゴリーを入力"/><h5 style="display: inline">（10文字以内で入力してください）</h5><br />

		<label for="selectCategory"></label>
		<SELECT name="selectCategory">
			<option value="" selected>カテゴリーを選択</option>
			<c:forEach items="${categories}" var="category">
				<c:if test="${selectCategory == category.category }">
					<option  value="${category.category}" selected><c:out value="${category.category}"></c:out></option>
				</c:if>
				<c:if test="${selectCategory != category.category }">
					<option  value="${category.category}" ><c:out value="${category.category}"></c:out></option>
				</c:if>
			</c:forEach>
		</SELECT><br />

		<label for="text"><b>本文</b></label><h5 style="display: inline">（1000文字以内で入力してください）</h5><br />
		<textarea style="resize:none" name="text" rows="7" cols="100" class="tweet-box" maxlength="1000" onkeyup="CountDownLength( 'cdlength' , value , 1000 );">${message.text }</textarea><br />
		<input type="submit" value="投稿する" onClick="disabledButton(this)"><div id="cdlength" style="display: inline">残り1000文字</div><br /><br /><br />
	</form>

	<div class="header">

	</div>
	</div>
</body>
</html>