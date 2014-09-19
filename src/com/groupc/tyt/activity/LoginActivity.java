package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.RegisterFirstStepActivity;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LoginActivity  extends Activity{

	private Button LoginButton;
	private EditText editText1, editText2;
	private String name, psd;
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil+"loginService",
			title = "user", keys[] = { "iduser", "uno", "username","phone","tx","jf","xydj",
					"hydj","rzjg" };
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.login);
		editText1 = (EditText) findViewById(R.id.edt_username);
		editText2 = (EditText) findViewById(R.id.edt_password);
		LoginButton = (Button) findViewById(R.id.LoginButton);

		LoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = editText1.getText().toString();
				psd = editText2.getText().toString();
				new Thread(runnable).start();
			}
		});

	}

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
				List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
				try {
					mylist = HttpClientUtil.jsonToList(feedback, title, keys);
	
					        int i=0;
							 User.uid=mylist.get(i).get("iduser");
							 User.name=mylist.get(i).get("username");
							 User.uno=mylist.get(i).get("uno");
							 User.phone=mylist.get(i).get("phone");
							 User.tx=mylist.get(i).get("tx");
							 User.rzjg=mylist.get(i).get("rzjg").equals("1")?true:false;
							 User.jf=Double.parseDouble(mylist.get(i).get("jf"));
							 User.hydj=Double.parseDouble(mylist.get(i).get("hydj"));
							 User.xydj=Double.parseDouble(mylist.get(i).get("xydj"));
							 Log.e("json", "uid="+User.uid+"|name="+User.name+"|uno="+User.uno
									 +"|phone="+User.phone+"|tx="+User.tx+"|rzjg="+User.rzjg
									 +"|jf="+User.jf+"|hydj="+User.hydj+"|xydj="+User.xydj);
							 Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_SHORT)
								.show();
							 startActivity(new Intent(getApplicationContext(),MainActivity.class));
							 finish();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.e("json", "json解析出错");
				}
			} else {
				if (feedback.equalsIgnoreCase("2")) {
					Toast.makeText(getApplicationContext(), "用户名不存在！", Toast.LENGTH_SHORT)
							.show();
				} else if (feedback.equals("3")) {
					Toast.makeText(getApplicationContext(), "密码不正确!", Toast.LENGTH_SHORT)
							.show();

				} else if (feedback.equals("0")) {
					Toast.makeText(getApplicationContext(), "其他原因错误!", Toast.LENGTH_SHORT)
							.show();

				}
				else{
					Toast.makeText(getApplicationContext(), "想死的心都有了!", Toast.LENGTH_SHORT)
					.show();
				}
			}
		}
		}
	};
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			// Bundle data = new Bundle();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nickname", name));
			params.add(new BasicNameValuePair("psd", psd));
			String feedback;
			feedback = HttpClientUtil
					.httpPostClient(getApplicationContext(), url, params);	
				msg.obj = feedback;
				handler.sendMessage(msg);
		}

	};

	@SuppressLint("NewApi")
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
	     //添加菜单项
		 MenuItem reg=menu.add(0, 1, 0, "注册");
        //绑定到ActionBar  
	    reg.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);   

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			Intent i = new Intent(getApplicationContext(),
					RegisterFirstStepActivity.class);
			startActivity(i);
			finish();
			break;
		}
		return true;

	}

}