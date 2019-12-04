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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form:form action="${actionURL}" modelAttribute="purchase">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	
	
	<jstl:if test="${fn:length(records) > 0}">
	<spring:message code="purchase.record.title" />:
	<form:select id="records" path="record" >
		<jstl:forEach var="item" items="${records}">
            <form:option value="${item}">${item.title}</form:option>
		</jstl:forEach>	
	</form:select>
	</jstl:if>
	<jstl:if test="${fn:length(records) == 0}">
		<spring:message code="purchase.notrecords" /><br />
	</jstl:if>
	
	<br />
	
	<jstl:if test="${cc == null}">
	<fieldset>
		<legend>
			<spring:message code="purchase.creditCard" />
		</legend>
		<acme:textbox code="creditCard.holderName" path="creditCard.holderName" placeholder="Lorem Ipsum" />
		<br />

		<acme:selectString items="${creditCardBrands}" itemLabel="creditCard.brandName" code="creditCard.brandName" path="creditCard.brandName"/>
		<br />
		
		<acme:textbox code="creditCard.number" path="creditCard.number"	placeholder="NNNNNNNNNNNNNNNN" />
		<br />
		
		<acme:textbox code="creditCard.expirationMonth" path="creditCard.expirationMonth" placeholder="MM" />
		<br />
		
		<acme:textbox code="creditCard.expirationYear" path="creditCard.expirationYear" placeholder="YY"  />
		<br />
		
		<acme:textbox code="creditCard.cvvCode" path="creditCard.cvvCode" placeholder="NNN" />
		<br />
	</fieldset>
	<br />
	</jstl:if>
	
	<jstl:if test="${cc != null}">
	<fieldset>
		<legend>
			<spring:message code="purchase.creditCard" />
		</legend>
		<acme:textbox code="creditCard.holderName" path="creditCard.holderName" value="${cc.holderName}" />
		<br />
		
		<form:label path="creditCard.brandName">
		<spring:message code="creditCard.brandName" />:
		</form:label>
		<form:select id="creditCardBrands" path="creditCard.brandName">
		<jstl:forEach var="item" items="${creditCardBrands}">
            <form:option value="${item}">${item}</form:option>
		</jstl:forEach>	
		</form:select>
		<form:errors cssClass="error" path="creditCard.brandName" />
		<br/>
		
		<acme:textbox code="creditCard.number" path="creditCard.number"	value="${cc.number}" />
		<br />
		
		<acme:textbox code="creditCard.expirationMonth" path="creditCard.expirationMonth" value="${cc.expirationMonth}" />
		<br />
		
		<acme:textbox code="creditCard.expirationYear" path="creditCard.expirationYear" value="${cc.expirationYear}"  />
		<br />
		
		<acme:textbox code="creditCard.cvvCode" path="creditCard.cvvCode" value="${cc.cvvCode}" />
		<br />
	</fieldset>
	<br />
	</jstl:if>
	
	<jstl:if test="${fn:length(records) > 0}">
	<acme:submit name="save" code="purchase.save"/>
	</jstl:if>
	<acme:cancel url="customer/purchase/list.do" code="purchase.cancel" />
	

</form:form>
