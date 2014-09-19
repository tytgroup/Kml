package com.groupc.tyt.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.GoodsinActivity;
import com.groupc.tyt.adapter.GoodsListViewAdapter;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.SerializableMap;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.HttpClientUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavorFragment extends Fragment implements OnItemClickListener{
	private List<NameValuePair> params;
	private String url = ConstantDef.BaseUil+"FavorGoodsService",title = "favorgoods", 
			keys[] = {"gid","gname","guid","price","gpicture","gquantity","gdescribe","ptime"};
	private List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
	private ListView listview;
	private GoodsListViewAdapter adapter;
	private String uid;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.goods_list,container,false);
		
}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		listview=(ListView)getActivity().findViewById(R.id.list);
		listview.setOnItemClickListener(new OnItemClickListener() {
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
		});
		if(User.uid.equals("-1")){
			Toast.makeText(getActivity(), "您还没登录！", Toast.LENGTH_SHORT).show();
		}
		else{
			Log.e("FavorFragment", "start");
			uid=User.uid;
		new Thread(runnable).start();
		}
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
						adapter = new GoodsListViewAdapter(getActivity(), mylist);
						listview.setAdapter(adapter);
						//Log.e("gtype1", gtype);
	
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
			params.add(new BasicNameValuePair("uid", uid));
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
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    	Log.e("FavorFragment", "isvisible1");
        if (getUserVisibleHint()) {
        	Log.e("FavorFragment", "isvisible2");
//    		new Thread(runnable).start();
        } else {
            //相当于Fragment的onPause
        }
    }

}
