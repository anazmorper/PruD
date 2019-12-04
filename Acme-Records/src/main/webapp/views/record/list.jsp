
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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="record" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	

	<spring:message code="record.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="false">
		<a href="record/display.do?recordId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>
	
	 <acme:column code="record.ticker" property="ticker" sortable="false" />
	<acme:column code="record.coverPhoto" property="coverPhoto"/>
	<acme:column code="record.retailPrice" property="retailPrice" />
	<acme:column code="record.lyrics" property="lyrics" />
	<acme:column code="record.attachments" property="attachments"/>
	<acme:column code="record.type" property="type"/>		

	
	<spring:message code="record.artist.name" var="artistHeader"/>
	<display:column title="${artistHeader}">
		<a href="artist/listbyrecord.do?recordId=${row.id}">
		<jstl:out value="${row.artist.name}" />
		<%-- <spring:message code="record.artists" />  --%>
		</a>
	</display:column> 
	
	
<%-- 	<security:authorize access="hasRole('MANAGER')">
	
	<spring:message code="sponsorship.create" var="Create" />
	<display:column title="${Create}" sortable="true">
	<jstl:if test="${row.finalMode==true && (today1 lt row.endDate)}">
		<spring:url value="sponsorship/sponsor/create.do" var="createURL">
			<spring:param name="conferenceId" value="${row.id}" />
		</spring:url>
		<a href="${createURL}"><spring:message code="sponsorship.create" /></a>
	</jstl:if>
	<jstl:if test="${row.finalMode==false || !(today1 lt row.endDate)}">
		<spring:message code="sponsorship.cannot.create" />
	</jstl:if>
	</display:column>
</security:authorize> --%>
	

	<security:authorize access="hasRole('MANAGER')">
		
		<display:column>
		<jstl:if test="${row.finalMode=='false'}">
			<a href="manager/record/edit.do?recordId=${row.id}"><spring:message code="record.edit"/></a>
		</jstl:if>
		</display:column>
		
		
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">	
		<display:column>			
					<a href="administrator/setter/create.do?recordId=${row.id}"> <spring:message
					code="setter.create" /></a>
		</display:column>
	</security:authorize>
	
	<spring:message code="record.setters" var="settersHeader"/>
	<display:column title="${settersHeader}">
		<a href="setter/listByRecord.do?recordId=${row.id}">
		<spring:message code="record.setters" />
		</a>
	</display:column>

</display:table>

<!-- Action links -->

	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="record.return" />
  </a>
 </div>