package com.fermaursilor.model;



import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fermaursilor.android.PenaltiesActivity;
import com.fermaursilor.android.R;
	
	public class tabelAdapter extends BaseAdapter{ 
	   
		ArrayList<String> listaTexte;
		ArrayList<String> listaValori;
		
		private PenaltiesActivity activitateParinte;
	     private Context context;
	     private static LayoutInflater inflater=null;

	    public tabelAdapter(PenaltiesActivity mainActivity, ArrayList<String> listaText, ArrayList<String> listaVal) {
	        
	    	this.listaTexte = listaText;
	    	this.listaValori = listaVal;
	    	activitateParinte = mainActivity;
	    	
	        
	        context=mainActivity;
	       
	        inflater = ( LayoutInflater )context.
	                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	    
	    
	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return listaTexte.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	 
	    public class Holder
	    {
	        TextView tvText;
	        TextView tvVal;
	        
	    }
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        Holder holder=new Holder();
	        View rowView;        
	             rowView = inflater.inflate(R.layout.layout_table_row, null);
	             holder.tvText=(TextView) rowView.findViewById(R.id.TextViewTabel_Text);
	             //holder.tvVal = (TextView) rowView.findViewById(R.id.TextViewTabel_Valoare);
	             
	             
	         int Valoare = Integer.valueOf(listaValori.get(position));
	         if(Valoare==0)
	        	 {
	        	 
	        	 	holder.tvText.setTextSize(19);
	        	 	holder.tvText.setTextColor(Color.rgb(123, 143, 197));
	        	 	holder.tvText.setText(listaTexte.get(position));
	        	 	//holder.tvVal.setVisibility(View.GONE);
	        	 }
	         else
	         	{
	        	 	holder.tvText.setTextSize(15);
	        	 	holder.tvText.setText(listaTexte.get(position)+" - "+listaValori.get(position)+" puncte");
	        	 	//.tvVal.setTextSize(23);
	        	 	//holder.tvVal.setTextColor(Color.RED);
	        	  
	        	 	//holder.tvVal.setText(listaValori.get(position));
	        	 }
	         
	         
	        
	        return rowView;
	    }
	 
	}
	
	

