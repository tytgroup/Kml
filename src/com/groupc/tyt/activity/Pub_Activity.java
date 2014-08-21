package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Pub_Activity extends Activity{
	private Button addphoto;
	private Button confirm;
	private EditText describe;
	private EditText price;
	private EditText num;
	private CheckBox checkbox1;
	private List<String> list = new ArrayList<String>();
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;  
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("取消发布");
		spannableString.setSpan(new TypefaceSpan("monospace"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.publish);
		list.add("自行车");    
        list.add("书籍");    
        list.add("电子产品");    
        list.add("运动器材");    
        list.add("其它");
        list.add("十万火急");   
         mySpinner = (Spinner)findViewById(R.id.spinner); 
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){    
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {    
             
                arg0.setVisibility(View.VISIBLE);    
            }    
            public void onNothingSelected(AdapterView<?> arg0) {    
                // TODO Auto-generated method stub    
                arg0.setVisibility(View.VISIBLE);    
            }    
        });    
        
        /*下拉菜单弹出的内容选项焦点改变事件处理*/    
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){    
        public void onFocusChange(View v, boolean hasFocus) {    
         }    
        });   
        describe=(EditText)findViewById(R.id.gddecribe);
     	price=(EditText)findViewById(R.id.price);
     	num=(EditText)findViewById(R.id.num);
     	addphoto=(Button)findViewById(R.id.addphoto);
     	confirm=(Button)findViewById(R.id.cfpub);
     	checkbox1=(CheckBox)findViewById(R.id.checkBox1);
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

