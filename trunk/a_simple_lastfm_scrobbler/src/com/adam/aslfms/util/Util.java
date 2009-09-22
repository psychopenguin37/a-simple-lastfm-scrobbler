/**
 *  This file is part of A Simple Last.fm Scrobbler.
 *
 *  A Simple Last.fm Scrobbler is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  A Simple Last.fm Scrobbler is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with A Simple Last.fm Scrobbler.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  See http://code.google.com/p/a-simple-lastfm-scrobbler/ for the latest version.
 */

package com.adam.aslfms.util;

import java.util.Calendar;
import java.util.TimeZone;

import com.adam.aslfms.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.format.DateUtils;

public class Util {
	@SuppressWarnings("unused")
	private static final String TAG = "Util";

	/**
	 * 
	 * @return the current time since 1970, UTC, in seconds
	 */
	public static long currentTimeSecsUTC() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"))
				.getTimeInMillis() / 1000;
	}

	public static long currentTimeMillisLocal() {
		return Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();
	}

	public static String timeFromLocalMillis(Context ctx, long millis) {
		return DateUtils.formatDateTime(ctx, millis, DateUtils.FORMAT_SHOW_TIME
				| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE);
	}

	public static void confirmDialog(Context ctx, int s,
			OnClickListener onPositive) {
		new AlertDialog.Builder(ctx).setTitle(R.string.are_you_sure)
				.setMessage(s).setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.yes, onPositive).setNegativeButton(
						R.string.no, null).show();
	}

	public static boolean checkForInstalledApp(Context ctx, String pkgName) {
		try {
			PackageManager pm = ctx.getPackageManager();
			pm.getPackageInfo(pkgName, 0);
			// Log.d(TAG, pkgString + " is installed");
			return true;
		} catch (NameNotFoundException e) {
			// Log.d(TAG, pkgString + " is not installed");
		}
		return false;
	}

	public static String getAppName(Context ctx, String pkgName) {
		try {
			PackageManager pm = ctx.getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(pkgName, 0);
			String label = pm.getApplicationLabel(appInfo).toString();
			return label;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	public static String getAppVersionName(Context ctx, String pkgName) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
			String ver = pkgInfo.versionName;
			return ver;
		} catch (NameNotFoundException e) {
			return "0";
		}
	}

	public static int getAppVersionCode(Context ctx, String pkgName) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
			return pkgInfo.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}
}
