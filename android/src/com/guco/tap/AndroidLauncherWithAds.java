package com.guco.tap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.guco.tap.ad.AdController;
import com.guco.tap.ads.AdsUtils;
import com.guco.tap.game.TapDungeonGame;

public class AndroidLauncherWithAds extends AndroidApplication implements AdController {
	private final int HIDE_ADS=0;
	private final int SHOW_ADS=1;
	protected AdView adView;

	private RewardedAd rewardedAd;
	private RewardedAdCallback rewardedAdCallback;
	RewardedAdLoadCallback adLoadCallback;
	RewardedAdLoadCallback rewardedAdLoadCallback;
	private boolean rewardEarned;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch ((msg.what)) {
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initUi();
		rewardedAd = initRewardedAd();
	}

	private void initUi() {
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new TapDungeonGame(true,this), config);

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams gameParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);

		layout.addView(gameView, gameParams);

		setContentView(layout);
	}

	public RewardedAd initRewardedAd() {
		rewardedAd = new RewardedAd(this, AdsUtils.REWARDED_VIDEO_ID_TEST);
		adLoadCallback = new RewardedAdLoadCallback() {
			@Override
			public void onRewardedAdLoaded() {
				rewardedAd = initRewardedAd();
				Gdx.app.debug("AndroidLauncher","RewardedApp loaded");
			}
			@Override
			public void onRewardedAdFailedToLoad(int errorCode) {
				Gdx.app.debug("AndroidLauncher","RewardedApp failed to load");
			}
		};
		rewardedAdCallback = new RewardedAdCallback() {
			@Override
			public void onRewardedAdClosed(){
				initRewardedAd();
			}

			@Override
			public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
				rewardEarned = true;
			}
		};
		loadRewardedAd();

		return rewardedAd;
	}

	@Override
	public void showRewardedAd() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showRewardAdOnUI();
				}
			});
		}

	private void showRewardAdOnUI() {
		if (rewardedAd.isLoaded()) {
			rewardedAd.show(this, rewardedAdCallback);
		}
	}

	public boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null &&  networkInfo.isConnected();
	}

	private void loadRewardedAd() {
		if (isNetworkConnected()) {
			rewardedAd.loadAd(new AdRequest.Builder().build(), rewardedAdLoadCallback );
		}
	}

	@Override
	public boolean isRewardEarned() {
		return rewardEarned;
	}

	@Override
	public void claimReward() {
		rewardEarned=false;
	}
}
