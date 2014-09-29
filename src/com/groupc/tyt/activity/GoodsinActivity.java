package com.groupc.tyt.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.MenuItem;
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

@SuppressLint("SimpleDateFormat")
public class GoodsinActivity extends Activity {
	
	private static int APPLY_TAG=0,COLLECTION_TAG=1,CHANGE_COLLECTION_TAG=2;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<String,String> map;
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"ApplyService";
	private String urlbegin=ConstantDef.BaseUil+"CheckCollection";
	private String urlchange=ConstantDef.BaseUil+"CollectionService";
	private String gid,uid,atime,gquantity;
	private ImageView gdimage;
	private TextView gdname;
	private ImageButton favor;
	private TextView gdprice;
	private TextView gtime;
	private TextView gddescribe;
	private TextView gnum;
	private TextView srzjg;
	private TextView isfavor;
	private EditText num;
	private Button cfap;
	private String isCollection;
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar actionBar = getActionBar();
	SpannableString spannableString = new SpannableString("返回");
	getActionBar().setTitle(spannableString);
    actionBar.setDisplayHomeAsUpEnabled(true);
	setContentView(R.layout.goods_detail);
	
	num=(EditText)findViewById(R.id.num);
	gnum=(TextView)findViewById(R.id.gnum);
	gtime=(TextView)findViewById(R.id.gtime);
	isfavor=(TextView)findViewById(R.id.txt_favor);
	gdimage=(ImageView)findViewById(R.id.gdimage);
	gdname=(TextView)findViewById(R.id.gdname);
	srzjg=(TextView)findViewById(R.id.srzjg);
	gdprice=(TextView)findViewById(R.id.gdprice);
	gddescribe=(TextView)findViewById(R.id.gddescribe);
	favor=(ImageButton)findViewById(R.id.favorbutton);
	cfap=(Button)findViewById(R.id.cfap);	
	favor.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(User.uid.equals("-1")){
			    Toast.makeText(getApplicationContext(), "您还没登录！", Toast.LENGTH_SHORT).show();
			}
			else{
			if(isCollection.equals("1")){
				isfavor.setText("点击收藏");
				favor.setImageResource(android.R.drawable.btn_star_big_off);
				isCollection="0";
			}
			else{
				isfavor.setText("取消收藏");
				favor.setImageResource(android.R.drawable.btn_star_big_on);
				isCollection="1";
			}
			new Thread(runchange).start();
		}
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
			if(gquantity.equals("")){
				Toast.makeText(getApplicationContext(), "请输入要买的数量！", Toast.LENGTH_SHORT).show();
			}
			else if(Integer.parseInt(gquantity)>Integer.parseInt(map.get("gquantity"))){
				Toast.makeText(getApplicationContext(), "购买数量不能多于总数！", Toast.LENGTH_SHORT).show();
			}
			else{
				if(uid.equals(map.get("guid"))){
					Toast.makeText(getApplicationContext(), "这是您自己的商品！", Toast.LENGTH_SHORT).show();
				}
				else{
        	     new Thread(runnable).start();
				}
			}
		}
 		
 	});
	Bundle extras = getIntent().getExtras();//获取传递过来的json
	SerializableMap smap=(SerializableMap)extras.getSerializable(("map"));
	map = smap.getMap();
	ini();
	setView();	
	if(User.uid.equals("-1")){
	    Toast.makeText(getApplicationContext(), "您还没登录！", Toast.LENGTH_SHORT).show();
	}
	else{
		uid=User.uid;
	  new Thread(runbegin).start();
	
	}
}
	public void setView(){
		gid=map.get("gid");
		gtime.setText(map.get("ptime").substring(0, 10));
		gnum.setText(map.get("gquantity")); 
		gdname.setText(map.get("gname"));
		gdprice.setText(map.get("price"));
		String rzjg;
		if(map.get("rzjg").equals("1")){
			rzjg="已认证用户";
		}
		else{
			rzjg="未认证用户";
		}
		srzjg.setText(rzjg);
		gddescribe.setText(map.get("gdescribe"));		
		String imageurl=ConstantDef.BaseImageUil+map.get("gpicture")+".jpg";
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
//	.delayBeforeLoading(2000)//延时
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
		         if(msg.what==APPLY_TAG){
		            if(feedback.equals("ok")){
		    	       Toast.makeText(getApplicationContext(), "申请成功！", Toast.LENGTH_SHORT).show();
		              }
		               else{
		    	         Toast.makeText(getApplicationContext(), "申请失败！", Toast.LENGTH_SHORT).show();
		                }
	              }
		            else if(msg.what==COLLECTION_TAG){
		            	if(feedback.equals("ok")){
		            		favor.setImageResource(android.R.drawable.btn_star_big_on);
		            		isfavor.setText("取消收藏");
		            		isCollection="1";
				              }
				               else{
				            	   favor.setImageResource(android.R.drawable.btn_star_big_off);
				            	   isfavor.setText("点击收藏");
				            	   isCollection="0";
				                }
		          }
		            else if(msg.what==CHANGE_COLLECTION_TAG){
		            	if(feedback.equals("ok")&&isCollection.equals("1")){
		            		Toast.makeText(getApplicationContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
				              }
				               else if(feedback.equals("ok")&&isCollection.equals("0")){
				            	   Toast.makeText(getApplicationContext(), "取消收藏！", Toast.LENGTH_SHORT).show();
				                }
				               else{
				            	   Toast.makeText(getApplicationContext(), "收藏出错！", Toast.LENGTH_SHORT).show();
				               }
		            }         
	      }  
	    }
	}; 
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	        msg.what=APPLY_TAG;
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
	Runnable runbegin = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	        msg.what=COLLECTION_TAG;
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("gid", gid));
			params.add(new BasicNameValuePair("uid", uid));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), urlbegin, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
	Runnable runchange = new Runnable(){
	    @Override
	    public void run() {
	        Message msg = new Message();
	        msg.what=CHANGE_COLLECTION_TAG;
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("gid", gid));
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("isCollection", isCollection));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), urlchange, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};

	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	        	  finish();  
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
}
