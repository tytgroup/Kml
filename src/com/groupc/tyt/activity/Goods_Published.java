package com.groupc.tyt.activity;

import com.groupc.tyt.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Goods_Published extends Activity{
	private ImageView gdimage;
	private TextView gdname;
	private TextView gdprice;
	private TextView pid;
	private TextView ptime;
	private TextView ausr;
	private TextView commentseller ;
	private Button pubcancel;
	private Button finish;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("������Ľ���");
		spannableString.setSpan(new TypefaceSpan("monospace"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.goods_published);
		gdimage =(ImageView)findViewById(R.id.list_image);
		gdname =(TextView)findViewById(R.id.gdname);
		gdprice =(TextView)findViewById(R.id.gdprice);
		pid =(TextView)findViewById(R.id.pid);
		ptime =(TextView)findViewById(R.id.ptime);
		ausr =(TextView)findViewById(R.id.ausr);
		commentseller =(TextView)findViewById(R.id.p4);
		pubcancel =(Button)findViewById(R.id.pubcancel);
		finish =(Button)findViewById(R.id.finish);
		
		commentseller.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
     		
     	});
		
		pubcancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
     		
     	});
		finish.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
     		
     	});
		
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	            Intent intent = new Intent(this, Applied_Activity.class);  
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	            startActivity(intent);  
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
}

