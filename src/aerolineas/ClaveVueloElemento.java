package aerolineas;

import java.io.Serializable;
import java.util.Objects;

import elementos.ElementoEstructural;
import vuelos.Vuelo;

/**
 * Clase que representa una clave compuesta por un vuelo y un elemento estructural, 
 * utilizada para mapear y registrar el uso de infraestructuras por los vuelos en un HashMap.
 * Esta clave combina un objeto {@link Vuelo} y un objeto {@link ElementoEstructural}.
 * 
 * Es útil para identificar de manera única la relación entre un vuelo y un elemento estructural
 * y poder asociar información como los usos en un aeropuerto.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class ClaveVueloElemento implements Serializable {
	private static final long serialVersionUID = 1L;
    private Vuelo vuelo;
    private ElementoEstructural elemento;

    /**
     * Constructor de la clase ClaveVueloElemento.
     *
     * @param vuelo    Vuelo asociado a la clave.
     * @param elemento Elemento estructural asociado a la clave.
     */
    public ClaveVueloElemento(Vuelo vuelo, ElementoEstructural elemento) {
        this.vuelo = vuelo;
        this.elemento = elemento;
    }

    /**
     * Obtiene el vuelo asociado a la clave.
     *
     * @return Objeto Vuelo asociado.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Obtiene el elemento estructural asociado a la clave.
     *
     * @return Objeto ElementoEstructural asociado.
     */
    public ElementoEstructural getElemento() {
        return elemento;
    }

    
    /**
     * Genera un código hash único basado en el vuelo y el elemento, para su uso
     * en estructuras de datos como HashMap.
     *
     * @return Código hash generado a partir de vuelo y elemento.
     */
    @Override
    public int hashCode() {
        return Objects.hash(vuelo, elemento);
    }

    /**
     * Compara si dos objetos ClaveVueloElemento son iguales. Son considerados iguales si
     * ambos tienen el mismo vuelo y el mismo elemento estructural.
     *
     * @param obj Objeto a comparar.
     * @return True si los objetos son iguales, False en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClaveVueloElemento that = (ClaveVueloElemento) obj;
        return Objects.equals(vuelo, that.vuelo) && Objects.equals(elemento, that.elemento);
    }
}
