/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import NEGOCIO.AmigoAcademico;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author estudiante
 */
@WebServlet(name = "Registrar", urlPatterns = {"/registrar.do"})
public class Registrar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            int cont = 0;
            String[] partes = null;
            String[] dia = new String[20];
            String[] jornada = new String[20];
            String[] check = request.getParameterValues("chkBox");
            for (int i = 0; i < check.length; i++) {
                if (!check[i].isEmpty()) {
                    partes = check[i].split("-");
                    cont++;
                }
                System.out.println(" asas" + partes[0]);
                dia[i]= partes[0];
                jornada[i] = partes[1];
            }
            if (cont == 3) {
                AmigoAcademico ac = new AmigoAcademico();
                if (!request.getParameter("codigo").isEmpty()) {
                    if (ac.registrarAmigo(codigo)) {
                        for (int i = 0; i < cont; i++) {
                            ac.registrarHorario(codigo, Integer.parseInt(dia[i]), Integer.parseInt(jornada[i]));
                        }
                        request.getSession().setAttribute("amigo", ac);
                        request.getRequestDispatcher("./index.html").forward(request, response);
                    } else {
                        System.err.println("falso");
                        request.getSession().setAttribute("error", "");
                        request.getRequestDispatcher("./Error/error.jsp").forward(request, response);
                    }
                } else {
                    System.err.println("falso");
                    request.getSession().setAttribute("error", "No ha ingresado codigo");
                    request.getRequestDispatcher("./Error/error.jsp").forward(request, response);
                }

            } else if (cont < 3) {
                int horas = (3 - cont) * 4;
                System.err.println("falso");
                request.getSession().setAttribute("error", "El amigo academico no cumple el horario porque le faltan " + horas + " horas");
                request.getRequestDispatcher("./Error/error.jsp").forward(request, response);
            } else {
                System.err.println("falso");
                request.getSession().setAttribute("error", "El amigo academico ha seleccionado mas horas ");
                request.getRequestDispatcher("./Error/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("el error es: " + e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
            request.getRequestDispatcher("./Error/error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
