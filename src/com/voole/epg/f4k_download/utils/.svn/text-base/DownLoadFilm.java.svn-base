package com.voole.epg.f4k_download.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.voole.epg.corelib.model.account.Product;
import com.voole.epg.corelib.model.movies.Content;
import com.voole.epg.corelib.model.movies.Detail;
import com.voole.epg.corelib.model.movies.Film;
import com.voole.epg.corelib.model.movies.MovieManager;
import com.voole.epg.corelib.model.play.Ad;
import com.voole.epg.corelib.model.play.BasePlayManager;
import com.voole.epg.f4k.BuildConfig;
import com.voole.epg.f4k.R;
import com.voole.epg.f4k_download.F4KDownManagerActivity;
import com.voole.epg.f4k_download.F4KListActivity;
import com.voole.epg.f4k_download.bean.IContent;
import com.voole.epg.f4k_download.domain.FilmDownLoad4k;
import com.voole.epg.f4k_download.domain.FilmDownLoad4k.DownType;
import com.voole.epg.player.ad.vo.ADResponse;

public class DownLoadFilm {
	private Context context;
	private FilmDownLoad4k filmf4K;
	protected static final String TAG = "DownLoadFilm";

	public DownLoadFilm(Context context) {
		this.context = context;
	}

	public interface DownListener {
		/**
		 * 
		 * @param code
		 *            {@link F4KListActivity
		 *             @link IContent
		 *            }
		 * @param film
		 */
		void onResult(int code, FilmDownLoad4k film);
	}

