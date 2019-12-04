<%--
 * edit.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="administrator/editcreate.do"  modelAttribute="administrator">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />



	<acme:textbox code="actor.name" path="name" />
	<br />

	
	<acme:textbox code="actor.surname" path="surname" />
	<br />
	
	<acme:textbox code="actor.photo" path="photo" />
	<br />
	
	<acme:textbox code="actor.email" path="email" />
	<br />
	
	<acme:textbox code="actor.phoneNumber" path="phoneNumber" />
	<br />
	
	<acme:textbox code="actor.address" path="address" />
	<br />
	
	
	
	<div>
	<form:label readonly="true" path="userAccount.username">
		<spring:message code="administrator.username"/>: 
	</form:label>
	<form:input minlength="5" path="userAccount.username" required="true"/>
	<form:errors  path="userAccount.username"/><br />
	</div>
	<br/>
	
	
	
	
	<form:label path="userAccount.password">
		<spring:message code="actor.password"/>: 
	</form:label>
	<form:password path="userAccount.password" required="true" />
	<form:errors  path="userAccount.password"/><br />
	<br />
	
	

	<!-- Buttons -->
		
	<acme:submit name="save"  code="actor.save" />

	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel"/>"
		onclick="javascript: window.location.replace('welcome/index.do')" />

</form:form>