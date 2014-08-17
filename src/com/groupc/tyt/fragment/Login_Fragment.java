package com.groupc.tyt.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.Reg_Activity;
import com.groupc.tyt.util.HttpClientUtil;
import android.content.Intent;
import android.os.Bundle;
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

public class Login_Fragment extends Fragment {

	private ImageButton LoginButton;
	private EditText editText1,editText2;
	private String name,psd;
	private List<NameValuePair> params;
	private TextView txt_show;
	private String url="http://192.168.1.100:8080/json/login.jsp",title="test",keys[]={"goodId","goodName","goodPicture","goodNum"};
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     	View v = inflater.inflate(R.layout.login, container, false);
	     	editText1=(EditText)v.findViewById(R.id.editText1);
	     	editText2=(EditText)v.findViewById(R.id.editText2);
	     	txt_show=(TextView)v.findViewById(R.id.textShow);
	     	LoginButton=(ImageButton)v.findViewById(R.id.LoginButton);

		return v;

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		Log.e("xsc", "xs");
		name=editText1.getText().toString();
		psd=editText2.getText().toString();
		
		LoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("nickname", "name"));
				params.add(new BasicNameValuePair("psd", "psd"));
				JSONObject jsonObject=new JSONObject();
                jsonObject = HttpClientUtil.httpPostClient(getActivity(), url, params);
                List<Map<String,String>> mylist=new ArrayList<Map<String,String>>();
                try {
					mylist=HttpClientUtil.jsonToList(jsonObject, title, keys);
					for(int i=0;i<mylist.size();i++){
						for(int j=0;j<keys.length;j++){
						txt_show.append(mylist.get(i).get(keys[j]));
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.e("json", "json½âÎö³ö´í");
				}
			}
		});
	
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, 1, 0, "×¢²á")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			Intent i = new Intent(getActivity().getBaseContext(),
					Reg_Activity.class);
			startActivity(i);
			break;
		}
		return true;

	}

}