package com.example.hackgt;

import java.util.Map;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends ActionBarActivity {
	
	WheelView temperatures;
	int[] degrees = new int[31];
	Firebase myFirebaseRef;
	TextView targetTemp;
	TextView turnOffTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFirebaseRef = new Firebase("https://popping-torch-9411.firebaseio.com/");
        
        temperatures = (WheelView) findViewById(R.id.city);
        temperatures.setVisibleItems(5); // Number of items
        temperatures.setWheelBackground(R.drawable.wheel_bg_holo);
        temperatures.setWheelForeground(R.drawable.wheel_val_holo);
        temperatures.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
        temperatures.setViewAdapter(new DegreeAdapter(this));
		temperatures.setCurrentItem(15);
		
		targetTemp = (TextView)findViewById(R.id.current_target_temp);
		targetTemp.setText("Target Temperature: None");
		turnOffTime = (TextView)findViewById(R.id.turn_off_time);
		turnOffTime.setText("Turn Off Time: None");
    }
    
	private class DegreeAdapter extends AbstractWheelTextAdapter {
		// City names
		
		/**
		 * Constructor
		 */
		protected DegreeAdapter(Context context) {
			super(context, R.layout.city_holo_layout, NO_RESOURCE);
			for (int i=70; i < 101; i++) {
				degrees[i-70] = 170-i;
			}
			setItemTextResource(R.id.city_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return degrees.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return " " + Integer.toString(degrees[index]) + "\u00b0" + " ";
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {	
    	int temperature = temperatures.getCurrentItem();
    	Time now = new Time();
    	now.setToNow();
    	String date = now.month + "-" + now.monthDay + "-" + now.year;
//    	try {
//    		message = Integer.parseInt(editText.getText().toString());
//    	} catch (NumberFormatException name) {
//    		
//    	}
    	myFirebaseRef.child("target_temp").setValue(degrees[temperature]);
    	myFirebaseRef.child("target_temp").addValueEventListener(new ValueEventListener() {

    		  @Override
    		  public void onDataChange(DataSnapshot snapshot) {
    			  System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
    			  targetTemp.setText("Target Temperature: " + snapshot.getValue());
    		  }

    		  @Override public void onCancelled(FirebaseError error) { }
    	});

    }
    public void setTurnOffTime(View view) {
    	String nextAlarm = "";
    	try{
    		nextAlarm = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.NEXT_ALARM_FORMATTED);
    		nextAlarm = nextAlarm.substring(4, nextAlarm.length());
    	} catch(Exception e) {
    		//heh
    	}   	
    	myFirebaseRef.child("alarm_time").setValue(nextAlarm);
    	if (nextAlarm != "") {
    		turnOffTime.setText("Turn Off Time: " + nextAlarm);
    	}
    }
    public void displayGraph(View view) {
    	Intent intent = new Intent(this, Graph.class);
    	startActivity(intent);	
    }
    
    public void displayRangeGraph(View view) {
    	Intent intent = new Intent(this, RangeGraph.class);
    	startActivity(intent);	
    }
}
