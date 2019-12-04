
<%--
 * list.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Listing grid -->

<spring:message code="artist" />

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="artists" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<display:column title="${nameHeader}" sortable="false">
		<a href="artist/display.do?artistId=${row.id}"><jstl:out
				value="${row.name}" /></a>
	</display:column>
	<acme:column code="artist.photo" property="photo" sortable="false" />
	<acme:column code="artist.biography" property="biography"/>
	<acme:column code="artist.homePage" property="homePage" />
	
	
	<spring:message code="artist.record" var="artistHeader"/>
	<display:column title="${artistHeader}" >
	<a href="record/listbyartistaut.do?artistId=${row.id}">
		<spring:message code="artist.record" />
	</a>
	</display:column> 
	

<%-- 	<security:authorize access="hasRole('MANAGER')">
		
		<display:column>
		<jstl:if test="${row.finalMode=='false'}">
			<a href="record/manager/edit.do?recordId=${row.id}"><spring:message code="record.edit"/></a>
		</jstl:if>
		</display:column>
		
		
	</security:authorize>  --%>


</display:table>

<!-- Action links -->

<%-- <security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="record/manager/create.do"> <spring:message
				code="record.create" />
		</a>
	</div>
</security:authorize> --%>
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="artist.return" />
  </a>
 </div>