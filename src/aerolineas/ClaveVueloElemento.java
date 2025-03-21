package aerolineas;

import java.util.Objects;

import elementos.ElementoEstructural;
import vuelos.Vuelo;

public class ClaveVueloElemento {
    private Vuelo vuelo;
    private ElementoEstructural elemento;

    public ClaveVueloElemento(Vuelo vuelo, ElementoEstructural elemento) {
        this.vuelo = vuelo;
        this.elemento = elemento;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public ElementoEstructural getElemento() {
        return elemento;
    }

    // Sobrescribir hashCode() y equals() para asegurar que se usen correctamente como clave de HashMap
    @Override
    public int hashCode() {
        return Objects.hash(vuelo, elemento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClaveVueloElemento that = (ClaveVueloElemento) obj;
        return Objects.equals(vuelo, that.vuelo) && Objects.equals(elemento, that.elemento);
    }
}
