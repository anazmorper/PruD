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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="workProgramme/record/manager/editcreate.do"  modelAttribute="record">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="purchases" />
	<form:hidden path="ticker" />
	<form:hidden path="finalMode" value="false"/>



	<acme:textbox code="record.title" path="title" />
	<br />
	
	<acme:textbox code="record.coverPhoto" path="coverPhoto" placeholder="http:"  />
	<br />
	
	<acme:textbox code="record.retailPrice" path="retailPrice" placeholder="Ex: 15.0"/>
	<br />
	
	<acme:textbox code="record.lyrics" path="lyrics" />
	<br />
	
	<form:label path="attachments">
			<spring:message code="record.attachments" />:
	</form:label>
	<form:input code="record.attachments" path="attachments" placeholder="http:"/>
	<br />
	<br />
	<form:label path="type">
		<spring:message code="record.type" />:
	</form:label>
	<form:select path="type">
		<form:options items="${types}" />
	</form:select>
	<br />
	<br />
	
	<form:label path="artist">
		<spring:message code="record.artist.name" />:
	</form:label>
	<jstl:if test="${fn:length(artists) > 0}">
		<form:select path="artist">
			<form:options items="${artists}" itemValue="id"
				itemLabel="name" />/>
		</form:select>
	</jstl:if>
	<jstl:if test="${fn:length(artists) == 0}">
		<spring:message code="record.notartist" />
	</jstl:if>
	<br />
	<br />
	<!-- Buttons -->


	<acme:submit name="save" code="workProgramme.save"/>

	<acme:cancel url="workProgramme/manager/listMyWorkProgrammes.do" code="workProgramme.cancel" />
	
	<br/>
		

</form:form>