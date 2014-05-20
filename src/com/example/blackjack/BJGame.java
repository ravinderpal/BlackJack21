package com.example.blackjack;

import java.util.ArrayList;
import java.util.Random;

public class BJGame {
	private int userBalance=1000;
	public ArrayList<Card> playerCards=new ArrayList<Card>();
	public ArrayList<Card> dealerCards=new ArrayList<Card>();
	boolean gameOverFlag;
	int bid;
	int mazzoCounter=0;
	
	public static final int VINCITA_BANCO=-1;
	public static final int VINCITA_PLAYER=1;
	public static final int VINCITA_DRAW=0;
				
	Card[] mazzo=new Card[52];
	
	private Random mRand=new Random();
	
	//carte
	public BJGame(){
		for(int i=0; i<4; i++){      //SEME
			for(int j=1; j<14; j++){ //NUMERO
				mazzo[13*i+j-1]=new Card(j, i);
			}
		}
		
		Card temp=null; //var. temp.
		int rand;
		//miscuglio carte
		for(int i=0; i<52; i++){
			rand=mRand.nextInt(52);
			temp=mazzo[i];
			mazzo[i]=mazzo[rand];
			mazzo[rand]=temp;
		}
	}
	
	
	public int getUserBalance(){
		return userBalance;
	}
	
	public int getRemainingCards(){
		return 52-mazzoCounter;
	}
	
	public void firstHand(){
		playerCards.add(mazzo[mazzoCounter]);
		mazzoCounter++;
		playerCards.add(mazzo[mazzoCounter]);
		mazzoCounter++;
		
		dealerCards.add(mazzo[mazzoCounter]);
		mazzoCounter++;
		
	}
	
	public void wantAnotherCard(){
		playerCards.add(mazzo[mazzoCounter]);
		mazzoCounter++;
	}
	
	public void secondHand(){
		while(getSumDealer()<17){
			dealerCards.add(mazzo[mazzoCounter]);
			mazzoCounter++;
		}
	}
	
	public int getSumOfCard(ArrayList<Card> a){
		int counter=0;
		for(int i=0;i<a.size();i++){  
			if(a.get(i).getValore()==1&&counter<=10){ // valore per A (1 o 10)
				counter+=10;
			}
			counter+=a.get(i).getValore();//valori per le altre carte
		}
		return counter;
	}
	
	public int getSumDealer(){
		return getSumOfCard(dealerCards);
	}
	
	public int getSumPlayer(){
		return getSumOfCard(playerCards);
	}
	
	public int checkForWinner(){
		
		if (getRemainingCards()<15){
			gameOverFlag=true;
		}
		if(getSumPlayer()>21){
			return VINCITA_BANCO;
			
		}else if(getSumDealer()>21){
			userBalance+=2*bid;
			return VINCITA_PLAYER;
			
		}else if(getSumPlayer()>getSumDealer()){
			userBalance+=2*bid;
			return VINCITA_PLAYER;
		}else if(getSumPlayer()<getSumDealer()){
			return VINCITA_BANCO;
		}else{
			userBalance+=bid;
			return VINCITA_DRAW;
			
		}
		
	}
	
	}
	
	
	
	






