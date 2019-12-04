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



<form:form action="personaldata/actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	
	
	<div>
		<form:label path="name">
			<spring:message code="actor.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
	</div>

	<div>
		<form:label path="surname">
			<spring:message code="actor.surname" />:
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
	</div>
	
	<div>
		<form:label path="phoneNumber">
			<spring:message code="actor.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber" />
	</div>
	
	<div>
		<form:label path="email">
			<spring:message code="actor.email" />:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
	</div>
	
	<div>
		<form:label path="address">
			<spring:message code="actor.address" />:
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
	</div>
	
	<div>
		<form:label path="vat">
			<spring:message code="actor.vat" />:
		</form:label>
		<form:input path="vat" />
		<form:errors cssClass="error" path="vat" />
	</div>
	
	<div>
		<form:label path="photo">
			<spring:message code="actor.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
	</div>
	
	<acme:textbox code="actor.username" path="userAccount.username" />
	<br />
	
	
	<form:label path="userAccount.password">
		<spring:message code="actor.password"/>: 
	</form:label>
	<form:password path="userAccount.password"/>
	<form:errors  path="userAccount.password"/><br />
	<br />


	<!-- Buttons -->
	<input type="submit" name="save" value="<spring:message code = "actor.save"/>" />
	<%-- <acme:submit name="${actorTye}.save" code="actor.save" />  --%>
	<acme:cancel url="/" code="actor.cancel" />
	<br />
	<a href="personaldata/actor/deleteActor.do?actorId=${actor.id}" onclick="javascript: return confirm('<spring:message code="actor.confirm.delete" />')"><spring:message code="actor.delete"/></a>
</form:form>
