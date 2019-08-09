package com.guco.tap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.guco.tap.game.TapDungeonGame;

public class AndroidLauncher extends AndroidApplication {
	private final String TAG="AndroidLauncher";
	protected AdView adView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		View gameView = initializeForView(new TapDungeonGame(true), config);
		View gameView = initializeForView(new TapDungeonGame(true), config);
		layout.addView(gameView);

		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visiblity = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visiblity);
				Log.i(TAG, "Ad Loaded...");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		//http://www.google.com/admob
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		AdRequest.Builder builder = new AdRequest.Builder();
		//run once before uncommenting the following line. Get TEST device ID from the logcat logs.
		builder.addTestDevice("5802FF93CB222F7E80F335A611AE88AC");

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);

		layout.addView(adView, adParams);
		adView.loadAd(builder.build());

		setContentView(layout);

	}
}
