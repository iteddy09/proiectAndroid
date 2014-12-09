package com.fermaursilor.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Question {
	
	public String question;
	public String answer1;
	public String answer2;
	public String answer3;
	public String imageUrl;
	
	private Integer chosenAnswer;
	private Integer correctAnswer;
	
	public Question(String question,String answer1,String answer2,String answer3,String imageUrl,int rightAnswers){
		this.question = question;
		this.answer1=answer1;
		this.answer2=answer2;
		this.answer3=answer3;
		

		this.imageUrl= imageUrl;
		this.correctAnswer=rightAnswers;
	}		
	
	
	public boolean isCorrect(Integer answeredCode){
		this.chosenAnswer = answeredCode;
		
		if(correctAnswer.equals(answeredCode))
			return true;
		else
			return false;
	}
	
	public boolean correctAnswered(){
		return isCorrect(chosenAnswer);
	}
	
	public String getCorrectAnswerString(){
		switch(correctAnswer){
		case 1: return "C";
		case 10: return "B";
		case 100: return "A";
		case 110: return "A B";
		case 111: return "A B C";
		case 101: return "A C";
		case 11: return "B C";
		default: return "Error";
		}
	}
 
}
