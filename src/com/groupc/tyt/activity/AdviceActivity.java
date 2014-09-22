package com.groupc.tyt.activity;

import com.groupc.tyt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdviceActivity extends Activity{

	private EditText edt_message,edt_connection;
	private Button btn_submit;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advice);
		edt_message=(EditText)findViewById(R.id.leave_message);
		edt_connection=(EditText)findViewById(R.id.connection);
		btn_submit=(Button)findViewById(R.id.submit_button);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edt_message.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "请先填写建议", Toast.LENGTH_SHORT).show();
				}
				else if(edt_connection.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "请先填写邮箱", Toast.LENGTH_SHORT).show();
				}
				else{
				Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
				finish();
				}
			}
		});
	}
}
