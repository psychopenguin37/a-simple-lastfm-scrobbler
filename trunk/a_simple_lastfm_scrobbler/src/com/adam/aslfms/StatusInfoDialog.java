package com.adam.aslfms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adam.aslfms.util.Util;

public class StatusInfoDialog {

	// private static final String TAG = "StatusInfoDialog";

	private final Context mCtx;
	private final AppSettings settings;

	private AlertDialog mDialog = null;
	private View mDialogView = null;

	public StatusInfoDialog(Context ctx) {
		super();
		this.mCtx = ctx;
		this.settings = new AppSettings(ctx);
	}

	private void clearStuff() {
		this.mDialog = null;
		this.mDialogView = null;
	}

	public void showDialog() {
		final LayoutInflater factory = LayoutInflater.from(mCtx);

		mDialogView = factory.inflate(R.layout.status_info, null);

		innerUpdate();

		AlertDialog.Builder adBuilder = new AlertDialog.Builder(mCtx).setTitle(
				R.string.status_title).setIcon(
				android.R.drawable.ic_dialog_info).setView(mDialogView)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								StatusInfoDialog.this.clearStuff();
							}
						}).setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						StatusInfoDialog.this.clearStuff();
					}
				});

		mDialog = adBuilder.show();
	}

	public void updateDialog() {
		if (mDialog == null) {
			return;
		}
		innerUpdate();
	}

	private void innerUpdate() {
		TextView authText = ((TextView) mDialogView
				.findViewById(R.id.status_auth));
		TextView scrobbleText = ((TextView) mDialogView
				.findViewById(R.id.status_scrobbling));
		TextView npText = ((TextView) mDialogView.findViewById(R.id.status_np));
		TextView scrobbleStatsText = ((TextView) mDialogView
				.findViewById(R.id.status_scrobble_stats));
		TextView npStatsText = ((TextView) mDialogView
				.findViewById(R.id.status_np_stats));

		// authText
		if (settings.getAuthStatus() == Status.AUTHSTATUS_BADAUTH) {
			authText.setText(R.string.auth_bad_auth);
		} else if (settings.getAuthStatus() == Status.AUTHSTATUS_FAILED) {
			authText.setText(R.string.auth_internal_error);
		} else if (settings.getAuthStatus() == Status.AUTHSTATUS_RETRYLATER) {
			authText.setText(R.string.auth_network_error);
		} else if (settings.getAuthStatus() == Status.AUTHSTATUS_OK) {
			authText.setText(mCtx.getString(R.string.logged_in_as) + " "
					+ settings.getUsername());
		} else if (settings.getAuthStatus() == Status.AUTHSTATUS_UPDATING) {
			authText.setText(R.string.auth_updating);
		} else { // Status.AUTHSTATUS_NOAUTH
			authText.setText(R.string.user_credentials_summary);
		}

		// scrobbleText
		if (!settings.isScrobblingEnabled()) {
			scrobbleText.setText(R.string.scrobbling_disabled);
		} else {
			long time = settings.getLastScrobbleTime();
			String when;
			String what;
			if (time == -1) {
				when = mCtx.getString(R.string.never);
				what = "";
			} else {
				when = Util.timeFromLocalMillis(mCtx, time);
				what = "\n" + settings.getLastScrobbleInfo();
			}
			scrobbleText.setText(mCtx.getString(R.string.scrobble_last_at)
					+ " " + when + what);
		}

		// npText
		if (!settings.isNowPlayingEnabled()) {
			npText.setText(R.string.nowplaying_disabled);
		} else {
			long time = settings.getLastNPTime();
			String when;
			String what;
			if (time == -1) {
				when = mCtx.getString(R.string.never);
				what = "";
			} else {
				when = Util.timeFromLocalMillis(mCtx, time);
				what = "\n" + settings.getLastNPInfo();
			}
			npText.setText(mCtx.getString(R.string.nowplaying_last_at) + " "
					+ when + what);
		}

		// statsText
		scrobbleStatsText.setText(mCtx.getString(R.string.stats_scrobbles)
				+ " " + settings.getNumberOfScrobbles());
		npStatsText.setText(mCtx.getString(R.string.stats_nps) + " "
				+ settings.getNumberOfNPs());
	}
}
