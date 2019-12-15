<%-- 
    Document   : buscarhorario
    Created on : 15/12/2019, 01:29:33 PM
    Author     : stive
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="../../buscarHorario.do">
            <p>Por favor digite el codigo:</p>
                <p>Codigo<input type="number" name="codigo" required="true"> </p>
                 <input type="submit" placeholder="ver horario">
        </form>
    </body>
</html>
