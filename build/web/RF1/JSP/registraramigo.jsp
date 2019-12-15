<%-- 
    Document   : registraramigo
    Created on : 5/12/2019, 08:38:02 AM
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
        <h3>BIENVENIDOS AL SISTEMA DE REGISTRO DE HORARIO DE AMIGOS ACADEMICOS</h3>
        <div>
            <form action="../../registrar.do">
                <p>Por favor digite su codigo y horario:</p>
                <p>Codigo<input type="number" name="codigo" required="true"> </p>
                <p>Lunes:           Jornada:<input id="checkbox" type="checkbox" value="1-1" name="chkBox" > Mañana - <input id="checkbox" type="checkbox" value="1-2" name="chkBox">Tarde </p>
                <p>Martes:          Jornada:<input id="checkbox" type="checkbox" value="2-1" name="chkBox" > Mañana - <input id="checkbox" type="checkbox" value="2-2" name="chkBox">Tarde </p>
                <p>Miercoles:       Jornada:<input id="checkbox" type="checkbox" value="3-1" name="chkBox">  Mañana - <input id="checkbox" type="checkbox" value="3-2" name="chkBox">Tarde </p>
                <p>Jueves:          Jornada:<input id="checkbox" type="checkbox" value="4-1" name="chkBox"> Mañana - <input id="checkbox" type="checkbox" value="4-2" name="chkBox">Tarde </p>
                <p>Viernes:         Jornada:<input id="checkbox" type="checkbox" value="5-1" name="chkBox"> Mañana - <input id="checkbox" type="checkbox" value="5-2" name="chkBox">Tarde </p>

                <input type="submit" placeholder="Registrar horario">
            </form>
        </div>
    </body>
</html>
