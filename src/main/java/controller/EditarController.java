package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empleado;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editar")
public class EditarController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");
        if (dni == null) {
            request.getRequestDispatcher("/views/formularioEditar.jsp").forward(request, response);
            return;
        }
        try {
            Empleado empleado = facade.buscarEmpleado(dni);
            request.setAttribute("empleado", empleado);
            request.getRequestDispatcher("/views/editar.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError(request, response, "Error al buscar el empleado", null);
        } catch (IllegalArgumentException e) {
            mostrarError(request, response, e.getMessage(), "editar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Empleado empleado;
        try {
            String dni = request.getParameter("dni").trim();
            String nombre = request.getParameter("nombre").trim();
            String sexo = request.getParameter("sexo").trim();
            int categoria = Integer.parseInt(request.getParameter("categoria"));
            int anyos = Integer.parseInt(request.getParameter("anyos"));

            empleado = new Empleado(nombre, dni, sexo.charAt(0), categoria, anyos);

        } catch (IllegalArgumentException | NullPointerException ex) {
            mostrarError(request, response,
                    "Datos no correctos: la categoría debe estar entre 1 y 10 y los años no pueden ser negativos",
                    "editar");
            return;
        }
        try {
            facade.editarEmpleado(empleado);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarError(request, response, "Error al guardar los cambios en la base de datos", null);
        } catch (IllegalArgumentException e) {
            mostrarError(request, response, e.getMessage(), "editar");
        }
    }
}
