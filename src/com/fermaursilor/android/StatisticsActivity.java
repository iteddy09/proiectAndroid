package com.fermaursilor.android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.fermaursilor.model.Ratio;
import com.fermaursilor.sqlite.SqliteController;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		SqliteController ctrl = new SqliteController(this);
		
		ArrayList<Ratio> list = ctrl.getAllRatio();
	
		for(Ratio ratio : list){
			System.out.println(ratio);
		}
		
	}

}
