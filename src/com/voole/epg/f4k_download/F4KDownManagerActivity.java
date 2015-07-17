package com.voole.epg.f4k_download;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.voole.epg.corelib.model.account.Product;
import com.voole.epg.corelib.model.movies.Content;
import com.voole.epg.corelib.model.movies.Detail;
import com.voole.epg.corelib.model.movies.Film;
import com.voole.epg.corelib.model.movies.MovieManager;
import com.voole.epg.corelib.model.play.Ad;
import com.voole.epg.corelib.model.play.BasePlayManager;
import com.voole.epg.corelib.model.play.PlayCheckInfo;
import com.voole.epg.f4k.BuildConfig;
import com.voole.epg.f4k.R;
import com.voole.epg.f4k_download.base.BaseActivity;
import com.voole.epg.f4k_download.base.TVAlertDialog;
import com.voole.epg.f4k_download.domain.FilmDownLoad4k;
import com.voole.epg.f4k_download.domain.FilmDownLoad4k.DownType;
import com.voole.epg.f4k_download.utils.Config;
import com.voole.epg.f4k_download.utils.F4kDownResourceUtils;
import com.voole.epg.f4k_download.widget.AdapterItemView;
import com.voole.epg.f4k_download.widget.DownLoadMangerView;
import com.voole.epg.f4k_download.widget.FilmLinearLayout;
import com.voole.epg.player.ad.vo.ADResponse;

public class F4KDownManagerActivity extends BaseActivity implements OnClickListener{

	private static final String TAG = "F4KDownManagerActivity";
	private FilmLinearLayout adapterLinearLayout;
	private DownLoadMangerView view;
	private static final int UPGRADE_PROGRESS = 0x1001;//更新下载状态
	protected static final int FILE_IS_NULL = UPGRADE_PROGRESS+1;
	private static final int NOT_ORDERED = UPGRADE_PROGRESS+2;
	private static final int NOT_ORDERED_INFO = UPGRADE_PROGRESS+3;
	protected static final int ORDERED_TIMEOUT = UPGRADE_PROGRESS+4;
	protected static final int GET_PLAY_URL_FAIL = UPGRADE_PROGRESS+5;
	protected static final int GET_PLAY_URL_ERROR = UPGRADE_PROGRESS+6;
	
