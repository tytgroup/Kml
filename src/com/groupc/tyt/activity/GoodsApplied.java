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
import android.os.Bundle;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GoodsApplied extends Activity{
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private Map<String,String> map;
	private ImageView gdimage;
	private TextView gdname;
	private TextView gdprice;
	private TextView apid;
	private TextView gnum;
	private TextView atime;
	private TextView applystate;
	private LinearLayout commentbuyer;
	private Button apcancel;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("已申请的交易");
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.goods_applied);
		gdimage =(ImageView)findViewById(R.id.list_image);
		gdname =(TextView)findViewById(R.id.gdname);
		gdprice =(TextView)findViewById(R.id.gdprice);
		apid =(TextView)findViewById(R.id.apid);
		atime =(TextView)findViewById(R.id.atime);
		gnum=(TextView)findViewById(R.id.gnum);
		applystate =(TextView)findViewById(R.id.apply_state);
		commentbuyer =(LinearLayout)findViewById(R.id.relative);
		apcancel =(Button)findViewById(R.id.apcancel);
		
		commentbuyer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
     		
     	});
		
		apcancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
     		
     	});
		
			
		Bundle extras = getIntent().getExtras();//获取传递过来的json
		SerializableMap smap=(SerializableMap)extras.getSerializable(("map"));
		map = smap.getMap();
		
		ini();
		setView();	 
	}
	
	public void setView(){

		gdname.setText(map.get("gname"));
		gdprice.setText(map.get("price"));
		apid.setText(map.get("aid"));
		atime.setText(map.get("atime"));
		/** 待改进**/
//		gnum.setText(map.get("gquantity")); 
		applystate.setText(map.get("astate"));
		
		String imageurl=ConstantDef.BaseImageUil+map.get("gpicture");
		imageLoader.displayImage(imageurl, gdimage, options, animateFirstListener);
//		transstate =(TextView)findViewById(R.id.trans_state);
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
    public void ini(){
    	imageLoader = ImageLoader.getInstance();
      options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.im_chatroom_msg_loading)
	.showImageForEmptyUri(R.drawable.ic_launcher)
	.showImageOnFail(R.drawable.im_chatroom_msg_failed)
	.resetViewBeforeLoading(true)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//缩放图片
	.delayBeforeLoading(2000)//延时
	.displayer(new RoundedBitmapDisplayer(70))
	.displayer(new FadeInBitmapDisplayer(100))
	.build();
}
}

