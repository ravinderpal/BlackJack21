package com.example.blackjack;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardView extends LinearLayout {

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(HORIZONTAL);
	}

	public CardView(Context context) {
		super(context);
		this.setOrientation(HORIZONTAL);
	}

	void delete(){
		this.removeAllViews();
	}
	
	public void updateView(ArrayList<Card> a){
		delete();
		for(int i =0; i<a.size(); i++){
			TextView temp=new TextView(this.getContext());
			temp.setTextSize(30);
			temp.setTextColor(Color.RED);
			temp.setText(a.get(i).getNome()+a.get(i).getSeme()+"  ");
			this.addView(temp);
		}
	}

}
