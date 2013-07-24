package com.example.itunessax;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;



import com.sqllite.db.AlbumDatabaseAdapter;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ListView;

public class SAXParsingClass extends AsyncTask<String, Integer, MyData>{
	
	
	Activity context;
	ListView listView;
	ProgressDialog pg;
	AlbumDatabaseAdapter albumDbAdapter;
	
	private static final String kEntry = "entry";
	private static final String kTitle = "title";
	private static final String kImage = "image";
	private static final String kLink="link";
	ArrayList<MyData> listDatas;

	//
	public SAXParsingClass(Activity c, ListView lv, ProgressDialog p) {
		context = c;
		listView = lv;
		pg = p;		
		// get Instance  of Database Adapter to save XML data
		albumDbAdapter=new AlbumDatabaseAdapter(context);
		albumDbAdapter=albumDbAdapter.open();
	}

	
	@Override
	protected MyData doInBackground(String... params) {
		// TODO Auto-generated method stub
		MyData myData = new MyData();
		try {
			URL url = new URL(params[0]);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			try {
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				MySAXHandler mySAXHandler = new MySAXHandler();
				xr.setContentHandler(mySAXHandler);
				xr.parse(new InputSource(url.openStream()));
				myData = mySAXHandler.getParsedData();
				return myData;

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return myData;
	}

	
	
	@Override
	protected void onPostExecute(MyData result) {
		super.onPostExecute(result);
		pg.dismiss();
		
		for (MyData each : listDatas) {
			
			Log.d("Each Element 1st", String.format("%s  %s %s", each.mTitle,each.mImage,
					each.mSongLink));
			Log.i("each element 2nd", each.mTitle+"   "+"Link "+"  "+each.mSongLink+"  "+"ImageLink"+each.mImage);
			
		}
		
				
		ListingAdapter listAdapt = new ListingAdapter(context, listDatas);
		
		//gives a sliding menu effect by using the Slide Expandable library
	
		listView.setAdapter(new SlideExpandableListAdapter(listAdapt,R.id.l1,R.id.expandable));
		
		pg.dismiss();
		albumDbAdapter.close();

	}
	
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	
	class MySAXHandler implements ContentHandler {
		boolean hasStarted;
		boolean isElement;
		String currentElement;
		StringBuilder builder;
		MyData mydata;

		public MyData getParsedData() {
			// TODO Auto-generated method stub
			return null;
		}

		
		@Override
		public void startDocument() throws SAXException {
			listDatas = new ArrayList<MyData>();
			
			Log.d("Started Document", "Document Parsing has been started");
			mydata = new MyData();
		}

		
		
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			if (localName.equalsIgnoreCase(kEntry)) {
				hasStarted = true;
			}
			isElement = true;
			
			
			if (hasStarted
					&& ((localName.equalsIgnoreCase(kImage)) && (atts.getValue("height")).equalsIgnoreCase("170")))
					{

				currentElement = localName;
				builder = new StringBuilder();
				
				isElement = false;
				
				}
			
			else if(hasStarted &&(localName.equalsIgnoreCase(kTitle)))
			{
				currentElement = localName;
				builder = new StringBuilder();
				
				isElement = false;
			}
			
			/***Gets Song Weblink*/
			else if ((hasStarted &&((localName.equalsIgnoreCase(kLink)))&&(atts.getValue("rel").equalsIgnoreCase("alternate"))))
			{
				currentElement = atts.getValue("href");
				
				builder = new StringBuilder();
				Log.i("Songlink", currentElement);
				isElement = false;
				mydata.mSongLink=currentElement;
			}
		
		}
		

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			if (!isElement) {
				String string = new String(ch, start, length);
								
				/***Gets album image link*/
				 if (currentElement.equalsIgnoreCase(kImage))
					{
					mydata.mImage = string;
				Log.i("ImageLink", mydata.mImage);
					}
				
				 /***Gets Song Title*/
				else if (currentElement.equalsIgnoreCase(kTitle))
				{
								
				/*****To print the complete title name avoiding the spaces and newline**/  		
				builder.append(ch,start,length);
				mydata.mTitle=builder.toString();
				}
				 
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (hasStarted
					&& (((localName.equalsIgnoreCase(kImage)))
					|| (localName.equalsIgnoreCase(kLink))
					|| (localName.equalsIgnoreCase(kTitle))) && !isElement) {
				isElement = true;
				/**Gets the title and avoid the newline and whitespace to get the entire title**/
				builder.toString().trim();
				Log.i("Builder2",builder.toString());
				builder.setLength(0);
				

			} else if (localName.equalsIgnoreCase(kEntry)) {

				
				//store in sqlite database
				albumDbAdapter.insertEntry(mydata.mTitle, mydata.mImage, mydata.mSongLink);
				//get data from sqlite and store in list
				listDatas=(ArrayList<MyData>) albumDbAdapter.getAllData();
				
				mydata = new MyData() ;
			}
			

		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void skippedEntity(String name) throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}
	
	}
	
	
}






