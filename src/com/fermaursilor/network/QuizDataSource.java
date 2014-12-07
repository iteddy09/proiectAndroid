package com.fermaursilor.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.util.Log;

import com.fermaursilor.android.QuizActivity;

public class QuizDataSource extends Thread{
	
	QuizActivity activity;
	InputStream inputStream;
	JSONArray json;
	String result;
	
	public QuizDataSource(QuizActivity activity){
		this.activity = activity;
	}
	
	public void run(){
		String url_select = "http://fermaursilor.com/chestionarB.json";
   

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(url_select);
           
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();
            
            json = new JSONArray(result); 
            activity.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					 activity.onQuizResponse(json);
				}
            	
            });

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            e.printStackTrace();
        }
        
        
        
	}
	
}
