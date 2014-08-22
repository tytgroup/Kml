package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;






import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.util.HttpClientUtil;

public class Reg_Activity extends Activity{
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"RegisterService";
	private String uno,name,psd,phone,img_xsz,img_head;
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
            	uno=stunum.getText().toString();
            	name = usrname.getText().toString();
            	psd = usrpsw.getText().toString();
            	phone = usrphone.getText().toString();
            	img_xsz = null;
            	img_head = null;
            	new Thread(runnable).start();
            }    
  
        });   
	}
	Handler handler = new Handler(){
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
		    if(feedback.equals("ok")){
		    	Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
		    }
		    else{
		    	Toast.makeText(getApplicationContext(), "ע注册失败！", Toast.LENGTH_SHORT).show();
		    }
	    	
	    }
	    }
	}; 
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uno", uno));
			params.add(new BasicNameValuePair("nickname", name));
			params.add(new BasicNameValuePair("psd", psd));
			params.add(new BasicNameValuePair("phone", phone));
			params.add(new BasicNameValuePair("img_xsz", img_xsz));
			params.add(new BasicNameValuePair("img_head", img_head));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
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
