package com.pmse;



import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeSettings extends Activity implements View.OnClickListener{

	EditText editText1;
	EditText editText2;
	EditText editText3;

	TextView tv;

	Button save, back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		main();
	}
	
	public void main() {
		editText1 = (EditText) findViewById(R.id.serverIp);
		editText2 = (EditText) findViewById(R.id.et_un);
		editText3 = (EditText) findViewById(R.id.et_pw);

		editText1.setText(PMSEServer.serverIp);
		

		save = (Button) findViewById(R.id.savebt);
		save.setOnClickListener(this);
		back = (Button) findViewById(R.id.backbt);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == save) {
			PMSEServer.serverIp = editText1.getText().toString();
			PMSEServer.un = editText2.getText().toString();
			PMSEServer.pw = editText3.getText().toString();
			PMSEServer.httpServerUrl = "http://" + PMSEServer.serverIp + ":"
					+ PMSEServer.serverPort + "/"+PMSEServer.project+"/";
			
			Toast.makeText(ChangeSettings.this, "Settings Changed Successfully", Toast.LENGTH_SHORT)
			.show();
		}
		
		if (v == back) {
			Intent intent = new Intent(ChangeSettings.this, MainActivity.class);			
			startActivity(intent);
		}
		
	}

	public static int locationROundOff() {
		Random r = new Random();
        int Low = 0;
        int High = 999;
        int R = r.nextInt(High - Low) + Low;
        return R;
	}
	
}
