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

<form:form action="manager/workProgramme/activity/edit.do"  modelAttribute="activity">
	
	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="activity.title" path="title" />
	<br />
	
	<acme:textbox code="activity.description" path="description" />
	<br />
	
	<acme:textbox code="activity.startDate" path="startDate" />
	<br />
	
	<acme:textbox code="activity.endDate" path="endDate" />
	<br />
	
	<form:label path="status">
		<spring:message code="activity.status" />:
	</form:label>
	<form:select path="status">
		<form:options items="${status}" />
	</form:select>

	<br />
	<br />
	<!-- Buttons -->


	<acme:submit name="save" code="activity.save"/>

	<acme:cancel url="manager/workProgramme/listMyWorkProgrammes.do" code="activity.cancel" />
	
	<br/>
		

</form:form>