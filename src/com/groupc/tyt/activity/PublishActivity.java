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

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;
import com.groupc.tyt.util.UploadUtil;

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
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class PublishActivity extends Activity {
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;
	private static final int PHOTO_REQUEST_CUT = 3;
	private List<NameValuePair> params;
	private boolean flag=false;
	private String url = ConstantDef.BaseUil + "PublishService";
	private String gname, gtype, guid, ptime, gprice, gpicture, gquantity,
			gdescribe;
	private ImageButton img_goods;
	private Button confirm;
	private EditText describe;
	private EditText name;
	private EditText price;
	private EditText num;
	private TextView txt_advice;
	private CheckBox checkbox1;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String[] list = { "自行车", "书籍", "电子产品", "运动器材", "服饰", "其它" };
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;
	private String imgName;
	String saveDirPath = Environment.getExternalStorageDirectory().getPath()
			+ "/TytImage";
	File saveDir = new File(saveDirPath);
	File tempFile = new File(saveDir, getPhotoFileName()); // 临时保存
	File fileGoods;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("取消发布");
		getActionBar().setTitle(spannableString);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.publish);

		ConstantDef.currenttab=0;
		
		img_goods = (ImageButton) findViewById(R.id.image_goods);
		mySpinner = (Spinner) findViewById(R.id.spinner);
		describe = (EditText) findViewById(R.id.gddecribe);
		txt_advice=(TextView)findViewById(R.id.txt_advice);
		name = (EditText) findViewById(R.id.publishgname);
		price = (EditText) findViewById(R.id.price);
		num = (EditText) findViewById(R.id.num);
		confirm = (Button) findViewById(R.id.cfpub);
//		checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
		adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item,
				list);
		adapter.setDropDownViewResource(R.layout.my_spinner);
		mySpinner.setAdapter(adapter);
		mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						gtype = "" + arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// arg0.setVisibility(View.VISIBLE);
					}
				});

		img_goods.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgName = "" + User.uno + "_" + getTime();
				showDialog();
			}
		});

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// gname ;
				if (User.uid.equals("-1")) {
					Toast.makeText(getApplicationContext(), "您还没登陆，请先登陆！",
							Toast.LENGTH_SHORT).show();
				} else {
					guid = User.uid;
					Date date = new Date();
					ptime = sdf.format(date);
					gname = name.getText().toString();
					gprice = price.getText().toString();
					gquantity = num.getText().toString();
					gdescribe = describe.getText().toString();
					if(flag){
					new Thread(runnable2).start();
					}
					else{
						imgName="black";
						new Thread(runnable).start();
					}
				}
			}
		});
		if (User.uid.equals("-1")) {
			Toast.makeText(getApplicationContext(), "您还没登陆，请先登陆！",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String feedback = (String) msg.obj;

			if (feedback.equals("null")) {
				Toast.makeText(getApplicationContext(), "请检查网络连接！",
						Toast.LENGTH_SHORT).show();
			} else if (feedback.equals("nd")) {
				Toast.makeText(getApplicationContext(), "没有数据返回！",
						Toast.LENGTH_SHORT).show();
			} else if (feedback.equals("wtc")) {
				Toast.makeText(getApplicationContext(), "网络连接出现问题！",
						Toast.LENGTH_SHORT).show();
			} else {
				if (feedback.equals("ok")) {
					Toast.makeText(getApplicationContext(), "发布成功！",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getApplicationContext(),MainActivity.class));
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "发布失败！",
							Toast.LENGTH_SHORT).show();
				}

			}
		}
	};
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			// Bundle data = new Bundle();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("gname", gname));
			params.add(new BasicNameValuePair("guid", guid));
			params.add(new BasicNameValuePair("ptime", ptime));
			params.add(new BasicNameValuePair("price", gprice));
			params.add(new BasicNameValuePair("gpicture", gpicture));
			params.add(new BasicNameValuePair("gdescribe", gdescribe));
			params.add(new BasicNameValuePair("gtype", gtype));
			params.add(new BasicNameValuePair("gquantity", gquantity));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(getApplicationContext(),
					url, params);
			msg.obj = feedback;
			handler.sendMessage(msg);
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int feedback = msg.what;
			if (feedback == 200) {
					gpicture=imgName;
					new Thread(runnable).start();
			} else if (feedback == 3) {
				Toast.makeText(getApplicationContext(), "上传图片失败！",
						Toast.LENGTH_SHORT).show();
			} else if (feedback == 2) {
				Toast.makeText(getApplicationContext(), "请确保网络连接正常！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "其它问题导致失败！",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	Runnable runnable2 = new Runnable() {
		@Override
		public void run() {
			fileGoods = new File(saveDir, imgName + ".jpg");
			int feedback = UploadUtil.uploadFile(fileGoods, ConstantDef.BaseUil
					+ "UploadPicture");
			handler2.sendEmptyMessage(feedback);
		}
	};

	private void showDialog() {
		new AlertDialog.Builder(this)
				.setTitle("头像设置")
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(tempFile));
						startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
					}
				})
				.setNegativeButton("选择照片",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								Intent intent = new Intent(Intent.ACTION_PICK,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										"image/*");
								startActivityForResult(intent,
										PHOTO_REQUEST_GALLERY);
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
				savePhoto(data, imgName);
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
	private void savePhoto(Intent picdata, String name) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			FileOutputStream b = null;
			File file = new File(saveDir, name + ".jpg");
			try {
				b = new FileOutputStream(file);
				photo.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				img_goods.setBackgroundDrawable(drawable);
				img_goods.clearFocus();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					b.flush();
					b.close();
					flag=true;
					txt_advice.setText("图像添加完成，您还可以点击图像修改！");
					Log.e("photo","finish");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	private String getTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) ;
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
