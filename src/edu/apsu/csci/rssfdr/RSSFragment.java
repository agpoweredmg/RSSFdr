package edu.apsu.csci.rssfdr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RSSFragment extends Fragment{

	public RSSFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.rss, container, false);
		
		return view;
	}

}
