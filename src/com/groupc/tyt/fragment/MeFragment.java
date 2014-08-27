package com.groupc.tyt.fragment;

import java.util.ArrayList;
import java.util.List;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.AppliedActivity;
import com.groupc.tyt.activity.SplashActivity;
import com.groupc.tyt.activity.SplashActivity2;
import com.groupc.tyt.constant.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeFragment extends Fragment {
	private ListView listView;
	private ImageView img_tx;
	private TextView txt_name,txt_xydj,txt_hydj,txt_jf;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.me,container,false);
		img_tx=(ImageView)v.findViewById(R.id.imageView1);
		txt_name=(TextView)v.findViewById(R.id.textView1);
		txt_xydj=(TextView)v.findViewById(R.id.textView5);
		txt_hydj=(TextView)v.findViewById(R.id.textView6);
		txt_jf=(TextView)v.findViewById(R.id.textView7);
		listView = (ListView) v.findViewById(R.id.listviewme);
		if(User.uid.equals("-1")){
			Toast.makeText(getActivity(), "您还没有登陆！", Toast.LENGTH_SHORT).show();
		}
		else{
			img_tx.setBackgroundResource(R.drawable.tx);
			txt_name.setText(User.name);
			txt_xydj.setText(""+User.xydj);
			txt_hydj.setText(""+User.hydj);
			txt_jf.setText(""+User.jf);
		}
		init();	
		return v;
	}
	
	
	private void init() {
		List<String> items = new ArrayList<String>();
		items.add("已申请的交易");
		items.add("已发布的交易");
		items.add("十万火急求买");
		items.add("购买积分");
		items.add("我有更好的建议（采纳送积分)");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, items);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                    switch(position){
                    case 0:
                            Intent localIntent=new Intent(getActivity(),SplashActivity.class);
                            startActivity(localIntent);
                            break;
                    case 1:
                    	Intent localIntent2=new Intent(getActivity(),SplashActivity2.class);
                        startActivity(localIntent2);
                        break;
                    case 2:
                    	Toast.makeText(getActivity(), "功能开发中", Toast.LENGTH_SHORT).show();
                    	break;
                    case 3:
                    	Toast.makeText(getActivity(), "功能开发中", Toast.LENGTH_SHORT).show();
                    	break;
                    case 4:
                    	Toast.makeText(getActivity(), "功能开发中", Toast.LENGTH_SHORT).show();
                    	break;
                    }
                    
            }
    });
}
}