package com.fermaursilor.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	Button chestionarA,chestionarB,chestionarC,tabelPenalizari ;
	Button butonPenalties;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        
        chestionarA = (Button) findViewById(R.id.button1);
        chestionarB = (Button) findViewById(R.id.button2);
        chestionarC = (Button) findViewById(R.id.button3);
        tabelPenalizari = (Button) findViewById(R.id.button4);
        
        OnClickListener action =new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
				startActivity(intent);
			}
		};

        butonPenalties = (Button)findViewById(R.id.button4);
        butonPenalties.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(getApplicationContext(), PenaltiesActivity.class);
                startActivity(intent);
                
                
            }
        });
        
		chestionarA.setOnClickListener(action);
        chestionarB.setOnClickListener(action);
        chestionarC.setOnClickListener(action);
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
