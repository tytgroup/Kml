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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsApplied extends Activity{
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	private String url=ConstantDef.BaseUil+"CancelApplyService";
	private String url2=ConstantDef.BaseUil+"BuyerRelativeApplyService";
	private String relative;
	private List<NameValuePair> params;
	private Map<String,String> map;
	private Boolean flag=true;
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
		gnum=(TextView)findViewById(R.id.apply_num);
		applystate =(TextView)findViewById(R.id.apply_state);
		commentbuyer =(LinearLayout)findViewById(R.id.relative);
		apcancel =(Button)findViewById(R.id.apcancel);
					
		Bundle extras = getIntent().getExtras();//获取传递过来的json
		SerializableMap smap=(SerializableMap)extras.getSerializable(("map"));
		map = smap.getMap();
		
		ini();
		setView();	 
			
		commentbuyer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag){
				if(map.get("astate").equals("true")){
					if(map.get("brelative").equals("0")){
					newDialog(); 
					}
					else{
						Toast.makeText(getApplicationContext(), "您已经评价过！", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "交易还没完成", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(getApplicationContext(), "您已经评价过！", Toast.LENGTH_SHORT).show();
			}
			}
     		
     	});
		
		apcancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(runnable).start();
			}
     		
     	});
	}
	
	public void setView(){

		gdname.setText(map.get("gname"));
		gdprice.setText(map.get("price"));
		apid.setText(map.get("aid"));
		atime.setText(map.get("atime"));
		gnum.setText(map.get("gquantity")); 
		if(map.get("astate").equals("true")){
		    applystate.setText("已被确认");
		}
		else{
			applystate.setText("申请中");
		}
		String imageurl=ConstantDef.BaseImageUil+map.get("gpicture")+".jpg";
		imageLoader.displayImage(imageurl, gdimage, options, animateFirstListener);
//		transstate =(TextView)findViewById(R.id.trans_state);
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
		    if(feedback.equals("cancelok")){
		    	Toast.makeText(getApplicationContext(), "取消成功！", Toast.LENGTH_SHORT).show();
		    	ConstantDef.applyflag=1;
		    	finish();
		    }
		    else if(feedback.equals("relativeok")){
		    	Toast.makeText(getApplicationContext(), "评价成功！", Toast.LENGTH_SHORT).show();
		    	flag=false;
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
			params.add(new BasicNameValuePair("aid", map.get("aid")));
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
			params.add(new BasicNameValuePair("aid", map.get("aid")));
			params.add(new BasicNameValuePair("uid", map.get("uid")));
			params.add(new BasicNameValuePair("relative", relative));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url2, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
	private void newDialog() {
        LinearLayout layout = new LinearLayout(GoodsApplied.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText textviewGid = new EditText(GoodsApplied.this);
        textviewGid.setBackgroundColor(getResources().getColor(R.color.font_white));
        textviewGid.setHint("-5分到5分之间");
        textviewGid.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        layout.addView(textviewGid);

        AlertDialog.Builder builder = new AlertDialog.Builder(
        		GoodsApplied.this);
        builder.setView(layout);
        builder.setTitle("请对卖家评分").setPositiveButton("提交",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    	relative=textviewGid.getText().toString();
                    	if(Integer.parseInt(relative)>5||Integer.parseInt(relative)<-5){
            		    	Toast.makeText(getApplicationContext(), "请输入正确的数字！", Toast.LENGTH_SHORT).show(); 		
                    	}
                    	else{
                    	new Thread(runnable2).start();
                    	}
                    }

                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
        builder.show();
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

