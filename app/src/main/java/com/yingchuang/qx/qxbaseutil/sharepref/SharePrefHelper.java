package com.yingchuang.qx.qxbaseutil.sharepref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Sharepreference操作帮助
 * 
 * @author 洋芋饭
 *
 */
public class SharePrefHelper {

	public static final String SHARE_JSON = "SHARE_JSON"; //
	public static final String SHARE_USERINFO = "SHARE_USERINFO";// 用户信息
	// 可以自定义其他的

	private Context context;
	private String sName;
	private SharedPreferences s;
	private Editor editor;

	public SharePrefHelper(Context context, String sName) {
		this.context = context;
		this.sName = sName;
		this.s = this.context.getSharedPreferences(this.sName, 0);
		this.editor = this.s.edit();
	}

	public String getSharePre(String sKey, String defaultVal) {
		String str;
		str = s.getString(sKey, defaultVal);
		return str;
	}

	public float getSharePreFloat(String sKey, float defaultVal) {
		float str;
		str = s.getFloat(sKey, defaultVal);
		return str;
	}

	public boolean getSharePreBoolean(String key, boolean defaultVal) {
		return s.getBoolean(key, defaultVal);
	}

	public int getSharePreInt(String sKey, int defaultVal) {
		int str;
		str = s.getInt(sKey, defaultVal);
		return str;
	}

	public void setSharePre(String sKey, String sValue) {

		editor.putString(sKey, sValue);
		editor.commit();
	}

	public void setSharePre(String sKey, boolean sValue) {
		editor.putBoolean(sKey, sValue);
		editor.commit();
	}

	public void setSharePre(String sKey, float sValue) {
		editor.putFloat(sKey, sValue);
		editor.commit();
	}

	public void setSharePre(String sKey, int sValue) {
		editor.putInt(sKey, sValue);
		editor.commit();
	}

	public void setSharePre(String sKey, long sValue) {
		editor.putLong(sKey, sValue);
		editor.commit();
	}

	public void removeSharePre(String sKey) {
		editor.remove(sKey);
		editor.commit();
	}

	public void clearSharePre() {
		editor.clear();
		editor.commit();
	}
}
