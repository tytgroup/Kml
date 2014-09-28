package com.groupc.tyt.activity;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class SplashActivity2  extends Activity {
	
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil+"HavePublishedService";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("加载中");
		getActionBar().setTitle(spannableString);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.splash);	

		Thread thread=new Thread(runnable);//开始下载，UI界面继续保持progress
		thread.start();
	}	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String feedback = (String) msg.obj;

			if (feedback.equals("null")) {
				Toast.makeText(getApplicationContext(), "请检查网络连接！", Toast.LENGTH_SHORT)
						.show();
			} 
			else if(feedback.equals("nd")){
				Toast.makeText(getApplicationContext(), "没有数据返回！", Toast.LENGTH_SHORT)
				.show();
			}
			else if(feedback.equals("wtc")){
				Toast.makeText(getApplicationContext(), "网络连接出现问题！", Toast.LENGTH_SHORT)
				.show();
			}
			else {
			if (HttpClientUtil.isjson(feedback)) {			
					 Intent intent=new Intent(SplashActivity2.this,PublishedActivity.class);
					 intent.putExtra("feedback", feedback);
					 startActivity(intent);
					
			} 
			else {
				if (feedback.equalsIgnoreCase("2")) {
					Toast.makeText(getApplicationContext(), "没有发布过！", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "其他原因错误!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
			 SplashActivity2.this.finish();	
		}
	};
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			// Bundle data = new Bundle();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid", User.uid));
			String feedback;
			feedback = HttpClientUtil
					.httpPostClient(getApplicationContext(), url, params);	
				msg.obj = feedback;
				handler.sendMessage(msg);
		}

	};
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	        	  finish(); 
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
}
