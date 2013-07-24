package com.example.itunessax;






import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewlayout);
		
		ListView lv = (ListView)findViewById(R.id.layoutListView);
		lv.setClickable(true);
		lv.setItemsCanFocus(false);
		
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setTitle("Connecting ...");
		dialog.setMessage("Please wait while loading data.");
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.show();
		SAXParsingClass spc = new SAXParsingClass(this,lv,dialog);
		spc.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml");
		
		
		}

}
