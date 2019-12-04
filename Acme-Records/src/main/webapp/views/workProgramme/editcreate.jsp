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

<form:form action="manager/workProgramme/editcreate.do"  modelAttribute="workProgramme">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="activities" />
	<form:hidden path="records" />


	<acme:textbox code="workProgramme.codeName" path="codeName" />
	<br />
	
	<acme:textbox code="workProgramme.startDate" path="startDate" placeholder="yyyy/MM/dd HH:mm"/>
	<br />
	
	<acme:textbox code="workProgramme.endDate" path="endDate" placeholder="yyyy/MM/dd HH:mm"/>
	<br />

	
	<!-- Buttons -->


	<acme:submit name="save" code="workProgramme.save"/>

	<acme:cancel url="manager/workProgramme/listMyWorkProgrammes.do" code="workProgramme.cancel" />
	
	<br/>
		

</form:form>