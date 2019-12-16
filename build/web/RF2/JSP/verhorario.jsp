<%-- 
    Document   : verhorario
    Created on : 5/12/2019, 09:44:31 AM
    Author     : estudiante
--%>


<%@page import="java.util.TreeMap"%>
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
            TreeMap<Integer, TreeMap<Integer, Boolean>> treedos = new TreeMap<Integer, TreeMap<Integer, Boolean>>();
            String nombres [] = new String[]{"mañana","tarde"};
            for (int i = 0; i < list.size(); i++) {
                treedos.computeIfAbsent(list.get(i).getDia(), a -> new TreeMap<Integer, Boolean>()).put(list.get(i).getJornada(), true);
            }
            int total = 3;
            int cantDias = 6;
        %>
        <table border="1" id="example" class="display">
            ${request.horario }
            <thead>
                <tr>
                    <th>jornada </th>
                    <th>Lunes</th>
                    <th>Martes</th>
                    <th>Miercoles</th>
                    <th>Jueves</th>
                    <th>Viernes</th>
                </tr>
            </thead>
            <tbody>
                
                    <%
                        for (int i = 1; i < total; i++) {
                            String nombre = nombres [i-1];
                    %> <tr bgcolor="yellow">
                       <td >  <%=nombre%> </td><%
                        for (int j = 1; j < cantDias; j++) {
                            TreeMap<Integer, Boolean> prov = treedos.get(j);
                            if (prov == null) {
                    %> <td>   </td> <%
                    } else {
                        Boolean jornada = prov.get(i);
                        if (jornada == null) {
                    %> <td>    </td> <%
                    } else {
                    %> <td bgcolor="#FF0000"></td> <%
                                }

                            }

                        } %>
                        
                    </tr> <%
                     }
  %>
            </tbody>
        </table>
    </body>
</html>
