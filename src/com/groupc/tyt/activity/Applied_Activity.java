package com.groupc.tyt.activity;

import java.util.List;

import com.groupc.tyt.util.ShopService;
import com.groupc.tyt.util.Shop;
import com.groupc.tyt.util.ShopListAdapter;
import com.groupc.tyt.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Applied_Activity extends Activity {
	private ListView listView1;
	 private String jsonstring;
	 private ShopListAdapter shopListAdapter;
	 private List<Shop> shoplist;
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
	jsonstring=extras.getString("jsonstring");
	getdata();//初始化数据
	
	listView1.setOnItemClickListener(new OnItemClickListener() {//点击listview中的某项
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent=new Intent();
			intent.setClass(Applied_Activity.this, Goods_Applied.class);
			startActivity(intent);
	      
			}
		});
}
/*
 * 初始化获取数据
 */
private void getdata(){
	try {
		shoplist=ShopService.getJSONlistshops(jsonstring,1,10);
	} catch (Exception e) {
		e.printStackTrace();
	}
	shopListAdapter = new ShopListAdapter(this, shoplist, listView1);
	shopListAdapter.refreshData(shoplist);
	listView1.setAdapter(shopListAdapter);
}
/*
 * 获取等多数据
 */


}
