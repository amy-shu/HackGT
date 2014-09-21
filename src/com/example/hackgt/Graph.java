package com.example.hackgt;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class Graph extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graph);
    	XYSeriesRenderer renderer = getRenderer();
    	XYMultipleSeriesRenderer mRenderer = getMRenderer(renderer);
    	GraphicalView chartView = ChartFactory.getLineChartView(this, getDataset(), mRenderer);
    	graphLayout.addView(chartView, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
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
	
	private XYMultipleSeriesDataset getDataset() { 	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYSeries series = new XYSeries("Time vs Temperature");
		series.add(1, 50);
		series.add(2, 4);
		series.add(3, 30);
		series.add(4, 10);
		series.add(5, 80);	
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
		mRenderer.setYAxisMin(0);
		mRenderer.setShowGrid(true); 
		return mRenderer;
	}

}
