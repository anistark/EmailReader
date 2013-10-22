package com.stark.emailreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EmailReaderActivity extends Activity {
	BufferedReader optionReader;
	Folder inbox;
	static Integer count;
	TextView txtcount;
	static Context context = null;
	private ListView lvItem;
	private static ArrayList<String> itemArreySub, itemArreyContent, itemArreyDate, itemArreyFrom;
	private static ArrayAdapter<String> itemAdapter;
	static String etInputs,etInputd,etInputf,etInputc,s;
	String email, pass;
	Boolean client;
	final static String keyinvoice = "invoice";
	final static String keyleave = "leave";
	final static String keypo = "po";
	/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mainact);
	        
	        txtcount=(TextView)findViewById(R.id.textView1);
	        Intent i4=getIntent();
	        email= i4.getStringExtra("email");
	        pass= i4.getStringExtra("pass");
	        client= i4.getBooleanExtra("client", true);
	        
	        if(email==null){
		        email="enbizsmtp@gmail.com";
	        }
	        if(pass==null)
	        {
		        pass="check@123";
	        }
	        
	        Properties props = System.getProperties();
	        props.setProperty("mail.store.protocol", "imaps");
	        try {
	                Session session = Session.getDefaultInstance(props, null);
	                Store store = session.getStore("imaps");
	                	if(client=true){
		                	// IMAP host for gmail.
			                // Replace <username> with the valid username of your Email ID.
			                // Replace <password> with a valid password of your Email ID.
		    	        	store.connect("imap.gmail.com", email, pass);
		    	        }else if(client=false){
		    	        	// IMAP host for yahoo.
			                store.connect("imap.mail.yahoo.com", email, pass);
		    	        }

	                Toast.makeText(getApplicationContext(),store.toString(),Toast.LENGTH_LONG).show();

	                inbox = store.getFolder("Inbox");
	                inbox.open(Folder.READ_ONLY);
	                
	                optionReader = new BufferedReader(new InputStreamReader(System.in));
	        } catch (NoSuchProviderException e) {
	        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
	          //  System.exit(1);
	        } catch (MessagingException e) {
	            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
	            //System.exit(2);
	        }
    }
    
    static public void showUnreadMails(Folder inbox){
    	count=0;
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message msg[] = inbox.search(ft);
            
            //Toast.makeText(context, "MAILS: "+msg.length,Toast.LENGTH_LONG).show();
            for(Message message:msg) {
                try {
                	etInputd="DATE: "+message.getSentDate().toString();
                	etInputf="FROM: "+message.getFrom()[0].toString();            
                    etInputs="SUBJECT: "+message.getSubject().toString();
                    //etInputc="CONTENT: "+message.getContent().toString();
                    
                    s = message.getContent() + "";
                    if(s.indexOf("MimeMultipart") != -1){
                        Multipart multipart = (Multipart) message.getContent();

                          for (int x = 0; x < multipart.getCount(); x++) {
                          BodyPart bodyPart = multipart.getBodyPart(x);

                          //String disposition = bodyPart.getDisposition();
                          //Log.d("disposition", disposition + "");
                          etInputc=bodyPart.getContent().toString();   
                          }
                    }
                    count++;
                    addItemList();
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "No Information",Toast.LENGTH_LONG).show();
                }
            }
        } catch (MessagingException e) {
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    static public void showInvoiceMails(Folder inbox){        
    	count=0;
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message msg[] = inbox.search(ft);
            
            //Toast.makeText(context, "MAILS: "+msg.length,Toast.LENGTH_LONG).show();
            for(Message message:msg) {
                try {
                	etInputd="DATE: "+message.getSentDate().toString();
                	etInputf="FROM: "+message.getFrom()[0].toString();            
                    etInputs="SUBJECT: "+message.getSubject().toString();
                    //etInputc="CONTENT: "+message.getContent().toString();
                    
                    s = message.getContent() + "";
                    if(s.indexOf("MimeMultipart") != -1){
                        Multipart multipart = (Multipart) message.getContent();

                          for (int x = 0; x < multipart.getCount(); x++) {
                          BodyPart bodyPart = multipart.getBodyPart(x);

                          //String disposition = bodyPart.getDisposition();
                          //Log.d("disposition", disposition + "");
                          etInputc=bodyPart.getContent().toString();   
                          }
                    }
                    int slen= etInputs.length();
                    int klen= keyinvoice.length();
                    boolean foundIt= false;
                    for (int i=0; i<= (slen- klen); i++){
                    	if(etInputs.regionMatches(i, keyinvoice, 0, klen)){
                    		foundIt= true;
                    		count++;
                            addItemList();
                            break;
                    	}
                    }
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "No Information",Toast.LENGTH_LONG).show();
                }
            }
        } catch (MessagingException e) {
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    static public void showLeaveMails(Folder inbox){        
    	count=0;
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message msg[] = inbox.search(ft);
            
            //Toast.makeText(context, "MAILS: "+msg.length,Toast.LENGTH_LONG).show();
            for(Message message:msg) {
                try {
                	etInputd="DATE: "+message.getSentDate().toString();
                	etInputf="FROM: "+message.getFrom()[0].toString();            
                    etInputs="SUBJECT: "+message.getSubject().toString();
                    //etInputc="CONTENT: "+message.getContent().toString();
                    
                    s = message.getContent() + "";
                    if(s.indexOf("MimeMultipart") != -1){
                        Multipart multipart = (Multipart) message.getContent();

                          for (int x = 0; x < multipart.getCount(); x++) {
                          BodyPart bodyPart = multipart.getBodyPart(x);

                          //String disposition = bodyPart.getDisposition();
                          //Log.d("disposition", disposition + "");
                          etInputc=bodyPart.getContent().toString();   
                          }
                    }
                    int slen= etInputs.length();
                    int klen= keyleave.length();
                    boolean foundIt= false;
                    for (int i=0; i<= (slen- klen); i++){
                    	if(etInputs.regionMatches(i, keyleave, 0, klen)){
                    		foundIt= true;
                    		count++;
                            addItemList();
                            break;
                    	}
                    }
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "No Information",Toast.LENGTH_LONG).show();
                }
            }
        } catch (MessagingException e) {
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    static public void showPoMails(Folder inbox){        
    	count=0;
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message msg[] = inbox.search(ft);
            
            //Toast.makeText(context, "MAILS: "+msg.length,Toast.LENGTH_LONG).show();
            for(Message message:msg) {
                try {
                	etInputd="DATE: "+message.getSentDate().toString();
                	etInputf="FROM: "+message.getFrom()[0].toString();            
                    etInputs="SUBJECT: "+message.getSubject().toString();
                    //etInputc="CONTENT: "+message.getContent().toString();
                    
                    s = message.getContent() + "";
                    if(s.indexOf("MimeMultipart") != -1){
                        Multipart multipart = (Multipart) message.getContent();

                          for (int x = 0; x < multipart.getCount(); x++) {
                          BodyPart bodyPart = multipart.getBodyPart(x);

                          //String disposition = bodyPart.getDisposition();
                          //Log.d("disposition", disposition + "");
                          etInputc=bodyPart.getContent().toString();   
                          }
                    }
                    int slen= etInputs.length();
                    int klen= keypo.length();
                    boolean foundIt= false;
                    for (int i=0; i<= (slen- klen); i++){
                    	if(etInputs.regionMatches(i, keypo, 0, klen)){
                    		foundIt= true;
                    		count++;
                            addItemList();
                            break;
                    	}
                    }
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "No Information",Toast.LENGTH_LONG).show();
                }
            }
        } catch (MessagingException e) {
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
  /*  static public void showAllMails(Folder inbox){
        try {
            Message msg[] = inbox.getMessages();
		Toast.makeText(context, "MAILS: "+msg.length,Toast.LENGTH_LONG).show();
            for(Message message:msg) {
                try {
                    etInputd="DATE: "+message.getSentDate().toString();
                	etInputf="FROM: "+message.getFrom()[0].toString();            
                    etInputs="SUBJECT: "+message.getSubject().toString();
                    etInputc="CONTENT: "+message.getContent().toString();
                    
                    addItemList();
                } catch (Exception e) {
                	 Toast.makeText(context, "No Information",Toast.LENGTH_LONG).show();
                }
            }
        } catch (MessagingException e) {
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
*/
private void setUpView() {
    // TODO Auto-generated method stub
    lvItem = (ListView)this.findViewById(R.id.listView_items);
    itemArreySub = new ArrayList<String>();
    itemArreyContent = new ArrayList<String>();
    itemArreyFrom = new ArrayList<String>();
    itemArreyDate = new ArrayList<String>();
    itemArreySub.clear();
    itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemArreySub);
    lvItem.setAdapter(itemAdapter);
    lvItem.setOnItemClickListener(new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(),itemArreyDate.get(arg2), Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(),itemArreyFrom.get(arg2), Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(),itemArreySub.get(arg2), Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(),itemArreyContent.get(arg2), Toast.LENGTH_LONG).show();
			
			  
			Intent showmail= new Intent(getApplicationContext(), ShowMail.class);
			showmail.putExtra("mailcontent", itemArreyContent.get(arg2).toString());
			showmail.putExtra("mailsub", itemArreySub.get(arg2).toString());
			showmail.putExtra("mailfromid", email);
			showmail.putExtra("mailfrompass", pass);
			
			startActivity(showmail);
		}
    	
    });
}

