package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.adapter.GoodsListViewAdapter;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import com.groupc.tyt.R;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Applied_Activity extends Activity {
	private ListView listView1;
	private String feedback;
	private List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
	private String title = "apply", keys[] = { "gname","gprice","gpicture" };
	private GoodsListViewAdapter adapter;
	
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar actionBar = getActionBar();
	Resources r = getResources();
	Drawable myDrawable = r.getDrawable(R.drawable.top_back);
	actionBar.setBackgroundDrawable(myDrawable);
	SpannableString spannableString = new SpannableString("已申请的交易");
	spannableString.setSpan(new TypefaceSpan("monospace"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	getActionBar().setTitle(spannableString);
    actionBar.setDisplayHomeAsUpEnabled(true);
	setContentView(R.layout.goods_list);
	
	listView1 = (ListView) this.findViewById(R.id.list);
	Bundle extras = getIntent().getExtras();//获取传递过来的json
	feedback=extras.getString("feedback");
	
		try {
			mylist = HttpClientUtil.jsonToList(feedback, title, keys);
			adapter = new GoodsListViewAdapter(getApplicationContext(), mylist);
			listView1.setAdapter(adapter);			
					 Log.e("json", "uid="+User.uid+"|name="+User.name+"|uno="+User.uno
							 +"|phone="+User.phone+"|tx="+User.tx+"|rzjg="+User.rzjg
							 +"|jf="+User.jf+"|hydj="+User.hydj+"|xydj="+User.xydj);	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e("json", "json解析出错");
		}

	listView1.setOnItemClickListener(new OnItemClickListener() {//点击listview中的某项
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent=new Intent();
			intent.setClass(Applied_Activity.this, Goods_Applied.class);
			startActivity(intent);	      
			}
		});
}

}
