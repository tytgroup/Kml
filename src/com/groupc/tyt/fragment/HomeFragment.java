package com.groupc.tyt.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.AppliedActivity;
import com.groupc.tyt.activity.GoodsApplied;
import com.groupc.tyt.activity.GoodsinActivity;
import com.groupc.tyt.adapter.GoodsListViewAdapter;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener,OnItemClickListener{

	private String  gtype;
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil+"GoodsService",title = "goods", 
			keys[] = {"gid","gname","guid","price","gpicture","gquantity","gdescribe","ptime"};
	private ImageButton bicycle;
	private ImageButton book;
	private ImageButton electronic;
	private ImageButton sport;
	private ImageButton more;
	private ImageButton hotlist;
	private List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
	private ListView homelist;
	private GoodsListViewAdapter adapter;
	private boolean isFirstTime=true;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_first,container,false);		
		findview(v);
       return v;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		setListener();
		gtype="0";
		new Thread(runnable).start();
	}
	
public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	super.onCreateOptionsMenu(menu, inflater);
	getActivity().getMenuInflater().inflate(R.menu.options_menu, menu);  
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchableInfo info=searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setSearchableInfo(info);
        searchView.setIconifiedByDefault(false); 
        return;
    }


public void findview(View v){
	bicycle = (ImageButton)v.findViewById(R.id.imageButton1);
	book = (ImageButton)v.findViewById(R.id.imageButton2);
	electronic = (ImageButton)v.findViewById(R.id.imageButton3);
    sport  = (ImageButton)v.findViewById(R.id.imageButton4);
	more = (ImageButton)v.findViewById(R.id.imageButton5);
	hotlist = (ImageButton)v.findViewById(R.id.imageButton6);
	homelist = (ListView)v.findViewById(R.id.homelist);
}
public void setListener(){
	bicycle.setOnClickListener(this);
	book.setOnClickListener(this);
	electronic.setOnClickListener(this);
    sport.setOnClickListener(this);
	more.setOnClickListener(this);
	hotlist.setOnClickListener(this);
	homelist.setOnItemClickListener(this);
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	int id=v.getId();
	switch(id){
	case R.id.imageButton1:
		gtype="0";
		break;
	case R.id.imageButton2:
		gtype="1";
		break;
	case R.id.imageButton3:
		gtype="2";
		break;
	case R.id.imageButton4:
		gtype="3";
		break;
	case R.id.imageButton5:
		gtype="4";
		break;
	case R.id.imageButton6:
		gtype="5";
		break;
	}
	new Thread(runnable).start();

}


@SuppressLint("HandlerLeak")
Handler handler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		String feedback = (String) msg.obj;

		if (feedback.equals("null")) {
			Toast.makeText(getActivity(), "请检查网络连接！", Toast.LENGTH_SHORT)
					.show();
		} 
		else if(feedback.equals("nd")){
			Toast.makeText(getActivity(), "没有数据返回！", Toast.LENGTH_SHORT)
			.show();
		}
		else if(feedback.equals("wtc")){
			Toast.makeText(getActivity(), "网络连接出现问题！", Toast.LENGTH_SHORT)
			.show();
		}
		else {
		if (HttpClientUtil.isjson(feedback)) {			
			
			try {
				mylist = HttpClientUtil.jsonToList(feedback, title, keys);
				if(isFirstTime){
					adapter = new GoodsListViewAdapter(getActivity(), mylist);
					homelist.setAdapter(adapter);
					Log.e("gtype1", gtype);
				}
				else{
				adapter.setdata(mylist);
				adapter.notifyDataSetInvalidated();
				Log.e("gtype", gtype);
			}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Log.e("json", "json解析出错");
			}	
		} 
		else {
			Log.e("feedback", ""+feedback);
			if (feedback.equalsIgnoreCase("no")) {
				Toast.makeText(getActivity(), "没有数据！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "其他原因错误!", Toast.LENGTH_SHORT)
						.show();
			}
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
		params.add(new BasicNameValuePair("gtype", gtype));
		String feedback;
		feedback = HttpClientUtil
				.httpPostClient(getActivity(), url, params);	
			msg.obj = feedback;
			handler.sendMessage(msg);
	}

};

@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	Intent intent=new Intent();
	Bundle value=new Bundle();
	SerializableMap smap=new SerializableMap();  
    smap.setMap(mylist.get(arg2));  
	value.putSerializable("map", smap);
	intent.putExtras(value);
	intent.setClass(getActivity(), GoodsinActivity.class);
	startActivity(intent);	 
}

}
