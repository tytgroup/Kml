package com.groupc.tyt.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import com.groupc.tyt.util.UploadUtil;

public class RegisterSecondStepActivity extends Activity implements OnClickListener{
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;
	private static final int PHOTO_REQUEST_CUT = 3;
	private int haveUpdateHead=0,haveUpdateXSZ=0;
	private String filename,imgName;
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"RegisterService";
	private String img_xsz=null,img_head="black";
    private ImageButton imgButton_head,imgButton_xsz;
	private Button btn_head,btn_xsz,btn_nextstep;
	String saveDirPath = Environment.getExternalStorageDirectory().getPath()+"/TytImage";
	File saveDir = new File(saveDirPath);
	File tempFile = new File(saveDir, getPhotoFileName());  //临时保存
	File use;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		if(!saveDir.exists()){
			saveDir.mkdirs();
		}
		SpannableString spannableString = new SpannableString("注册");
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.register_secondstep_activity);
        Log.e("uno=",User.uno);
		imgButton_head=(ImageButton)findViewById(R.id.image_tx);
		imgButton_xsz=(ImageButton)findViewById(R.id.image_xsz);
		btn_head= (Button)findViewById(R.id.btn_submittx);
		btn_xsz= (Button)findViewById(R.id.btn_submitxsz);
		btn_nextstep= (Button)findViewById(R.id.btn_nextstep);
		
		imgButton_head.setOnClickListener(this);
		imgButton_xsz.setOnClickListener(this);
		btn_head.setOnClickListener(this);
		btn_xsz.setOnClickListener(this);
		btn_nextstep.setOnClickListener(this);
 
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.image_tx:
			filename="img_tx_"+User.uno;
			showDialog();
			img_head="black";
			break;		
		case R.id.image_xsz:
			filename="img_xsz_"+User.uno;
			showDialog();
			img_xsz=null;
			break;			
		case R.id.btn_submittx:
	    	if(haveUpdateHead==0){
	    		Toast.makeText(getApplicationContext(), "请先选择照片！", Toast.LENGTH_SHORT).show();
	    	}
	    	else if(haveUpdateHead==2){
	    		Toast.makeText(getApplicationContext(), "您已上传头像！", Toast.LENGTH_SHORT).show();
	    	}
	    	else{
	    		imgName="img_tx_"+User.uno;
			new Thread(runnable2).start();
	    	}
			break;			
		case R.id.btn_submitxsz:
	    	if(haveUpdateXSZ==0){
	    		Toast.makeText(getApplicationContext(), "请先选择照片！", Toast.LENGTH_SHORT).show();
	    	}
	    	else if(haveUpdateXSZ==2){
	    		Toast.makeText(getApplicationContext(), "您已上传学生证！", Toast.LENGTH_SHORT).show();
	    	}
	    	else{
	    		imgName="img_xsz_"+User.uno;
			new Thread(runnable2).start();
	    	}
			break;			
		case R.id.btn_nextstep:
				User.tx=img_head;
				User.szx=img_xsz;
			new Thread(runnable).start();
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
		    if(!feedback.equals("no")){
		    	User.uid=feedback;
		    	Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
		    	ConstantDef.currenttab=0;
		    	finish();
		    }
		    else{
		    	Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
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
			params.add(new BasicNameValuePair("uno", User.uno));
			params.add(new BasicNameValuePair("nickname", User.name));
			params.add(new BasicNameValuePair("psd", User.passWord));
			params.add(new BasicNameValuePair("phone", User.phone));
			params.add(new BasicNameValuePair("img_xsz", img_xsz));
			params.add(new BasicNameValuePair("img_head", img_head));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);

	    }
	};
	@SuppressLint("HandlerLeak")
	Handler handler2 = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        int feedback=msg.what;
	        if(feedback==200){
        		if(haveUpdateHead==1){
        			img_head="img_tx_"+User.uno;
        			haveUpdateHead=2;
        			Toast.makeText(getApplicationContext(), "上传头像成功！", Toast.LENGTH_SHORT).show();
        		}
        		else if(haveUpdateXSZ==1){
        			img_xsz="img_xsz_"+User.uno;
        			haveUpdateXSZ=2;
        			Toast.makeText(getApplicationContext(), "上传学生证成功！", Toast.LENGTH_SHORT).show();
        		}
        	}
        	else if(feedback==3){
        		Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT).show();
        	}
        	else if(feedback==2){
        		Toast.makeText(getApplicationContext(), "请确保网络连接正常！", Toast.LENGTH_SHORT).show();
        	}
        	else {
        		Toast.makeText(getApplicationContext(), "其它问题导致失败！", Toast.LENGTH_SHORT).show();
        	}
	    }
	};
	Runnable runnable2 = new Runnable(){
	    @Override
	    public void run() {
	    	use = new File(saveDir,imgName+".jpg");  
        	int feedback=UploadUtil.uploadFile(use, ConstantDef.BaseUil+"UploadPicture");
        	handler2.sendEmptyMessage(feedback);    	
	    }
	    };
	private void showDialog() {
		new AlertDialog.Builder(this).setTitle("头像设置").setPositiveButton("拍照", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		}).setNegativeButton("选择照片", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
			}
		}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile), 450);
			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoZoom(data.getData(), 450);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null)
				savePhoto(data,filename);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue
		intent.putExtra("crop", "true");

		// aspectX aspectY
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	@SuppressWarnings("deprecation")
	private void savePhoto(Intent picdata,String name) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			 FileOutputStream b = null;           
	            File file = new File(saveDir,name+".jpg");  
	            try {  
	                b = new FileOutputStream(file);  
	                photo.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
	                if(name.equals("img_tx_"+User.uno)){
	                	haveUpdateHead=1;
	                	imgButton_head.setBackgroundDrawable(drawable);
	                }
	                else{
	                	haveUpdateXSZ=1;
	                	imgButton_xsz.setBackgroundDrawable(drawable);
	                }
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            } finally {  
	                try {  
	                    b.flush();  
	                    b.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	        	startActivity(new Intent(getApplicationContext(),RegisterFirstStepActivity.class));
	        	  finish();
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),RegisterFirstStepActivity.class));
			finish();
		   return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}

