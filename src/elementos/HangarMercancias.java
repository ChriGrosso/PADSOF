package elementos;

import java.time.LocalDateTime;

public class HangarMercancias extends Hangar {
	
	private boolean materialesPeligrosos;

	public HangarMercancias(String id, double costeph, LocalDateTime fchRegistro, int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza) {
		super(id, costeph, fchRegistro, numPlazas, alturaPlaza, anchuraPlaza, largoPlaza);
		// TODO Auto-generated constructor stub
		this.setMaterialesPeligrosos(false);
	}

	/**
	 * @return the materialesPeligrosos
	 */
	public boolean isMaterialesPeligrosos() {
		return materialesPeligrosos;
	}

	/**
	 * @param materialesPeligrosos the materialesPeligrosos to set
	 */
	public void setMaterialesPeligrosos(boolean materialesPeligrosos) {
		this.materialesPeligrosos = materialesPeligrosos;
	}

}
