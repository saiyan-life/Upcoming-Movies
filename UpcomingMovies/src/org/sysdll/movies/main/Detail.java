package org.sysdll.movies.main;

import java.util.HashMap;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import org.sysdll.movies.R;
import org.sysdll.movies.app.AppController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends Activity  {
	//BaseSliderView.OnSliderClickListener
	int id;
	String url,url2;
	TextView title , overView;
	ImageView starR1,starR2,starR3,starR4,starR5;
	
	Map<String,String> urlImg = new HashMap<String, String>();
	
	 private SliderLayout mDemoSlider;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		// 
		 mDemoSlider = (SliderLayout)findViewById(R.id.slider);
		 
		 starR1 = (ImageView) findViewById(R.id.strr1);
		 starR2 = (ImageView) findViewById(R.id.strr2);
		 starR3 = (ImageView) findViewById(R.id.strr3);
		 starR4 = (ImageView) findViewById(R.id.strr4);
		 starR5 = (ImageView) findViewById(R.id.strr5);
		 
		 final ImageView[] strImgs = {starR1 , starR2,starR3,starR4,starR5};
		 
		Intent i_get = getIntent();
		id = i_get.getIntExtra("id_for_detail", 0);
		
		url="http://api.themoviedb.org/3/movie/"+id+"?api_key=b7cd3340a794e5a2f35e3abb820b497f";

		title = (TextView) findViewById(R.id.title2);
		overView = (TextView) findViewById(R.id.overView);
		
		
	//	Log.d("walo response1", "test id = "+id);
		

		JsonObjectRequest movieReq = new JsonObjectRequest(Method.GET,url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				VolleyLog.d("tag", "Response: " + response.toString());
											try {
												title.setText(response.getString("title"));
												overView.setText(response.getString("overview"));
												
										        int  popl= (int) response.getDouble("popularity");
										         Log.d("response", "popl = " +popl);
										         setTitle(response.getString("title"));

										       //  String posterImg="http://image.tmdb.org/t/p/w342/"+response.getString("poster_path");
													getActionBar().setIcon(R.drawable.icon_mov);
//											
													
													////////SETTING THE THE GOLD STARS /////////
													if(popl>5)
													{
														for(int i=0;i<5;i++)
												         {strImgs[i].setBackgroundResource(R.drawable.star_a);	 
												         }
													}
													else if(popl<=5)
													{
											         for(int i=0;i<popl;i++)
											         {strImgs[i].setBackgroundResource(R.drawable.star_a);	 
											         }
													}
													
										       
										         
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("tag", "Error: " + error.getMessage());
			}
		});
		
		AppController.getInstance().addToRequestQueue(movieReq);
		
		
		new GetImagesAsynctask().execute();
		

	}
	

	//Asyntask For Images

	public class GetImagesAsynctask extends AsyncTask<Void, Void, Void>{
		ProgressDialog progress;
    	@Override
		 protected void onPreExecute() {
		  // TODO Auto-generated method stub
    		
    		// show progress dialog when fetching data from database
    		progress= ProgressDialog.show(
    				Detail.this, 
    				"", 
    				getString(R.string.wait), 
    				true);
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
						//////////////////////////////GET THE IMAGES ///////////////////////////////////////////
									
						url2="http://api.themoviedb.org/3/movie/"+id+"/images?api_key=b7cd3340a794e5a2f35e3abb820b497f";
						
						JsonObjectRequest imgs = new JsonObjectRequest(Method.GET,url2, null, new Response.Listener<JSONObject>() {
						
						@Override
						public void onResponse(JSONObject response) {
						//VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							Log.d("response", "dkhel");
							parseJsonFeed2(response);
						//	sliderLoop();
						}
						}
						}, new Response.ErrorListener() {
						
						@Override
						public void onErrorResponse(VolleyError error) {
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
						});
						
						// Adding request to volley request queue
						AppController.getInstance().addToRequestQueue(imgs);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					    public void run() {
					    	progress.dismiss();
					    }
					}, 2000); // 3000 milliseconds delay
		
			
		}
	}
	
	
	
	
	private void parseJsonFeed2(JSONObject response) {
		try {
		
			JSONArray feedArray = response.getJSONArray("backdrops");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);
				Log.d("response", "dkhel2");
				String img="http://image.tmdb.org/t/p/w342/"+feedObj.getString("file_path");

				//url_maps.put(" "+i, img);
				urlImg.put("img "+i, img);
				Log.d("response", "dkhel3 "+feedObj.getString("file_path"));
			}

			Log.d("loop", "enter you idiot");
			Log.d("loop", "size :" + urlImg.size());
			
			int j=0;
			
		//	for(String key : url_maps.keySet()){
				
			for (int i = 0; i < feedArray.length(); i++) {
				Log.d("loop", "key :"+i + " url_maps.get(key) "+urlImg.get("img "+i));
	            TextSliderView textSliderView = new TextSliderView(this);
	            // initialize a SliderLayout
	            if(j<5){
	            textSliderView
	                    .description("img "+i)
	                    .image(urlImg.get("img "+i))
	                    .setScaleType(BaseSliderView.ScaleType.Fit);
	                    //.setOnSliderClickListener(this);

	            
	            //add your extra information
	            textSliderView.getBundle()
	                    .putString("extra","img "+i);

	           mDemoSlider.addSlider(textSliderView);
	            }
	           j++;
			}
	      //  }
	        
	        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
	        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
	        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
	        mDemoSlider.setDuration(4000);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
	//	Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
	//	mDemoSlider.clearAnimation()
	//	mDemoSlider.refreshDrawableState();
	//	mDemoSlider.removeSliderAt(0);
	//	mDemoSlider.removeAllViewsInLayout();
	//	mDemoSlider.
    //  mDemoSlider.removeAllSliders();
    //  mDemoSlider.removeSliderAt(0);
    //	mDemoSlider.destroyDrawingCache();
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
