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
public class WantedActivity extends Activity {
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil + "PublishService";
	private String gname, gtype, guid, ptime, gprice, gpicture, gquantity,
			gdescribe;
	private Button waddphoto;
	private Button wconfirm;
	private EditText wdescribe;
	//private EditText wname;
	private EditText wprice;
	private EditText wnum;
	private CheckBox wcheckbox1;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<String> list = new ArrayList<String>();
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("取消求购");
		getActionBar().setTitle(spannableString);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.wanted);
		list.add("自行车");
		list.add("书籍");
		list.add("电子产品");
		list.add("运动器材");
		list.add("服饰");
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
		wdescribe = (EditText) findViewById(R.id.wgddecribe);
		wprice = (EditText) findViewById(R.id.wprice);
		wnum = (EditText) findViewById(R.id.wnum);
		waddphoto = (Button) findViewById(R.id.waddphoto);
		wconfirm = (Button) findViewById(R.id.wcfpub);
		wcheckbox1 = (CheckBox) findViewById(R.id.wcheckBox1);
		waddphoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		wconfirm.setOnClickListener(new OnClickListener() {

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
					//gname = wname.getText().toString();
					gprice = wprice.getText().toString();
					gquantity = wnum.getText().toString();
					gdescribe = wdescribe.getText().toString();
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
