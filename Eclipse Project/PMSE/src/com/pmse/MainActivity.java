package com.pmse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener,
		TextWatcher {

	Button but1;
	Button but2;
	
	public AutoCompleteTextView txtAutoComplete;
	private static String[] keywords = { "Specify" };
	public ArrayAdapter<String> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main();
	}

	public void main() {
		txtAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		but1 = (Button) findViewById(R.id.button1);
		but1.setOnClickListener(this);
		but2 = (Button) findViewById(R.id.button2);
		but2.setOnClickListener(this);

		txtAutoComplete.addTextChangedListener(this);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, keywords);
		txtAutoComplete.setAdapter(adapter);

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class LoadWebPageASYNC extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {			
			
			InputStream is = null;
			try {			
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urls[0]);
				// to send parameters to servlet	    
				
			    HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				
				is = entity.getContent();
				
			} catch (Exception e) {
				return "Connection Exception : " + e.toString();
			}
			// convert response to string
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();

				
				return sb.toString();
			} catch (Exception e) {
				return "Conversion Exception : " + e.toString();
			}

			
		}

		@Override
		protected void onPostExecute(String result) {
			if(result.split("[$$$]+").length>1)
			{
				keywords = result.split("[$$$]+");
			}
			else
			{
				String a[] = {result.trim()};
				keywords = a;
			}
				
			
		}
	}

	public void dummyFunc(View view) {
		Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT)
				.show();

	}

	public void readWebpage(View view) {
		//String keyword = ""+txtAutoComplete.getText(); 
		//Toast.makeText(MainActivity.this, PMSEServer.httpServerUrl+"mobQueryResults.jsp?search="+PMSEServer.keyword, Toast.LENGTH_SHORT).show();
		
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		String lat = getString(R.string.lat)+ChangeSettings.locationROundOff()+"N";
		String longt = getString(R.string.longt)+ChangeSettings.locationROundOff()+"E";
		webView.loadUrl(PMSEServer.httpServerUrl+"mobQueryResults.jsp?search="+PMSEServer.keyword+"&un="+PMSEServer.un+"&pw="+PMSEServer.pw+"&la="+lat+"&lo="+longt);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == but1) {
			readWebpage(v);
		}
		
		if (v == but2) {			
				Intent intent = new Intent(MainActivity.this,ChangeSettings.class);			
				startActivity(intent);			
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub		
		PMSEServer.keyword=""+txtAutoComplete.getText();
		LoadWebPageASYNC task = new LoadWebPageASYNC();
		String lat = getString(R.string.lat)+ChangeSettings.locationROundOff()+"N";
		String longt = getString(R.string.longt)+ChangeSettings.locationROundOff()+"E";
		
		task.execute(new String[] { PMSEServer.httpServerUrl+"mobDynResults.jsp?search="+PMSEServer.keyword+"&un="+PMSEServer.un+"&pw="+PMSEServer.pw+"&la="+lat+"&lo="+longt});
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, keywords);
		txtAutoComplete.setAdapter(adapter);
	}

}
