package com.guco.tap;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.guco.tap.game.TapDungeonGame;

public class AndroidLauncher extends AndroidApplication {
	private final String TAG = "AndroidLauncher";
	private AdView bannerView;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new TapDungeonGame(true, null), config);
	}
}
