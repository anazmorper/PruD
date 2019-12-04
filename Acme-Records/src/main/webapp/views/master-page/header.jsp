<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme Records Co., Inc." width="989" height="207"/></a>
</div>


<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.admin.dashboard" /></a></li> 				
					<li><a href="administrator/downloadCSV.do"><spring:message code="master.page.author.export" /></a></li>
					<li><a href="administrator/setter/list.do"><spring:message code="master.page.customer.setter" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="message/administrator/send.do"><spring:message code="master.page.messageSend" /></a></li>
					<li><a href="message/administrator/sendBroadcast.do"><spring:message code="master.page.messageSendBroadcast" /></a></li>
					<li><a href="message/administrator/list.do"><spring:message code="master.page.messageList" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="administrator/create.do"><spring:message code="master.page.createadmin" /></a></li>
			<li><a class="fNiv" href="adminconfig/administrator/edit.do"><spring:message code="master.page.adminconfig" /></a></li>
			<li><a class="fNiv" href="record/list.do"><spring:message code="master.page.record.list"/></a></li>
			<li><a class="fNiv" href="artist/list.do"><spring:message code="master.page.artist.list"/></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
				
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/downloadCSV.do"><spring:message code="master.page.customer.export" /></a></li>
					<li><a href="customer/editCC.do"><spring:message code="master.page.customer.editCC" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="customer/purchase/list.do"><spring:message code="master.page.customerPurchase" /></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="message/customer/send.do"><spring:message code="master.page.messageSend" /></a></li>
					<li><a href="message/customer/list.do"><spring:message code="master.page.messageList" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="record/list.do"><spring:message code="master.page.record.list"/></a></li>
			<li><a class="fNiv" href="customer/record/listrecord.do"><spring:message code="master.page.artist.listrecord"/></a></li>
			<li><a class="fNiv" href="artist/list.do"><spring:message code="master.page.artist.list"/></a></li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
				<li><a href="manager/downloadCSV.do"><spring:message code="master.page.customer.export" /></a></li>
				<li><a class="fNiv"><spring:message	code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="message/manager/send.do"><spring:message code="master.page.messageSend" /></a></li>
					<li><a href="message/manager/list.do"><spring:message code="master.page.messageList" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="record/list.do"><spring:message code="master.page.record.list"/></a></li>
			<li><a class="fNiv" href="artist/list.do"><spring:message code="master.page.artist.list"/></a></li>
			<li><a class="fNiv" href="manager/listMyArtists.do"><spring:message code="master.page.artist.listbyactor"/></a></li>
<%-- 			<li><a class="fNiv" href="manager/workProgramme/list.do"><spring:message code="master.page.workProgramme.list"/></a></li>
 --%>		<li><a class="fNiv" href="manager/workProgramme/listMyWorkProgrammes.do"><spring:message code="master.page.workProgramme.listMyWorkProgrammes"/></a></li>
			
		</security:authorize>
		
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/create.do"><spring:message code="master.page.create" /></a></li>
			<li><a class="fNiv" href="manager/create.do"><spring:message code="master.page.createb" /></a></li>
			<li><a class="fNiv" href="record/search.do"><spring:message code="master.page.record.search"/></a></li>
			<li><a class="fNiv" href="record/list.do"><spring:message code="master.page.record.list"/></a></li>
			<li><a class="fNiv" href="artist/list.do"><spring:message code="master.page.artist.list"/></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">			
			<li><a class="fNiv" href="record/search.do"><spring:message code="master.page.record.search"/></a></li>
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="personaldata/actor/edit.do"><spring:message
								code="master.page.personalData" /> </a></li>
					<li><a href="personaldata/actor/display.do"><spring:message
								code="master.page.myprofile" /> </a></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>			
			
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

