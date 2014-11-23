package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.util.HttpClientUtil;
import com.groupc.tyt.util.MailSender;

public class CheckEmailActivity extends Activity{

	private EditText edt_email;
	private Button   btn_submit;
	private String   username,email;
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil+"CheckEmailService";
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_email);
		edt_email = (EditText) findViewById(R.id.edt_email);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		username = getIntent().getStringExtra("username");
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email=edt_email.getText().toString();
				if(email.isEmpty()){
					Toast.makeText(getApplicationContext(), "请先输入邮箱", Toast.LENGTH_SHORT).show();
				}else{
					new Thread(runnable).start();
				}
			}
		});
	}
	Runnable runnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nickname", username));
			params.add(new BasicNameValuePair("email", email));
			String feedback;
			feedback = HttpClientUtil
					.httpPostClient(getApplicationContext(), url, params);	
		    msg.obj = feedback;
		    handler.sendMessage(msg);
		}
		
	};
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			final String feedback = (String) msg.obj;
			if (feedback.equals("null")) {
				Toast.makeText(getApplicationContext(), "请检查网络连接！", Toast.LENGTH_SHORT).show();
			}else if(feedback.equals("nd")){
				Toast.makeText(getApplicationContext(), "没有数据返回！", Toast.LENGTH_SHORT).show();
			}else if(feedback.equals("wtc")){
				Toast.makeText(getApplicationContext(), "网络连接出现问题！", Toast.LENGTH_SHORT).show();
			}else if(feedback.equals("no user")){
				Toast.makeText(getApplicationContext(), "用户名不对", Toast.LENGTH_SHORT).show();
			}else if(feedback.equals("wrong email")){
				Toast.makeText(getApplicationContext(), "请确保邮箱为绑定邮箱", Toast.LENGTH_SHORT).show();
			}else if(feedback.equals("other wrong")){
				Toast.makeText(getApplicationContext(), "其它错误，请稍后重试", Toast.LENGTH_SHORT).show();
			}else{
				new Thread(new Runnable() {		
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						MailSender sender = new MailSender("tyt_team_service@163.com", "tyt1234team");
						try {
							sender.send(email, "挑一挑客服端密码反馈", "尊敬的"+username+"：您的密码为："+feedback.substring(4)+"请及时修改您的密码");
							msg.obj="ok";
						} catch (AddressException e) {
							// TODO Auto-generated catch block
							msg.obj="邮件发送错误：地址出错！";
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							msg.obj="邮件发送错误：网络出错！";
							e.printStackTrace();
						}finally{
							handler2.sendMessage(msg);
						}
					}
				}).start();
			}
		}
	};
	Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				finish();
			}else{
			final String feedback = (String) msg.obj;
			if(feedback.equals("ok")){
			Toast.makeText(getApplicationContext(),"已向您的邮箱发送邮件，请及时确认，谢谢！", Toast.LENGTH_SHORT).show();
			handler2.sendEmptyMessageDelayed(1,3000);
			}else{
			Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_SHORT).show();
			}
		}
		}
      };
}
