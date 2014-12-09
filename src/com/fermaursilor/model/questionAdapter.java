package com.fermaursilor.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.fermaursilor.android.QuizActivity;
import com.fermaursilor.android.R;

public class questionAdapter extends BaseAdapter {
	private QuizActivity context;
	private Question q;
	private static LayoutInflater inflater = null;
	CheckBox r1, r2, r3;

	public questionAdapter(QuizActivity activity, Question q) {
		this.q = q;
		context = activity;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto/-generated method stub
		View rowView;
		rowView = inflater.inflate(R.layout.layout_question, null);

		ImageView img = (ImageView) rowView.findViewById(R.id.img);
		TextView intrb = (TextView) rowView.findViewById(R.id.intrb);
		r1 = (CheckBox) rowView.findViewById(R.id.r1);
		r2 = (CheckBox) rowView.findViewById(R.id.r2);
		r3 = (CheckBox) rowView.findViewById(R.id.r3);

		OnCheckedChangeListener listner = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				context.modifySelectedAnswers(r1, r2, r3);
			}
		};

		r1.setOnCheckedChangeListener(listner);
		r2.setOnCheckedChangeListener(listner);
		r3.setOnCheckedChangeListener(listner);
		if (!q.imageUrl.isEmpty()) {
			int id = context.getResources().getIdentifier(
					"question_img_" + q.imageUrl, "drawable",
					context.getPackageName());
			img.setImageResource(id);
			img.setVisibility(View.VISIBLE);
		} else {
			img.setVisibility(View.GONE);
		}

		intrb.setText(q.question);
		r1.setText(q.answer1);
		r2.setText(q.answer2);
		r3.setText(q.answer3);

		return rowView;
	}

}
