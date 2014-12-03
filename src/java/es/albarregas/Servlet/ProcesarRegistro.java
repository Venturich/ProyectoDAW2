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

import es.albarregas.DAO.ClientesDAO;
import es.albarregas.DAO.UsuariosDAO;
import es.albarregas.Modelo.Clientes;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ventura
 */
public class ProcesarRegistro extends HttpServlet {

    HttpSession sesion;

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
        UsuariosDAO userDAO = new UsuariosDAO();
        ClientesDAO clientDAO = new ClientesDAO();
        sesion = request.getSession();
        int estado = 0;
        response.setContentType("text/html;charset=UTF-8");

        estado = userDAO.insertarUsuario((Usuarios) sesion.getAttribute("usuario"));
        if (estado > 0) {
            clientDAO.insertarCliente((Clientes) sesion.getAttribute("cliente"), estado);
            getServletContext().getRequestDispatcher("/JSP/Login/Login.jspx").forward(request, response);
        } else {
            sesion.setAttribute("error", "el registro no se ha llevado a cabo con exito");
            getServletContext().getRequestDispatcher("/index.jspx").forward(request, response);
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
