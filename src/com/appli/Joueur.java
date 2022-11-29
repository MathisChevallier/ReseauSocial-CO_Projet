package com.appli;

import java.util.HashMap;
import java.util.Map;

public abstract class Joueur {

	final int INFINI = Integer.MAX_VALUE;

	private String pseudo; // il est forcément unique, def dans setPseudo
	private int nbJeuxMax;
	// private boolean acquerirJeux;
	private int nbAmisMax;
	private int nbPartiesMax;
	private int nbPartiesJouees = 0;

	// boolean pour lien tuteur-enfant, empeche la suppression d'ami d'un tuteur par
	// un enfant et inversement
	private Map<String, Boolean> amis = new HashMap<String, Boolean>();

	abstract void afficherInfoJoueur();

	public void setNbJeuxMax(int a) {
		this.nbJeuxMax = a;
	}

	public int getNbJeuxMax() {
		return this.nbJeuxMax;
	}

	public void setNbAmisMax(int a) {
		this.nbAmisMax = a;
	}

	public int getNbAmisMax() {
		return this.nbAmisMax;
	}

	/**
	 * @param ps
	 */
	public void setPseudo(String ps) {
		// verifie que le pseudo est unique
		if (MapsStorage.contientPseudoGlobal(ps) == false) {
			this.pseudo = ps; // ajout dans MapsStorage.pseudoGlobal dans les constructeurs
		} else {
			// on modifie le pseudo
			int i = 0;
			String ps2 = ps;
			System.out.println("Le pseudo est deja occupé, voici votre nouveau pseudo :");
			while (MapsStorage.contientPseudoGlobal(ps2)) {
				ps2 = ps;
				i++;
				ps2 = ps2 + i;
			}
			ps = ps + i;
			System.out.println(ps);
			this.pseudo = ps;
		}
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setNbPartiesMax(int n) {
		this.nbPartiesMax = n;
	}

	public int getNbPartiesMax() {
		return nbPartiesMax;
	}

	public void setNbPartiesJoueesIncr() {
		this.nbPartiesJouees += 1;
	}

	public int getNbPartiesJouees() {
		return nbPartiesJouees;
	}

	public int getSizeAmis() {
		return amis.size();
	}

	public Map<String, Boolean> getAmis() {
		return amis;
	}

	/**
	 * @param pseudo
	 * @return boolean
	 */
	public boolean contientAmis(String pseudo) {
		return amis.containsKey(pseudo);
	}

	/**
	 * @param pseudoJ2
	 */
	public void ajouterAmis(String pseudoJ2) {
		Joueur j2 = MapsStorage.getJoueurWithPseudo(pseudoJ2);
		if (j2 != null) {
			this.amis.put(pseudoJ2, false);
			j2.amis.put(this.getPseudo(), false);
		} else {
			System.err.println("Ce pseud n'existe pas, ajout d'ami impossible.");
		}
	}

	/**
	 * @param pseudoJ2
	 */
	public void supprimerAmis(String pseudoJ2) {
		Joueur j2 = MapsStorage.getJoueurWithPseudo(pseudoJ2);
		if (j2 != null) {
			if (this.amis.get(pseudoJ2) == false) { // verfie le lien tuteur-enfant
				this.amis.remove(pseudoJ2); // si pseudoj2 pas dans liste d'amis, alors il ne se passe rien
				j2.amis.remove(this.getPseudo());
			} else {
				System.err.println("Suppression impossible car lien tuteur-enfant permanent");
			}
		} else {
			System.err.println("Suppresion d'ami impossible, ce joueur n'hesiste pas");
		}
	}

	public void afficherAmis() {
		if (this.getSizeAmis() == 0) {
			System.out.println("Vous n'avez pas encore d'ami.");
		} else {
			System.out.println("Voici vos ami(s):");
			for(Map.Entry m:this.getAmis().entrySet()){
		        System.out.println("Amis: "+m.getKey()+" | Lien tuteur-enfant? "+m.getValue());
		    }
		}
	}
}
