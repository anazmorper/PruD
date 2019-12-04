

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="artist/create.do" modelAttribute="artist">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="record" />



	<acme:textbox code="artist.name" path="name" />
	<br />

	<acme:textbox code="artist.biography" path="biography" />
	<br />

	<acme:textbox code="artist.photo" path="photo" placeholder="https://"/>
	<br />

	<acme:textbox code="artist.homePage" path="homePage" placeholder="https://"/>
	<br />

	<!-- Buttons -->


	<acme:submit name="save" code="artist.save" />

	<acme:cancel url="manager/listMyArtists.do"
		code="artist.cancel" />

	<br />


</form:form>