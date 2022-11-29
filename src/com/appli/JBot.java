package com.appli;

public class JBot extends Joueur {

	//il existe un bot par jeu
	private String nomJeu;
	
	/**Constructeur de JBot
	 * @param titreJeu
	 */
	public JBot(String titreJeu) {
		super.setPseudo("Bot-"+ titreJeu);
		this.nomJeu=titreJeu;
		super.setNbJeuxMax(INFINI);
		super.setNbAmisMax(INFINI);
		super.setNbPartiesMax(INFINI);
		MapsStorage.ajouterPseudoGlobal(this.getPseudo(),this);

	}
	
	public String getNomJeu() {
		return this.nomJeu;
	}
	
	/**Affiche info du bot
	 */
	@Override
	public void afficherInfoJoueur() {
		String pseudo = this.getPseudo();
		String info="Info du joueur | pseudo: "+pseudo;
		info+=" | profil: "+ MapsStorage.getStringJoueurWithPseudo(pseudo);
		info+=" | jeux: " + this.getNomJeu();
		System.out.println(info);
	}
	
	/**
	 *Empêche les enfants d'ajouter des amis
	 */
	@Override
	public void ajouterAmis(String pseudoJ2){
		System.err.println("Ajout d'ami impossible pour un bot.");
	}
	
	/**
	 *Empêche les enfants de supprimer des amis
	 */
	@Override
	public void supprimerAmis(String pseudoJ2){ 
		System.err.println("Suppression d'ami impossible pour un bot.");
	}
	
}
