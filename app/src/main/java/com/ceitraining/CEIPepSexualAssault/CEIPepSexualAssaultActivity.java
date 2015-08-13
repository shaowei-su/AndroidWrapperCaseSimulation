package com.ceitraining.CEIPepSexualAssault;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class CEIPepSexualAssaultActivity extends Activity {
    /** Called when the activity is first created. */
    WebView mWebView;
    ProgressBar mProgressBar;
    
    /******************************************************************/
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.main);
        
        /*getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        
        //Set the home title
	    TextView title=(TextView)findViewById(R.id.title);
	    title.setText("Home");
		 
	    // Set the home icon
	    ImageView icon = (ImageView)findViewById(R.id.icon);
	    icon.setBackgroundResource(R.drawable.ic_menu_home);*/
        
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
       
        
        WebSettings webSettings  = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
        mWebView.setWebViewClient(new MyWebViewClient());
        
        mWebView.loadUrl("http://m.ceitraining.org/app/guidelines/pep_sexual_assault/index.jsp");
        
    }
    /******************************************************************/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      //setContentView(R.layout.main);
    }
    /******************************************************************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    /******************************************************************/
	class MyWebViewClient extends WebViewClient  {
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("BugTest", "shouldOverrideUrlLoading: " + url);
			
			if (url.contains("hivguidelines.org") || url.contains("ceitraining.org/?mobile=no")) {
				Log.i("BugTest", "contains");
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				return true;  
	        }else{
	        	Log.i("BugTest", "NOT contains");
	        	view.loadUrl(url); // Stay within this webview and load url
	            return true;
	        }
	    }
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			Log.i("BugTest", "onPageStarted: " + url);
			
			if (!NetworkConnection.isNetworkAvailable(getApplicationContext())) {
				mWebView.loadUrl("file:///android_asset/noconnection.html");
			}
			else
				mProgressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			Log.i("BugTest", "onPageFinished: " + url);
			mProgressBar.setVisibility(View.GONE);
			
		}

	}

}