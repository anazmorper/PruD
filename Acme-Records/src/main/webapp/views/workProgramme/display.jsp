<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- Attributes -->

	<p>
		<b><spring:message code="workProgramme.codeName" /></b> :
		<jstl:out value="${workProgramme.codeName}" />
	</p>
	
	
	<p>
		<b><spring:message code="workProgramme.startDate" /></b> :
		<jstl:if test="${language=='en'}">
			<fmt:formatDate value="${workProgramme.startDate}" pattern="yyyy/MM/dd HH:mm" />
		</jstl:if>
		<jstl:if test="${language=='es'}">
			<fmt:formatDate value="${workProgramme.startDate}" pattern="dd/MM/yyyy HH:mm" />
		</jstl:if>
	</p>
	
	<p>
		<b><spring:message code="workProgramme.endDate" /></b> :
		<jstl:if test="${language=='en'}">
			<fmt:formatDate value="${workProgramme.endDate}" pattern="yyyy/MM/dd HH:mm" />
		</jstl:if>
		<jstl:if test="${language=='es'}">
			<fmt:formatDate value="${workProgramme.endDate}" pattern="dd/MM/yyyy HH:mm" />
		</jstl:if>
	</p>
	
	<br />
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${canDelete == true}">
			<div>
				<a href="workProgramme/manager/delete.do?workProgrammeId=${workProgramme.id}" onclick="javascript: return confirm('<spring:message code="workProgramme.confirm.delete" />')"><spring:message code="workProgramme.delete"/></a>  	
 			</div>
		</jstl:if>
	</security:authorize>
			
	<div>
	  	<a href="javascript:window.history.back()"> <spring:message
	    code="workProgramme.return" />
		</a>
	</div>

	