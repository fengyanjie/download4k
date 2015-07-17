package com.voole.epg.f4k_download;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.voole.epg.f4k_download.utils.F4kDownResourceUtils;

public class UsbStateReceiver extends BroadcastReceiver {

	private static final String TAG = "UsbStateReceiver";
	public static final int USB_STATE_MSG = 0x00020;
	public static final int USB_STATE_ON = 0x00021;
	public static final int USB_STATE_OFF = 0x00022;
	public static final String USB_STATE = "USB_STATE";
	public static final String USB_PATH = "USB_PATH";
	private Toast toast;
	private String usbStr;
	private Handler handler  = new Handler(Looper.getMainLooper());

	@SuppressLint("ShowToast")
	public void registerReceiver(Context context) {
		if (toast == null) {
			toast = Toast.makeText(context, usbStr, 3000);
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_CHECKING);
		filter.addAction(Intent.ACTION_MEDIA_EJECT);
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
//		filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		// 必须要有此行，否则无法收到广播
		filter.addDataScheme("file");
		context.registerReceiver(this, filter);
		
	}

	@Override
	public void onReceive(final Context context, final Intent intent) {
		Log.v(TAG, "usb action = " + intent.getAction());
		final File path = new File(F4kDownResourceUtils.getDownLoadPath());
		// if(!path.getParentFile().exists()||path.getParentFile().length()<=0){
		// if(BuildConfig.DEBUG)Toast.makeText(context,
		// "!path.exists()"+!path.exists(), 0).show();
		// F4kDownResourceUtils.clearDLData();
		// }
		toast.setText("优盘 已经拔出......");
		new Thread() {
			public void run() {
				
					if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)
							|| intent.getAction().equals(Intent.ACTION_MEDIA_CHECKING)
							) {
						usbStr = null;
					} else if(intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)){
						if(F4kDownResourceUtils.getFileDir().length > 0 ){//mun/usb/目录
							usbStr = null;
						} else {
							usbStr = "";
						} 
					}
					
					if (path.getParent() == null) {
						F4kDownResourceUtils.clearDLData();
						Log.e(TAG, "下载路径不可用...");
						return;
					}
					SystemClock.sleep(1000);
					if (!F4kDownResourceUtils.isDownRootExists(path.getParent().toString())) {
						Log.e(TAG, "文件夹 被移除...................");
						F4kDownResourceUtils.clearDLData();
						handler.post(new Runnable() {
							@Override
							public void run() {
								if(usbStr != null){
									toast.show();
								}
							}
						});
					}
			};
		}.start();

	}

}
