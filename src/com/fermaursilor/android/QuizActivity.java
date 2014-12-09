package com.fermaursilor.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.fermaursilor.model.Question;
import com.fermaursilor.model.questionAdapter;
import com.fermaursilor.network.QuizDataSource;


public class QuizActivity extends Activity {

	private List<Question> quiz;
	private ListView listView;
	private Integer questionNumber = 0;
	private Integer chosenAnswer = 0;
	
	private OnClickListener sendAnswerListner,nextQuestionListener;
	
 
	
	Button answerLater, sendAnswer;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz);
         
        listView = (ListView) findViewById(R.id.questionLw);
        
       
		
        QuizDataSource getQuiz = new QuizDataSource(this);
        getQuiz.start();
        
        this.quiz = new ArrayList<Question>();
        
        answerLater = (Button) findViewById(R.id.respondLater);
        sendAnswer = (Button) findViewById(R.id.sendAnswer);
        
        answerLater.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadQuestionOnView(++questionNumber);
			}
		});
        
        sendAnswerListner = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendAnswer();
			}
		};
		nextQuestionListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				clearButton();
				
				loadQuestionOnView(++questionNumber);
				sendAnswer.setOnClickListener(sendAnswerListner);
			}
		};
        sendAnswer.setOnClickListener(sendAnswerListner);
        Log.i("APLICATIE","S-a incarcat");
    }
    
    public void modifySelectedAnswers(CheckBox r1, CheckBox r2, CheckBox r3){
    	chosenAnswer =0;
    	if(r1.isChecked()) chosenAnswer+=100;
    	if(r2.isChecked()) chosenAnswer+=10;
    	if(r3.isChecked()) chosenAnswer+=1;
    }

    public void clearButton(){
    	sendAnswer.setText(R.string.sendAnswer);
    	sendAnswer.setBackgroundColor(color.background_dark);
    }
    
    public void sendAnswer(){
    	if(chosenAnswer.equals(0)){
    		Toast.makeText(this, "Va rugam alegeti un raspuns.", Toast.LENGTH_SHORT).show();
    	}else{

    		Question question = quiz.get(questionNumber);
    		if(question.isCorrect(chosenAnswer)){
    			sendAnswer.setText("Corect: "+question.getCorrectAnswerString()+" >");
    			sendAnswer.setBackgroundColor(Color.GREEN);
    		}else{
    			sendAnswer.setText("Gresit: "+question.getCorrectAnswerString()+" >");
    			sendAnswer.setBackgroundColor(Color.RED);
    		}
    		
    		updateTopStats();
    		
    		sendAnswer.setOnClickListener(nextQuestionListener);
    	}
    }
    
    public void updateTopStats(){
    	
    }
    
    public void onQuizResponse(JSONArray jsonQuiz){
    	Log.i("QUIZ ", "RETREIVED "+jsonQuiz.length()+"");
    	
    	for(int i =0;i< jsonQuiz.length();i++){
    		try {
				JSONObject question = jsonQuiz.getJSONObject(i);
				
				String intrb = question.getString("intrb");
				String r1= question.getString("r1");
				String r2= question.getString("r2");
				String r3= question.getString("r3");
				int rc= question.getInt("rc");
				String img= question.getString("img");
				
				Question q = new Question(intrb, r1, r2, r3, img, rc);
				this.quiz.add(q);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	Log.i("QUIZ ", "PARSED "+quiz.size()+"");
    	
    	loadQuestionOnView(0);
    }

    public void loadQuestionOnView(int index){
    	chosenAnswer=0;
    	
    	questionAdapter adaptor = new questionAdapter(this, quiz.get(index));
		listView.setAdapter(adaptor);
		
		
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
