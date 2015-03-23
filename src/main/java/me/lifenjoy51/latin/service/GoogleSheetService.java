package me.lifenjoy51.latin.service;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;
import me.lifenjoy51.latin.domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lifenjoy51 on 2015-03-16.
 */
@Service
public class GoogleSheetService {

    @Autowired
    WordRepository wordRepository;
    
    public static int TOTAL_COUNT = 1;

    
    public void sync()  {
        String applicationName = "latinApp";
        String key = "11112k2YoMzePZcbvZTSnFU8YDgKqSgBbgCjwWXeFhDA";

        SpreadsheetService service = new SpreadsheetService(applicationName);
        
        URL url = null; //seq, visibility, type.
        try {
            url = FeedURLFactory.getDefault().getListFeedUrl(key, "1", "public", "values");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        ListFeed listFeed = null;        
        try {
            listFeed = service.getFeed(url, ListFeed.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        //headers.
        for (String s : listFeed.getEntries().get(0).getCustomElements().getTags()) {
            System.out.println(s);
        }

        //total counts.
        TOTAL_COUNT = listFeed.getEntries().size();

        //한줄씩 작업.
        for (ListEntry entry : listFeed.getEntries()) {
            CustomElementCollection c = entry.getCustomElements();
            System.out.println("시트 : "+entry.getPlainTextContent());

            String titleWord = LatinConverter.convert(c.getValue("titleword"));
            String korean = c.getValue("korean");
            String english = c.getValue("english");

            int unit = 0;
            try {
                unit = Integer.parseInt(c.getValue("unit"));
            }catch(NumberFormatException nfe){
                
            }
                
            String partOfSpeech = c.getValue("partofspeech");
            String desc = LatinConverter.convert(c.getValue("desc"));
            String more = c.getValue("more");

            //생성.
            Word w = new Word(titleWord, korean, english);
            w.setUnit(unit);
            w.setPartOfSpeech(partOfSpeech);
            w.setMeaning(desc);
            w.setMore(more);
            //System.out.println("생성 : "+w);

            //저장.
            Word savedWord = wordRepository.saveAndFlush(w);
            //System.out.println("저장 : "+savedWord);
            //System.out.println(wordRepository.findAll());
        }
    }
}
