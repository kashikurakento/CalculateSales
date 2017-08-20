<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/signupStyle.css" rel="stylesheet" type="text/css">
<script>
function disabledButton(btn){
	btn.disabled=true;
	btn.form.submit();
}
function check(){
	if(window.confirm('本当にログアウトしますか？')){ // 確認ダイアログを表示
		return true; // 「OK」時は送信を実行
	}
	else{ // 「キャンセル」時の処理
		return false; // 送信を中止
	}
}
</script>
<title>ユーザー新規登録</title>
</head>
<body>
	<div class="link">
		<a href="manage" class="manageLink">ユーザー管理に戻る</a>
		<form  method="POST" onClick="return check()" style="display: inline">
			<a href="logout" style="float:right;" class="logoutLink">ログアウト</a>
		</form><br />
	</div>
	<div class="signupForm">
	<h2><c:out value="ユーザー新規登録" /></h2>
	<div class="main-contents">
		<c:if test="${ not empty errorMessages }">
			<div style="color:red" class="errorMessages">
				<p class="errorMessage">
					<c:forEach items="${errorMessages}" var="message">
						<c:out value="${message}" /><br />
					</c:forEach>
				</p>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>


		<form method="post" action="signup" class="contact">

		<p>入力が完了したら「入力内容を登録」ボタンをクリックしてください。</p>

		<table >
			<tbody>
				<tr>
					<th><label for="name">名前</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td><input type="text" name="name" value="${user.name}" maxlength='10' id="name" size="15"><br>
					<span class="supplement">（10文字以内で入力してください）</span></td>
				</tr>
				<tr>
					<th><label for="loginId">ログインID</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td><input type="text" name="loginId" value="${user.loginId}" maxlength='20' id="loginId" size="20"><br>
					<span class="supplement">（半角英数字6文字以上20文字以下で入力してください）</span></td>
				</tr>
				<tr>
					<th><label for="password">パスワード</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td><input name="password" type="password" maxlength='20' id="password" size="20"><br>
					<span class="supplement">（記号または半角文字6文字以上20文字以下で入力してください）</span></td>
				</tr>
				<tr>
					<th><label for="checkPassword">確認用パスワード</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td><input name="checkPassword" type="password" maxlength='20' id="checkPassword" size="20"><br>
					<span class="supplement">（パスワードを再度入力してください）</span></td>
				</tr>
				<tr>
					<th ><label for="branch">支店</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td>
						<select name="branch" id="branch">
							<c:if test="${user.branchId == null}">
								<option value="" selected>選択してください</option>
							</c:if>
							<c:if test="${user.branchId != null}">
								<option value="">選択してください</option>
							</c:if>
							<c:forEach items="${branches}" var="branch" >
								<c:if test="${user.branchId == branch.id}">
									<option value="${branch.id}" selected>${branch.name}</option>
								</c:if>
								<c:if test="${user.branchId != branch.id}">
									<option value="${branch.id}">${branch.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th ><label for="position">部署/役職</label></th>
					<td class="required"><img src="css/required1.gif" alt="必須" width="26" height="15"></td>
					<td>
						<select name="position" id="position">
							<c:if test="${user.branchId == null}">
								<option value="" selected>選択してください</option>
							</c:if>
							<c:if test="${user.branchId != null}">
								<option value="">選択してください</option>
							</c:if>
							<c:forEach items="${positions}" var="position" >
								<c:if test="${user.positionId == position.id}">
									<option value="${position.id}" selected>${position.name}</option>
								</c:if>
								<c:if test="${user.positionId != position.id}">
									<option value="${position.id}">${position.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>

<p class="button"><input type="submit" value="入力した内容で登録" onClick="disabledButton(this)"></p><br /><br />

</form>

	</div>
</div>
</body>
</html>
