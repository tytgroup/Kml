package com.groupc.tyt.activity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class PubActivity extends Activity {
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil + "PublishService";
	private String gname, gtype, guid, ptime, gprice, gpicture, gquantity,
			gdescribe;
	private Button addphoto;
	private Button confirm;
	private EditText describe;
	private EditText name;
	private EditText price;
	private EditText num;
	private CheckBox checkbox1;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<String> list = new ArrayList<String>();
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("取消发布");
		getActionBar().setTitle(spannableString);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.publish);
		list.add("自行车");
		list.add("书籍");
		list.add("电子产品");
		list.add("运动器材");
		list.add("其它");
		mySpinner = (Spinner) findViewById(R.id.spinner);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner.setAdapter(adapter);
		mySpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						gtype = "" + arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// arg0.setVisibility(View.VISIBLE);
					}
				});

		/* 下拉菜单弹出的内容选项焦点改变事件处理 */
		mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
			}
		});
		describe = (EditText) findViewById(R.id.gddecribe);
		name = (EditText) findViewById(R.id.gdname);
		price = (EditText) findViewById(R.id.price);
		num = (EditText) findViewById(R.id.num);
		addphoto = (Button) findViewById(R.id.addphoto);
		confirm = (Button) findViewById(R.id.cfpub);
		checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
		addphoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
					new Thread(runnable).start();
				}
			}

		});
		if (User.uid.equals("-1")) {
			Toast.makeText(getApplicationContext(), "您还没登陆，请先登陆！",
					Toast.LENGTH_SHORT).show();
		}

	}

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
