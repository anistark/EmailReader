package com.stark.emailreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForwardMail extends Activity{
	EditText edTo, edAdd;
	TextView txtSub, txtBody;
	String stadd, stsub, stto;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailforward);
        
        edTo=(EditText)findViewById(R.id.edtxtTo);
        txtSub=(TextView)findViewById(R.id.txtSub);
        txtBody=(TextView)findViewById(R.id.txtBody);
        edAdd=(EditText)findViewById(R.id.edtxtAdd);
        
        Intent i3=getIntent();
        String sub= i3.getStringExtra("mailSub");
        String body= i3.getStringExtra("mailBody");
        txtSub.setText("Forwarded Mail: "+sub);
        txtBody.setText("Content: "+ body);
        
        stadd= txtBody.getText().toString().trim()+" " +edAdd.toString().trim();
        stsub= txtSub.getText().toString().trim();
        stto= edTo.toString().trim();
    }
	public void fforward(View v) {
        try {
        	GMailSender sender = new GMailSender("joydhanbad@gmail.com","10041992joy");
            sender.sendMail(stsub,stadd,"joydhanbad@gmail.com",stto);
            Toast.makeText(getApplicationContext(), "Forwarded Email Sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {   
            Log.e("SendForwardMail", e.getMessage(), e);
            Toast.makeText(getApplicationContext(), "Forwarded Email Not Sent"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
	public void fclear(View v) {
        try {   
            edTo.setText("");
            edAdd.setText("");
        } catch (Exception e) {   
            Log.e("SendForwardMail", e.getMessage(), e);   
        } 
    }
}
