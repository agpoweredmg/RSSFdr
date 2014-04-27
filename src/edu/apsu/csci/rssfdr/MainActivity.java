package edu.apsu.csci.rssfdr;

import java.util.ArrayList;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private static final int BLACK_ON_WHITE_BG = Color.WHITE;
	private static final int BLACK_ON_WHITE_TXT = Color.BLACK;
	
	private static final int WHITE_ON_BLACK_BG = Color.BLACK;
	private static final int WHITE_ON_BLACK_TXT = Color.WHITE;
	
	private ArrayList<RSS> rssList = new ArrayList<RSS>();
	private ArrayList<RSS> current = new ArrayList<RSS>();
	private ArrayAdapter<RSS> rssAdapter;
	private ListView rssView;
	
	protected static final String URL_FIELD = "url";
	
	private LinearLayout ll;
	private TextView tv;
	
	private int toggleColorBG;
	private int toggleColorTXT;
	
	private int initialRSSFeeds = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ll = (LinearLayout) findViewById(R.id._layout);
		ll.setBackgroundColor(Color.WHITE);

		//tv = (TextView) findViewById(R.id.adapter_tv);
		//tv.setTextColor(Color.BLUE);
		//tv.setTextColor(Color.BLUE);
		
		rssList = new ArrayList<RSS>();
		current = new ArrayList<RSS>();
		RSSList();
		initialSetUp();

	}

	private void RSSList() {
		// TODO Auto-generated method stub
		rssList = new ArrayList<RSS>();
		String[] rssTitle = { "CNN TOP STORIES", "CNN WORLD", "Reuters Arts ",
				"Reuters Entertainment"};

		String[] rssUrl = { "http://rss.cnn.com/rss/cnn_topstories.rss",
							"http://rss.cnn.com/rss/cnn_world.rss ",
							"http://feeds.reuters.com/news/artsculture",
							"http://feeds.reuters.com/reuters/entertainment", };

		for (int i = 0; i < rssTitle.length; i++) {
			rssList.add(new RSS(rssUrl[i], rssTitle[i]));
		}
		for (int j = 0; j < initialRSSFeeds; j++) {
			current.add(rssList.get(j));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_settings:
			
			break;
		case R.id.add_rss_feed:
			addRSSFeed();
			break;
		case R.id.delete_rss_feed:
			deleteRSSFeed();
			break;
		case R.id.black_on_white:
			toggleColors(true);
			break;
		case R.id.white_on_black:
			toggleColors(false);
			break;
		case R.id.change_text_size:
			changeTextSize();
			break;
		default:
			break;
		
		
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void initialSetUp() {

		new ArrayList<Articles>();

		rssView = (ListView) findViewById(R.id.rss_list);
		rssAdapter = new ArrayAdapter<RSS>(this, R.layout.adapter_text_view,
				current);
		rssView.setAdapter(rssAdapter);
		rssView.setOnItemClickListener(new RSSListener());
	}

	class RSSListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			RSS rss = (RSS) parent.getItemAtPosition(position);
			String rssUrlString = rss.getUrlString();
			Intent intent = new Intent(getApplicationContext(), RSS.class);
			intent.putExtra(URL_FIELD, rssUrlString);
			startActivity(intent);
		}

	}

	public void addRSSFeed(){
	
	}
	
	public void deleteRSSFeed(){
		
	}
	
	public void toggleColors(boolean swap){
		if(swap){
			toggleColorBG = BLACK_ON_WHITE_BG;
			toggleColorTXT = BLACK_ON_WHITE_TXT;
		}else{
			toggleColorBG = WHITE_ON_BLACK_BG;
			toggleColorTXT = WHITE_ON_BLACK_TXT;
		}
		
		for(int i = 0; i < rssList.getChildCount(); i++){ 
			View listItem = rssList.getChildAt(i); 
			View tv = listItem.findViewById(R.id.adapter_tv): 
			listItem.setBackgroundColor(toggleColorBG): 
			tv.setBackgroundColor(toggleColorTXT); 
			}
		
	}
	
	public void changeTextSize(){
	
	}
	
	@Override
	public void onClick(View v) {
		
	}
}
