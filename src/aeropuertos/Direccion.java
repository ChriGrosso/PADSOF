package aeropuertos;

/**
 * Enumeración que representa las direcciones cardinales principales.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public enum Direccion {
	NORTE,
	SUR,
	ESTE,
	OESTE;
	
	/**
     * Devuelve la dirección correspondiente a la cadena proporcionada.
     * La comparación ignora mayúsculas y minúsculas.
     *
     * @param dir Cadena con la dirección a convertir.
     * @return La dirección correspondiente si es válida, o null en caso contrario.
     */
    public static Direccion getDireccion(String dir) {
        try {
            return Direccion.valueOf(dir.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null; // Retorna null si la cadena no coincide con ninguna dirección válida.
        }
    }
}
