package com.appli;

public class Jeux {

	private int rang;	//on considere le rang comme cle car unique, on ne tient pas compte des changements "au demarrage" en cas de modification du fichier jeux
	private String nom;
	private String machine;
	private int annee;
	private String genre;
	private String editeur;

	public Jeux() {
	}
	
	public void setRang(int a) {
		this.rang=a;
	}
	public int getRang() {
		return this.rang;
	}
	
	public void setNom(String a) {
		this.nom=a;
	}
	public String getNom() {
		return this.nom;
	}
	
	public void setMachine(String a) {
		this.machine=a;
	}
	public String getMachine() {
		return this.machine;
	}
	
	public void setAnnee(int a) {
		this.annee=a;
	}
	public int getAnnee() {
		return this.annee;
	}
	
	public void setGenre(String a) {
		this.genre=a;
	}
	public String getGenre() {
		return this.genre;
	}
	
	public void setEditeur(String a) {
		this.editeur=a;
	}
	public String getEditeur() {
		return this.editeur;
	}


}
