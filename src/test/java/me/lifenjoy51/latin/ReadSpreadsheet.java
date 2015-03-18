package me.lifenjoy51.latin;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

import java.net.URL;

/**
 * Created by lifenjoy51 on 2015-03-16.
 */
public class ReadSpreadsheet {
    
    public static void main(String[] args) throws Exception{
        String applicationName = "latinApp";
        String key = "11112k2YoMzePZcbvZTSnFU8YDgKqSgBbgCjwWXeFhDA";
        
        SpreadsheetService service = new SpreadsheetService(applicationName);
        URL url = FeedURLFactory.getDefault().getListFeedUrl(key, "1", "public", "values"); //seq, visibility, type.
        ListFeed listFeed = service.getFeed(url, ListFeed.class);
        
        //headers.
        for(String s : listFeed.getEntries().get(0).getCustomElements().getTags()){
            System.out.println(s);
        }
        
        //한줄씩 작업.
        for( ListEntry entry : listFeed.getEntries() ) {
            CustomElementCollection c = entry.getCustomElements();
            
            System.out.println(c.getValue("unit"));
            System.out.println(c.getValue("partofspeech")); //key.
            System.out.println(c.getValue("presentactive"));
            System.out.println(c.getValue("presentinfinitive"));
            System.out.println(c.getValue("perfectactive"));
            System.out.println(c.getValue("supine"));
            System.out.println(c.getValue("korean"));
            System.out.println(c.getValue("english"));
            System.out.println(c.getValue("more"));
            System.out.println();

        }
    }

}
