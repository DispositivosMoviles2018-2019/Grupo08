package com.dldiaz.proyecto.ddiazexamen2h.controlador;

import com.dldiaz.proyecto.ddiazexamen2h.modelo.Reserva;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;

import java.util.Collection;
import java.util.List;

public interface Crud {
    /**
     * Metodo que permite crear un nuevo Objeto de cualquier clase.
     * @param obj Es el nuevo Objeto que se va a crear
     * @return Un mensaje para alertar al usuario
     */
    public String crear(Object obj);

    /**
     * Metodo que permite actualizar el estado de un Objeto.
     * @param id El id del Objeto a ser actualizado
     * @return Un mensaje para alertar al usuario
     */
   public String actualizar(Object id);

    /**
     * Metodo que permite eliminar un Objeto
     * @param id El id del Objeto a ser eliminado
     * @return Un mensaje para alertar al usuario
     */
    public String borrar(Object id);

    /**
     * Metodo que permite buscar un Objeto
     * @param lista La coleccion donde se va a buscar
     * @param parametro El parametro del Objeto que se está buscando
     * @return El Objeto encontrado, si el objeto no existe, retorna null
     */
    //public Object buscarPorParametro(Collection lista[], Object parametro);

    /**
     * Metodo que permita listar los Objetos
     *
     * @return La colección con el resultado
     */
    public List<Vehiculo> listar();
    public List<Reserva> listarR();
}
