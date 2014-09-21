package com.example.hackgt;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.chart.BarChart;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class RangeGraph extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_range_graph);
		LinearLayout graphLayout = (LinearLayout)findViewById(R.id.rangeGraph);
    	XYSeriesRenderer renderer = getRenderer();
    	XYMultipleSeriesRenderer mRenderer = getMRenderer(renderer);
    	GraphicalView chartView = ChartFactory.getRangeBarChartView(this, getDataset(), mRenderer, BarChart.Type.DEFAULT);
    	graphLayout.addView(chartView, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.range_graph, menu);
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
		RangeCategorySeries series = new RangeCategorySeries("Temperature Range");
		series.add(20, 30);
		series.add(14, 50);
		series.add(12, 20);
		series.add(4, 10);
		series.add(80, 100);
		dataset.addSeries(series.toXYSeries());
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
		mRenderer.setChartTitle("Temperature Range");
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
		mRenderer.setXAxisMin(0);
		mRenderer.setShowGrid(true); 
		return mRenderer;
	}
}
