<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/settingStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function check(){
	if(window.confirm('本当にログアウトしますか？')){ // 確認ダイアログを表示
		return true; // 「OK」時は送信を実行
	}
	else{ // 「キャンセル」時の処理
		return false; // 送信を中止
	}
}
function double(btn){
btn.disabled=true;
}
</script>
<title>ユーザー編集</title>
</head>
<body>
	<form method="POST" onClick="return check()" style="display: inline">
		<a href="logout" style="float:right;">ログアウト</a>
	</form>
	<p class="header"><c:out value="　ユーザー編集　" /></p>
	<div class="header2"><c:out value="　ユーザー編集　" /></div><br><br>
	<div class="main-contents">
		<c:if test="${ not empty errorMessages }">
			<div style="color:red" class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>

		<form action="setting" method="post">
		<div class="settingForm">
<br>
			<label	for="name">名前</label>
			<input name="name" value="${editUser.name}" maxlength='10' class="text" /><h5>（10文字以内で入力してください）</h5><br />

			<label for="loginId">ログインID</label>
			<input name="loginId" value="${editUser.loginId}" maxlength='20'class="text"/><h5>（半角英数字6文字以上20文字以下で入力してください）</h5><br />

			<label	for="password">パスワード</label>
			<input name="password" 	type="password" maxlength='20' class="text"/><h5>（記号または半角文字6文字以上20文字以下で入力してください）</h5><br />

			<label for="checkPassword">パスワード(再入力)</label>
			<input name="checkPassword" type="password" maxlength='20' class="text"/> <br /><br />

						現在の支店:<c:out value="支点" />
				<br />
				変更後の支店:
				<SELECT name="branch" class="dropdown">
							<option value="${branch.id}" selected>支点</option>
				</SELECT>
				<br>
					現在の役職:<c:out value="役職" />
				<br />

				変更後の役職:
				<SELECT name="branch" class="dropdown">
							<option value="${branch.id}" selected>役職</option>
				</SELECT>
				<br /><br />
			</div>
			<br><br>

			<button type="submit" name="id" value="" class="settings" onClick="javascript:double(this)">入力した内容で編集</button><br /><br />
			<a class="setting" href="setting.jsp">編集</a>
			<a href="setting.jsp" class="stop">停止</a>
			<a href="setting.jsp" class="work">復活</a>
			<button type="submit" name="id" value="" class="work" class="work">復活</button>
		</form>

	</div>
</body>
</html>