/** 
 * 	Projet ET3 Java - Application simplifi� d'un r�seau social de jeux vid�os
 * 
 * @author CHEVALLIER Mathis
 * 
 * Cette application � pour but de relier et de faire int�ragir entre-eux des joueurs de jeux vid�os.
 * Affichage simple en mode texte dans la console.
 * Les joueurs ont un des profils suivant : Gold, Standard, Enfant ou Bot.
 * Les Bots sont des simulations d'IA jouant avec un joueur.
 * Les enfants ont eux des restrictions par rapport aux autres joueurs.
 * 
 * Rappel de certaines r�gles �tablient dans le contrat �crit:
 * - Un compte enfant doit �tre cr�� par un compte d'adulte, celui-ci devient obligaterement un des tuteur (donc ami) de l'enfant.
 * - Les joueurs adultes peuvent �tre amis entre-eux, et r�ciproquement pour les joueurs enfants.
 * - Possibilit� de supprimer un joueur de ses amis.
 * - L'affiche des informations d'un joueur d�pend de son profil.
 * - Les joueurs adultes peuvent acheter des jeux s'ils ont la machine/console de jeu ad�quate.
 * - Les joueurs adultes peuvent offrir un jeu � un amis, et de m�me pour ses machines.
 * - Un joueur peut jouer en multijoueur avec un autre joueur si et seulement si les deux joueurs sont amis, et poss�de le m�me jeu.
 * - Les parties multijoueurs sont cross-platforme.
 * - Possibilit� de jouer en multijoueur avec un bot, ce bot reste unique.
 * - La liste des parties jou�es de chaque joueur est enregistr�e.
 * - Un maximum de cas d'erreurs possibles � l'ex�cution � �t� identifi� et corrig�.
 */


