package com.stark.emailreader;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class EmailSetup extends Activity{
	//private RadioGroup radioMailGroup;
	  private RadioButton radioG, radioY;
	  private Button btnSet, btnexit;
	  EditText edid,edpwd;
	  String id,pwd;
	  Boolean client;
	  TabHost tabHost;
	  Resources ressources;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home);
	        addListenerOnButton();
	        edid=(EditText)findViewById(R.id.editId);
	        edpwd=(EditText)findViewById(R.id.editPwd);
	        
	        radioG=(RadioButton)findViewById(R.id.radioG);
	        radioY=(RadioButton)findViewById(R.id.radioY);
	        
	}
	
	public void addListenerOnButton() {
		 
		//radioMailGroup = (RadioGroup) findViewById(R.id.radioMail);
		btnSet = (Button) findViewById(R.id.btnSet);
		btnexit = (Button) findViewById(R.id.btnExit);
		btnSet.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//int selectedId = radioMailGroup.getCheckedRadioButtonId();
				//id=edid.getText().toString();
		        //pwd=edpwd.getText().toString();
				// find the radiobutton by returned id
			        //radioMailButton = (RadioButton) findViewById(selectedId);
			        if(radioG.isChecked())
			        {
			        	client=true;
			        	//Toast.makeText(EmailSetup.this,radioG.getText(), Toast.LENGTH_SHORT).show();
			        }
			        else if(radioY.isChecked())
			        {
			        	client=false;
			        	//Toast.makeText(EmailSetup.this,radioY.getText(), Toast.LENGTH_SHORT).show();
			        }
			        
			        Intent mail= new Intent().setClass(getApplicationContext(), EmailReaderActivity.class);
		    		mail.putExtra("email", edid.getText().toString());
		    		mail.putExtra("pass", edpwd.getText().toString());
		    		mail.putExtra("client", client);

		    		startActivity(mail);
		    		
				//Toast.makeText(EmailSetup.this,id.toString()+" "+pwd.toString()+" "+client, Toast.LENGTH_SHORT).show();
			}
		});
		
		btnexit.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	  }
}
