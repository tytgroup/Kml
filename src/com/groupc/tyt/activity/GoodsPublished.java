package com.groupc.tyt.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.adapter.AnimateFirstDisplayListener;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.groupc.tyt.util.HttpClientUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsPublished extends Activity implements OnClickListener{

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"FinishTradeService";
	private String url2=ConstantDef.BaseUil+"CancelTradeService";
	private Map<String, String> map;
	private ImageView gdimage;
	private TextView gdname;
	private TextView gdprice;
	private TextView pid;
	private TextView ptime;
	private TextView gnum;
	private TextView gstate;
	private LinearLayout ausr;
	private LinearLayout commentseller;
	private Button pubcancel;
	private Button finish;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("已发布的交易");
		getActionBar().setTitle(spannableString);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.goods_published);
		
		gdimage = (ImageView) findViewById(R.id.list_image);
		gdname = (TextView) findViewById(R.id.gdname);
		gdprice = (TextView) findViewById(R.id.gdprice);
		pid = (TextView) findViewById(R.id.pid);
		gnum=(TextView) findViewById(R.id.goods_num);
		ptime = (TextView) findViewById(R.id.ptime);	
		gstate=(TextView) findViewById(R.id.goods_state);
		
		ausr = (LinearLayout) findViewById(R.id.ausr);
		commentseller = (LinearLayout) findViewById(R.id.relative);
		pubcancel = (Button) findViewById(R.id.pubcancel);
		finish = (Button) findViewById(R.id.finish);
		
		commentseller.setOnClickListener(this);
		pubcancel.setOnClickListener(this);
		finish.setOnClickListener(this);
		ausr.setOnClickListener(this);
		
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
		gnum.setText(map.get("gquantity"));
		ptime.setText(map.get("ptime"));
		if(map.get("gstate").equals("0")){
		gstate.setText("审核中");
		}
		else if(map.get("gstate").equals("1")){
			gstate.setText("商城交易中");
		}
		else{
			gstate.setText("已下架");
		}
		String imageurl = ConstantDef.BaseImageUil+map.get("gpicture")+".jpg";
		imageLoader.displayImage(imageurl, gdimage, options,
				animateFirstListener);
		// transstate =(TextView)findViewById(R.id.trans_state);
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
//				.delayBeforeLoading(2000)
				// 延时
				.displayer(new RoundedBitmapDisplayer(70))
				.displayer(new FadeInBitmapDisplayer(100)).build();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch(id){
		case R.id.relative:
			SplashActivity4.gid=map.get("gid");
			startActivity(new Intent(GoodsPublished.this,SplashActivity4.class));
			break;
		case R.id.pubcancel:
			new Thread(runnable2).start();
			break;
		case R.id.finish:
			new Thread(runnable).start();
			break;
		case R.id.ausr:
			SplashActivity3.gid=map.get("gid");
			startActivity(new Intent(GoodsPublished.this,SplashActivity3.class));
			break;
		}
	}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
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
		    if(feedback.equals("finishok")){
		    	Toast.makeText(getApplicationContext(), "操作成功！", Toast.LENGTH_SHORT).show();
		    	ConstantDef.publishflag=1;
		    	finish();
		    }
		    else if(feedback.equals("finishno")){
		    	Toast.makeText(getApplicationContext(), "操作失败请重试！", Toast.LENGTH_SHORT).show();
		    }
		    else if(feedback.equals("cancelok")){
		    	Toast.makeText(getApplicationContext(), "取消成功！", Toast.LENGTH_SHORT).show();
		    	ConstantDef.publishflag=1;
		    	finish();
		    }
		    else{
		    	Toast.makeText(getApplicationContext(), "操作失败请重试！", Toast.LENGTH_SHORT).show();
		    }
	    }
	    }
	}; 
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("gid", map.get("gid")));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
	Runnable runnable2 = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("gid", map.get("gid")));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url2, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
}
