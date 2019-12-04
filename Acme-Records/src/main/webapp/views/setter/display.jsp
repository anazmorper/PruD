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
		<b><spring:message code="setter.ticker" /></b> :
		<jstl:out value="${setter.ticker}" />
	</p>
	
	<p>
		<b><spring:message code="setter.body" /></b> :
		<jstl:out value="${setter.body}" />
	</p>

	<p>
		<b><spring:message code="setter.picture" /></b> :
		<jstl:out value="${setter.picture}" />
	</p>
	
	<p>
		<jstl:if test="${language=='en'}">		
		<b><spring:message code="setter.publicationMoment" /></b> :
		<fmt:formatDate value="${setter.publicationMoment}" pattern="yy/MM/dd HH:mm" />
		</jstl:if>
		
		<jstl:if test="${language=='es'}">		
		<b><spring:message code="setter.publicationMoment" /></b> :
		<fmt:formatDate value="${setter.publicationMoment}" pattern="dd-MM-yy HH:mm" />
		</jstl:if>		
	</p>
	
					
	<br></br>		
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="setter.return" />
  </a>
 </div>