	public void onDownLoad(final Film film, final DownListener listener) {
		String fid = film.getFilmID();

		if (!TextUtils.isEmpty(fid)) {

			if (F4kDownResourceUtils.getDownLoadFlag() == null
					|| !"1".equals(F4kDownResourceUtils.getDownLoadFlag())) {
				Toast.makeText(context, R.string.down_flag_is_not_open, 0).show();
				listener.onResult(IContent.CREATE_TASK_FAIL_CODE, null);
				return;
			}

			filmf4K = F4kDownResourceUtils.getDLstaus(fid);

			if (filmf4K == null) {// 立即下载
				// context.showDialog();
				// context.setProgressDialogCancelable(false);
				new Thread() {
					File path = new File(F4kDownResourceUtils.getDownLoadPath());

					public void run() {
						if (TextUtils.isEmpty(F4kDownResourceUtils
								.getDownLoadPath())/***/
						|| !path.getParentFile().exists() || !path.exists()) {
							F4kDownResourceUtils.initDownPath();
						}

						path = new File(F4kDownResourceUtils.getDownLoadPath());
						if (!F4kDownResourceUtils.isDownRootExists(path.getParent())) {
							listener.onResult(IContent.PLEASE_INSERT_DISK_AND_RETRY_CODE, null);
							return;
						}
						if (path.getParentFile().getFreeSpace() < 2f * 1024 * 1024 * 1024) {// 如果小于2G
																							// 则提示空间不足
							listener.onResult(IContent.DISK_FREESPACE_NOT_ENOUGH_CODE, filmf4K);
							return;
						}

						LinkedHashMap<String, FilmDownLoad4k> downStatus = F4kDownResourceUtils.getDLStatus();
						if (downStatus == null) {// 如果没有创建下载列表,则创建
							downStatus = new LinkedHashMap<String, FilmDownLoad4k>();
						}

						Detail detail = MovieManager.GetInstance().getDetailFromMid(film.getMid());
						if (detail == null) {
							return;
						}
						List<Content> contents = detail.getContentList();
						List<Product> products = detail.getProductList();
						if (contents != null && contents.size() > 0&& products != null && products.size() > 0) {

							Ad ad = BasePlayManager.GetInstance().getPlayUrl(
									film.getMid(),
									contents.get(0).getContentIndex(),
									contents.get(0).getFid(),
									products.get(0).getPtype(),
									contents.get(0).getDownUrl());

							if (ad == null) {
								listener.onResult(IContent.NOT_ORDERED_INFO_CODE, filmf4K);
								Log.e(TAG, "没有订购, 只能预览  不可以下载");
								return;
							}

							if ("0".equals(ad.getPriview())|| ("1".equals(ad.getPriview()) && "0".equals(ad.getPriviewTime()))) {
								if (ad.getAdxml() != null) {
									try {
										ADResponse adResponse = new ADResponse(ad.getAdxml());
										String playUrl = adResponse.play_url;
										if (!TextUtils.isEmpty(playUrl)) {
											creatDownload(film, path,downStatus, products,playUrl);
										} else {
											listener.onResult(IContent.GET_DOWN_URL_FALL_CODE, filmf4K);
										}
									} catch (XmlPullParserException e) {
										Log.e(TAG, "XmlPullParserException", e);
									} catch (IOException e) {
										Log.e(TAG, "IOException", e);
									}
								} else {
									listener.onResult(IContent.CREATE_TASK_FAIL_CODE, filmf4K);
								}
							} else if (Integer.parseInt(ad.getStatus()) < 0) {
								listener.onResult(IContent.GET_PLAY_URL_ERROR_CODE, filmf4K);
								Log.e(TAG, "没有订购, 只能预览  不可以下载 ad.getStatus()"
										+ ad.getStatus());
								return;
							} else {
								listener.onResult(IContent.NOT_ORDERED_CODE, filmf4K);
								Log.e(TAG, "没有订购, 只能预览  不可以下载");
								return;
							}

						} else {
							listener.onResult(IContent.CREATE_TASK_FAIL_CODE, filmf4K);
						}
					}

					private void creatDownload(final Film film,final File path,LinkedHashMap<String, FilmDownLoad4k> downStatus,List<Product> products, String playUrl) {
						Log.e(TAG, "adResponse.play_url--> " + playUrl);
						final FilmDownLoad4k filmDownLoad4k = F4kDownResourceUtils.DlCreate(playUrl); // 创建下载
						filmf4K = filmDownLoad4k;
						if (filmDownLoad4k != null) {
							filmDownLoad4k.fid_epg = film.getFilmID();
							filmDownLoad4k.FilmName = film.getFilmName();
							filmDownLoad4k.Mid = products.get(0).getMid();
							filmDownLoad4k.Sid = products.get(0).getSid();
							filmDownLoad4k.downType = DownType.DOWNLOADING;
							filmDownLoad4k.playUrl = playUrl;
							
							if (path.getFreeSpace()- F4kDownResourceUtils.getTotalDLSize() > filmDownLoad4k.totalsize) {
								
								F4kDownResourceUtils.initdb(downStatus);
								F4kDownResourceUtils.putFilm(filmDownLoad4k,false);
								if (F4kDownResourceUtils.writeDownStatus(downStatus)) {
									listener.onResult(IContent.CREATE_TASK_SUCCESS_CODE, filmf4K);
								} else {
									filmDownLoad4k.downType = DownType.UNDOWN;
									F4kDownResourceUtils.delDLstatus(filmDownLoad4k);
									listener.onResult(IContent.CREATE_TASK_FAIL_CODE, filmf4K);
								}
							} else {
								F4kDownResourceUtils.delDLstatus(filmDownLoad4k);
								listener.onResult(IContent.CREATE_STORE_NOT_ENOUGH_CODE, filmDownLoad4k);
								return;
							}
						} else {
							Log.e(TAG, "创建下载失败....");
							F4kDownResourceUtils.DLPause(new FilmDownLoad4k());
							listener.onResult(IContent.CREATE_TASK_FAIL_CODE, filmf4K);
						}
					};
				}.start();
			} else {// 跳至下载管理
				if (BuildConfig.DEBUG)
					Toast.makeText(context, "跳到下载管理", 0).show();
				Intent intent = new Intent();
				intent.setClass(context, F4KDownManagerActivity.class);
				context.startActivity(intent);
			}
		} else {
			if (BuildConfig.DEBUG)
				Toast.makeText(context, "获取影片信息出错...", 0).show();
		}

	}
	
	
	
	



}
