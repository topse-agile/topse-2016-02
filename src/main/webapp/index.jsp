<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main</title>
        <script type="text/javascript">
        <!--
        	function regist(){
        	alert("登録しました");
        	}
        -->
        </script>
        <script>
        <!--
        	function sysdate(){
        	//今日の日付データを変数hidukeに格納
        	var hiduke=new Date(); 
        	//年・月・日・曜日を取得する
        	var year = hiduke.getFullYear();
        	var month = hiduke.getMonth()+1;
        	var day = hiduke.getDate();
        	document.write(year + "/" + month + "/" + day);
        	}
        -->
        </script>
</head>
<body>

        <form action="./" method="POST">
        　　　　　　　 <span><script type="text/javascript">sysdate();</script></span>　
            <input type="text" name="arg1" value="<%= request.getAttribute("arg1") %>" />
            <input type="submit" value="登録" onClick="regist()"/>
        </form>

        <!--ul>
<%
List<String> history = (List<String>)request.getAttribute("history");
if (history != null) {
for (String item : history) {
%>
        <li><%= item %></li>
<%
}
}
 %>
        </ul-->
</body>
</html>