<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/additional-methods.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/additional-methods.min.js"></script>
<title>Add Restaurant</title>
</head>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">GULP!</a>
	    </div>
	    <div>
	      <ul class="nav navbar-nav">
	      	<li><a href="RestaurantList">Restaurant List</a></li>
	      	<li><a href="/GulpAssignment/RegisterRestaurant.jsp">Register a Restaurant</a></li>
    	    <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${reviewer_name}<span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="Profile">Profile</a></li>
		            <li role="separator" class="divider"></li>
       				<li><a href="Logout">Log out</a>
		          </ul>
	        </li>
			


	      </ul>
	    </div>
	  </div>
	</nav>
	<div class= "panle panel-primary col-sm-6 col-sm-offset-3">
		<div class ="panel-heading">
			Add Restaurant
		</div>
		
		<div class="panel-body">
			<form role="form" action="AddRestaurant" method = "POST">
				<div class="form-group">
					<label for="name">Name:</label>
					<input type="text" class="form-control" name="name"/>
				</div>
				
				<div class="form-group">
					<label for="name">Address:</label>
					<input type="text" class="form-control" name="name"/>
				</div>
				
				<div class="form-group">
					<label for="name">Description:</label>
					<input type="text" class="form-control" name="name"/>
				</div>
				
				<button type="submit" value = "submit" class= "button btn-primary">Add Restaurant</button>
			</form>
		</div>
	</div>
</body>
</html>