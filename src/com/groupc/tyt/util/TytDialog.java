package com.groupc.tyt.util;

import com.groupc.tyt.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TytDialog extends Dialog{

	private TextView tv_title;
	private TextView tv_body;
	private Button btn_left_dialog;
	private Button btn_right_dialog;
	
	public TytDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public TytDialog(Context context, int theme){
	    super(context, theme);
	    }
	protected void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	     this.setContentView(R.layout.tytdialog);
	     tv_title = (TextView) findViewById(R.id.dialog_title);
	     tv_body = (TextView) findViewById(R.id.dialog_body);
	     btn_left_dialog = (Button) findViewById(R.id.dialog_left_btn);
	     btn_right_dialog = (Button) findViewById(R.id.dialog_right_btn);
	}
	
	public void setTitleValue(String title){
		tv_title.setText(title);
	} 
	public void setBody(String body){
		tv_body.setText(body);
	}
	public void setLeftButton(String button,View.OnClickListener onclicklistener){
		btn_left_dialog.setText(button);
		btn_left_dialog.setOnClickListener(onclicklistener);
	}
	public void setRightButton(String button,View.OnClickListener onclicklistener){
		btn_right_dialog.setText(button);
		btn_right_dialog.setOnClickListener(onclicklistener);
	}
}

