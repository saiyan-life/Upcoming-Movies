package org.sysdll.movies.main;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.sysdll.movies.R;
import org.sysdll.movies.adapter.CustomListAdapter;
import org.sysdll.movies.app.AppController;
import org.sysdll.movies.model.Movie;



public class MainActivity extends Activity {
	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();

	// Movies json url
	private static final String url = "http://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f";
	
	 //StringBuilder 
	 StringBuilder stringBuilder = new StringBuilder();
  
	
	private ProgressDialog pDialog;
	private List<Movie> movieList = new ArrayList<Movie>();
	
	private ListView listView;
	private CustomListAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		listView.setAdapter(adapter);

		  // Log.d(TAG, "0");
		   
		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));
		Log.d(TAG, "0.1");

			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,url, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(TAG, "Response: " + response.toString());
							if (response != null) {
								parseJsonFeed(response);
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
					});

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
	//	}
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("MainAcitivity", "movieList id : "+movieList.get(position).getId() );
				Intent i = new Intent(MainActivity.this, Detail.class);
				i.putExtra("id_for_detail", movieList.get(position).getId());
				startActivity(i);
				//Toast.makeText(MainActivity.this, ""+movieList.get(position).getId(), Toast.LENGTH_LONG).show();
				
			}
		});
	}
	
	
	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		try {
		
			JSONArray feedArray = response.getJSONArray("results");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				//FeedItem item = new FeedItem();
				Movie item = new Movie();
				
				item.setId(feedObj.getInt("id"));
				
				item.setTitle(feedObj.getString("original_title"));
				item.setYear(feedObj.getString("release_date"));
				
				if(feedObj.getString("poster_path")!="null")
				{String image="http://image.tmdb.org/t/p/w342/"+feedObj.getString("poster_path");
				item.setThumbnailUrl(image);
				}
				else
				{item.setThumbnailUrl("https://browshot.com/static/images/not-found.png");	
				}
				
				if(!feedObj.getBoolean("adult"))
				{
				    item.setAdult("(U/A)");
				}
				else
				{
					item.setAdult("(A)");
				}
				
				// Image might be null sometimes
			//	String image = feedObj.isNull("image") ? null : feedObj.getString("image");
				//item.setImge(image);
				
				movieList.add(item);
			}

			// notify data changes to list adapater
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.about:
			Intent i = new Intent(this, About.class);
			startActivity(i);
	        
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);

	}
	
}
