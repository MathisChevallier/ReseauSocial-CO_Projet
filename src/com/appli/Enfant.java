package com.appli;

import java.time.LocalDate;

public class Enfant extends JHumain {

	/**Constructeur d'un Enfant
	 * @param ps
	 * @param d
	 * @param mail
	 * @param machine
	 */
	public Enfant(String ps, LocalDate d, String mail, String machine)  {
		super(ps, d, mail, machine, "Enfant");
		MapsStorage.ajouterPseudoGlobal(this.getPseudo(), this);
	}

	/**Modifie les éléments affichés pour les enfants
	 *
	 */
	@Override
	public void afficherInfoJoueur() {
		String pseudo = this.getPseudo();
		String info = "Info du joueur | pseudo: " + pseudo;
		info += " | profil: " + MapsStorage.getStringJoueurWithPseudo(pseudo);
		info += " | nombre de parties jouées: " + this.getNbPartiesJouees();
		System.out.println(info);
	}

	/**Les enfants peuvent ajouter que d'autre enfant en amis, sauf leur parents
	 *
	 */
	@Override
	public void ajouterAmis(String pseudoJ2) {// empeche l'ajout d'adulte en ami
		if (MapsStorage.contientPseudoGlobal(pseudoJ2) == true) {
			Joueur j2 = MapsStorage.getJoueurWithPseudo(pseudoJ2);
			if (j2 instanceof Enfant) {
				super.ajouterAmis(pseudoJ2);
			} else {
				System.err.println("Cet ami n'est pas un enfant, ajout annulé.");
			}
		} else {
			System.err.println("Ce pseudo n'existe pas, ajout annulé.");
		}
	}
	

}
