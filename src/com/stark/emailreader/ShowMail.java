package com.stark.emailreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMail extends Activity{
	final Context context = this;
	String mailto, content, subject, mailfromid, mailfrompass;
	TextView tvmail;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmail);
        tvmail= (TextView)findViewById(R.id.txtviewmail);
        Intent i2=getIntent();
        content= i2.getStringExtra("mailcontent");
        subject= i2.getStringExtra("mailsub");
        mailfromid= i2.getStringExtra("mailfromid");
        mailfrompass=i2.getStringExtra("mailfrompass");
        tvmail.setText(content);

	}
	public void erback(View vb){
		finish();
	}
	public void sendingAccept(View v) {
        try {
        	GMailSender sender = new GMailSender(mailfromid,mailfrompass);
            sender.sendMail("Mail Accepted",   
                    "Congratulations!! Your proposal has been accepted. ",   
                    mailfromid,   
                    mailto);
            Toast.makeText(getApplicationContext(), "Accpet Email Sent", Toast.LENGTH_SHORT).show();
    		
        } catch (Exception e) {   
        	Toast.makeText(getApplicationContext(), "Accept Email Sent. Err: "+e, Toast.LENGTH_SHORT).show();
        } 
    }
    public void sendReject(View vr){
    	try{
    		GMailSender sender = new GMailSender(mailfromid,mailfrompass);
            sender.sendMail("Mail Accepted",   
                    "Sorry!! Your proposal has been rejected. ",   
                    mailfromid,   
                    mailto);
            Toast.makeText(getApplicationContext(), "Reject Email Sent", Toast.LENGTH_SHORT).show();
    	}catch(Exception e){
    		Toast.makeText(getApplicationContext(), "Reject Email Sent. Err: "+e, Toast.LENGTH_SHORT).show();
    	}
    }
    public void forward(View vf){
    	try{
    		Intent mailforward= new Intent(getApplicationContext(), ForwardMail.class);
    		mailforward.putExtra("mailSub", subject);
    		mailforward.putExtra("mailBody", tvmail.getText().toString());
			startActivity(mailforward);
    	}catch(Exception e){
    		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
    	}
    }
    public void more(View vm){
    	try{
            Toast.makeText(this, "Click on More Info", Toast.LENGTH_SHORT).show();
    	}catch(Exception e){
    		Log.e("SendRejectMail", e.getMessage(), e);
    	}
    }
 // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menushow, menu);
        return true;
    }
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        /*case R.id.menu_bookmark:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(ShowMail.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_save:
            Toast.makeText(ShowMail.this, "Save is Selected", Toast.LENGTH_SHORT).show();
            return true;*/
 
        case R.id.menu_emailto:
            //Toast.makeText(EmailActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
        	// get prompts.xml view
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.dlgmailfrom, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);

			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
					mailto=userInput.getText().toString();
					if(mailto==null){
						mailto="enbizsmtp@gmail.com";
					}
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
return true;
 
        /*case R.id.menu_share:
            Toast.makeText(ShowMail.this, "Share is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_delete:
            Toast.makeText(ShowMail.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_preferences:
            Toast.makeText(ShowMail.this, "Preferences is Selected", Toast.LENGTH_SHORT).show();
            return true;*/
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
