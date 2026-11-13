package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/listar")
public class ListarController extends BaseController{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("empleados", facade.listarEmpleados());
            request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError(request, response, "Error al listar los empleados", null);
        }
    }

}
