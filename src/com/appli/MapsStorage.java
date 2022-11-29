package com.appli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MapsStorage {

	public MapsStorage() {
	}

	// map stockant tous les pseudos de la platforme
	private static Map<String, Joueur> pseudoGlobal = new HashMap<String, Joueur>();
	// map stockant tous les jeux connu par la platforme
	private static Map<Integer, Jeux> fichierJeux = new HashMap<Integer, Jeux>();
	// array stockant le nom des machines existantes, on pourrait aussi faire une map si machine etait un type...
	//compléter par lors du chargement des jeux
	public static ArrayList<String> machineExistante = new ArrayList<String>(); 
	

	public static ArrayList<String> getArrayMachine() {
		return machineExistante;
	}

	/**Affiche les machines d'une liste
	 * @param listeMachine
	 */
	public static void afficherMachine(ArrayList<String> listeMachine) {
		for(int i = 0; i<listeMachine.size(); i++) {
			System.out.println(listeMachine.get(i));
		}
	}
	
	public static Map<Integer, Jeux> getJeuxCharges() {
		return fichierJeux;
	}

	public static Map<String, Joueur> getPseudoGlobal() {
		return pseudoGlobal;
	}

	/**Ajoute un pseudo dans la map des pseudoGlobal
	 * @param pseudo
	 * @param j
	 */
	public static void ajouterPseudoGlobal(String pseudo, Joueur j) {
		pseudoGlobal.put(pseudo, j);
	}

	/**Verifie si le pseudo existe
	 * @param pseudo
	 * @return boolean
	 */
	public static boolean contientPseudoGlobal(String pseudo) {
		if (pseudoGlobal.containsKey(pseudo)) {
			// System.out.println("le pseudo est deja la ... ");
			return true;
		} else {
			// System.out.println("le pseudo est dispo !! ");
			return false;
		}
	}

	/**Affice une map de joueur
	 * @param m
	 */
	public static void afficherMapJoueur(Map<String, Joueur> m) {
		System.out.println("Voici les pseudos: ");
		for (String joueur : m.keySet()) {
			System.out.println(joueur);
		}
	}

	/**obtient type Joueur avec le pseudo
	 * @param pseudo
	 * @return Joueur
	 */
	public static Joueur getJoueurWithPseudo(String pseudo) {
		if (contientPseudoGlobal(pseudo)) {
			return pseudoGlobal.get(pseudo);
		} else {
			System.err.println("Erreur ce pseudo est introuvable, la valeur est null...");
			return null;
		}
	}

	/**obtient la chaine de charactère du type Joueur avec le pseudo
	 * @param pseudo
	 * @return String
	 */
	public static String getStringJoueurWithPseudo(String pseudo) {
		Joueur j = getJoueurWithPseudo(pseudo);
		if (j != null) {
			String s = j + ""; // conversion
			if (s.contains("Gold")) {
				return "Gold";
			} else if (s.contains("Standard")) {
				return "Standard";
			} else if (s.contains("Enfant")) {
				return "Enfant";
			} else if (s.contains("Bot")) {
				return "Bot";
			} else {
				return s;
			}
		} else {
			System.err.println("Erreur ce pseudo est introuvable, la valeur est null...");
			return null;
		}
	}

	/**Charge le fichier jeu en TSV
	 * Ajoute les jeux bien rempli à la map des jeux globaux
	 * Rempli liste avec les machines qui existent
	 * 
	 */
	public static void chargerFichierJeux() {
		try {
			File file = new File("fichier_jeux.txt"); // le fichier a été converti en TSV
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String ligne = br.readLine(); // skip 1ere ligne
			while ((ligne = br.readLine()) != null) {
				String str[] = ligne.split("\t");
				Jeux j = new Jeux();
				j.setRang(Integer.parseInt(str[0]));
				j.setNom(str[1]);
				j.setMachine(str[2]);
				if (str[3].contains("N/A")) {
					j.setAnnee(-1);
				} else {
					j.setAnnee(Integer.parseInt(str[3]));
				}
				j.setGenre(str[4]);
				j.setEditeur(str[5]);
				fichierJeux.put(j.getRang(), j); // s'il manque info, ne pas ajouter !
				if (!machineExistante.contains(str[2])) { // ajoute une fois le nom de toutes les machines
					machineExistante.add(str[2]);
				}
			}
			fr.close();
			System.out.println("Chargement du fichier jeux reussi ! ");
			System.out.println("Taille du fichier jeu chargé : " + fichierJeux.size());
		} catch (IOException e) {
			System.err.println("Chargement du fichier jeux raté... ");
			e.printStackTrace();
		}
	}

	/**Verifie si un jeu existe
	 * @param id
	 * @return boolean
	 */
	public static boolean jeuExiste(int id) {
		if (id != -1) {
			return fichierJeux.containsKey(id);
		} else {
			return false;
		}
	}

	/**Verifie si une machine existe
	 * @param machine
	 * @return boolean
	 */
	public static boolean machineExiste(String machine) {
		if (machine != null && machineExistante.contains(machine)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**Obtient le rang d'un jeu avec le couple nom/machine 
	 * @param nom
	 * @param machine
	 * @return int
	 */
	public static int getRangWithNom(String nom, String machine) { // le nom n'est pas unique donc n'est pas une clé, //
																	// mais combinaison rang-machine unique
		int i = 0;
		int t = fichierJeux.size();
		String nomJeux = "";
		String machineJeux = "";
		boolean trouve = false;
		while (!trouve && (i + 1 < t)) {
			i++;
			if (jeuExiste(i)) {
				Jeux j = getJeuxWithRang(i);
				nomJeux = j.getNom();
				machineJeux = j.getMachine();
				if (nomJeux.equals(nom) && machineJeux.equals(machine)) {
					trouve = true;
				}
			}
		}
		if (trouve) {
			return i;
		} else {
			// System.err.println("Rang non trouvé par le nom du jeu");
			return -1;
		}
	}

	/**Obtient jeu avec le rang
	 * @param id
	 * @return Jeux
	 */
	public static Jeux getJeuxWithRang(int id) {
		if (jeuExiste(id)) {
			return fichierJeux.get(id);
		} else {
			System.err.println("Le jeu n'existe pas...");
			// exeption !!
			return null;
		}
	}

	/**Obtient jeu avec le couple nom/machine
	 * @param nom
	 * @param machine
	 * @return Jeux
	 */
	public static Jeux getJeuxWithNom(String nom, String machine) {
		int rang = getRangWithNom(nom, machine);
		return getJeuxWithRang(rang);
	}

	/**Affiche les informations chargées d'un jeu par son id
	 * @param id
	 */
	public static void afficherInfoJeux(int id) { // pour faciliter la commande utilisateur, on simplifie les paramètres
												// à entrer
		if (jeuExiste(id)) {
			Jeux j = getJeuxWithRang(id);
			String info = "Info jeu | nom: " + j.getNom();
			info += " | machine: " + j.getMachine();
			info += " | année: " + j.getAnnee();
			info += " | genre: " + j.getGenre();
			info += " | éditeur: " + j.getEditeur();
			info += " | rang (nb vente décroissant): " + j.getRang();
			System.out.println(info);
		} else {
			System.err.println("Affichage impossible car jeu innexistant.");
		}
	}
	
	/**Affiche les informations chargées d'un jeu par le couple nom/machine
	 * @param id
	 */
	public static void afficherInfoJeux(String nom, String machine) {
		int id = getRangWithNom(nom, machine);
		afficherInfoJeux(id);
	}

	
	/**Retourne la liste de tous les jeux pour une machine et un genre
	 * @param machine
	 * @param genre
	 * @return Map<Integer, Jeux>
	 */
	public static Map<Integer, Jeux> parcoursListeJeuxMachineGenre(String machine, String genre) {
		Map<Integer, Jeux> jeuxFiltres = new HashMap<Integer, Jeux>();
		int i = 1;
		int t = fichierJeux.size();
		while (i <= t) {
			if (jeuExiste(i)) {
				Jeux j = getJeuxWithRang(i);
				if (j != null) {
					if (j.getMachine().equals(machine) == true && j.getGenre().equals(genre) == true) {
						jeuxFiltres.putIfAbsent(j.getRang(), j);
					}
				}
			}
			i++;
		}
		System.out
				.println("Nombre de jeux selectionnés pour ( " + machine + ", " + genre + " ) : " + jeuxFiltres.size());
		return jeuxFiltres;
	}

	/**Affiche une map Map<Integer, Jeux> de jeux
	 * @param m
	 */
	public static void afficherMapJeux(Map<Integer, Jeux> m) {
		for (Integer map : m.keySet()) {
			afficherInfoJeux(map);
		}
	}

	// on condidere que tous les titres sont egaux quel que soit la machine de jeu
	/**Vérifie si le joueur à le jeu en commun avec son ami
	 * On condidere que tous les titres sont egaux quel que soit la machine de jeu
	 * @param titre
	 * @param j1
	 * @param j2
	 * @return true si en commun, false sinon
	 */
	public static boolean jeuCommun(String titre, JHumain j1, Joueur j2) {
		int t = machineExistante.size();
		int i = 0;
		boolean j1PossedeJeu = false;
		boolean j2PossedeJeu = false;
		if (j2 instanceof JBot && ((JBot) j2).getNomJeu() == titre) {
			j2PossedeJeu = true;
		}
		while (i < t && (!j1PossedeJeu || !j2PossedeJeu)) {
			if (j1.joueurPossedeJeu(titre, machineExistante.get(i))) {
				j1PossedeJeu = true;
			} else if (j2 instanceof JHumain && ((JHumain) j2).joueurPossedeJeu(titre, machineExistante.get(i))) {
				j2PossedeJeu = true;
			}
			i++;
		}
		if (j1PossedeJeu && j2PossedeJeu) {
			return true;
		} else {
			return false;
		}
	}
}
