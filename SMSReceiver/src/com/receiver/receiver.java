package com.receiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class receiver extends Activity {
    /** Called when the activity is first created. */
    
	private int status=-1;
	SMSReceiver receiver=null;
	IntentFilter filter=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startLogic();
    }
    
    
    //====================================================
    private void changeReceiverStatus(int status){
    	
    	try{
    		
    		if(receiver==null)
    			receiver=new SMSReceiver();
    		
    		if(filter==null){
	    		
    			filter = new IntentFilter();
	    		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
    		}
    		
    		if(status<0)
    			this.unregisterReceiver(receiver);
    		else
    			this.registerReceiver(receiver, filter);
    			
    	}catch(Exception e){e.printStackTrace();}
    }
    
    //====================================================
    private void startLogic(){
    	
    	try{
    		
    		final Handler handler=new Handler(){
    			public void handleMessage(Message msg){
    				
    				try{
    					
    					int status=msg.getData().getInt("status");
    					
    					if(status<0)
    		    			Toast.makeText(receiver.this, "Unregistering receiver for 20 sec", Toast.LENGTH_SHORT).show();
    		    		else
    		    			Toast.makeText(receiver.this, "Registering receiver for 20sec", Toast.LENGTH_SHORT).show();
    					
    				}catch(Exception e){e.printStackTrace();}
    			}};
    		
    		new Thread(){
    			public void run(){
    				
    				try{
    				
    					while(true){
    						
	    					Bundle b=new Bundle();
	    					b.putInt("status", status);
	    					Message msg=new Message();
	    					msg.setData(b);
	    					
	    					handler.sendMessage(msg);
	    					
	    					changeReceiverStatus(status);
	    					status=status*-1;
	    					
	    					Thread.sleep(20000);
    					}
    					
    				}catch(Exception e){e.printStackTrace();}
    				
    			}}.start();
    		
    	}catch(Exception e){e.printStackTrace();}
    }
    
}