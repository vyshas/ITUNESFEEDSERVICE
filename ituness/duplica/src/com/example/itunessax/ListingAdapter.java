package com.example.itunessax;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import android.app.Activity;
import android.content.Context;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListingAdapter extends BaseAdapter {
	
	ImageManager imageManager;
	//
	ImageLoaderConfiguration  imageLoader;
	private Activity activity;
	private ArrayList<MyData> lists;
	LayoutInflater li;
	Object[] datas;
	//For saving the position before incrementing
	int p,pimage;
	//for retreiving values in the button 
	MyData mydata1;
	LinearLayout icon;
	ImageView imageview1;
	
	
	public ListingAdapter(Activity a, ArrayList<MyData> lists) {
		activity = a;
		this.lists = lists;
		datas = lists.toArray();
		li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//imageManager = new ImageManager(activity.getApplicationContext(),100);
		//ImageLoader	library using universal image loader library	
		imageLoader=new ImageLoaderConfiguration.Builder(activity.getApplicationContext()).build();
		ImageLoader.getInstance().init(imageLoader);
	}
	
	@Override
	public int getCount()
	{
		return lists.size();
	}
	
	private class ViewHolder
	{
		TextView titleView;
		ImageView imageView;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MyData mydata;
		View view = convertView;
		LinearLayout l;
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			view = li.inflate(R.layout.listlayout, null);
			holder.titleView = (TextView) view.findViewById(R.id.titleView);
			holder.imageView = (ImageView) view.findViewById(R.id.imageView);
			
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			
		}
		mydata = (MyData) datas[position];
		
		/** The position gets incremented by 1 for ever save image button 
		 * hence  will store the value before the position gets incremented and use that to retrieve the value from the list*/
		p=position-1;
		icon = (LinearLayout)view.findViewById(R.id.l1);
		if(p>=0)
		{
		mydata1=(MyData)datas[p];
		//imageview1 = (ImageView)icon.getChildAt(p).findViewById(R.id.imageView);
		View v=icon.getChildAt(1);
			//imageview1 = (ImageView)icon.getChildAt(0).findViewById(R.id.imageView);
			imageview1=(ImageView)v;
		}
		
		holder.titleView.setText(mydata.mTitle);
		//To prevent the image being displayed only after scrolling
		holder.imageView.setTag(mydata.mImage);
			
		//to cache the image and display by visiting the URL
		ImageLoader.getInstance().displayImage(mydata.mImage, holder.imageView);
		
				
		/**Save Image button
		 * Gets the image and saves it to gallery
		 * 
		 * 
		 * **/
		Button saveimagebutton=(Button)view.findViewById(R.id.saveimage);
		saveimagebutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("DATA",String.valueOf(p)+mydata1.mImage);
				
				imageview1.setDrawingCacheEnabled(true);
				
				Bitmap p=imageview1.getDrawingCache();
				
				//to store images
				Images.Media.insertImage(activity.getContentResolver(), p, mydata1.mTitle, "Image");
				
				Toast.makeText(activity, "Image saved to gallery", Toast.LENGTH_SHORT).show();
			}
		});
		
		/** Preview button
		 * Gets the preview link and visits the link on click	 * 
		 * 
		 * **/
		
		Button previewbutton=(Button)view.findViewById(R.id.preview);
		previewbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Preview link/Song Link",String.valueOf(p)+mydata1.mSongLink);
				Intent intent = new Intent(activity, WebViewActivity.class);
				intent.putExtra("SongLink", mydata1.mSongLink);
			    activity.startActivity(intent);
			}
		});
		
				
		return view;
	   }
	
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
  }