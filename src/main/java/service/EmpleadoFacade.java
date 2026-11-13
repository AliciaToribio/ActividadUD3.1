package service;

import dao.EmpleadoDAO;
import model.Empleado;
import model.Nomina;

import java.sql.SQLException;
import java.util.List;

public class EmpleadoFacade {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoFacade() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    /** Lista todos los empleados */
    public List<Empleado> listarEmpleados() throws SQLException {
        return empleadoDAO.mostrarEmpleados();
    }

    /** Busca un empleado por dni; lanza excepción si no existe */
    public Empleado buscarEmpleado(String dni) throws SQLException {
        validarDni(dni);
        Empleado e = empleadoDAO.obtenerEmpleado(dni.trim());
        if (e == null) {
            throw new IllegalArgumentException("No existe ningún empleado con DNI " + dni);
        }
        return e;
    }

    /** Valida y actualiza un empleado (y su nómina) */
    public void editarEmpleado(Empleado e) throws SQLException {
        validarEmpleado(e);
        boolean actualizado = empleadoDAO.editar(e);
        if (!actualizado) {
            throw new IllegalArgumentException("No se pudo actualizar: el empleado no existe");
        }
    }

    public double calcularSueldo(Empleado e) {
        return Nomina.sueldo(e);
    }


    private void validarDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
    }

    private void validarEmpleado(Empleado e) {
        if (e == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        validarDni(e.getDni());
        if (e.getCategoria() < 1 || e.getCategoria() > 10) {
            throw new IllegalArgumentException("La categoría debe estar entre 1 y 10");
        }
        if (e.getAnyos() < 0) {
            throw new IllegalArgumentException("Los años no pueden ser negativos");
        }
    }
}
