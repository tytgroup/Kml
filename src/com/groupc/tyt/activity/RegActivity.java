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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;






import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.util.HttpClientUtil;

public class RegActivity extends Activity{
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;
	private static final int PHOTO_REQUEST_CUT = 3;
	private boolean haveUpdateHead=false,haveUpdateXSZ=false;
	private String filename;
	private List<NameValuePair> params;
	private String url=ConstantDef.BaseUil+"RegisterService";
	private String uno,name,psd,phone,img_xsz,img_head;
	private EditText stunum;
	private EditText usrname;
	private EditText usrpsw;
	private EditText usrpsw2;
	private EditText usrphone;
	private Button upstuphoto;
	private Button upusrphoto;
	private Button cfreg;
	String saveDirPath = Environment.getExternalStorageDirectory().getPath()+"/TytImage";
	File saveDir = new File(saveDirPath);
	File tempFile = new File(saveDir, getPhotoFileName());  //临时保存
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		if(!saveDir.exists()){
			saveDir.mkdirs();
//			Log.e("createfile",saveDirPath);
		}
		SpannableString spannableString = new SpannableString("登录");
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.reg);
		stunum = (EditText)findViewById(R.id.stunum);
		usrname = (EditText)findViewById(R.id.usrname);
		usrpsw = (EditText)findViewById(R.id.usrpsw);
		usrpsw2 = (EditText)findViewById(R.id.usrpsw2);
		usrphone = (EditText)findViewById(R.id.usrphone);
		upstuphoto= (Button)findViewById(R.id.upstuphoto);
		upusrphoto= (Button)findViewById(R.id.upusrphoto);
		cfreg= (Button)findViewById(R.id.cfreg);
		
		upstuphoto.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {    
            	  filename="userPhoto";
                  showDialog();
            }    
  
        });    
		upusrphoto.setOnClickListener(new Button.OnClickListener(){//创建监听 
            public void onClick(View v) {    
          	  filename="xszPhoto";
              showDialog();
            }    
  
        });   
		cfreg.setOnClickListener(new Button.OnClickListener(){//创建监听  
            public void onClick(View v) {    
            	uno=stunum.getText().toString();
            	name = usrname.getText().toString();
            	psd = usrpsw.getText().toString();
            	phone = usrphone.getText().toString();
            	if(haveUpdateHead){
            	    img_head = "head"+name;	
            	}
            	else{
            		img_head = null;
            	}
            	if(haveUpdateXSZ){
            		img_xsz = "xsz"+name;
            	}
            	else{
            		Toast.makeText(RegActivity.this, "请先上传学生证照片!", Toast.LENGTH_SHORT).show();
            	}         	
            	if (registerIsSuccess()) {
            		new Thread(runnable).start();
            	}
            }    
  
        });   
	}
	
	private boolean registerIsSuccess(){
		
    	//获取用户输入的信息
    	
    	String password1=usrpsw.getText().toString();
    	String password2=usrpsw2.getText().toString();
    	String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        
    	if("".equals(uno)){
    		Toast.makeText(RegActivity.this, "请输入学号!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if("".equals(name)){
        		//用户输入用户名为空
        		Toast.makeText(RegActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
        		return false;
    	}else if("".equals(password1)){
    		//密码不能为空
    		Toast.makeText(RegActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if(!password1.equals(password2)){
    		Toast.makeText(RegActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if(!phone.matches(telRegex)){
    		Toast.makeText(RegActivity.this, "请输入正确手机号!", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
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
		    	Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
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
			params.add(new BasicNameValuePair("uno", uno));
			params.add(new BasicNameValuePair("nickname", name));
			params.add(new BasicNameValuePair("psd", psd));
			params.add(new BasicNameValuePair("phone", phone));
			params.add(new BasicNameValuePair("img_xsz", img_xsz));
			params.add(new BasicNameValuePair("img_head", img_head));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(), url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
//				Intent intent=new Intent();
//				intent.setClass(RegActivity.this, MainActivity.class);
//				startActivity(intent);	
				finish();
	    }
	};
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	        	  finish();
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
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

	private void savePhoto(Intent picdata,String name) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			 FileOutputStream b = null;           
	            File file = new File(saveDir,name+".jpg");  
	            try {  
	                b = new FileOutputStream(file);  
	                photo.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
	                if(name.equals("userPhoto")){
	                	haveUpdateHead=true;
	                }
	                else{
	                	haveUpdateXSZ=true;
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

}
