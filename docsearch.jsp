<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >
<title>Doc Search</title>
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
          <input type="text" class="form-control" placeholder="Search" name="sqry">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div style="padding-left:10px;padding-right:10px;">
<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title">Search Results</h3>
  </div>
  <div class="panel-body">
<%@ page import="java.util.List" %>
<%@ page import="lucenePkg.Searcher" %>
<%
	String q = request.getParameter("sqry");
    List<String> docres = Searcher.search(q);
    		String stem = "stemmedcorpus";
    		for(String x : docres){
    			if(x.contains(stem)){
    				x=x.replace(stem, "alphanumcorpus");
    			}
    		%>
    			<a href="file://<%=x%>"><%= x %></a><br>
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