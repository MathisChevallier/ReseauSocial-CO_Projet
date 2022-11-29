package com.appli;

import java.time.LocalDate;

public class PartiesJouees {

	private Joueur j1;
	private Joueur j2;
	private String titre;
	private LocalDate date;
	
	public PartiesJouees(Joueur joueur1, Joueur joueur2, String titre) {
		this.setJoueur1(joueur1);
		this.setJoueur2(joueur2);
		this.setTitre(titre);
		this.date=date.now();
	}
	
	public void setJoueur1(Joueur j) {
		this.j1=j;
	}
	public Joueur getJoueur1() {
		 return this.j1;
	}
	
	public void setJoueur2(Joueur j) {
		this.j2=j;
	}
	public Joueur getJoueur2() {
		 return this.j2;
	}
	
	public void setTitre(String t) {
		this.titre=t;
	}
	public String getTitre() {
		 return this.titre;
	}
	public LocalDate getDate() {
		 return this.date;
	}
}
