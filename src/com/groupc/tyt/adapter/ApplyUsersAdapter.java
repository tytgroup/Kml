package com.groupc.tyt.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.util.HttpClientUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ApplyUsersAdapter extends BaseAdapter{
	private String url=ConstantDef.BaseUil+"AgreeApplyService";
	private int position;
	private List<NameValuePair> params;
	private String bname,atime,gquantity,haverelative[];
	private Button btn_change;
	private ViewHolder holder;
	private LayoutInflater inflater;
	private List<Map<String, String>> mylist;
	private Context mContext;
	public ApplyUsersAdapter(Context context, List<Map<String, String>> list){
		inflater = LayoutInflater.from(context);
		mContext=context;
		mylist=list;
		haverelative=new String[list.size()];
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder2;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.applyusers_item, null);
			holder.btn_agree = (Button) view
					.findViewById(R.id.agree);
			holder.txt_bnum = (TextView) view
					.findViewById(R.id.bnum);
			holder.txt_btime = (TextView) view
					.findViewById(R.id.btime);
			holder.txt_bname = (TextView) view
					.findViewById(R.id.bname);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder2=holder;
		holder.btn_agree.setTag(""+position);
		bname= mylist.get(position).get("bname");
		atime= mylist.get(position).get("atime").substring(0, 10);
		gquantity=mylist.get(position).get("gquantity");
		holder.txt_bname.setText(bname);
		holder.txt_btime.setText(atime);
		holder.txt_bnum.setText(gquantity);
		if(haverelative[position]==null&&mylist.get(position).get("astate").equals("false")){
			holder.btn_agree.setText("同意申请");
		}
		else{
			holder.btn_agree.setText("已确认申请");
		}

		holder.btn_agree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(haverelative[position]==null){
            		setPosition(Integer.parseInt(holder2.btn_agree.getTag().toString()));
            		setButton(holder2.btn_agree);
				new Thread(runnable).start();
				}
				else{
					Toast.makeText(mContext, "您已同意!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}
	class ViewHolder {
		Button   btn_agree;
		TextView txt_bnum;
		TextView txt_btime;
		TextView txt_bname;
	}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        String feedback = (String) msg.obj;
	        if (feedback.equals("null")) {
				Toast.makeText(mContext, "请检查网络连接！", Toast.LENGTH_SHORT)
						.show();
			} 
			else if(feedback.equals("nd")){
				Toast.makeText(mContext, "没有数据返回！", Toast.LENGTH_SHORT)
				.show();
			}
			else if(feedback.equals("wtc")){
				Toast.makeText(mContext, "网络连接出现问题！", Toast.LENGTH_SHORT)
				.show();
			}
			else {
		    if(feedback.equals("applyok")){
		    	Toast.makeText(mContext, "确认成功！", Toast.LENGTH_SHORT).show();
//		    	 Map<String,String> map=new HashMap<String,String>();
//		    	 map.put("astate","true");
//		    	 map.put("bname", bname);
//		    	 map.put("gquantity", gquantity);
//		    	 map.put("atime", atime);
//		    	 map.put("aid", mylist.get(position).get("aid"));
//		    	mylist.set(position,map);
		    	haverelative[getPosition()]="done";
		    	getButton().setText("已确认申请");
		    }
		    else{
		    	Toast.makeText(mContext, "操作失败请重试！", Toast.LENGTH_SHORT).show();
		    }
	    }
	    }
	}; 
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	    	final int position=getPosition();
	        Message msg = new Message();
	       // Bundle data = new Bundle();
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("aid", mylist.get(position).get("aid")));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(mContext, url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};
	private void setPosition(int position){
		this.position=position;
	}
private int getPosition(){
	return position;
}
	private void setButton(Button btn){
		this.btn_change=btn;
	}
	private Button getButton(){
		return btn_change;
	}
}
