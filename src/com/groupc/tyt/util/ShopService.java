package com.groupc.tyt.util;
/*
 *从json总取特定区域的值
 * */
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.groupc.tyt.util.Shop;

public class ShopService {
	/*
	 * josn:传入的json字符串
	 * totalpage:显示几版
	 * pagecount：每个版面显示几页
	 * */
	public static List<Shop>getJSONlistshops(String json,int totalpage,int pagecount) throws Exception{
		  JSONObject jsonobj=new JSONObject(json);
		  List<Shop> Shops=new ArrayList<Shop>();
		  JSONArray array=jsonobj.getJSONArray("hotels");
		  for(int i=0; i<totalpage*pagecount; i++)
		  {
			  if(i>array.length()){
				  return Shops;
		  }
		   JSONObject item=array.getJSONObject(i);
		   String thumbnail=item.getString("thumbnail");
		   String name=item.getString("name");
		   String street_address=item.getString("street_address");
		   double nightly_rate=item.getDouble("nightly_rate");
		   Shops.add(new Shop(thumbnail, name, street_address,nightly_rate));
		  }
		  return Shops;
		 }
}
