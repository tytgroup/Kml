package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.groupc.tyt.R;
import com.groupc.tyt.adapter.RelativeApplyAdapter;
import com.groupc.tyt.util.HttpClientUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class RelativeApplyActivity extends Activity{

	private String feedback;
	private ListView listview;
	private List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
	private String title = "applyuser", keys[] = { "bname","aid","gname","astate","gquantity","uid"};
	private RelativeApplyAdapter adapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_apply_activity);
		
		Bundle extras = getIntent().getExtras();//获取传递过来的json
		feedback=extras.getString("feedback");
		try {
			mylist = HttpClientUtil.jsonToList(feedback, title, keys);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e("json", "json解析出错");
		}
		listview=(ListView)findViewById(R.id.applylistview);
		adapter=new RelativeApplyAdapter(getApplicationContext(), mylist);
		listview.setAdapter(adapter);
	}	
}
