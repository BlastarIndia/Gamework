package cz.robyer.gamework.service;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Helper class for connect javascript in webview with java methods.
 * @author Robert P�sel
 */
public class JavaScriptHandler {
	
	Activity parent;
	
	public JavaScriptHandler(Activity activity) {
		parent = activity;
	}
	
	@JavascriptInterface
	public void showToast(String text) {
		Toast.makeText(parent, text, Toast.LENGTH_LONG).show();
	}

}
