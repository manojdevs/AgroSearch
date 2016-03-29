<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AgroSearch</title>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="search.html">Agrosearch</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="search.html">Home <span class="sr-only">(current)</span></a></li>
        <li><a href="#">About</a></li>
      </ul>
      <form class="navbar-form navbar-right" role="search" action="docsearch.jsp" method="GET">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Doc Search" name="sqry">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<%@ page import="Search.SearchMain" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="lucenePkg.Searcher" %>
<div style="padding-left:10px;padding-right:10px;">
<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title">Search Results</h3>
  </div>
  <div class="panel-body">
<% 
	List<String> res =new ArrayList<String>();
	Set<String> set = new TreeSet<String>();
	String q = request.getParameter("sqry");
	String ra = request.getParameter("optradio");
	if(ra.equals("google")){
		res=SearchMain.search(q, 1);
	}
	else if(ra.equals("bing")){
		res=SearchMain.search(q, 2);
	}
	else if(ra.equals("yahoo")){
		res = SearchMain.search(q, 3);
	}
	else if(ra.equals("meta")){
		res = SearchMain.search(q, 4);
	}
	else if(ra.equals("doc")){
		res = SearchMain.search(q, 5);
	}
	set.addAll(res);
	res = new ArrayList<String>(set);
	
	for(String r : res){%>
		
    <a href="<%=r %>"><%=r%></a><br>
		
	<%}
%>
</div>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" ></script>
</body>
</html>