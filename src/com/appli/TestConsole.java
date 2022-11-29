package com.appli;

public class TestConsole {
/*
	public static void main(String[] args) {
		// creation date
		String dateE = "2012-08-23";
		String dateA = "2000-08-23";
		LocalDate anneeE = LocalDate.parse(dateE, DateTimeFormatter.ISO_DATE);
		System.out.println(anneeE.getYear());
		LocalDate anneeA = LocalDate.parse(dateA, DateTimeFormatter.ISO_DATE);
		System.out.println(anneeA.getYear());

		// creation joueurs
		System.out.print("\n");
		Gold j1 = new Gold("j1", anneeA, "a@gmail.com", "PS3");
		Standard j2 = new Standard("j2", anneeA, "a@gmail.com", "PS3");
		Gold j3 = new Gold("j3", anneeA, "a@gmail.com", "PS3");
		Gold j4 = new Gold("j3", anneeA, "a@gmail.com", "PS3");// pseudo modifié, parfait <3
		//Enfant jr1 = j1.creerCompteEnfant("jr1", anneeE, "aa@gmail.com", "PS3");
		//Enfant jr2 = j2.creerCompteEnfant("jr2", anneeE, "aa@gmail.com", "PS3");

		// test pseudo
		System.out.print("\n");
		System.out.println("pseudoGlobal j2 (true) : " + MapsStorage.contientPseudoGlobal("j2"));
		System.out.println(MapsStorage.getJoueurWithPseudo("j1"));
		System.out.println("pseudo j3bis: " + j4.getPseudo());

		// test relation amis
		System.out.print("\n");
		j1.ajouterAmis("j2");
		System.out.println("j1 amis avec j2 (true) : " + j1.contientAmis("j2"));
		System.out.println("j2 amis avec j1 (true) : " + j2.contientAmis("j1"));
		j1.supprimerAmis("j2");
		System.out.println("j2 amis avec j1 (false) : " + j2.contientAmis("j1"));
		j1.ajouterAmis("j2");

		
		// test relation tuteur-enfant
		System.out.print("\n");
		System.out.println("jr1 contiens j1 (true) : " + MapsStorage.getJoueurWithPseudo("jr1").contientAmis("j1"));
		System.out.println("j1 contiens jr1 (true) : " + j1.contientAmis("jr1"));
		System.out.println("jr1 contiens j2 (x) : " + MapsStorage.getJoueurWithPseudo("jr1").contientAmis("j2"));
		System.out.println("j2 contiens jr1 (x) : " + j2.contientAmis("jr1"));
		System.out.println("jr2 contiens j2 (true) : " + MapsStorage.getJoueurWithPseudo("jr2").contientAmis("j2"));
		System.out.println("j2 contiens jr2 (true) : " + j2.contientAmis("jr2"));
		System.out.println("jr2 contiens j3 (x) : " + MapsStorage.getJoueurWithPseudo("jr2").contientAmis("j3"));
		System.out.println("j3 contiens jr2 (x) : " + j3.contientAmis("jr2"));

		//chargement fichier jeu
		System.out.print("\n");
		MapsStorage.chargerFichierJeux();
		//MapsStorage.afficherMapJeux(MapsStorage.fichierJeux);

		//test méthode jeu
		System.out.print("\n");
		MapsStorage.afficherInfoJeux(17);
		MapsStorage.afficherInfoJeux("Grand Theft Auto V", "PS3");
		MapsStorage.afficherInfoJeux(MapsStorage.getJeuxWithRang(17).getNom(), MapsStorage.getJeuxWithRang(17).getMachine());
		MapsStorage.afficherMapJeux(MapsStorage.parcoursListeJeuxMachineGenre("XOne", "Racing"));
		
		//ajout des machines
		j1.ajouterMachine("XOne");
		j1.ajouterMachine("X360");
		j2.ajouterMachine("Wii");
		j2.ajouterMachine("PS3");
		//jr1.ajouterMachine("Wii");
		//jr2.ajouterMachine("Wii");

		//test achat jeu
		System.out.print("\n");
		j1.acheterJeu("Grand Theft Auto V", "PS3"); //j1 n'a pas de ps3
		j1.acheterJeu(2347);
		System.out.println("j1 possede jeu GTA5 #17? (false): "+j1.joueurPossedeJeu(17));
		System.out.println("j1 possede jeu F1 2010 #2347? (true): "+j1.joueurPossedeJeu("F1 2010","X360"));
		j1.offrirJeuAmis("F1 2010", "PS3", j2);
		System.out.println("j2 possede jeu F1 2010 #2347? (true): "+j2.joueurPossedeJeu("F1 2010", "PS3"));

		//j1.offrirJeuAmis(MapsStorage.getJeuxWithNom("Mario Kart Wii", "Wii"), jr1);
		//j1.offrirJeuAmis(MapsStorage.getJeuxWithNom("Mario Kart Wii", "Wii"), jr2);
		//System.out.println("jr1 possede jeu MarioKart? (true) "+jr1.joueurPossedeJeu(3));
		//System.out.println("jr2 possede jeu MarioKart? (x) "+jr2.joueurPossedeJeu(3));

		//test jouer en multijoueur
		System.out.print("\n");
		j1.jouerMultijoueur("F1 2010", j2); //affiche de la merde... mais ok
		j1.jouerMultijoueur("Grand Theft Auto V", j2);
		j1.jouerMultijoueur("F1 2010");
		//j1.jouerMultijoueur("F1 2010", j3); //impossible car j1 et j3 ne sont pas amis
		j2.jouerMultijoueur("F1 2010"); //Ne crée pas de nouveau bot

		//affichage info joueur
		System.out.print("\n");
		j1.afficherInfoJoueur();
		//jr1.afficherInfoJoueur();
	
	}

*/
}
