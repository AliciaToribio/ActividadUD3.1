package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmpleadoFacade;

import java.io.IOException;

public abstract class BaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected EmpleadoFacade facade;
    @Override
    public void init(){
        this.facade = new EmpleadoFacade();
    }

    protected void mostrarError(HttpServletRequest request, HttpServletResponse response,
                              String mensaje, String volver) throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        request.setAttribute("volver", volver);
        request.getRequestDispatcher("/views/error.jsp").forward(request, response);
    }
}
