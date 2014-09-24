package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.groupc.tyt.adapter.GoodsListViewAdapter;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import com.groupc.tyt.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AppliedActivity extends Activity {
	private ListView listView1;
	private String feedback;
	private List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
	private String title = "apply", keys[] = { "uid","gname","price","gpicture","aid","atime","astate","gquantity","brelative","srelative"};
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

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e("json", "json解析出错");
		}

		listView1.setOnItemClickListener(new OnItemClickListener() {//点击listview中的某项
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent();
				Bundle value=new Bundle();
				SerializableMap smap=new SerializableMap();  
	            smap.setMap(mylist.get(position));  
				value.putSerializable("map", smap);
				intent.putExtras(value);
				intent.setClass(AppliedActivity.this, GoodsApplied.class);
				startActivity(intent);	      
				}
			});
	}
    protected void onResume() {
		super.onResume();
		if( ConstantDef.applyflag==1){
      startActivity(new Intent(AppliedActivity.this,SplashActivity.class));
      ConstantDef.applyflag=0;
      finish();
		}
    }
    public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	             finish();
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
		}

	}