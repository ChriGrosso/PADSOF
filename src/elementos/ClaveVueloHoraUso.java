package elementos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import vuelos.Vuelo;

public class ClaveVueloHoraUso implements Serializable {
	private static final long serialVersionUID = 1L;
    private Vuelo vuelo;
    private LocalDateTime horaUso;

    public ClaveVueloHoraUso(Vuelo vuelo,  LocalDateTime horaUso) {
        this.vuelo = vuelo;
        this.horaUso = horaUso;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public LocalDateTime getHoraUso() {
        return horaUso;
    }

    // Sobrescribir hashCode() y equals() para asegurar que se usen correctamente como clave de HashMap
    @Override
    public int hashCode() {
        return Objects.hash(vuelo, horaUso);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClaveVueloHoraUso that = (ClaveVueloHoraUso) obj;
        return Objects.equals(vuelo, that.vuelo) && Objects.equals(horaUso, that.horaUso);
    }
}
