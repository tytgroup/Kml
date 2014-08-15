package com.groupc.tyt.activity;


import com.groupc.tyt.R;
import com.groupc.tyt.util.HttpUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class SplashActivity2 extends Activity {
	
	MyHandler myHandler;//消息处理的Handler对象
	String jsonstring;//用来装载网络下载的json，并将传递到下一个activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("加载中");
		spannableString.setSpan(new TypefaceSpan("monospace"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.splash);	
		  myHandler = new MyHandler();//消息处理对象
		  MyThread m = new MyThread();//json数据下载对象
	       new Thread(m).start();//开始下载，UI界面继续保持progress
	}

	
    private String GetSource() throws Exception{
	return HttpUtil.getRequest(HttpUtil.BaseUrl);
    }
    
    /**
     * 内部Handler类
     * 接受消息,处理消息 ,此Handler会与当前主线程一块运行
     * */

@SuppressLint("HandlerLeak")
class MyHandler extends Handler {
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             Bundle b = msg.getData();
             String status = b.getString("status");
             if(status=="ok"){//如果正确获取数据
			 Intent intent=new Intent(SplashActivity2.this,Published_Activity.class);
			 intent.putExtra("jsonstring", jsonstring);//传递到MainActivity页面
			 startActivity(intent);
			 SplashActivity2.this.finish();
             }
         }
     }
/*
 * 此内部线程类用来下载json
 * 处理完后想主线程发送消息
 * */
    class MyThread implements Runnable {
        public void run() {    
                try {
                	Thread.sleep(1000);
                	jsonstring= GetSource();
				} catch (Exception e) {
					e.printStackTrace();
				}
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("status","ok");
                msg.setData(b);
                SplashActivity2.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }
}
