<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/loginStyle.css" rel="stylesheet" type="text/css">
<title>ログイン</title>
</head>
<body>
<div class="link">リンクです</div>
	<div class="login">
		<p class="login-header">　ログイン　</p><br>




		<form action="login.jsp" method="post" class="login-container">

						<div style="color:#ff6161" class="errorMessages">
				<ul>
						エラーメッセージです
				</ul>
			</div>

			<label for="loginId">ログインID</label>
			<p class="loginId"><input name="loginId"  value="${loginId }" placeholder="login id"/></p><br />

			<label for="password">パスワード</label>
			<p class="password"><input name="password" type="password" placeholder="password"/></p><br />

			<p class="submit"><input type="submit" value="ログイン" /></p><br />

		</form>

<div class="commentNumber">コメント○○件</div>
	<div class="container">
<div class="top">
</div>
<label for="btn">コメント表示/非表示</label>
<input id="btn" type="checkbox" >
<div class="btm">
<p>相手のいいたいことをきいてあげようという思いやり。<br>
相手の悪いところを大目にみてあげようという寛大さ。<br>
自分の気持ちや心を言葉で表すこと。<br>
あきらめずお互いに努力すること。</p>
</div>
<!--container--></div>
	</div>
</body>
</html>
