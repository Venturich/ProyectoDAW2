/*
 *  
 *  	Proyecto para:
 *  	DI DWC DWE
 *  
 *  	Autor:
 * 	Ventura Preciado S�nchez
 * 
 * 	DAW2 IES ALBARREGAS
 * 
 */
package es.albarregas.Servlet;

import es.albarregas.DAO.ProductosDAO;
import es.albarregas.DAO.UsuariosDAO;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ventura
 */
public class BloquearUsuarios extends HttpServlet {

    private HttpSession sesion;

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
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        sesion = request.getSession();

        Map<String, String[]> entrada = request.getParameterMap();
        ArrayList<Usuarios> usuarios = (ArrayList<Usuarios>) sesion.getAttribute("listaUsuarios");
        Enumeration<String> nombres = request.getParameterNames();

        ArrayList<Usuarios> modificar = new ArrayList<Usuarios>();
        Usuarios user;

        while (nombres.hasMoreElements()) {
            user = new Usuarios();
            String key = nombres.nextElement();
            for (Usuarios u : usuarios) {
                if (!entrada.get(key)[0].equals(u.getBloqueado()) && Integer.parseInt(key)==(u.getId())) {
                    user.setId(Integer.parseInt(key));
                    user.setBloqueado(entrada.get(key)[0]);
                    modificar.add(user);
                    System.out.println("Modificar: " + user.getBloqueado() + " - " + user.getId());
                }
            }
        }
        UsuariosDAO userDao = new UsuariosDAO();
        userDao.updateUsuarios(modificar);
        sesion.setAttribute("mensaje", "Usuarios actualizados");

        getServletContext().getRequestDispatcher("/JSP/Administracion/AvisoBloqueo.jspx").forward(request, response);
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
