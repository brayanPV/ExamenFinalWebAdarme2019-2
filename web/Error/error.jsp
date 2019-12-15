<%-- 
    Document   : error
    Created on : 5/12/2019, 09:49:59 AM
    Author     : estudiante
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <%
            String error=(String)(request.getSession().getAttribute("error"));
        %>
        <h1 class="register-title">Hubo un error: <%=error%></h1>
        <p>
            <a href="./index.html">Regresar a Inicio</a>
        </p>
    </body>
</html>
