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
		<b><spring:message code="manager.name" /></b> :
		<jstl:out value="${manager.name}" />
	</p>


	<p>
		<b><spring:message code="manager.surname" /></b> :
		<jstl:out value="${manager.surname}" />
	</p>
	
	<p>
		<b><spring:message code="manager.photo" /></b> :
		<jstl:out value="${manager.photo}" />
	</p>
	
	<p>
		<b><spring:message code="manager.email" /></b> :
		<jstl:out value="${manager.email}" />
	</p>
	
	<p>
		<b><spring:message code="manager.phoneNumber" /></b> :
		<jstl:out value="${manager.phoneNumber}" />
	</p>
	
	<br />
	
					
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="manager.return" />
  </a>
 </div>