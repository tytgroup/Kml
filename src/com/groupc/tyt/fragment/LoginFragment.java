package com.groupc.tyt.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.RegActivity;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LoginFragment extends Fragment {

	private ImageButton LoginButton;
	private EditText editText1, editText2;
	private String name, psd;
	private List<NameValuePair> params;
	private TextView txt_show;
	private String url = ConstantDef.BaseUil+"loginService",
			title = "user", keys[] = { "iduser", "uno", "username","phone","tx","jf","xydj",
					"hydj","rzjg" };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login, container, false);
		editText1 = (EditText) v.findViewById(R.id.editText1);
		editText2 = (EditText) v.findViewById(R.id.editText2);
		txt_show = (TextView) v.findViewById(R.id.textShow);
		LoginButton = (ImageButton) v.findViewById(R.id.LoginButton);

		return v;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

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
				Toast.makeText(getActivity(), "请检查网络连接！", Toast.LENGTH_SHORT)
						.show();
			} 
			else if(feedback.equals("nd")){
				Toast.makeText(getActivity(), "没有数据返回！", Toast.LENGTH_SHORT)
				.show();
			}
			else if(feedback.equals("wtc")){
				Toast.makeText(getActivity(), "网络连接出现问题！", Toast.LENGTH_SHORT)
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
							 Toast.makeText(getActivity(), "登陆成功！", Toast.LENGTH_SHORT)
								.show();
							 
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.e("json", "json解析出错");
				}
			} else {
				if (feedback.equalsIgnoreCase("2")) {
					Toast.makeText(getActivity(), "用户名不存在！", Toast.LENGTH_SHORT)
							.show();
				} else if (feedback.equals("3")) {
					Toast.makeText(getActivity(), "密码不正确!", Toast.LENGTH_SHORT)
							.show();

				} else if (feedback.equals("0")) {
					Toast.makeText(getActivity(), "其他原因错误!", Toast.LENGTH_SHORT)
							.show();

				}
				else{
					Toast.makeText(getActivity(), "想死的心都有了!", Toast.LENGTH_SHORT)
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
					.httpPostClient(getActivity(), url, params);	
				msg.obj = feedback;
				handler.sendMessage(msg);
		}

	};

	@SuppressLint("NewApi")
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, 1, 0, "注册")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			Intent i = new Intent(getActivity().getBaseContext(),
					RegActivity.class);
			startActivity(i);
			break;
		}
		return true;

	}

}