//package com.voole.epg.f4k_download;
//
//import java.util.LinkedHashMap;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.voole.epg.f4k_download.domain.FilmDownLoad4k;
//import com.voole.epg.f4k_download.widget.AdapterItemView;
//
//public class F4kAdapter extends BaseAdapter{
//	private LinkedHashMap<String ,FilmDownLoad4k> filmDownLoad4ks;
//	private Context context;
//
//	public F4kAdapter(LinkedHashMap<String ,FilmDownLoad4k> filmDownLoad4ks, Context context) {
//		this.filmDownLoad4ks = filmDownLoad4ks;
//		this.context = context;
//	}
//
//	@Override
//	public int getCount() {
//		return filmDownLoad4ks == null ? 0 : filmDownLoad4ks.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		int pos = 0;
//		Entry<String, FilmDownLoad4k> filmDownLoad4kEntry = null;
//		Set<Entry<String, FilmDownLoad4k>> entrySet = filmDownLoad4ks.entrySet();
//		if (filmDownLoad4ks==null) {
//			return null;
//		}
//		for (Entry<String, FilmDownLoad4k> entry : entrySet) {
//			if (pos==position) {
//				filmDownLoad4kEntry = entry;
//				break;
//			}
//			pos++;
//		}
//		
//		return filmDownLoad4kEntry;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.e(this.getClass().getName(), "getview " + position);
//		Entry<String, FilmDownLoad4k> entry = (Entry<String, FilmDownLoad4k>) this.getItem(position);
//		FilmDownLoad4k filmDownLoad4k = entry.getValue();
//		AdapterItemView view= new AdapterItemView(context);
//		return view;
//	}
//}