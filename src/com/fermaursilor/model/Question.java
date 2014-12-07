package com.fermaursilor.model;

public class Question {
	
	public String question;
	public String answer1;
	public String answer2;
	public String answer3;
	private String imageUrl;
	private String rightAnswers;
	
	public Question(String question,String answer1,String answer2,String answer3,String imageUrl,String rightAnswers){
		this.question = question;
		this.answer1=answer1;
		this.answer2=answer2;
		this.answer3=answer3;
		
		this.imageUrl= imageUrl;
		this.rightAnswers=rightAnswers;
	}		
}
