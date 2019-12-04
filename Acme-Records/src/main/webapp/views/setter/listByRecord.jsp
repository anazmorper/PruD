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
	name="setters" requestURI="${requestURI}" id="row">
	
	
	<%!String estilo;%>

	<jstl:choose>
		<jstl:when test="${row.tipoColor == 1}">
			<%=estilo = "estiloMenos1"%>
		</jstl:when>
		<jstl:when test="${row.tipoColor == 2}">
			<%=estilo = "estiloMenos2Mas1"%>
		</jstl:when>
		<jstl:when test="${row.tipoColor == 3}">
			<%=estilo = "estiloMas2"%>
		</jstl:when>
	</jstl:choose>

	
	<!-- Attributes -->

	<spring:message code="setter.ticker" var="tickerHeader" />
	<display:column title="${tickerHeader}" sortable="true" class="<%= estilo %>" > 
		<a href="setter/display.do?setterId=${row.id}" class="<%= estilo %>" ><jstl:out
				value="${row.ticker}" /></a>
	</display:column>

	
	
	 <acme:column code="setter.body" property="body" />
	 <acme:column code="setter.picture" property="picture" />
	 
	<jstl:if test="${language=='en'}">
	<spring:message code="setter.publicationMoment" var="publicationMomentHeader" />
	<display:column title="${publicationMomentHeader}" sortable="true">
	<fmt:formatDate value="${row.publicationMoment}" pattern="yy/MM/dd HH:mm" />	
	</display:column>		
	</jstl:if>

	<jstl:if test="${language=='es'}">
	<spring:message code="setter.publicationMoment" var="publicationMomentHeader" />
	<display:column title="${publicationMomentHeader}" sortable="true">
	<fmt:formatDate value="${row.publicationMoment}" pattern="dd-MM-yy HH:mm" />	
	</display:column>	
	</jstl:if>

</display:table>

<!-- Action links -->


	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="setter.return" />
  </a>
 </div>