package com.appli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface Application {

	public static void main(String[] args) {
		// charger le fichier de jeux
		MapsStorage.chargerFichierJeux();
		
//Les initialisations sont uniquement pr�sentent pour faciliter la premi�re utilisation, elles sont optionelles//
		
		// initialisation date
		String dateE = "2012-08-23"; //enfant
		String dateA = "2000-08-23"; //adulte
		LocalDate anneeE = LocalDate.parse(dateE, DateTimeFormatter.ISO_DATE);
		LocalDate anneeA = LocalDate.parse(dateA, DateTimeFormatter.ISO_DATE);

		// initialisation joueurs
		System.out.print("\n");
		Gold j1 = new Gold("Jean", anneeA, "jean@gmail.com", "XOne");
		Standard j2 = new Standard("Bernard", anneeA, "bernard@gmail.com", "PS3");
		Gold j3 = new Gold("Th�o", anneeA, "theo@gmail.com", "PS3");
		Gold j4 = new Gold("Chlo�", anneeA, "chlo�@gmail.com", "PS3");// pseudo modifi�, parfait <3
		// Enfant jr1 = j1.creerCompteEnfant("Lucas", anneeE,"lucas@gmail.com", "PS3");
		// Enfant jr2 = j2.creerCompteEnfant("Anna", anneeE,"anna@gmail.com", "PS3");

		// initialisation d'amis
		j1.ajouterAmis("Bernard");
		j1.ajouterAmis("Th�o");

		// initialiser machines
		j1.ajouterMachine("XOne"); //pr�sent qu'une fois dans le liste, alors qu'est aussi dans le constructeur
		j1.ajouterMachine("Wii");
		j2.ajouterMachine("PC");

		// initialisation de jeu
		j1.acheterJeu("F1 2015", "XOne");
		j2.acheterJeu(13342);
		// ces deux jeux sont les m�mes

		boolean quitter = false;
		while (!quitter) {
			Scanner inputReaderString = new Scanner(System.in);
			String entreeString = null;
			boolean retour = false;

			System.out.println("\nBienvenue ! Choisissez l'option qui vous convient.\n");
			System.out.println("0 - Quitter");
			System.out.println("1 - Se connecter � un joueur");
			System.out.println("2 - S'inscrire sur la platforme\n");
			entreeString = inputReaderString.next();

			switch (entreeString) {
			case "0":
				quitter = true;
				break;

			case "1":// se connecter � un joueur
				MapsStorage.afficherMapJoueur(MapsStorage.getPseudoGlobal());
				System.out.println("\nVeuillez entrer votre pseudo pour vous connecter");
				entreeString = inputReaderString.next();
				Joueur joueur = MapsStorage.getJoueurWithPseudo(entreeString);
				if (joueur instanceof JHumain) {
					while (!retour) {// while21
						retour = false;
						System.out.println("\n0 - Retour au menu principal");
						System.out.println("1 - Jouer en multijoueur");
						System.out.println("2 - Afficher les information de mon profil");
						System.out.println("3 - Cr�er un compte pour un enfant");
						System.out.println("4 - Amis");
						System.out.println("5 - Jeux");
						System.out.println("6 - Machines\n");

						entreeString = inputReaderString.next();

						switch (entreeString) {// switch21
						case "0":
							retour = true;
							break;

						case "1":// Jouer en multijoueur
							System.out.println("Voici vos jeux:");
							MapsStorage.afficherMapJeux(((JHumain) joueur).getListeJeuxJoueur()); // affiche les jeux
							System.out.println("\nA quel jeu voulez-vous jouer ? (nom)");
							inputReaderString.nextLine();
							String nomJeu = inputReaderString.nextLine();
							System.out.println("Sur quelle machine ?");
							String machine = entreeString = inputReaderString.nextLine();
							if (((JHumain) joueur).joueurPossedeJeu(nomJeu, machine)) {
								Jeux jeu = MapsStorage.getJeuxWithNom(nomJeu, machine);
								System.out.println("Voulez vous jouer avec un Bot ?\n1 - Oui\n2 - Non");
								entreeString = inputReaderString.next();
								if (entreeString.equals("1")){
									((JHumain) joueur).jouerMultijoueur(nomJeu);
								} else {
									System.out.println("Avec qui voulez-vous jouer en multijoueur ?");
									joueur.afficherAmis();
									inputReaderString.nextLine();
									String nomJoueur = inputReaderString.nextLine();
									Joueur joueur2 = MapsStorage.getJoueurWithPseudo(nomJoueur);
									((JHumain) joueur).jouerMultijoueur(nomJeu, j2); // pas de soucis meme si j2 est un
																						// // bot
								}
							}
							break;

						case "2":// info du profil
							joueur.afficherInfoJoueur();
							break;

						case "3":// creer compte enfant
							if (joueur instanceof JAdulte) {
								System.out.println(
										"\nQuel sera le pseudo de votre enfant ? (sous r�serve de modification en fonction des pseudos d�j� utlilis�s)");
								String pseudoEnfant = inputReaderString.next();
								System.out.println("Quel est sa date de naissance ? (aaaa-mm-jj)");
								String dateNEnfant = inputReaderString.next();
								LocalDate anneeDateNEnfant = LocalDate.parse(dateNEnfant, DateTimeFormatter.ISO_DATE);
								System.out.println("Quel est sa machine ? ");
								MapsStorage.afficherMachine(MapsStorage.getArrayMachine());
								String machineEnfant = inputReaderString.next();
								((JAdulte) joueur).creerCompteEnfant(pseudoEnfant, anneeDateNEnfant,
										((JAdulte) joueur).getMail(), "machineEnfant");
							}
							break;

						case "4":// Amis
							while (!retour) {
								retour = false;
								System.out.println("\n0 - Retour au menu principal");
								System.out.println("1 - Voir mes amis");
								System.out.println("2 - Est-ce que j'ai ce joueur en ami ?");
								System.out.println("3 - Ajouter un ami");
								System.out.println("4 - Supprimer un ami");
								System.out.println("5 - Voir tous les joueurs existants\n");

								entreeString = inputReaderString.next();

								switch (entreeString) {// switch41
								case "0":
									retour = true;
									break;

								case "1": // mes amis
									joueur.afficherAmis();
									break;

								case "2": // contient amis
									System.out.println("Pour quel pseudo voulez-vous v�rifier si vous �tes amis ?");
									inputReaderString.nextLine();
									entreeString = inputReaderString.nextLine();
									if (joueur.contientAmis(entreeString)) {
										System.out.println("Vous �tes ami avec " + entreeString + ".");
									} else {
										System.out.println("Vous n'�tes pas ami avec " + entreeString + ".");
									}
									break;

								case "3": // ajouter amis
									MapsStorage.afficherMapJoueur(MapsStorage.getPseudoGlobal());
									System.out.println("Quel est le pseudo du joueur que vous voulez ajouter en ami ?");
									entreeString = inputReaderString.next();
									joueur.ajouterAmis(entreeString);
									break;

								case "4": // supprimer amis
									joueur.afficherAmis();
									System.out
											.println("Quel est le pseudo du joueur que vous voulez supprimer en ami ?");
									inputReaderString.nextLine();
									entreeString = inputReaderString.nextLine();
									joueur.supprimerAmis(entreeString);
									break;

								case "5": // voir tous les joueurs
									MapsStorage.afficherMapJoueur(MapsStorage.getPseudoGlobal());
									break;
								}// switch41
							}
							break;

						case "5":// Jeux
							while (!retour) {
								retour = false;
								System.out.println("\n0 - Retour au menu principal");
								System.out.println("1 - Afficher tous les jeux charg�s");
								System.out.println("2 - Afficher mes jeux");
								System.out.println("3 - Est-ce que je poss�de ce jeu ?");
								System.out.println("4 - Rechercher/Acheter un jeu");
								System.out.println("5 - Offir un jeu � un ami\n");

								entreeString = inputReaderString.next();

								switch (entreeString) {// switch31
								case "0":
									retour = true;
									break;

								case "1": // affiche tous les jeux
									MapsStorage.afficherMapJeux(MapsStorage.getJeuxCharges());
									break;
								case "2": // affiche les jeux du joueur
									MapsStorage.afficherMapJeux(((JHumain) joueur).getListeJeuxJoueur());
									break;

								case "3": // contient jeu ?
									System.out.println("Comment voulez-vous v�rifier ?");
									System.out.println("1 - Par identifiant/classement");
									System.out.println("2 - Par couple (nom/machine)");

									entreeString = inputReaderString.next();
									switch (entreeString) {
									case "1": // par id
										System.out.println("\nQuel jeu voulez-vous v�rifier que vous poss�dez ? (id)");
										entreeString = inputReaderString.next();
										if (((JHumain) joueur).joueurPossedeJeu(Integer.parseInt(entreeString))) {
											System.out.println("Vous poss�dez bien le jeu: " + entreeString);
										} else {
											System.out.println("Vous ne poss�dez pas le jeu: " + entreeString);
										}
										break;

									case "2": // par nom
										System.out.println("\nQuel jeu voulez-vous v�rifier que vous poss�dez ? (nom/machine)");
										inputReaderString.nextLine();
										nomJeu = inputReaderString.nextLine();
										System.out.println("Le jeu � chercher est :"+nomJeu);
										System.out.println("Pour quelle machine ?");
										String machineJeu = inputReaderString.next();										  
										System.out.println("La machine est: "+machineJeu);
										if (((JHumain) joueur).joueurPossedeJeu(nomJeu, machineJeu)) {
											System.out.println("Vous poss�dez bien le jeu: " + nomJeu);
										} else {
											System.out.println("Vous ne poss�dez pas le jeu: " + nomJeu);
										}
										break;
									}
									break;

								case "4": // acheterjeux
									if (joueur instanceof JAdulte) {
										System.out.println("Voici vos machine: ");
										MapsStorage.afficherMachine(((JHumain)joueur).getArrayMachine());
										while (!retour) {
											retour = false;
											System.out.println("\nRechercher des jeux (Par machine et genre)");
											System.out.println("Machine:");
											String machineJeu = inputReaderString.next();
											System.out.println("Genre:");
											String genreJeu = inputReaderString.next();
											MapsStorage.afficherMapJeux(
													MapsStorage.parcoursListeJeuxMachineGenre(machineJeu, genreJeu));
											System.out.println("1 - Retour");
											System.out.println("2 - Continuer les recherches");
											entreeString = inputReaderString.next();
											if (entreeString.equals("1")) {
												retour = true;
											}
										}
										System.out.println("\nComment voulez-vous acheter le jeu ?");
										System.out.println("1 - Par identifiant/classement");
										System.out.println("2 - Par couple (nom/machine)");
										System.out.println("3 - Retour");

										entreeString = inputReaderString.next();
										switch (entreeString) {
										case "1": // par id
											System.out.println("\nQuel jeu voulez-vous acheter ? (id)");
											entreeString = inputReaderString.next();
											if (joueur instanceof JAdulte) {
												((JAdulte) joueur).acheterJeu(Integer.parseInt(entreeString));
											}
											break;

										case "2": // par nom
											inputReaderString.reset();
											System.out.println("\nQuel jeu voulez-vous acheter ? (nom/machine)");
											System.out.print("Nom du jeu:\n");
											inputReaderString.nextLine();
											String nomJeu2 = inputReaderString.nextLine();
											System.out.print("Machine:\n");
											String machine2 = inputReaderString.nextLine();
											if (joueur instanceof JAdulte) {
												((JAdulte) joueur).acheterJeu(nomJeu2, machine2);
											}
											break;

										case "3": // retour
											break;
										}
									}
									break;

								case "5": // offrirjeux
									if (joueur instanceof JAdulte) {
										System.out.println("Voici vos machine: ");
										MapsStorage.afficherMachine(((JHumain)joueur).getArrayMachine());
										while (!retour) {
											retour = false;
											System.out.println("\nRechercher des jeux (Par machine et genre)");
											System.out.println("Machine:");
											String machineJeu = inputReaderString.next();
											System.out.println("Genre:");
											String genreJeu = inputReaderString.next();
											MapsStorage.afficherMapJeux(
													MapsStorage.parcoursListeJeuxMachineGenre(machineJeu, genreJeu));
											System.out.println("1 - Retour");
											System.out.println("2 - Continuer les recherches");
											entreeString = inputReaderString.next();
											if (entreeString.equals("1")) {
												retour = true;
											}
										}
										joueur.afficherAmis();
										System.out.println("\nA qui voulez vous offrir le jeu ?");
										entreeString = inputReaderString.next();
										Joueur joueur2 = MapsStorage.getJoueurWithPseudo(entreeString);
										System.out.println("Voici les machines de "+entreeString);
										MapsStorage.afficherMachine(((JHumain)joueur2).getArrayMachine());
										if (joueur2 instanceof JHumain) {
											System.out.println("Comment voulez-vous offrir le jeu ?");
											System.out.println("1 - Par identifiant/classement");
											System.out.println("2 - Par couple (nom/machine)");
											System.out.println("3 - Retour");

											entreeString = inputReaderString.next();
											switch (entreeString) {

											case "1": // par id
												System.out.println("\nQuel jeu voulez-vous offrir ? (id)");
												entreeString = inputReaderString.next();
												if (joueur instanceof JAdulte) {
													((JAdulte) joueur).offrirJeuAmis(
															MapsStorage.getJeuxWithRang(Integer.parseInt(entreeString)),
															((JHumain) joueur2));
												}
												break;

											case "2": // par nom
												inputReaderString.reset();
												System.out.println("\nQuel jeu voulez-vous offrir ? (nom/machine)");
												System.out.print("Nom du jeu:\n");
												inputReaderString.nextLine();
												String nomJeu2 = inputReaderString.nextLine();
												System.out.print("Machine:\n");
												String machine2 = inputReaderString.nextLine();
												if (joueur instanceof JAdulte) {
													((JAdulte) joueur).offrirJeuAmis(nomJeu2, machine2,((JHumain) joueur2));
												}
												break;

											case "3": // retour
												break;
											}

										}

										break;
									}
								}
							}
							break;

						case "6":// Machines
							while (!retour) {
								retour = false;
								System.out.println("\n0 - Retour au menu principal");
								System.out.println("1 - Afficher toutes les machines");
								System.out.println("2 - Afficher mes machines");
								System.out.println("3 - Ajouter une machine\n");

								entreeString = inputReaderString.next();

								switch (entreeString) {// switch31
								case "0":
									retour = true;
									break;

								case "1":// affiche tt machine
									MapsStorage.afficherMachine(MapsStorage.getArrayMachine());
									break;

								case "2": // afficher mes machines
									MapsStorage.afficherMachine(((JHumain) joueur).getArrayMachine());
									break;

								case "3": // ajouter machine
									MapsStorage.afficherMachine(MapsStorage.getArrayMachine());
									System.out.println("Quelle machine voulez-vous ajouter ?");
									machine = inputReaderString.next();
									((JHumain) joueur).ajouterMachine(machine);
									break;
								}
							}
							break;

						}// switch21
					} // while21
					break;
				}

			case "2": // inscription
				while (!retour) {
					retour = false;
					System.out.println("\n0 - Retour au menu principal");
					System.out.println("1 - Serait-ce un boutton magique ?");
					System.out.println("2 - Se cr�er un compte\n");
					entreeString = inputReaderString.next();

					switch (entreeString) {

					case "0":
						retour = true;
						break;

					case "1":
						System.out.println("Perdu pour cette fois ! \n");
						break;

					case "2":
						while (!retour) {
							retour = false;
							System.out.println(
									"Quel sera votre pseudo ? (Une modification du pseudo est effectu� s'il est d�j� occup�)");
							String pseudo = inputReaderString.next();
							try {
								System.out.println("Quel est votre date de naissance ? (aaaa-mm-jj)");
								String date = inputReaderString.next();
								LocalDate anneeDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
								if (JHumain.estJoueurMajeur(anneeDate)) {
									System.out.println("Quel est votre adressse mail ? (ex: pseudo@entreprise.com)");
									String mail = inputReaderString.next();
									System.out.println("\nVoici la liste des machines disponible: ");
									MapsStorage.afficherMachine(MapsStorage.machineExistante);
									System.out.println("Quelle machine poss�dez-vous ? ");
									String machine = inputReaderString.next();
									if (MapsStorage.machineExiste(machine)) {
										System.out.println(
												"Quel type de profil souhaitez vous ?\n1 - Gold\n2 - Standard");
										entreeString = inputReaderString.next();
										switch (entreeString) {
										case "1":
											new Gold(pseudo, anneeDate, mail, machine).afficherInfoJoueur();
											break;

										case "2":
											new Standard(pseudo, anneeDate, mail, machine).afficherInfoJoueur();
											break;
										}
										retour = true;
									}
								}else {
									System.err.println("\nVous devez �tre majeur pour vous inscrire. Pas de fausse date svp ;)");
								}
							} catch (java.time.format.DateTimeParseException e) {
								System.err.println(e);
							}
						}
						break;

					}// switch22
				} // while22

				break;
			}// switch1
			
		} // while1

	}// main
}// classe
