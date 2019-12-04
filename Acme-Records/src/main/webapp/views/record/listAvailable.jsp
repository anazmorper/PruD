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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="records" requestURI="${requestURI}" id="row">
	
	<spring:message code="record.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="false">
		<a href="record/display.do?recordId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>
	
	<acme:column code="record.ticker" property="ticker" sortable="true" />
	
	<acme:column code="record.coverPhoto" property="coverPhoto"/>
	
	<acme:column code="record.retailPrice" property="retailPrice" sortable="true" />
	
	<acme:column code="record.lyrics" property="lyrics" />
	
	<acme:column code="record.attachments" property="attachments" />
	
	<acme:column code="record.type" property="type" sortable="true" />
	
	<acme:column code="record.artist.name" property="artist.name" sortable="true" />
	
	
<%-- 	 <spring:message code="record.artist.name" var="artist" />
	<display:column property="artist.name" title="${artist}" sortable="true" /> --%>
	
<%-- 		<spring:message code="record.artists" var="artistHeader"/>
		<display:column title="${artistHeader}">
		<a href="artist/listbyrecord.do?recordId=${row.id}">
		<spring:message code="record.artists" />
		</a>
	</display:column> --%>
	
</display:table>

<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="record.return" />
  </a>
 </div>