protected static void addItemList() {
    // TODO Auto-generated method stub
    // TODO Auto-generated method stub
    itemArreySub.add(0,etInputs);
    itemArreyContent.add(0,etInputc);
    itemArreyFrom.add(0,etInputf);
    itemArreyDate.add(0,etInputd);
    
    itemAdapter.notifyDataSetChanged();
}

@Override
public boolean onCreateOptionsMenu(Menu menu)
{
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menumain, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item)
{
     
    switch (item.getItemId())
    {
    case R.id.action_all:
    	setUpView();
        try {
        	showUnreadMails(inbox);
            optionReader.close();
        } catch (IOException e) {
        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
        txtcount.setText("Total No of Unread Mails: "+count.toString());
        return true;
    case R.id.action_invoice:
    	setUpView();
        try {
        	showInvoiceMails(inbox);
            optionReader.close();
        } catch (IOException e) {
        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
        txtcount.setText("Total No of Invoice Mails: "+count.toString());
        
        return true;
    case R.id.action_Leave:
    	setUpView();
        try {
        	showLeaveMails(inbox);
            optionReader.close();
        } catch (IOException e) {
        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
        txtcount.setText("Total No of Leave Mails: "+count.toString());
        return true;
    case R.id.action_PO:
    	setUpView();
        try {
        	showPoMails(inbox);
            optionReader.close();
        } catch (IOException e) {
        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
        txtcount.setText("Total No of Po Mails: "+count.toString());
        return true;
    case R.id.action_ToEmailSetup:
    	Intent ems= new Intent(getApplicationContext(), EmailSetup.class);
		startActivity(ems);
    
    default:
        return super.onOptionsItemSelected(item);
    }
}

}
