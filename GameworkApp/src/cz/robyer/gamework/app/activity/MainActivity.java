/*package cz.robyer.gamework.app.activity;

import cz.robyer.gamework.app.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
*/

package cz.robyer.gamework.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cz.robyer.gamework.app.R;
import cz.robyer.gamework.app.game.GameService;

/**
 * This is the main activity of application.
 * @author Robert P�sel
 */
public class MainActivity extends BaseActivity {
	//private static final String TAG = MainActivity.class.getSimpleName();

	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButtons();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (dialog != null)
			dialog.dismiss();
	}
	
	/**
	 * Init handlers for main buttons.
	 */
	private void initButtons() {

		((Button)findViewById(R.id.btn_play)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true, true);
            	
				// Start game service with example.xml
				Intent intent = new Intent(MainActivity.this, GameService.class);
				intent.putExtra("filename", "example.xml");
            	startService(intent);
			}
		});
		
		/*((Button)findViewById(R.id.btn_help)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HelpActivity.class);
				startActivity(intent);
			}
		});*/
	}

}
