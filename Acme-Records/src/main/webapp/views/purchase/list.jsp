
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="purchases" requestURI="${requestURI}" id="row">

	<!-- Attributes -->


	<spring:message code="purchase.moment" var="moment" />
	<display:column title="${moment}">
		<fmt:formatDate var="format" value="${row.moment}"
			pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>

	<spring:message code="purchase.creditCard" var="creditCard" />
	<display:column title="${creditCard}">
		<spring:message code="creditCard.holderName" />: ${row.creditCard.holderName}<br />
		<spring:message code="creditCard.brandName" />: ${row.creditCard.brandName}<br />
		<spring:message code="creditCard.number" />: ${row.creditCard.number}<br />
		<spring:message code="creditCard.expirationMonth" />: ${row.creditCard.expirationMonth}<br />
		<spring:message code="creditCard.expirationYear" />: ${row.creditCard.expirationYear}<br />
		<spring:message code="creditCard.cvvCode" />: ${row.creditCard.cvvCode}
	</display:column>

	<spring:message code="purchase.record.title" var="record" />
	<display:column property="record.title" title="${record}"
		sortable="true" />

</display:table>


<!-- Action links -->

<security:authorize access="hasRole('CUSTOMER')">
	<div>
		<a href="customer/purchase/create.do"> 
		<spring:message code="purchase.create" /></a>
	</div>
</security:authorize> 

<div>
	<a href="javascript:window.history.back()"> <spring:message
			code="purchase.return" />
	</a>
</div>