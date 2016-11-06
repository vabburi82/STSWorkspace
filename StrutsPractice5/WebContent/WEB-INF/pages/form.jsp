<%@ page language="java" contentType="text/html; charset=UTF-8"  
 pageEncoding="UTF-8"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>  
  
<html>  
<head>  
<title>Being Java Guys | Struts Form</title>  
</head>  
<body >  
 <center>  
  
  <h2>Being Java Guys | Registration Form</h2>  
    
   
  
  <s:form action="register" name="register" >  
   <s:textfield name="firstName" label="First Name: " />  
   <s:textfield name="lastName" label="Last Name: " />  
   <s:textfield name="email" label="Email: " />  
   <s:textfield name="phone" label="Phone: " />  
   <s:submit value="Register"/> 
  </s:form>  
  
  <s:if test="'true'.equals(#request.saveSuccess)">
	  <h2>Being Java Guys | User Details</h2>  
	  
	  <table >
	  <th>  
	   <tr>  
	   
	    <td>First Name</td> 
	     <td>Last Name</td> 
	      <td>Email</td> 
	       <td>Phone</td> 
	       
	       </tr>
	       </th>
	       <s:iterator value="#request.all_registerations">
	       
	       <tr>
	    <td><s:property value="firstName" /> </td>
	    <td><s:property value="lastName" /> </td>
	     <td><s:property value="email" /> </td>   
	      <td><s:property value="phone" />  </td>
	     
	   </tr> 
	   </s:iterator> 
	  </table> 
	 
 <script type="text/javascript" language="javascript">
	document.register.firstName.value="";
	document.register.lastName.value="";
	document.register.email.value="";
	document.register.phone.value="";
</script> 
  </s:if> 
 </center>
 

  
</body>  
</html>  