package com.voole.epg.f4k_download.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voole.epg.f4k.R;
import com.voole.epg.f4k_download.DisplayManager;
import com.voole.epg.f4k_download.base.BaseLinearLayout;

public class DownLoadMangerView extends BaseLinearLayout{
	private Context context;
	private int textSize = DisplayManager.TEXTSIZE;
	private TextView pageIndexTextview;
	private static int MANAGERLAYOUT_ID = 0x11111;
	private FilmLinearLayout adapterLinearLayout;
	public DownLoadMangerView(Context context) {
		super(context);
		this.context = context;
		init();
	}
	private void init() {
		this.setBackgroundResource(R.drawable.cs_recommend_bg);
		this.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout contentLayout = new RelativeLayout(context);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		contentLayout.setLayoutParams(layoutParams);
		this.addView(contentLayout);
		
		TextView titleTextView = new TextView(context);
		titleTextView.setText(R.string.download_manager);
		titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize+3);
		titleTextView.setTextColor(Color.WHITE);
		int titleMargin = 20;
		RelativeLayout.LayoutParams  titleParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleParams.leftMargin = titleMargin;
		titleParams.rightMargin = titleMargin;
		titleParams.topMargin = titleMargin;
		titleParams.bottomMargin = titleMargin;
		titleTextView.setLayoutParams(titleParams);
		contentLayout.addView(titleTextView);
		
		LinearLayout managerLayout = new LinearLayout(context);
		managerLayout.setId(MANAGERLAYOUT_ID);
		managerLayout.setOrientation(LinearLayout.VERTICAL);
		int managerPad = 10;
		managerLayout.setPadding(managerPad, managerPad, managerPad, managerPad);
		int height = DisplayManager.getInstance(context).getScreenHeight()-150;
		int width = DisplayManager.getInstance(context).getScreenWidth()-200;
		RelativeLayout.LayoutParams managerLayoutParams = new RelativeLayout.LayoutParams(width,height);
		managerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		managerLayout.setLayoutParams(managerLayoutParams);
		managerLayout.setBackgroundResource(R.drawable.bg_downmanger_content);
//		managerLayout.setBackgroundColor(Color.parseColor("#99000000"));
		contentLayout.addView(managerLayout);
		
		
		
