package com.groupc.tyt.activity;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.MailSender;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdviceActivity extends Activity{

	private EditText edt_message,edt_connection;
	private Button btn_submit;
	private String email,message;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advice);
		edt_message=(EditText)findViewById(R.id.leave_message);
		edt_connection=(EditText)findViewById(R.id.connection);
		if(!User.email.isEmpty()){
			edt_connection.setText(User.email);
		}
		btn_submit=(Button)findViewById(R.id.submit_button);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = edt_connection.getText().toString();
				message = edt_message.getText().toString();
				if(message.equals("")){
					Toast.makeText(getApplicationContext(), "请先填写建议", Toast.LENGTH_SHORT).show();
				}
				else{		
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg = new Message();
							MailSender sender = new MailSender("tyt_team_service@163.com", "tyt1234team");
							try {
								sender.send("tyt_team_service@163.com", "挑一挑客服端客服的反馈", "用户名为"+User.name+"的反馈信息："+message
										+"。用户联系方式:邮箱："+email+";电话："+User.phone);
								msg.obj="ok";
							} catch (AddressException e) {
								// TODO Auto-generated catch block
								msg.obj="反馈发送错误！";
								e.printStackTrace();
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								msg.obj="反馈发送错误：网络出错！";
								e.printStackTrace();
							}finally{
								handler.sendMessage(msg);
							}
						}
					}).start();
				}
			}
		});
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				finish();
			}else{
			final String feedback = (String) msg.obj;
			if(feedback.equals("ok")){
			Toast.makeText(getApplicationContext(),"感谢您的反馈，我们会积极改进，谢谢！", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(1,3000);
			}else{
			Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_SHORT).show();
			}
		}
		}
      };
}
