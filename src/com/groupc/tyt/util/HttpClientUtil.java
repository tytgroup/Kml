package com.groupc.tyt.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */

public class HttpClientUtil {

	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟 

	@SuppressWarnings("finally")
	public static String httpPostClient(Context context, String url,
			List<NameValuePair> params) {
		String msg="null";
		BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
	    HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost post = new HttpPost(url);
		try {
			Log.e("http", "1");
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));Log.e("http", "2");
			// 获得响应对象
			HttpResponse response = httpClient.execute(post);Log.e("http", "3");
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					 msg = EntityUtils.toString(response.getEntity());
				}
				else{
					msg="nd";  //no data
				}
			}
			else{
				msg="wtc"; //wrong to connect
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return msg;	
		}

	}
	public static boolean isjson(String msg)
	{
		if(msg.startsWith("{")){
			//Log.e("isjson", "yes"+msg);
			return true;
		}
		else{
			//Log.e("isjson", "no"+msg);
			return false;
		}
	}
	public static  List<Map<String, String>> jsonToList(String msg,String title,String[]keys)
			throws Exception{
		JSONObject jsonObject=new JSONObject(msg);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		JSONArray jsonArray = jsonObject.getJSONArray(title);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject item = jsonArray.getJSONObject(i); // 得到每个对象
			map = new HashMap<String, String>(); // 存放到Map里面
			for(int j=0;j<keys.length;j++){
			String values = item.getString(keys[j]);
			map.put(keys[j], values);
			}
			list.add(map);
		}
	return list;
	}
}