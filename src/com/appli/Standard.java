package com.appli;

import java.time.LocalDate;

public class Standard extends JAdulte{

	/**Constructeur d'un joueur Standard
	 * @param ps
	 * @param d
	 * @param mail
	 * @param machine
	 */
	public Standard(String ps, LocalDate d, String mail, String machine) {
		super(ps, d, mail, machine, "Standard");
		MapsStorage.ajouterPseudoGlobal(this.getPseudo(),this);
	}
	
}
