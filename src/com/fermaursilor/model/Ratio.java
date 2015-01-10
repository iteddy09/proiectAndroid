package com.fermaursilor.model;

import java.sql.Timestamp;

public class Ratio {
	public String day;
	public String tipChestionar;
	public int id;
	public Double ratio;
	
	public Ratio(int id,Double ratio,String day, String tipChestionar){
		
		
		this.day=day;
		this.id = id;
		this.ratio=ratio;
		this.tipChestionar=tipChestionar;
	}
	
	public String toString(){
		return String.format("#%d %f %s %s",id,ratio,day,tipChestionar);
	}
}
