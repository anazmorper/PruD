
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="records" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<c:set var="today" value="<%=new java.util.Date()%>"/>

	<spring:message code="record.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="false">
		<a href="manager/workProgramme/record/display.do?recordId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>
	
	 <acme:column code="record.ticker" property="ticker" sortable="false" />
	<acme:column code="record.coverPhoto" property="coverPhoto"/>
	<acme:column code="record.retailPrice" property="retailPrice" />
	<acme:column code="record.lyrics" property="lyrics" />
	<acme:column code="record.attachments" property="attachments"/>
	<acme:column code="record.type" property="type"/>		

	
	<spring:message code="record.artists" var="artistHeader"/>
	<display:column title="${artistHeader}">
		<a href="artist/listbyrecordSimple.do?recordId=${row.id}">
		<spring:message code="record.artists" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${(not fn:contains(mylist, row)) || (row.finalMode=='true')}">
			<display:column>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${(fn:contains(mylist, row)) && (row.finalMode=='false')}">
			<display:column>
				<a href="manager/workProgramme/record/edit.do?recordId=${row.id}"><spring:message code="record.edit"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<!-- Action links -->
	<jstl:if test="${puedeCrear=='true'}">
		<security:authorize access="hasRole('MANAGER')">
			<div>
				<a href="manager/workProgramme/record/create.do"> <spring:message
						code="record.create" />
				</a>
			</div>
		</security:authorize>
	</jstl:if>
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="record.return" />
  </a>
 </div>