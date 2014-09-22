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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RelativeApplyAdapter extends BaseAdapter{
	private String url=ConstantDef.BaseUil+"SellerRelativeApplyService";
	private  int position;
	private CharSequence inputText;
	private List<NameValuePair> params;
	private String bname,gname,relative[],haverelative[];
	private Button btn_change;
	private ViewHolder holder;
	private LayoutInflater inflater;
	private List<Map<String, String>> mylist;
	private Context mContext;
	public RelativeApplyAdapter(Context context, List<Map<String, String>> list){
		inflater = LayoutInflater.from(context);
		mContext=context;
		mylist=list;
		relative=new String[list.size()];
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
			view = inflater.inflate(R.layout.relative_item, null);
			holder.btn_agree = (Button) view
					.findViewById(R.id.btn_relative);
			holder.txt_gname = (TextView) view
					.findViewById(R.id.gname);
			holder.txt_bname = (TextView) view
					.findViewById(R.id.bname);
			holder.edt_relative=(EditText)view
					.findViewById(R.id.edt_relative);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder2=holder;
		holder.edt_relative.setTag(""+position);
		holder.btn_agree.setTag(""+position);
		bname= mylist.get(position).get("bname");
		gname= mylist.get(position).get("gname");
		holder.txt_bname.setText(bname);
		holder.txt_gname.setText(gname);
		if(haverelative[position]==null){
			holder.btn_agree.setText("提交评价");
		}
		else{
			holder.btn_agree.setText("已提交");
		}
		if(relative[position]!=null){
		holder.edt_relative.setText(relative[position]);
		}
		holder.btn_agree.setOnClickListener(new OnClickListener() {

		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(haverelative[position]==null){
				if(relative[position]!=null){
					if(Integer.parseInt(relative[position])>5||Integer.parseInt(relative[position])<-5){
        		    	Toast.makeText(mContext, "请输入正确的数字！", Toast.LENGTH_SHORT).show(); 		
                	}
                	else{
                		setPosition(Integer.parseInt(holder2.btn_agree.getTag().toString()));
                		setButton(holder2.btn_agree);
                	new Thread(runnable).start();
                	}
				}
				else{
					Toast.makeText(mContext, "请先评分", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(mContext, "您已评价该买家!", Toast.LENGTH_SHORT).show();
			}
			}
		});
		holder.edt_relative.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				// TODO Auto-generated method stub
				inputText = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Log.e("edittext","0");
				Log.e("holder",holder.edt_relative.getTag().toString());
				Log.e("holder2",holder2.edt_relative.getTag().toString());
				if (holder2.edt_relative.getTag().toString().equals("" + position)) {
					relative[position] = inputText.toString();
				}
			}
		});
		holder.edt_relative.clearFocus();
		return view;
	}
	class ViewHolder {
		Button   btn_agree;
		TextView txt_gname;
		TextView txt_bname;
		EditText edt_relative;
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
		    if(feedback.equals("relativeok")){
		    	Toast.makeText(mContext, "评价成功！", Toast.LENGTH_SHORT).show();
		    	haverelative[getPosition()]="done";
		    	getButton().setText("已提交");
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
	        Message msg = new Message();
	       // Bundle data = new Bundle();
	        final int position=getPosition();
	        Log.e("position",""+position);
			params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("aid", mylist.get(position).get("aid")));
			params.add(new BasicNameValuePair("uid", mylist.get(position).get("uid")));
			params.add(new BasicNameValuePair("relative", relative[position]));
			String feedback;
			feedback = HttpClientUtil.httpPostClient(mContext, url, params);
				msg.obj = feedback;
				handler.sendMessage(msg);
	    }
	};

	private void setButton(Button btn){
		this.btn_change=btn;
	}
	private Button getButton(){
		return btn_change;
	}
	private void setPosition(int position){
		this.position=position;
	}
private int getPosition(){
	return position;
}
}
