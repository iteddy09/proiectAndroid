package com.fermaursilor.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutApplication extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about_app);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.aboutApplication) {
        	Intent intent = new Intent(getApplicationContext(),AboutApplication.class);
			startActivity(intent);
        }
     
        if (id == R.id.statistics) {
        	Intent intent = new Intent(getApplicationContext(),StatisticsActivity.class);
			startActivity(intent);
        }
		return super.onOptionsItemSelected(item);
	}
}
