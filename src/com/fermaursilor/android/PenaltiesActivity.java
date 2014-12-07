package com.fermaursilor.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PenaltiesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_penalties);
		
		ListView lvTabel = (ListView)findViewById(R.id.listView1);
		citireTabel(lvTabel);
		
		
	}
	
	public void citireTabel(ListView lv){
		
		ArrayList<String> listTexte = new ArrayList<String>();
		ArrayList<String> listValori = new ArrayList<String>();
		
		
		InputStream inputStream = this.getResources().openRawResource(R.raw.tabel);
		
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		
		String text;
		String valoare;
		try {
			while((text=br.readLine())!=null)
			{
				listTexte.add(text);
				valoare=br.readLine();
					if(valoare!=null)
						listValori.add(valoare);
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("Testare",listTexte.size()+ "- " + listValori.size());
		tabelAdapter adaptor = new tabelAdapter(this, listTexte, listValori);
		lv.setAdapter(adaptor);
		
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
