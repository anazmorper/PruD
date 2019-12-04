<%--
 * edit.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="customer/editCC.do"  modelAttribute="customer">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="purchases" />
	<form:hidden path="userAccount" />
	<form:hidden path="name" />
	<form:hidden path="surname" />
	<form:hidden path="photo" />
	<form:hidden path="email" />
	<form:hidden path="phoneNumber" />
	<form:hidden path="address" />
	
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
	
	<%-- <div>
	<form:label path="userAccount.username">
		<spring:message code="actor.username" />
	</form:label>	
	<form:input minlength="5" path="userAccount.username" required="true" />	
	<form:errors path="userAccount.username" cssClass="error" />
</div>	
	
	
	<form:label path="userAccount.password">
		<spring:message code="actor.password"/>: 
	</form:label>
	<form:password path="userAccount.password" required="true"/>
	<form:errors  path="userAccount.password"/><br />
	<br /> --%>

	<!-- Buttons -->
		
	
	<input type="submit" name="save"
		value="<spring:message code="actor.save" />" />
	

	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel"/>"
		onclick="javascript: window.location.replace('welcome/index.do')" />
		
	<br/>

</form:form>