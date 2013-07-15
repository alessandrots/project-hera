<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-br">
<head>
	
	<title></title>
</head>


<body>

<h1>Test Servlet</h1>
<br />
<%String path_= request.getContextPath(); %>
<form action="<%=path_%>/project">
  	<input type="hidden" name="acao" value="cons">
  	<input type="submit" name="Enviar">
</form>
</body>
</html>