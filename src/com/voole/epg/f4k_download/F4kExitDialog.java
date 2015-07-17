package com.voole.epg.f4k_download;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.voole.epg.f4k.R;
import com.voole.util.prop.SharedPreferencesUtil;

public class F4kExitDialog {
	private static F4kExitDialog instance;
	private AlertDialog dialog;
	private Activity context;
	private F4kExitDialog(){}
	public static F4kExitDialog getInstance(Activity context){
		synchronized (F4kExitDialog.class) {
			if (instance==null) {
				instance = new F4kExitDialog();
			}
			if (instance.context !=context) {
				instance.context = context;
				instance.dialog =  new AlertDialog.Builder(context)
				.setCancelable(false)
		        .setTitle(R.string.pop_info)
		        .setPositiveButton(R.string.ok,
		                new DialogInterface.OnClickListener() {
		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                    	instance.context.setResult(800);
		                    	instance.context.finish();
		                    	SharedPreferencesUtil.putBoolean(instance.context, "voole_4k", "is_download_background", true);
		                    }
		                })
		        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	SharedPreferencesUtil.putBoolean(instance.context, "voole_4k", "is_download_background", false);
		            	instance.context.setResult(800);
                    	instance.context.finish();
		            }
		        }).create();
			}
			
		}
		return instance;
	}
	
	public void showDialogWithMessage(int string) {
		
		dialog.setMessage(context.getString(string));
		if(!context.isFinishing()&&!dialog.isShowing()){
			dialog.show();
		}
	}
	
	
}
