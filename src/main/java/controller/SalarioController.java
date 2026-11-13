package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empleado;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/salario")
public class SalarioController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni = request.getParameter("dni");
        if (dni == null) {
            request.getRequestDispatcher("/views/formularioSalario.jsp").forward(request, response);
            return;
        }
        try {
            Empleado empleado = facade.buscarEmpleado(dni);
            request.setAttribute("empleado", empleado);
            request.setAttribute("sueldo", facade.calcularSueldo(empleado));
            request.getRequestDispatcher("/views/salario.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError(request, response, "Error al consultar el salario", null);
        } catch (IllegalArgumentException e){
            mostrarError(request,response,e.getMessage(), "salario");
        }

    }
}
