package org.sysdll.movies.main;

import org.sysdll.movies.R;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.ProgressBar;

public class SplashScreen extends Activity {

ProgressBar prgLoading;
    
	int progress = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		 prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
	        
			prgLoading.setProgress(progress);

			
			new Loading().execute();
	}

	 /** this class is used to handle thread */
    public class Loading extends AsyncTask<Void, Void, Void>{
    	
    	@Override
		 protected void onPreExecute() {
		  // TODO Auto-generated method stub
    		
    	}
    	
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			while(progress < 100){
				try {
					Thread.sleep(1000);
					progress += 30;
				prgLoading.setProgress(progress);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			return null;
		}
	
	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			startActivity(i);
		}
		
    }
    
    
    
   
}
