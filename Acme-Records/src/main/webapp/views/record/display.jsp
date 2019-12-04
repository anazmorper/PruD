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
		<b><spring:message code="record.title" /></b> :
		<jstl:out value="${record.title}" />
	</p>
	
	
	<p>
		<b><spring:message code="record.ticker" /></b> :
		<jstl:out value="${record.ticker}" />
	</p>
	
	<p>
		<b><spring:message code="record.coverPhoto" /></b> :
		<jstl:out value="${record.coverPhoto}" />
	</p>
	
	<p>
		<b><spring:message code="record.retailPrice" /></b> :
		<jstl:out value="${record.retailPrice}" />
	</p>
	
	<p>
		<b><spring:message code="record.lyrics" /></b> :
		<jstl:out value="${record.lyrics}" />
	</p>
	
	<p>
		<b><spring:message code="record.attachments" /></b> :
		<jstl:out value="${record.attachments}" />
	</p>
	
	<p>
		<b><spring:message code="record.type" /></b> :
		<jstl:out value="${record.type}" />
	</p>
	
	<p>
		<b><spring:message code="record.artist.name" /></b> :
		<jstl:out value="${record.artist.name}" />
	</p>
	
	<br />
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${(canDelete == true) && (record.finalMode=='false')}">
			<div>
			  	<a href="workProgramme/record/manager/delete.do?recordId=${record.id}" onclick="javascript: return confirm('<spring:message code="record.confirm.delete" />')"><spring:message code="record.delete"/></a>  	
			</div>
		</jstl:if>
	</security:authorize>
					
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="customer.return" />
  </a>
 </div>