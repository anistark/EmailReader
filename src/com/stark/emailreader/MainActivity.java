package com.stark.emailreader;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		// Mail Setup tab
		Intent intentEmailSetup = new Intent().setClass(this,EmailSetup.class);
		TabSpec tabSpecAndroid = tabHost
			.newTabSpec("EmailSetup")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_android_config))
			.setContent(intentEmailSetup);

		// Mail Reader tab
		Intent intentEmailReader = new Intent().setClass(this, EmailReaderActivity.class);
		TabSpec tabSpecApple = tabHost
			.newTabSpec("EmailReader")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_apple_config))
			.setContent(intentEmailReader);
		
		// add all tabs 
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(2);
		
	}

}