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
  <s:actionerror />
  <s:form action="register" name="register" >  
   <s:textfield name="firstName" label="First Name: " />  
   <s:textfield name="lastName" label="Last Name: " />  
   <s:textfield name="email" label="Email: " />  
   <s:textfield name="phone" label="Phone: " />  
   <input name="updateRegId" type="hidden" value='<s:property value="id" />'>
   <s:submit value="Register"/> 
  </s:form>  
  
  
	  <h2>Being Java Guys | User Details</h2>  
	  
	  <table border="1">
	  <tr> 
	    <td>First Name</td>
	     <td>Last Name</td> 
	      <td>Email</td> 
	       <td>Phone</td>
	       <td></td> 
	    </tr>
	       <s:iterator value="#request.all_registerations">
	       <tr>
		    <td><s:property value="firstName" /> </td>
		    <td><s:property value="lastName" /> </td>
		     <td><s:property value="email" /> </td>   
		      <td><s:property value="phone" />  </td>
    		<td>
	    		<s:form action="eidtRegister"  >
	    		<input name="editRegId" type="hidden" value='<s:property value="id" />'>
	    		<s:submit value="Edit"/>
	    		</s:form>  
    		</td>
	     </tr> 
	   </s:iterator> 
	  </table> 
<s:if test="'true'.equals(#request.saveSuccess) and !('true'.equals(#request.editFlag))">	  
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