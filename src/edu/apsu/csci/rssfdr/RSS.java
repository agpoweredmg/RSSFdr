package edu.apsu.csci.rssfdr;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RSS extends Activity {

	private String rssTitle;
	private String rssAddress;
	private RSSaSTask download;
	
	public RSS(){

	}

	public RSS(String u, String n){
		rssAddress = u;
		rssTitle = n;
	}

	public String getUrlString(){
		return rssAddress;
	}

	public void setUrlString(String urlString){
		this.rssAddress = urlString;
	}

	public String getFeedName(){
		return rssTitle;
	}

	public void setFeedName(String feedName){
		this.rssTitle = feedName;
	}

	@Override
	public String toString(){
		return rssTitle;
	}

	@Override
	protected void onPause(){

		super.onPause();
		if (download != null){
			download.cancel(true);
			download = null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		if (extras != null){
			String rssUrl = extras.getString(MainActivity.URL_FIELD);

			try {
				URL url = new URL(rssUrl);
				download = new RSSaSTask();
				download.execute(url);
			} catch (MalformedURLException e){
				e.printStackTrace();
			}

		} else {

		}
	}
	

	class RSSaSTask extends AsyncTask<URL, Void, List<Articles>> {

		@Override
		protected List<Articles> doInBackground(URL... params){
			URL url = params[0];
			LinkedList<Articles> result = new LinkedList<Articles>();

			try {
				HttpURLConnection c = (HttpURLConnection) url.openConnection();

				InputStreamReader isr = new InputStreamReader(
						c.getInputStream());
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(isr);

				boolean inItem = false;

				boolean inUrl = false;
				String storyUrl = null;

				boolean inTitle = false;
				String title = null;

				boolean inSummary = false;
				String summary = null;

				int eventType = xpp.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT){
					if (eventType == XmlPullParser.START_TAG){
						if (xpp.getName().equalsIgnoreCase("item")){
							inItem = true;
						} else if (inItem
								&& xpp.getName().equalsIgnoreCase("link")){
							inUrl = true;
						} else if (inItem
								&& xpp.getName().equalsIgnoreCase("title")){
							inTitle = true;
						} else if (inItem
								&& xpp.getName()
										.equalsIgnoreCase("description")){
							inSummary = true;
						}
					} else if (eventType == XmlPullParser.END_TAG){
						if (xpp.getName().equalsIgnoreCase("item")){
							inItem = false;
							Articles s = new Articles();
							s.setArticle_title(title);
							s.setLink_to_article(storyUrl);
							s.setArticle_summary(summary);

							result.add(s);
						} else if (xpp.getName().equalsIgnoreCase("link")){
							inUrl = false;
						} else if (xpp.getName().equalsIgnoreCase("title")){
							inTitle = false;
						} else if (xpp.getName()
								.equalsIgnoreCase("description")){
							inSummary = false;
						}
					} else if (eventType == XmlPullParser.TEXT){
						if (inUrl){
							storyUrl = xpp.getText().replace("\n", "");
						} else if (inTitle){
							title = xpp.getText().replace("\n", " ");
						} else if (inSummary){
							summary = xpp.getText().replace("\n", " ");
						}
					}
					eventType = xpp.next();
				}

			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Articles> result){

			ListView lv = (ListView) findViewById(R.id.rss_listView);

			ArrayAdapter<Articles> adapter = new ArrayAdapter<Articles>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1,
					result.toArray(new Articles[] {}));

			lv.setAdapter(adapter);

			lv.setOnItemClickListener(new FeedListener());
		}

	}

	class FeedListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id){

			final Articles article = (Articles) parent.getItemAtPosition(position);

			Context mContext = RSS.this;
			AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

			alert.setTitle("Article Summary");
			WebView webView = new WebView(mContext);
			String html = article.getArticle_summary();

			webView.loadData(html, "text/html", "UTF-8");
			alert.setView(webView);
			alert.setMessage("Do you want to view this article?");
			alert.setIcon(R.drawable.ic_launcher);

			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							Uri uri = Uri.parse(article.getLink_to_article());
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
							
						}
					});
			alert.setNegativeButton("No",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							
						}
					});
			alert.show();

		}

	}

	

}
