package com.appli;

import java.time.LocalDate;

public class Gold extends JAdulte{
	
	/**Constructeur de Gold
	 * @param ps
	 * @param d
	 * @param mail
	 * @param machine
	 */
	public Gold(String ps, LocalDate d, String mail, String machine) {
		super(ps, d, mail, machine ,"Gold");
		MapsStorage.ajouterPseudoGlobal(this.getPseudo(),this);
	}
}
