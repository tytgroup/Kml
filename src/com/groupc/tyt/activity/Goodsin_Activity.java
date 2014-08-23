package com.groupc.tyt.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupc.tyt.R;

public class Goodsin_Activity extends Activity {
	private ImageView gdimage;
	private TextView gdname;
	private ImageButton favor;
	private TextView gdprice;
	private TextView gddescribe;
	private Button cfap;
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar actionBar = getActionBar();
	SpannableString spannableString = new SpannableString("返回");
	getActionBar().setTitle(spannableString);
    actionBar.setDisplayHomeAsUpEnabled(true);
	setContentView(R.layout.goods_detail);
	
	gdimage=(ImageView)findViewById(R.id.gdimage);
	gdname=(TextView)findViewById(R.id.gdname);
	gdprice=(TextView)findViewById(R.id.gdprice);
	gddescribe=(TextView)findViewById(R.id.gddescribe);
	favor=(ImageButton)findViewById(R.id.favorbutton);
	cfap=(Button)findViewById(R.id.cfap);
	
	favor.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
 		
 	});
	
	cfap.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
 		
 	});
}

}
