package com.appli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class JHumain extends Joueur {

	private String mail;
	private LocalDate dateNaissance;

	private ArrayList<String> arrayMachine = new ArrayList<String>();
	private ArrayList<PartiesJouees> arrayPartiesJouees = new ArrayList<PartiesJouees>();
	// integer car la clé est le rang
	private Map<Integer, Jeux> listeJeuxJoueur = new HashMap<Integer, Jeux>();

	/**
	 * Constructeur d'un joueur humain
	 * 
	 * @param ps
	 * @param d
	 * @param mail
	 * @param machine
	 * @param classe
	 */
	public JHumain(String ps, LocalDate d, String mail, String machine, String classe) {
		super.setPseudo(ps);
		this.setDateNaissance(d);
		this.setMail(mail);
		this.ajouterMachine(machine);

		if (classe == "Gold") {
			super.setNbJeuxMax(INFINI);
			super.setNbAmisMax(INFINI);
			super.setNbPartiesMax(10);
		}
		if (classe == "Standard") {
			super.setNbJeuxMax(50);
			super.setNbAmisMax(100);
			super.setNbPartiesMax(5);
		}
		if (classe == "Enfant") {
			super.setNbJeuxMax(30);
			super.setNbAmisMax(10);
			super.setNbPartiesMax(3);
		}
	}

	public void setMail(String m) {
		this.mail = m;
	}

	public String getMail() {
		return this.mail;
	}

	public void setDateNaissance(LocalDate a) {
		this.dateNaissance = a;
	}

	public LocalDate getDateNaissance() {
		return this.dateNaissance;
	}

	public ArrayList<String> getArrayMachine() {
		return arrayMachine;
	}

	/**
	 * Ajoute une nouvelle machine à un joueur
	 * 
	 * @param m
	 */
	public void ajouterMachine(String m) {
		if (MapsStorage.machineExistante.contains(m) && this.contientMachine(m) == false) {
			this.arrayMachine.add(m);
		}
	}

	/**
	 * Ajoute une partie joué dans la liste adapté pour chaque joueur
	 * 
	 * @param pj
	 */
	public void ajouterPartieJouee(PartiesJouees pj) {
		Joueur j2 = pj.getJoueur2();
		this.arrayPartiesJouees.add(pj);
		if ((j2 instanceof JHumain) == true) {
			((JHumain) j2).getListePartiesJouees().add(pj);
		}
	}

	/**
	 * Verifie si le joueur contient la machine
	 * 
	 * @param m
	 * @return boolean
	 */
	public boolean contientMachine(String m) {
		if (this.arrayMachine.contains(m)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<PartiesJouees> getListePartiesJouees() {
		return this.arrayPartiesJouees;
	}

	public Map<Integer, Jeux> getListeJeuxJoueur() {
		return this.listeJeuxJoueur;
	}

	public int getSizeListeJeuxJoueur() {
		return this.listeJeuxJoueur.size();
	}

	/**
	 * Verifie si le joueur est majeur
	 * 
	 * @param dateDeNaissance
	 * @return boolean
	 */
	public static boolean estJoueurMajeur(LocalDate dateDeNaissance) {
		int anneeNaissance = dateDeNaissance.getYear();
		int anneeActuelle = LocalDate.now().getYear();
		if (anneeActuelle - 18 < anneeNaissance) {// 2022-18=2004
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Ajoute un novel ami par son pseudo
	 * 
	 * @param pseudoJ2
	 */
	@Override // il y a des controles necessaire pour les joueurs humains
	public void ajouterAmis(String pseudoJ2) {
		Joueur j2 = MapsStorage.getJoueurWithPseudo(pseudoJ2);
		if (/* j2 != null && this.getNouvelAmis() && j2.getNouvelAmis() && */ this.getSizeAmis() < this.getNbAmisMax()
				&& j2.getSizeAmis() < j2.getNbAmisMax() && this.contientAmis(pseudoJ2) == false
				&& j2.contientAmis(this.getPseudo()) == false) {
			super.ajouterAmis(pseudoJ2);
		} else {
			System.err.println("L'ajout de cet ami est impossible.");
		}
	}

	/**
	 * Verifie si le joueur possède un jeu, recherche par rang
	 * 
	 * @param rang
	 * @return boolean
	 */
	public boolean joueurPossedeJeu(int rang) {
		if (this.listeJeuxJoueur.containsKey(rang)) {
			return true;
		} else
			return false;
	}

	/**
	 * Verifie si le joueur possède un jeu, recherche par couple nom/machine si nom
	 * est invalide, retourne false
	 * 
	 * @param nom
	 * @param machine
	 * @return
	 */
	public boolean joueurPossedeJeu(String nom, String machine) {
		int rang = MapsStorage.getRangWithNom(nom, machine);
		return joueurPossedeJeu(rang);

	}

	/**
	 * Jouer en multijoueur avec un joueur, crée une partie 
	 * @param titre
	 * @param j2
	 */
	public void jouerMultijoueur(String titre, Joueur j2) {
		String pseudoj2 = j2.getPseudo();
		if (MapsStorage.contientPseudoGlobal(pseudoj2) && super.contientAmis(pseudoj2)
				&& this.getNbPartiesJouees() < this.getNbPartiesMax() && MapsStorage.jeuCommun(titre, this, j2)) {
			if (j2 instanceof JHumain && j2.getNbPartiesJouees() < j2.getNbPartiesMax()) {
				this.setNbPartiesJoueesIncr();
				j2.setNbPartiesJoueesIncr();
				PartiesJouees pj = new PartiesJouees(this, j2, titre);
				this.ajouterPartieJouee(pj);
				System.out.println("Partie de " + titre + " jouée avec " + j2.getPseudo());
			} else if (j2 instanceof JBot) {
				this.setNbPartiesJoueesIncr();
				PartiesJouees pj = new PartiesJouees(this, j2, titre);
				this.ajouterPartieJouee(pj);
				System.out.println("Partie de " + titre + " jouée avec " + j2.getPseudo());
			} else {
				System.err.println(j2.getPseudo() + " ne peut pas jouer avec vous à " + titre);
			}
		} else {
			System.err.println("Vous ne pouvez pas jouer à " + titre + " avec " + j2.getPseudo()
					+ ". Vous aller donc jouer avec un bot.");
			this.jouerMultijoueur(titre);
		}
	}

	// jouer avec bot sans avoir besoin d'ami
	/**Jouer en multijoueur avec un bot, crée une partie 
	 * @param titre
	 * @return
	 */
	public boolean jouerMultijoueur(String titre) {
		int i = 0;
		boolean possedeJeu = false;
		// si le joueur possede le jeu, alors le jeu existe car il a été ajouté
		if (this.getNbPartiesJouees() < this.getNbPartiesMax()) {
			while (!possedeJeu && i < this.arrayMachine.size()) {
				if (this.joueurPossedeJeu(titre, this.arrayMachine.get(i))) {
					possedeJeu = true;
				}
				i++;
			}
			if (possedeJeu) {
				if (MapsStorage.contientPseudoGlobal("Bot-" + titre)) {
					this.setNbPartiesJoueesIncr();
					PartiesJouees pj = new PartiesJouees(this, MapsStorage.getJoueurWithPseudo("Bot-" + titre), titre);
					this.ajouterPartieJouee(pj);
					System.out.println("Pas de bot créé, partie de " + titre + " jouée");
					return true;
				} else {
					JBot bot = new JBot(titre);
					this.setNbPartiesJoueesIncr();
					PartiesJouees pj = new PartiesJouees(this, bot, titre);
					this.ajouterPartieJouee(pj);
					System.out.println("Nouveau bot créé, partie " + titre + " jouée");
					return true;
				}
			}
		}
		return false;

	}

}
