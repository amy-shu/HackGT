package com.example.hackgt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseData extends ActionBarActivity {
	Firebase firebase;
	Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firebase);
		Time now = new Time();
    	now.setToNow();
    	String address = now.year + "/" + (now.month + 1) + "/" + 21 + "/" + 3;
    	firebase = new Firebase("https://popping-torch-9411.firebaseio.com/temp/" + address);
    	ctx = this;
    	// Attach an listener to read the data at our posts reference

    	firebase.addValueEventListener(new ValueEventListener() {
    	    @Override
    	    public void onDataChange(DataSnapshot snapshot) {
    	        System.out.println("On data changed");
    	        int i = 0;
    	        
    	        ArrayList<String> times = new ArrayList<String>();
    	    	ArrayList<String> temperatures = new ArrayList<String>();
    	    	
    			Map<String, Object> x = (Map<String, Object>) snapshot.getValue();
    	        for (String key: x.keySet()) {
    				  Map<String, Object> y = (Map<String, Object>) x.get(key);
    				  times.add(i, (String)y.get("Current Time"));
    				  temperatures.add(i, (String)y.get("Temperature"));
    				  i++;
    			  }
    	        
    	        XYMultipleSeriesDataset data = getData(times, temperatures);
    			LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graph);
    			XYSeriesRenderer renderer = getRenderer();
    	    	XYMultipleSeriesRenderer mRenderer = getMRenderer(renderer);
    	    	GraphicalView chartView = ChartFactory.getTimeChartView(ctx, data, mRenderer, "hh:mm");
    	    	graphLayout.addView(chartView, 0);
    	    }

    	    @Override
    	    public void onCancelled(FirebaseError firebaseError) {
    	        System.out.println("The read failed: " + firebaseError.getMessage());
    	    }
    	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.firebase, menu);
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
	
	private XYMultipleSeriesDataset getData(ArrayList<String> times, ArrayList<String> temperatures) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		TimeSeries series = new TimeSeries("Time vs Temperature");
		//System.out.println(tempData[0][0]);
		System.out.println("hih");
		for (int j = 0; j < times.size(); j++) {
			//System.out.println(tempData[j][0]);
			System.out.println("hi");
			String s = times.get(j);
			int year = Integer.parseInt(s.substring(0, 4));
			int month = Integer.parseInt(s.substring(5, 7));
			int date = Integer.parseInt(s.substring(8, 10));
			int hour = Integer.parseInt(s.substring(11, 13));
			int minute = Integer.parseInt(s.substring(14, 16));
			series.add(new Date(year, month, date, hour, minute), Double.parseDouble(temperatures.get(j)));
		}
		dataset.addSeries(series);
		return dataset;
	}
	
	public XYSeriesRenderer getRenderer() {
		// Now we create the renderer
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(5);
		renderer.setColor(Color.RED);
		// Include low and max value
		renderer.setDisplayBoundingPoints(true);
		// we add point markers
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setPointStrokeWidth(3);
		return renderer;
	}
	
	public XYMultipleSeriesRenderer getMRenderer(XYSeriesRenderer renderer) {
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		// We want to avoid black border	
		mRenderer.setChartTitle("Time vs Temperature");
		mRenderer.setChartTitleTextSize(50);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);		
		mRenderer.setAxesColor(Color.WHITE);
		mRenderer.setLabelsColor(Color.WHITE);
		mRenderer.setMargins(new int[] {100, 150, 600, 50});
		mRenderer.setYLabelsAlign(Align.LEFT, 0);
		mRenderer.setYLabelsPadding(80);
		mRenderer.setXLabelsPadding(20);
		mRenderer.setPanEnabled(false, false);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setLabelsTextSize(40);
		mRenderer.setShowLegend(false);
		mRenderer.setAxisTitleTextSize(40);
		mRenderer.setXTitle("Time");
		mRenderer.setYTitle("Temperature");
		mRenderer.setYAxisMax(100);
		mRenderer.setYAxisMin(60);
		mRenderer.setShowGrid(true); 
		return mRenderer;
	}
}
