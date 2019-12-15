<%-- 
    Document   : verhorario
    Created on : 5/12/2019, 09:44:31 AM
    Author     : estudiante
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Horario"%>
<%@page import="NEGOCIO.AmigoAcademico"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%

            ArrayList<Horario> list = (ArrayList<Horario>) request.getSession().getAttribute("horario");
        %>
        <table border="1" id="example" class="display">
            ${request.horario }
            <thead>
                <tr>
                    <th>     </th>
                    <th>Lunes</th>
                    <th>Martes</th>
                    <th>Miercoles</th>
                    <th>Jueves</th>
                    <th>Viernes</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Mañana</td>
                    <%
                        for (int i = 0; i < list.size(); i++) { %>
                    <%  if (list.get(i).getJornada() == 1) {
                            }
                        }
                    %>
                </tr>
                <tr>
                    <td>Tarde</td>
                    <%
                        for (int i = 0; i < list.size(); i++) { %>
                    <%  if (list.get(i).getJornada() == 1) {
                            }
                        }
                    %>
                </tr>
            </tbody>
        </table>
    </body>
</html>
