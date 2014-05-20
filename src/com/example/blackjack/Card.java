package com.example.blackjack;

public class Card {
	
	public final static String[] listaNomi={" ", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	public final static char[] listaSemi={'C', 'Q', 'F', 'P'};//COME QUANDO FUORI PIOVE
	public final static String[] listaFullSemi={"Cuori", "Quadri", "Fiori", "Picche"};
	
	private int seme;
	private int num;
	
	public Card(int num, int seme){
		this.num=num;
		this.seme=seme;
	}
	
	public String getNome(){
		return listaNomi[num];
	}
	
	public int getValore(){
		if(num>=10){
			return 10;
		}
		
		return num; //L'asso vale 1 (per ora)
	}
	
	public char getSeme(){
		return listaSemi[seme];
	}
	
	public String getFullSeme(){
		return listaFullSemi[seme];
	}
	
}
