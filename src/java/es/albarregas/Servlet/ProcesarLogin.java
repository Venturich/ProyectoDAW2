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
import es.albarregas.DAO.PedidosDAO;
import es.albarregas.DAO.UsuariosDAO;
import es.albarregas.Modelo.Clientes;
import es.albarregas.Modelo.Pedidos;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Ventura
 */
public class ProcesarLogin extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        sesion = request.getSession();
        UsuariosDAO usersDAO = new UsuariosDAO();

        ArrayList<Usuarios> lista = usersDAO.getUsuarios();

        if (lista.isEmpty()) {
            sesion.setAttribute("error", "usuario no encontrado");
            request.getRequestDispatcher("index.jspx").forward(request, response);
        } else {
            Usuarios dato = (Usuarios) sesion.getAttribute("usuario");
            Iterator<Usuarios> it = lista.listIterator();
            while (it.hasNext()) {
                Usuarios test = it.next();
                /* Comprobamos si la clave y el email del modelo cargado en sesion es igual que alguno de la lista de la bbdd*/
                if (test.getEmail().equals(dato.getEmail())
                        && test.getClave().equals(dato.getClave())
                        && test.getBloqueado().equals("n")) {


                    /* iniciamos sesion y leemos los datos del cliente*/
                    if (test.getTipoAcceso().equals("u")) {
                        try {
                            ClientesDAO clientDAO = new ClientesDAO();
                            ArrayList<Clientes> listaCl = clientDAO.getDatosClientes(test.getId());

                            if (!listaCl.isEmpty()) {
                                Clientes cliente = listaCl.get(0);
                                Usuarios aux = (Usuarios) sesion.getAttribute("usuario");
                                /* copiamos todos los datos al modelo usuario*/
                                BeanUtils.copyProperties(aux, test);
                                sesion.setAttribute("usuario", aux);
                                /* copiamos todos los datos al modelo cliente*/
                                sesion.setAttribute("cliente", cliente);
                            }
                            PedidosDAO pedidosFin = new PedidosDAO();
                            PedidosDAO pedidosPen = new PedidosDAO();

                            ArrayList<Pedidos> pedidosFinalizados = pedidosFin.getPedidosFinalizados(test.getId());
                            ArrayList<Pedidos> pedidosPendientes = pedidosPen.getPedidosPendientes(test.getId());
                            sesion.setAttribute("pedidosFinalizados", pedidosFinalizados);
                            sesion.setAttribute("pedidosPendientes", pedidosPendientes);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(ProcesarLogin.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(ProcesarLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        getServletContext().getRequestDispatcher("/JSP/Usuario/MenuUsuario.jspx").forward(request, response);
                        return;

                    } else if (test.getTipoAcceso().equals("a")) {
                        sesion.setAttribute("listaUsuarios", lista);
                        getServletContext().getRequestDispatcher("/JSP/Administracion/MenuAdmin.jspx").forward(request, response);
                        return;

                    }
                    break;
                } else if (test.getEmail().equals(dato.getEmail())
                        && test.getClave().equals(dato.getClave()) && test.getBloqueado().equals("s")) {
                    sesion.setAttribute("error", "usuario bloqueado");
                }else{
                    sesion.setAttribute("error", "usuario o clave incorrectos");
                }
            }
            getServletContext().getRequestDispatcher("/index.jspx").forward(request, response);
            return;
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
