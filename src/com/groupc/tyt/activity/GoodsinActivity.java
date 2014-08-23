package com.groupc.tyt.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.adapter.AnimateFirstDisplayListener;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class GoodsinActivity extends Activity {
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<String,String> map;
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"ApplyService";
	private String gid,uid,atime,gquantity;
	private ImageView gdimage;
	private TextView gdname;
	private ImageButton favor;
	private TextView gdprice;
	private TextView gddescribe;
	private EditText num;
	private Button cfap;
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar actionBar = getActionBar();
	SpannableString spannableString = new SpannableString("返回");
	getActionBar().setTitle(spannableString);
    actionBar.setDisplayHomeAsUpEnabled(true);
	setContentView(R.layout.goods_detail);
	
	num=(EditText)findViewById(R.id.num);
	gdimage=(ImageView)findViewById(R.id.gdimage);
	gdname=(TextView)findViewById(R.id.gdname);
	gdprice=(TextView)findViewById(R.id.gdprice);
	gddescribe=(TextView)findViewById(R.id.gddescribe);
	favor=(ImageButton)findViewById(R.id.favorbutton);
	cfap=(Button)findViewById(R.id.cfap);	
	favor.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
 		
 	});
	
	cfap.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			gid=map.get("gid");
			uid = User.uid;
			Date date = new Date();
			atime = sdf.format(date);
			gquantity=num.getText().toString();
        	new Thread(runnable).start();
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
		gddescribe.setText(map.get("gdescribe"));		
		String imageurl=ConstantDef.BaseUil+"images/shu_lib.jpg";
		imageLoader.displayImage(imageurl, gdimage, options, animateFirstListener);
//		transstate =(TextView)findViewById(R.id.trans_state);
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
		    if(feedback.equals("ok")){
		    	Toast.makeText(getApplicationContext(), "申请成功！", Toast.LENGTH_SHORT).show();
		    }
		    else{
		    	Toast.makeText(getApplicationContext(), "申请失败！", Toast.LENGTH_SHORT).show();
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
			params.add(new BasicNameValuePair("gid", gid));
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("atime", atime));
			params.add(new BasicNameValuePair("gquantity", gquantity));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
}
