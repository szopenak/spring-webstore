<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Produkty</title>
	</head>
	<body>
		<section>
		<div class="jumbotron">
		<a href="<c:url value="/j_spring_security_logout" />" class="btn btn-danger btn-mini pull-right">
			Wyloguj się
		</a>
		<div class="pull-right" style="padding-right:50px">
			<a href="?language=en_US" >angielski</a>|<a href="?language=nl">holenderski</a>|<a href="?language=pl">polski</a>
		</div>
		<div class="container">
			<h1>Produkty</h1>
			<p>Dodaj produkty</p>
		</div>
		</div>
		</section>
		<section class="container">
			<form:form modelAttribute="newProduct" class="form-horizontal" enctype="multipart/form-data">
				<fieldset>
					<legend>Dodaj nowy produkt</legend>
						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="productId">
							<spring:message code="addProduct.form.productId.label"/>
							</label>
								<div class="col-lg-10">
								<form:input id="productId" path="productId" type="text" class="form:input-large"/>
								</div>
						</div>
						<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="name">
						<spring:message code="addProduct.form.name.label"/>
						</label>
								<div class="col-lg-10">
								<form:input id="name" path="name" type="text" class="form:input-large"/>
								</div>
						</div>
						<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="unitPrice">
						<spring:message code="addProduct.form.unitPrice.label"/>
						</label>
								<div class="col-lg-10">
								<form:input id="unitPrice" path="unitPrice" type="text" class="form:input-large"/>
								</div>
						</div>
						<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="manufacturer">
						<spring:message code="addProduct.form.manufacturer.label"/>
						</label>
								<div class="col-lg-10">
								<form:input id="manufacturer" path="manufacturer" type="text" class="form:input-large"/>
								</div>
						</div>
						<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="category">
						<spring:message code="addProduct.form.category.label"/>
						</label>
								<div class="col-lg-10">
								<form:input id="category" path="category" type="text" class="form:input-large"/>
								</div>
						</div>

						<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="unitsInStock">
						<spring:message code="addProduct.form.unitsInStock.label"/>
						</label>
								<div class="col-lg-10">
								<form:input id="unitsInStock" path="unitsInStock" type="text" class="form:input-large"/>
								</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="descriptionID" >
							<spring:message code="addProduct.form.description.label"/>
							</label>
							<div class="col-lg-10">
							<form:textarea id="descriptionID" path="description" rows = "3" />
							</div>
						</div>

					<div class="form-group">
						<label class="control-label col-lg-2" for="condition">
						<spring:message code="addProduct.form.condition.label"/>
						</label>
						<div class="col-lg-10">
						<form:radiobutton path="condition" value="New" />New
						<form:radiobutton path="condition" value="Old" />Used
						<form:radiobutton path="condition" value="Refurbished" />Refurbished
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2" for="productImage">
						<spring:message code="addProduct.form.productImage.label"/>
						</label>
						<div class="col-lg-10">
						<form:input id="productImage" path="productImage" type="file" class="form:input-large" />
					</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
						<input type="submit" id="btnAdd" class="btn btn-primary" value ="Dodaj"/>
						</div>
					</div>
				</fieldset>
			</form:form>
		</section>
	</body>
</html>