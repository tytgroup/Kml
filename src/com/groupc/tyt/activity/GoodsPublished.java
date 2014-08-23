package com.groupc.tyt.activity;

import java.util.Map;

import com.groupc.tyt.R;
import com.groupc.tyt.adapter.AnimateFirstDisplayListener;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsPublished extends Activity {

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private Map<String, String> map;
	private ImageView gdimage;
	private TextView gdname;
	private TextView gdprice;
	private TextView pid;
	private TextView ptime;
	private TextView ausr;
	private TextView commentseller;
	private Button pubcancel;
	private Button finish;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.top_back);
		actionBar.setBackgroundDrawable(myDrawable);
		SpannableString spannableString = new SpannableString("商品信息");
		spannableString.setSpan(new TypefaceSpan("monospace"), 0,
				spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0,
				spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.goods_published);
		gdimage = (ImageView) findViewById(R.id.list_image);
		gdname = (TextView) findViewById(R.id.gdname);
		gdprice = (TextView) findViewById(R.id.gdprice);
		pid = (TextView) findViewById(R.id.pid);
		ptime = (TextView) findViewById(R.id.ptime);
		ausr = (TextView) findViewById(R.id.ausr);
		commentseller = (TextView) findViewById(R.id.p4);
		pubcancel = (Button) findViewById(R.id.pubcancel);
		finish = (Button) findViewById(R.id.finish);
		commentseller.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		pubcancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Bundle extras = getIntent().getExtras();// 获取传递过来的json
		SerializableMap smap = (SerializableMap) extras
				.getSerializable(("map"));
		map = smap.getMap();

		ini();
		setView();
	}

	public void setView() {

		gdname.setText(map.get("gname"));
		gdprice.setText(map.get("price"));
		pid.setText(map.get("gid"));
		ptime.setText(map.get("ptime"));

		String imageurl = ConstantDef.BaseUil + "images/shu_lib.jpg";
		imageLoader.displayImage(imageurl, gdimage, options,
				animateFirstListener);
		// transstate =(TextView)findViewById(R.id.trans_state);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, AppliedActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void ini() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.im_chatroom_msg_loading)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.im_chatroom_msg_failed)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// 缩放图片
				.delayBeforeLoading(2000)
				// 延时
				.displayer(new RoundedBitmapDisplayer(70))
				.displayer(new FadeInBitmapDisplayer(100)).build();
	}
}
