package com.appli;

import java.time.LocalDate;
import java.util.Scanner;

public abstract class JAdulte extends JHumain { // methode abstraite ?

	public JAdulte(String ps, LocalDate d, String mail, String machine, String classe) {
		super(ps, d, mail, machine, classe);
	}

	/**
	 *Affiche les infos d'un joueur adulte
	 */
	@Override
	public void afficherInfoJoueur() {
		// pseuso, profil, mail, date, partie jouée, nb amis
		int i = 0;
		String pseudo = this.getPseudo();
		String info = "Info du joueur | pseudo: " + pseudo;
		info += " | profil: " + MapsStorage.getStringJoueurWithPseudo(pseudo);
		info += " | mail: " + this.getMail();
		info += " | date de naissance: " + this.getDateNaissance();
		info += " | nombre d'amis: " + this.getSizeAmis();
		info += " | parties jouées: " + this.getNbPartiesJouees();
		if (this.getNbPartiesJouees() > 0) {
			info += "\nDétail des parties :\n";
			while (i < this.getListePartiesJouees().size()) {
				info += "Autre joueur : " + this.getListePartiesJouees().get(i).getJoueur2().getPseudo();
				info += " | titre du jeu : " + this.getListePartiesJouees().get(i).getTitre();
				info += " | date : " + this.getListePartiesJouees().get(i).getDate();
				info += "\n";
				i++;
			}
		}
		System.out.println(info);
	}

	/**Permet à un adulte de créer un compte enfant
	 * @param ps
	 * @param d
	 * @param mail
	 * @param machine
	 * @return Enfant créé
	 */
	public Enfant creerCompteEnfant(String ps, LocalDate d, String mail, String machine) {
		if (super.estJoueurMajeur(d) == false) {
			Enfant e = new Enfant(ps, d, mail, machine);
			int nbTuteur = 1; // au minimum le createur du compte

			System.out.println(
					"Combien de tuteur(s) voulez vous ajouter en plus de vous a votre enfant " + e.getPseudo() + " ?");
			Scanner inputReader1 = new Scanner(System.in);
			nbTuteur = nbTuteur + inputReader1.nextInt();
			while (this.getSizeAmis() + nbTuteur > this.getNbAmisMax()) {
				Scanner inputReader2 = new Scanner(System.in);
				System.out.println(
						"Votre liste d'ami est pleine. Choisissez un ami à supprimer pour ajouter votre enfant: ");
				String byeByeAmigos = inputReader2.nextLine(); // on suppose le type entree correct
				if (MapsStorage.contientPseudoGlobal(byeByeAmigos) && this.contientAmis(byeByeAmigos)) {
					this.supprimerAmis(byeByeAmigos);
				} else {
					System.out.println("Pseudo non valide pour la commande, veuillez écrire un pseudo valide : ");
				}
			}
			int i = 1;
			while (i <= nbTuteur) {
				if (i == 1) {
						this.getAmis().put(e.getPseudo(), true);
						e.getAmis().put(this.getPseudo(), true);
					i++;
				} else {
					System.out.println("Pseudo du tuteur n°" + i + " de l'enfant " + e.getPseudo() + " est :");
					String psTuteur = "";
					Scanner inputReader3 = new Scanner(System.in);
					psTuteur = inputReader3.nextLine();
					if (MapsStorage.contientPseudoGlobal(psTuteur)) {
						Joueur tuteur = MapsStorage.getJoueurWithPseudo(psTuteur);
						if (tuteur instanceof JAdulte && tuteur.contientAmis(e.getPseudo()) == false
								&& e.contientAmis(tuteur.getPseudo()) == false) {
							tuteur.getAmis().put(e.getPseudo(), true);
							e.getAmis().put(tuteur.getPseudo(), true);
							i++;
						}
					}
				}
			}
			return e;
		} else {
			System.err.println("L'enfant n'est plus un enfant, il est majeur !");
			return null;
		}
	}

	/**
	 *Ajout d'amis entre adulte
	 */
	@Override
	public void ajouterAmis(String pseudoJ2) {// bloque ajout enfant en ami
		if (MapsStorage.contientPseudoGlobal(pseudoJ2) == true) {
			Joueur j2 = MapsStorage.getJoueurWithPseudo(pseudoJ2);
			if (j2 instanceof JAdulte || j2 instanceof JBot) {
				super.ajouterAmis(pseudoJ2);
			} else {
				System.err.println("Cette personne n'est pas un adulte ou un bot, ajout d'ami annulé.");
			}
		} else {
			System.err.println("Le pseudo n'existe pas, ajout d'ami annulé.");
		}
	}

	/**Achat de jeu (par identifiant) pour le joueur adulte
	 * @param rang
	 */
	public void acheterJeu(int rang) { 
		if (MapsStorage.jeuExiste(rang)) {
			Jeux jeu = MapsStorage.getJeuxWithRang(rang);
			if (jeu != null && this.joueurPossedeJeu(rang) == false
					&& super.getSizeListeJeuxJoueur() < super.getNbJeuxMax()
					&& super.contientMachine(jeu.getMachine())) {
				super.getListeJeuxJoueur().put(rang, jeu);
			}
		} else {
			System.err.println("L'achat de ce jeu est impossible.");
		}
	}

	/**Achat de jeu (par couple nom/machine) pour le joueur adulte
	 * @param nom
	 * @param machine
	 */
	public void acheterJeu(String nom, String machine) {
		int rang = MapsStorage.getRangWithNom(nom, machine);
		if (rang >= 0) {
			this.acheterJeu(rang);
		} else {
			System.err.println("L'achat de ce jeu par son nom et sa machine est impossible.");
		}
	}

	/**Permet à un adulte d'offrir un jeu à un ami
	 * @param jeu
	 * @param j2
	 */
	public void offrirJeuAmis(Jeux jeu, JHumain j2) {
		if (MapsStorage.contientPseudoGlobal(j2.getPseudo()) && MapsStorage.jeuExiste(jeu.getRang())) {
			String pseudoJ2 = j2.getPseudo();
			int rang = jeu.getRang();
			if (super.contientAmis(pseudoJ2) && j2.getSizeListeJeuxJoueur() < j2.getNbJeuxMax()
					&& j2.joueurPossedeJeu(rang) == false && j2.contientMachine(jeu.getMachine())) {
				j2.getListeJeuxJoueur().put(rang, jeu);
			} else {
				System.err.println("Vous ne pouvez pas offir ce jeu a cet ami...");
			}
		} else {
			System.err.println("Le jeu ou la personne n'existe pas");
		}
	}

	/**Permet à un adulte d'offrir un jeu à un ami par le couple nom/machine
	 * @param nom
	 * @param machine
	 * @param j2
	 */
	public void offrirJeuAmis(String nom, String machine, JHumain j2) {
		Jeux jeu = MapsStorage.getJeuxWithNom(nom, machine);
		if (jeu != null) {
			this.offrirJeuAmis(jeu, j2);
			;
		} else {
			System.err.println("Vous ne pouvez pas offir ce jeu car il n'existe pas.");
		}
	}

}