		LinearLayout managerTitleLayout = new LinearLayout(context);
		managerTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
		managerTitleLayout.setBackgroundResource(R.drawable.bg_down_title);
		LayoutParams managerTitleLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,textSize+50);
		managerTitleLayout.setLayoutParams(managerTitleLayoutParams);
		managerTitleLayout.setPadding(managerPad, managerPad-4, managerPad,managerPad+4);
		
		TextView nameText = new TextView(context);
		nameText.setText(R.string.filename);
		nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams nameTextParams = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,3);
		nameText.setTextColor(Color.parseColor("#d5ac84"));
		nameText.setGravity(Gravity.CENTER);
		nameText.setLayoutParams(nameTextParams);
		managerTitleLayout.addView(nameText);
		
		TextView progressText = new TextView(context);
		LayoutParams progressTextParams = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,4);
		progressText.setText(R.string.progress);
		progressText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		progressText.setTextColor(Color.parseColor("#d5ac84"));
		progressText.setGravity(Gravity.CENTER);
		progressText.setLayoutParams(progressTextParams);
		managerTitleLayout.addView(progressText);
		
		TextView speedText = new TextView(context);
		speedText.setText(R.string.speed);
		speedText.setTextColor(Color.parseColor("#d5ac84"));
		speedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams speedTextParams = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,2);
		speedText.setGravity(Gravity.CENTER);
		speedText.setLayoutParams(speedTextParams);
		managerTitleLayout.addView(speedText);
		
		TextView sizeText = new TextView(context);
		sizeText.setText(R.string.size);
		sizeText.setTextColor(Color.parseColor("#d5ac84"));
		sizeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams sizeTextParams = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,1);
		sizeText.setGravity(Gravity.CENTER);
		sizeText.setLayoutParams(sizeTextParams);
		managerTitleLayout.addView(sizeText);
		
		TextView handleText = new TextView(context);
		handleText.setText(R.string.operation);
		handleText.setTextColor(Color.parseColor("#d5ac84"));
		handleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams handleTextParams = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,2);
		handleText.setGravity(Gravity.CENTER);
		handleText.setLayoutParams(handleTextParams);
		managerTitleLayout.addView(handleText);
		LayoutParams parms = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
		parms.weight = 1;
		
		managerLayout.addView(managerTitleLayout,parms);
		
		
		
		
		adapterLinearLayout = new FilmLinearLayout(context);
		LayoutParams adapterLinearLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
		adapterLinearLayoutParams.weight = 6;
		managerLayout.addView(adapterLinearLayout,adapterLinearLayoutParams);
		adapterLinearLayout.setId(FilmLinearLayout.ADAPTERLINEARLAYOUT_ID);
		
		
		
		
		
		int pading = 20;
		LinearLayout pageIndexLayout = new LinearLayout(context);
		pageIndexLayout.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams pageIndexLayoutLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		pageIndexLayout.setLayoutParams(pageIndexLayoutLayoutParams);
		pageIndexLayoutLayoutParams.setMargins(0, pading, 0, 0);
		pageIndexLayout.setGravity(Gravity.CENTER);
		
		Button preButton = new FocusAbleButton(context);
		preButton.setText(R.string.pre_page);
		preButton.setTextColor(Color.WHITE);
		preButton.setGravity(Gravity.CENTER);
		preButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams preButtonViewParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		preButton.setLayoutParams(preButtonViewParams);
		pageIndexLayout.addView(preButton);
		preButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapterLinearLayout.showPrePage();
				pageIndexTextview.setText(adapterLinearLayout.getCurrentPage()+"/"+adapterLinearLayout.getTotalPage());
			}
		});
		preButton.setPadding(pading, 0, pading, 0);
		
		Button nextButton = new FocusAbleButton(context);
		nextButton.setText(R.string.next_page);
		nextButton.setTextColor(Color.WHITE);
		nextButton.setGravity(Gravity.CENTER);
		nextButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams nextButtonViewParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		nextButtonViewParams.leftMargin = textSize-5;
		nextButton.setLayoutParams(nextButtonViewParams);
		pageIndexLayout.addView(nextButton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapterLinearLayout.showNextPage();
				pageIndexTextview.setText(adapterLinearLayout.getCurrentPage()+"/"+adapterLinearLayout.getTotalPage());
			}
		});
		nextButton.setPadding(pading, 0, pading, 0);
		
		pageIndexTextview = new TextView(context);
		pageIndexTextview.setText(adapterLinearLayout.getCurrentPage()+"/"+adapterLinearLayout.getTotalPage());
		pageIndexTextview.setTextColor(Color.WHITE);
		pageIndexTextview.setGravity(Gravity.CENTER);
		pageIndexTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		LayoutParams pageIndexTextviewParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pageIndexTextviewParams.leftMargin = textSize-5;
		pageIndexTextview.setLayoutParams(pageIndexTextviewParams);
		pageIndexLayout.addView(pageIndexTextview);
		LayoutParams pageIndexLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
		pageIndexLayoutParams.weight = 1;
		managerLayout.addView(pageIndexLayout,pageIndexLayoutParams);
		
		
		
		TextView warnInfoTextView = new TextView(getContext());
		warnInfoTextView.setText(R.string.down_manager_warn_info);
		warnInfoTextView.setTextColor(Color.WHITE);
		warnInfoTextView.setGravity(Gravity.CENTER);
		warnInfoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		android.widget.RelativeLayout.LayoutParams warnInfoLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		warnInfoLayoutParams.addRule(RelativeLayout.BELOW, MANAGERLAYOUT_ID);
		warnInfoLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, MANAGERLAYOUT_ID);
		warnInfoLayoutParams.topMargin = textSize-10;
		contentLayout.addView(warnInfoTextView,warnInfoLayoutParams);
		
	}
	
	public void initPage(){
		if (pageIndexTextview!=null&&adapterLinearLayout!=null) {
			pageIndexTextview.setText(adapterLinearLayout.getCurrentPage()+"/"+adapterLinearLayout.getTotalPage());
		}
	}
	
	

}
