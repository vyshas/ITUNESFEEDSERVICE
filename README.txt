iTUNES TOP SONGS FEED SERVICE
The application reads XML data from the iTunes top chart service (http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml) and displays to the user.
The XML data is parsed using SAXParser and saved in to SQLite database on to the mobile device.
The saved data will be displayed to the user using a ListView. Since the xml data contains images  and in order to improve performance ,the application uses image caching using  Universal Image Loader(Using Universal Image Loader (c) 2011-2013, Sergey Tarasevich) .Hence ListView will load images as it scrolls down in a smooth manner.
On touching  each cell ,a Slide Expandable menu will be displayed which contains two options:
       * Save Image stores to gallery 
       * Preview link that displays the link available in itunes.
The Slide Expandable menu was implemented by using open source library by(TjerkWolterink) .


