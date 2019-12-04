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
	name="artist" requestURI="${requestURI}" id="row">
	
	<spring:message code="artist.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="false">
		<a href="artist/display.do?artistId=${row.id}"><jstl:out
				value="${row.name}" /></a>
	</display:column>
	 <acme:column code="artist.photo" property="photo" sortable="false" />
	<acme:column code="artist.biography" property="biography"/>
	<acme:column code="artist.homePage" property="homePage" />
	<acme:column code="artist.record" property="record.title" />	
	
	
	
	
</display:table>

<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="artist.return" />
  </a>
 </div>