	private boolean loop = true;
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPGRADE_PROGRESS:
				if (F4kDownResourceUtils.getDLStatus()!=null&&adapterLinearLayout.getTotalChildCount()!=F4kDownResourceUtils.getDLStatus().size()) {
					adapterLinearLayout.setData(F4kDownResourceUtils.getDLStatus());
					adapterLinearLayout.notifyDataChange();
					view.initPage();
				}
				adapterLinearLayout.notifyDataChange();
				break;
			case FILE_IS_NULL:
				if (BuildConfig.DEBUG) {
					Toast.makeText(getApplicationContext(), "文件为空", 0).show();
				}
				getSimpleDialog(getString(R.string.no_disk)).show();
				adapterLinearLayout.setData(null);
				mhandler.sendEmptyMessage(UPGRADE_PROGRESS);
				break;
			case NOT_ORDERED_INFO:
				Toast.makeText(getApplicationContext(), R.string.can_not_get_film_playinfo, 0).show();
				break;
			case NOT_ORDERED:
				F4KDownManagerActivity.this.getOrderDialog(getString(R.string.film_order_time_out)).show();
				break;
			case ORDERED_TIMEOUT:
				Toast.makeText(F4KDownManagerActivity.this, R.string.film_order_time_out, 1).show();
				break;
			case GET_PLAY_URL_ERROR:
				Toast.makeText(F4KDownManagerActivity.this, R.string.can_not_get_film_playinfo, 0).show();
				break;
			case GET_PLAY_URL_FAIL:
				Toast.makeText(F4KDownManagerActivity.this, R.string.get_playUrl_fail, 1).show();
				break;
			default:
				break;
			}
			F4KDownManagerActivity.this.cancelDialog();
		};
	};
	@Override
	protected void doHandleMessage(int what, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new DownLoadMangerView(this);
		setContentView(view);
		findview(view);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setData();
		loop = true;
		LinkedHashMap<String, FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
		if (downStatus==null) {
			return ;
		}
		new Thread(){
			public void run() {
				while(loop){
					SystemClock.sleep(Config.UPDATA_TIME);
					LinkedHashMap<String, FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
					if (downStatus==null) {
						mhandler.sendEmptyMessage(FILE_IS_NULL);
						return ;
					}
					mhandler.sendEmptyMessage(UPGRADE_PROGRESS);
				}
			};
		}.start();
	}

	private void setData() {
		LinkedHashMap<String, FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
		if (downStatus!=null) {
			adapterLinearLayout.setChildViews(downStatus);
			setItemListener();
			view.initPage();
		}
	}

	private void findview(View view) {
		adapterLinearLayout = (FilmLinearLayout) view.findViewById(FilmLinearLayout.ADAPTERLINEARLAYOUT_ID);
		
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case AdapterItemView.LEFTBUTTONSTART_ID:
			if(BuildConfig.DEBUG)Toast.makeText(getApplicationContext(), "开始", 0).show();
			final FilmDownLoad4k filmDl = (FilmDownLoad4k) v.getTag();
			switch (filmDl.downType) {
			case FINISH:
				if (DetailDialogf4k.listener != null) {
//					Film film = new Film();
//					film.setMid(filmDl.Mid);
//					film.setFilmID(filmDl.fid_download);
					DetailDialogf4k.listener.onPlay(this,filmDl.Mid);
				}else{
					Toast.makeText(this, R.string.the_net_not_support, 0).show();
				}
				
				
//				F4KDownManagerActivity.this.showDialog();
//				F4KDownManagerActivity.this.setProgressDialogCancelable(false);
//			new Thread(){
//				@Override
//				public void run() {
//					Detail detail = MovieManager.GetInstance().getDetailFromMid(film.Mid);
//					if (detail==null) {
//						mhandler.sendEmptyMessage(NOT_ORDERED_INFO);
//						Log.e(TAG, "没有订购, 只能预览  不可以下载");
//						return;
//					}
//					List<Content> contents = detail.getContentList();
//					List<Product> products = detail.getProductList();
//					if (contents!=null&&contents.size()>0&&products!=null&&products.size()>0) {
//						Ad ad = BasePlayManager.GetInstance().getPlayUrl(film.Mid,
//								contents.get(0).getContentIndex(),
//								contents.get(0).getFid(),
//								products.get(0).getPtype(),
//								contents.get(0).getDownUrl());
//						
//						
//						if(ad == null){
//							mhandler.sendEmptyMessage(NOT_ORDERED_INFO);
//							Log.e(TAG, "没有订购, 只能预览  不可以下载");
//							return;
//						}
//						if("0".equals(ad.getPriview())||("1".equals(ad.getPriview()) && "0".equals(ad.getPriviewTime()))){
//							if (ad.getAdxml()!=null) {
//								try {
//									ADResponse adResponse = new ADResponse(ad.getAdxml());
//									String playUrl = adResponse.play_url;
//									if (!TextUtils.isEmpty(playUrl)) {
//										film.playUrl  = playUrl;
//										mhandler.post(new Runnable() {
//											@Override
//											public void run() {
//												F4KDownManagerActivity.this.cancelDialog();
//												F4kDownResourceUtils.playFromLoacal(film, F4KDownManagerActivity.this);
//											}
//										});
//									}else if(Integer.parseInt(ad.getStatus())<0){
//										handler.sendEmptyMessage(GET_PLAY_URL_ERROR);
//										Log.e(TAG, "没有订购, 只能预览  不可以下载 ad.getStatus()"+ad.getStatus());
//										return;
//									}else{
//										handler.sendEmptyMessage(GET_PLAY_URL_FAIL);
//									}
//									Log.e(TAG, "adResponse.play_url--> "+adResponse.play_url);
//								} catch (XmlPullParserException e) {
//									Log.e(TAG, "XmlPullParserException",e);
//								} catch (IOException e) {
//									Log.e(TAG, "IOException",e);
//								}
//							}else{
//								handler.sendEmptyMessage(GET_PLAY_URL_FAIL);
//							}
//						}else{
//							mhandler.sendEmptyMessage(ORDERED_TIMEOUT);
//							Log.e(TAG, "当前由于已下线或超过观看期限无法观看");
//							return;
//						}
//					}
//				}
//			}.start();
//				
				break;
			case WAITING:
			case PAUSE:
				loop = true;
				Toast.makeText(getApplicationContext(), R.string.three_task_allow_at_one_time, 1).show();
				F4KDownManagerActivity.this.showDialog();
				new Thread(){
					@Override
					public void run() {
						if(F4kDownResourceUtils.DlContinue(filmDl)){
							filmDl.downType = DownType.DOWNLOADING;
							LinkedHashMap<String,FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
							F4kDownResourceUtils.putFilm(filmDl,true);
							F4kDownResourceUtils.writeDownStatus(downStatus);
							mhandler.sendEmptyMessage(UPGRADE_PROGRESS);
							F4KDownManagerActivity.this.cancelDialog();
						}else{
							mhandler.post(new Runnable() {
								@Override
								public void run() {
									F4KDownManagerActivity.this.cancelDialog();
									Toast.makeText(getApplicationContext(), R.string.start_fail,  0).show();
								}
							});
						}
					}
				}.start();
				break;
			case DOWNLOADING:
				F4KDownManagerActivity.this.showDialog();
				new Thread(){
					public void run() {
						if(F4kDownResourceUtils.DLPause(filmDl)){
							filmDl.downType = DownType.PAUSE;
							LinkedHashMap<String,FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
							if(downStatus==null)return;
							downStatus.put(filmDl.fid_epg,filmDl);
							F4kDownResourceUtils.writeDownStatus(downStatus);
							filmDl.realspeed = 0d;
							mhandler.sendEmptyMessage(UPGRADE_PROGRESS);
							F4KDownManagerActivity.this.cancelDialog();
						}else{
							mhandler.post(new Runnable() {
								@Override
								public void run() {
									F4KDownManagerActivity.this.cancelDialog();
									Toast.makeText(getApplicationContext(), R.string.pause_fail,  0).show();
								}
							});
						}
						
					}
				}.start();
				break;
			default:
				break;
			}
			break;
		case AdapterItemView.RIGHTBUTTONSTART_ID:
			
		 new TVAlertDialog.Builder(F4KDownManagerActivity.this)
			.setTitle(R.string.sure_to_delect)
			.setCancelable(false)
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							if(BuildConfig.DEBUG)Toast.makeText(getApplicationContext(), R.string.delete, 0).show();
							final FilmDownLoad4k filmDel = (FilmDownLoad4k) v.getTag();
							F4KDownManagerActivity.this.showDialog();
							new Thread(){
								public void run() {
									if(F4kDownResourceUtils.delDLstatus(filmDel)){
										mhandler.post(new Runnable() {
											@Override
											public void run() {
//												F4kDownResourceUtils.delDLstatus(filmDel);//从数据库删除
												F4kDownResourceUtils.writeDownStatus(F4kDownResourceUtils.getDLStatus());
												adapterLinearLayout.setData(F4kDownResourceUtils.getDLStatus());
												adapterLinearLayout.notifyDataChange();
												view.initPage();
												Toast.makeText(getApplicationContext(), R.string.delect_success,  0).show();
												F4KDownManagerActivity.this.cancelDialog();
											}
										});
									}else{
										mhandler.post(new Runnable() {
											@Override
											public void run() {
												F4KDownManagerActivity.this.cancelDialog();
												Toast.makeText(getApplicationContext(), R.string.delect_fail,  0).show();
											}
										});
									}
								};
							}.start();
						}
					})
			.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {

						}
					}).create().show(); 
			break;
		default:
			break;
		}
		
	}
	
	

	private void setItemListener() {
		int count = adapterLinearLayout.getChildCount();
		for (int i = 0; i < count; i++) {
			if (adapterLinearLayout.getChildAt(i) instanceof AdapterItemView) {
				AdapterItemView item = (AdapterItemView) adapterLinearLayout.getChildAt(i);
				if (item.getTag() instanceof FilmDownLoad4k) {
					item.setButtonClickListener(F4KDownManagerActivity.this);
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		loop = false;
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		loop = false;
		super.onPause();
	}
	
	
	@Override
	protected void onStop() {
		loop = false;
		super.onStop();
	}
	
}
