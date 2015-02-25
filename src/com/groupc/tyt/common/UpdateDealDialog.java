package com.groupc.tyt.common;

import com.groupc.tyt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UpdateDealDialog extends Activity implements OnClickListener{
	private  Button btnStop,btnDestroy,btnStart;
	private  TextView txtTitle,txtMessage;
	public static UpdateAPK.DownLoadAPK thread;
	public static String state,title;
	public static boolean shouldFinish=false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_deal_dialog);
		btnStop=(Button)findViewById(R.id.btn_stop);
		btnDestroy=(Button)findViewById(R.id.btn_destroy);
		btnStart=(Button)findViewById(R.id.btn_start);
		txtTitle=(TextView)findViewById(R.id.update_dialog_title);
		txtMessage=(TextView)findViewById(R.id.update_dialog_message);
		txtTitle.setText(title);
		txtMessage.setText(state);
		btnStop.setOnClickListener(this);
		btnDestroy.setOnClickListener(this);
		btnStart.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_stop:
			thread.setStop();
			state="暂停下载";
			break;
		case R.id.btn_destroy:
            thread.setDestroy();
            state="停止下载";
			break;
		case R.id.btn_start:
			thread.setStart();
			state="正在下载";
			break;
		}
		finish();
	}
	protected void onResume() {
	  super.onResume();
	  if(shouldFinish){
		  shouldFinish=false;
		  finish();
	  }
	}
}