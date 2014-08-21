package com.groupc.tyt.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.groupc.tyt.R;

public class Reg_Activity extends Activity{
	private EditText stunum;
	private EditText usrname;
	private EditText usrpsw;
	private EditText usrpsw2;
	private EditText usrphone;
	private Button upstuphoto;
	private Button upusrphoto;
	private Button cfreg;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("登录");
		spannableString.setSpan(new TypefaceSpan("monospace"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.reg);
		stunum = (EditText)findViewById(R.id.stunum);
		usrname = (EditText)findViewById(R.id.usrname);
		usrpsw = (EditText)findViewById(R.id.usrpsw);
		usrpsw2 = (EditText)findViewById(R.id.usrpsw2);
		usrphone = (EditText)findViewById(R.id.usrphone);
		upstuphoto= (Button)findViewById(R.id.upstuphoto);
		upusrphoto= (Button)findViewById(R.id.upusrphoto);
		cfreg= (Button)findViewById(R.id.cfreg);
		
		upstuphoto.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
                  
            }    
  
        });    
		upusrphoto.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
                  
            }    
  
        });   
		cfreg.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
                  
            }    
  
        });   
	}

	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	            Intent intent = new Intent(this, MainActivity.class);  
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	            startActivity(intent);  
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
}
