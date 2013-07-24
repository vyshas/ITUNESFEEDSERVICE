package com.example.itunessax;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		
		//get the preview link from the intent that started this activity
		
		Intent intent=getIntent();
		String SongLink=intent.getStringExtra("SongLink");
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(SongLink);
		
		
	}

	

}
