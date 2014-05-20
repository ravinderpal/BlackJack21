/*
 * BLACKJACK 21!
 * 
 * created by Singh Ravinder Pal
 * 
 * 
 */

package com.example.blackjack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BlackJackActivity extends Activity {
	private TextView mInfoTextView;
	private TextView mBalance;
	private TextView mBid;
	private TextView mPlayer_counter;
	private TextView mDealer_counter;
	private BJGame mBJGame;
	private Button hit, stand, bidUp,bidDown;
	private CardView playerCard, dealerCard;
	private int balanceValue=1500;
	private int bidValue=100;
	private int hit_counter=0;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.black_jack, menu);
		super.onCreateOptionsMenu(menu);
		
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black_jack);
		
		//CardView.setTextAppearance(getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
		mInfoTextView=(TextView) findViewById(R.id.info);
		mBalance=(TextView) findViewById(R.id.countBalance);
		mBalance.setText("1500");
		mInfoTextView.setText("Select a Bid!");
		mBid=(TextView) findViewById(R.id.countBid);
		mBid.setText("100");
		hit=(Button) findViewById(R.id.BT_Hit);
		bidUp=(Button) findViewById(R.id.BT_BidUp);
		bidDown=(Button) findViewById(R.id.BT_BidDown);
		stand=(Button) findViewById(R.id.BT_Stand);
		mPlayer_counter=(TextView) findViewById(R.id.player_counter);
		mDealer_counter=(TextView) findViewById(R.id.dealer_counter);
		
		playerCard=(CardView)findViewById(R.id.playerCard);
		dealerCard=(CardView)findViewById(R.id.dealerCard);
		
		stand.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				bidUp.setEnabled(false);
				bidDown.setEnabled(false);
				mBJGame.secondHand();
				stand.setEnabled(false);
				hit.setEnabled(false);
				printWinner(mBJGame.checkForWinner());
				updateScreen();

			}
        });
		
		hit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				bidUp.setEnabled(false);
				bidDown.setEnabled(false);
				hit_counter++;
				updateScreen();
				if(hit_counter<=3){
					mBJGame.wantAnotherCard();
					checkBust();
					updateScreen();
					if(hit_counter==3){
						hit_counter=0;
						hit.setEnabled(false);
						stand.setEnabled(false);
						mBJGame.secondHand();
						checkBust();
						updateScreen();
					}
				}
			}
		});
		
		bidUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bidValue<500&&balanceValue!=0){
					bidValue+=100;
				    balanceValue-=100;
					updateScreen();
				}
			}
		});
		
		bidDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bidValue>100){
					bidValue-=100;
					balanceValue+=100;
					updateScreen();
				}
			}
		});
		
		startNewGame();
	}
	
	
	
	public void startNewGame(){ 
		
		mBJGame=new BJGame();
		
		mBJGame.playerCards.clear();
		mBJGame.dealerCards.clear();
		bidValue=100;
		balanceValue=balanceValue-bidValue;
		if(balanceValue<=0)
			theEnd();
		else{
			hit.setEnabled(true);
			stand.setEnabled(true);
			bidUp.setEnabled(true);
			bidDown.setEnabled(true);
			Toast.makeText(this, "Select a BID!", Toast.LENGTH_SHORT).show();
			updateScreen();
			mBJGame.firstHand();
			mInfoTextView.setText("Hit or Stand?");
			updateScreen();	
		}
	}
	
	public void theEnd(){
		Toast.makeText(this, "IL GIOCO E' FINITO! HAI PERSO!!!!!", Toast.LENGTH_LONG).show();
		mInfoTextView.setTextSize(20);
		mInfoTextView.setText("GAME OVER!!!");
		bidUp.setEnabled(false);
		bidDown.setEnabled(false);
		hit.setEnabled(false);
		stand.setEnabled(false);
		mBJGame.playerCards.clear();
		mBJGame.dealerCards.clear();
	}
	
	public void updateScreen(){
		playerCard.updateView(mBJGame.playerCards);
		dealerCard.updateView(mBJGame.dealerCards);
		mDealer_counter.setText(String.valueOf(mBJGame.getSumDealer()));
		mPlayer_counter.setText(String.valueOf(mBJGame.getSumPlayer()));
		mBalance.setText(Integer.toString(balanceValue));
		mBid.setText(Integer.toString(bidValue));
		
	}
	
	public void checkBust(){
		if(mBJGame.getSumPlayer()>21||mBJGame.getSumDealer()>21){
			printWinner(mBJGame.checkForWinner());
			hit.setEnabled(false);
			stand.setEnabled(false);
		}
	}
	

	public void printWinner(int x){
		if(x==0){
			mInfoTextView.setText("DRAW");
			balanceValue+=bidValue;
			updateScreen();}
		else if(x==1){
			mInfoTextView.setText("YOU WIN!!!!!!");
		    balanceValue+=2*bidValue;
		    updateScreen();}
		else{
			mInfoTextView.setText("YOU LOSE!!!!");
			updateScreen();
			if(balanceValue==0)
				theEnd();}
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.newgame:
			startNewGame();
			break;

		case R.id.quit:
			onCreateDialog().show();
			break;
		}
		
		return true;
	}
	
	protected Dialog onCreateDialog(){
		Dialog dialog=null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		
		
			builder.setMessage("Really close?");
			builder.setCancelable(false);
			builder.setNegativeButton("No", null);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					BlackJackActivity.this.finish();
					
				}
			});
			dialog=builder.create();
			
		return dialog;
	}
	
	
}
