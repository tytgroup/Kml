package com.groupc.tyt.adapter;

import java.util.List;
import java.util.Map;

import com.groupc.tyt.R;
import com.groupc.tyt.TytApplication;
import com.groupc.tyt.constant.ConstantDef;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListViewAdapter extends BaseAdapter {
	private ViewHolder holder;
	private LayoutInflater inflater;
	private List<Map<String, String>> mylist;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader;
	DisplayImageOptions options;

	public void setdata(List<Map<String, String>> list) {
			mylist = list;
	}

	public GoodsListViewAdapter(Context context, List<Map<String, String>> list) {
		inflater = LayoutInflater.from(context);
		setdata(list);
		TytApplication.initImageLoader(context);
		ini();
	}
    public void ini(){
    	imageLoader = ImageLoader.getInstance();
      options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.im_chatroom_msg_loading)
	.showImageForEmptyUri(R.drawable.ic_launcher)
	.showImageOnFail(R.drawable.im_chatroom_msg_failed)
	.resetViewBeforeLoading(true)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//缩放图片
	.delayBeforeLoading(2000)//延时
	.displayer(new RoundedBitmapDisplayer(70))
	.displayer(new FadeInBitmapDisplayer(100))
	.build();
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final int p=position;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_row, null);
			holder.img_head = (ImageView) view
					.findViewById(R.id.list_image);
			holder.text_name = (TextView) view
					.findViewById(R.id.gdname);
			holder.text_price = (TextView) view
					.findViewById(R.id.gdprice);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.text_name.setText( mylist.get(position).get("gname"));
		holder.text_price.setText( mylist.get(position).get("price"));
		String imageurl=ConstantDef.BaseImageUil+mylist.get(position).get("gpicture");
		imageLoader.displayImage(imageurl, holder.img_head, options, animateFirstListener);
		return view;
	}

	class ViewHolder {
		ImageView img_head;
		TextView text_name;
		TextView text_price;

	}

}



