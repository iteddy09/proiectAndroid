package com.fermaursilor.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fermaursilor.model.Question;
import com.fermaursilor.model.questionAdapter;
import com.fermaursilor.network.QuizDataSource;
import com.fermaursilor.sqlite.SqliteController;

public class QuizActivity extends Activity {

	private List<Question> quiz;
	private ListView listView;
	private Integer questionNumber = 0;
	private Integer chosenAnswer = 0;
	private String tipChestionar;
	
	private Handler handler = new Handler();
	   
	private Runnable updateTimerThread = new Runnable() {
	        public void run() {
	        	Log.i("APLICATIE","update time");
	        	QuizActivity.this.updateRemainingTime();
	            if(QuizActivity.this.durationSeconds>0) handler.postDelayed(this, 1000);
	        }
	};
 
	private int remaining, gresite = 0, corecte = 0, maxGresite,
			durationSeconds = 0;

	private OnClickListener sendAnswerListner, nextQuestionListener;

	private TextView ramaseLabel, gresiteLabel, timpLabel;

	Button answerLater, sendAnswer;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz);
        
        if(!isNetworkAvailable()){
        	Toast.makeText(this, "Va rugam sa va conectati la internet!",Toast.LENGTH_LONG).show();
        }
        
        ramaseLabel = (TextView) findViewById(R.id.textViewRemaining);
        gresiteLabel = (TextView) findViewById(R.id.textViewWrong);
        timpLabel = (TextView) findViewById(R.id.textViewTime);
        
        listView = (ListView) findViewById(R.id.questionLw);
        
        Intent intent = getIntent();
        this.tipChestionar = intent.getStringExtra("tip_chestionar");
        
        if(tipChestionar.equals("b")){
        	this.remaining=26;
        	this.maxGresite=5;
        	this.durationSeconds=30*60;
        	
        }
        
        
       

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
	
	@Override
	protected void onStop(){
		super.onStop();
		handler.removeCallbacks(updateTimerThread);
		Log.i("APLICATIE","update time thread stopped");
		
	}
	
	public void modifySelectedAnswers(CheckBox r1, CheckBox r2, CheckBox r3) {
		chosenAnswer = 0;
		if (r1.isChecked())
			chosenAnswer += 100;
		if (r2.isChecked())
			chosenAnswer += 10;
		if (r3.isChecked())
			chosenAnswer += 1;
	}

	public void clearButton() {
		sendAnswer.setText(R.string.sendAnswer);
		sendAnswer.setBackground(getResources().getDrawable(
				R.drawable.custom_btn_fu));
	}

	public void setButtonBgGreen() {
		sendAnswer.setBackground(getResources().getDrawable(
				R.drawable.custom_btn_green));
	}

	public void setButtonBgRed() {
		sendAnswer.setBackground(getResources().getDrawable(
				R.drawable.custom_btn_red));
	}

	public void sendAnswer() {
		if (chosenAnswer.equals(0)) {
			Toast.makeText(this, R.string.select_answer, Toast.LENGTH_SHORT)
					.show();
		} else {
			this.remaining--;
			Question question = quiz.get(questionNumber);
			if (question.answer(chosenAnswer)) {

				this.corecte++;

				sendAnswer.setText(getResources().getString(
						R.string.correct_answer)
						+ question.getCorrectAnswerString() + " >");
				setButtonBgGreen();
			} else {
				this.gresite++;
				sendAnswer.setText(getResources().getString(
						R.string.wrong_answer)
						+ question.getCorrectAnswerString() + " >");
				setButtonBgRed();
				
				
				

			}

			updateTopStats();

			if(gresite==maxGresite || remaining==0){
				
				
				SqliteController ctrl = new SqliteController(this);
				ctrl.insertRatio(corecte*1.0/(gresite+corecte), tipChestionar);
				
				OnClickListener afisareToast = new OnClickListener() {
					@Override public void onClick(View v) { QuizActivity.this.afisareToastDeclarat(); }
				};
				
				sendAnswer.setOnClickListener(afisareToast);
				
				answerLater.setOnClickListener(afisareToast);
				
				
			}else
				sendAnswer.setOnClickListener(nextQuestionListener);
		}
	}

	public void afisareToastDeclarat(){
		
		String mesaj = "Ai fost declarat ";
		
		if(gresite==maxGresite){
			mesaj+="RESPINS";
		}
		
		
		if(corecte>gresite && remaining==0){
			mesaj+="ADMIS";
		}
		
		Toast.makeText(this, mesaj, Toast.LENGTH_SHORT).show();
	}
	
	
	public void updateTopStats() {
		ramaseLabel.setText(getResources().getString(R.string.remaining) + " " + remaining);
		gresiteLabel.setText(getResources().getString(R.string.wrongAnswers) + " " + gresite );

	}

	
	private void updateRemainingTime() {
		durationSeconds--;
		String remainingTime = String.format("%02d:%02d", (durationSeconds % 3600) / 60, (durationSeconds % 60));

		timpLabel.setText(getResources().getString(R.string.timeRemaning) + " " + remainingTime);
	}

	public void onQuizResponse(JSONArray jsonQuiz) {
		Log.i("QUIZ ", "RETREIVED " + jsonQuiz.length() + "");

		 findViewById(R.id.progressBar1).setVisibility(View.GONE);
		 findViewById(R.id.textViewLoading).setVisibility(View.GONE);
		 
		 //start coundown
	     handler.post(updateTimerThread);
		 
		 
	     updateTopStats();
	     
		for (int i = 0; i < jsonQuiz.length(); i++) {
			try {
				JSONObject question = jsonQuiz.getJSONObject(i);

				String intrb = question.getString("intrb");
				String r1 = question.getString("r1");
				String r2 = question.getString("r2");
				String r3 = question.getString("r3");
				int rc = question.getInt("rc");
				String img = question.getString("img");

				Question q = new Question(intrb, r1, r2, r3, img, rc);
				this.quiz.add(q);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Log.i("QUIZ ", "PARSED " + quiz.size() + "");

		loadQuestionOnView(0);
	}

	public void loadQuestionOnView(int index) {
		chosenAnswer = 0;

		questionAdapter adaptor = new questionAdapter(this, quiz.get(index));
		listView.setAdapter(adaptor);

	}

	
	  private boolean isNetworkAvailable() {
	        ConnectivityManager connectivityManager 
	              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	    }
}
