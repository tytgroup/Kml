package com.groupc.tyt.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.WelcomeActivity;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.util.TytDialog;

public class UpdateAPK {

	private final int NEED_UPDATE = 1, NOT_NEED_UPDATE = 2, BEGIN_DOWNLOAD = 3,
			FINISH_DOWNLOAD = 4, STOP_DOWNLOAD = 5, ING_DOWNLOAD = 6,
			DESTROY_DOWNLOAD = 7, NOTIFICATION_ID = 0;
	private Context context;
	private String savePath = Environment.getExternalStorageDirectory()
			.getPath() + "/TytDownload";
	private String url = ConstantDef.BaseUil + "version.xml";
	private double version = 0;
	private String size = null;
	private String apkUrl = null;
	private String name = null;
	private double APKSize = 0;
	private int lastprogress = 0;
	private int progress = 0;
	private List<String> details = new ArrayList<String>();

	private NotificationCompat.Builder builder;
	private NotificationManager manager;
	public DownLoadAPK thread;

	public void checkVersion(Context context) {
		this.context = context;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getServerVersion();
				if (version >= getLocalVersion()) {
					handler.sendEmptyMessage(NEED_UPDATE);
				} else {
					handler.sendEmptyMessage(NOT_NEED_UPDATE);
				}
			}
		}).start();
	}

	public void getServerVersion() {
		HttpURLConnection conn = null;
		URL mUrl = null;
		InputStream is = null;
		try {
			mUrl = new URL(url);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.connect();
			int res = conn.getResponseCode();
			if (res == 200) {
				is = conn.getInputStream();
				/*
				 * OMD使用说明 getNodeName获取tagName，例如<book>thinking in
				 * android</book>这个Element的getNodeName返回book
				 * getNodeType返回当前节点的确切类型，如Element、Attr、Text等 getNodeValue
				 * 返回节点内容，如果当前为Text节点，则返回文本内容；否则会返回null getTextContent
				 * 返回当前节点以及其子代节点的文本字符串，这些字符串会拼成一个字符串给用户返回。例如
				 * 对<book><name>thinking in
				 * android</name><price>12.23</price></book>调用此方法，则会返回“thinking
				 * in android12.23”
				 */
				// 实例化一个文档构建器工厂
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				// 通过文档构建器工厂获取一个文档构建器
				DocumentBuilder builder = factory.newDocumentBuilder();
				// 通过文档通过文档构建器构建一个文档实例
				Document document = builder.parse(is);
				// 获取XML文件根节点
				Element root = document.getDocumentElement();
				// 获得所有子节点
				NodeList childNodes = root.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node childNode = (Node) childNodes.item(j);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						if (childElement.getNodeName().equals("version")) {
							String V = childElement.getFirstChild()
									.getNodeValue();
							if (V != null)
								version = Double.parseDouble(V);
						} else if (childElement.getNodeName().equals("size")) {
							size = childElement.getFirstChild().getNodeValue();
						} else if (childElement.getNodeName().equals("url")) {
							apkUrl = childElement.getFirstChild()
									.getNodeValue();
						} else if (childElement.getNodeName().equals("name")) {
							name = childElement.getFirstChild().getNodeValue();
						} else if (childElement.getNodeName().equals("details")) {
							NodeList grandChildNodes = childNode
									.getChildNodes();
							Log.d("length", "" + grandChildNodes.getLength());
							for (int i = 0; i < grandChildNodes.getLength(); i++) {
								Node grandchildNode = (Node) grandChildNodes
										.item(i);
								if (grandchildNode.getNodeType() == Node.ELEMENT_NODE) {
									Element grandchildElement = (Element) grandchildNode;
									details.add(grandchildElement
											.getFirstChild().getNodeValue());
									// details.add(grandchildNode.getNodeValue());
								}
							}
						}
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	public double getLocalVersion() {
		double versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.groupc.tyt", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	class DownLoadAPK extends Thread {
		public boolean stop = false;
		public boolean destroy = false;
		public boolean start = false;
		public boolean isStop = false;

		DownLoadAPK() {
			stop = false;
			destroy = false;
			start = false;
			isStop = false;
		}

		public void run() {
			// 判断SD卡是否存在，并且是否具有读写权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				try {
					HttpURLConnection conn = null;
					URL mUrl = null;
					InputStream is = null;
					mUrl = new URL(apkUrl);
					conn = (HttpURLConnection) mUrl.openConnection();
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.setUseCaches(false);
					conn.setRequestMethod("POST");
//					conn.connect();
					StringBuffer params = new StringBuffer();
			        // 表单参数与get形式一样
			        params.append("apkname=").append(name);
			        byte[] bypes = params.toString().getBytes();
			        conn.getOutputStream().write(bypes);
					int res = conn.getResponseCode();
					if (res == 200) {
						boolean firstTime = true;
						int length = conn.getContentLength();
						APKSize = ((double) length) / (1024 * 1024);
						APKSize = ((int) (APKSize * 100)) / 100.0;
						is = conn.getInputStream();
						File saveFile = new File(savePath);
						if (!saveFile.exists()) {
							saveFile.mkdirs();
						}
						File APKFile = new File(savePath, name);
						FileOutputStream os = new FileOutputStream(APKFile);
						int count = 0, numread = 0;
						byte buf[] = new byte[1024];
						hDownload.sendEmptyMessage(BEGIN_DOWNLOAD);
						do {
							if (stop) {
								try {
									if (!isStop) { // 只第一次发送信息
										isStop = true;
										hDownload
												.sendEmptyMessage(STOP_DOWNLOAD);
									}
									sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else if (destroy) {
								hDownload.sendEmptyMessage(DESTROY_DOWNLOAD);
								break;
							} else {
								numread = is.read(buf);
								if (numread <= 0 && progress == 100) {
									hDownload.sendEmptyMessage(FINISH_DOWNLOAD);
									break;
								} else if (numread <= 0) { // 网络传输出现问题
									hDownload.sendEmptyMessage(STOP_DOWNLOAD);
									stop = true;
									isStop = false;
								} else {
									os.write(buf, 0, numread);
									count += numread;
									progress = (int) (((float) count / length) * 100);
									if (progress >= lastprogress + 1)
										hDownload
												.sendEmptyMessage(ING_DOWNLOAD);
									lastprogress = progress;
								}
							}
						} while (true);
						os.close();
						is.close();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(context, "请先检查您的SD卡！", Toast.LENGTH_SHORT)
						.show();
			}
		}

		public void setStop() {
			stop = true;
			isStop = false;
		}

		public void setDestroy() {
			destroy = true;
		}

		public void setStart() {
			stop = false;
			isStop = false;
		}
	}

	public void downloadAPK() {
		thread = new DownLoadAPK();
		UpdateDealDialog.thread = thread;
		UpdateDealDialog.title="最新"+version+"版本";
		UpdateDealDialog.state="正在下载";
		thread.start();
	}

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NEED_UPDATE:
				StringBuffer bodybuf=new StringBuffer();
				Log.d("update", name + "  " + version + "   " + "size" + "   "
						+ apkUrl + "    ");
				for (int i = 0; i < details.size(); i++)
					bodybuf.append(details.get(i)+"\n");
				final TytDialog dialog = new TytDialog(context,R.style.TytDialogStyle1);
				dialog.show();
				dialog.setTitleValue("最新版本为" + version);
				dialog.setBody("总大小为"+size+"\n"+bodybuf.toString());
				dialog.setLeftButton("稍后更新", new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.setRightButton("马上更新", new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						downloadAPK();
						dialog.dismiss();
					}
				});
				break;
			case NOT_NEED_UPDATE:
				Toast.makeText(context, "dont update", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};
	public Handler hDownload = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BEGIN_DOWNLOAD:
				showProgressBar();
				Log.d("download", "1");
				break;
			case ING_DOWNLOAD:
				builder.setContentTitle("正在下载")
						.setProgress(100, progress, false)
						.setContentInfo(progress + "%");
				manager.notify(NOTIFICATION_ID, builder.build());
				Log.d("download", "2");
				break;
			case FINISH_DOWNLOAD:
				builder.setContentTitle("下载完成")
						.setContentText("共下载" + APKSize + "MB")
						.setOngoing(false).setProgress(100, 100, false)
						.setContentInfo(progress + "%");
				manager.notify(NOTIFICATION_ID, builder.build());
				Log.d("download", "3");
				manager.cancel(NOTIFICATION_ID);
				UpdateDealDialog.shouldFinish=true;
				File APKFile2 = new File(savePath, name);
				// 安装 apk 文件
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(Intent.ACTION_VIEW);
				String type = "application/vnd.android.package-archive";
				intent.setDataAndType(Uri.fromFile(APKFile2), type); // 设置数据类型
				Activity activity = (Activity) context;
				activity.startActivityForResult(intent, 2);
				break;
			case STOP_DOWNLOAD:
				builder.setContentTitle("暂停下载")
						.setProgress(100, progress, false)
						.setContentInfo(progress + "%");
				manager.notify(NOTIFICATION_ID, builder.build());
				Log.d("download", "4");
				break;
			case DESTROY_DOWNLOAD:
				manager.cancel(NOTIFICATION_ID);
				Log.d("download", "5");
				File APKFile = new File(savePath, name);
				APKFile.delete();
				break;
			default:
				break;
			}
		};
	};

	@SuppressWarnings("static-access")
	private void showProgressBar() {

		Intent intent = new Intent(context, UpdateDealDialog.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, 0);
		Bitmap ic_launcher = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ic_launcher);
		manager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context);
		builder.setLargeIcon(ic_launcher).setSmallIcon(R.drawable.ic_launcher)
				.setTicker("在后台开始下载").setContentInfo(progress + "%")
				.setOngoing(true).setContentTitle("正在下载")
				.setContentIntent(pendingIntent);
		builder.setProgress(100, progress, false);
		manager.notify(NOTIFICATION_ID, builder.build());
	}
}
