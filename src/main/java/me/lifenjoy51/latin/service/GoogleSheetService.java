package me.lifenjoy51.latin.service;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import me.lifenjoy51.latin.domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lifenjoy51 on 2015-03-16.
 */
@Service
public class GoogleSheetService {

    public static int TOTAL_COUNT = 1;
    public static int MAX_UNIT = 1;
    @Autowired
    WordRepository wordRepository;

    SpreadsheetService service;
    URL workSheetFeedUrl;
    Map<String, WorksheetEntry> worksheetMap;

    public void sync() {
        String applicationName = "latinApp";
        String key = "11112k2YoMzePZcbvZTSnFU8YDgKqSgBbgCjwWXeFhDA";

        service = new SpreadsheetService(applicationName);
        worksheetMap = new HashMap<String, WorksheetEntry>();

        try {
            workSheetFeedUrl = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "full");
            WorksheetFeed worksheetFeed = service.getFeed(workSheetFeedUrl, WorksheetFeed.class);
            for (WorksheetEntry e : worksheetFeed.getEntries()) {
                String title = e.getTitle().getPlainText();
                worksheetMap.put(title, e);
                System.out.println(title);
            }
            
            //do sync..
            //1) word
            this.syncWords();
            //2) sentence
            this.syncSentences();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void syncWords() throws IOException, ServiceException {
        
        //get workd worksheet
        WorksheetEntry wordWorkSheet = worksheetMap.get("word");
        ListFeed listFeed = service.getFeed(wordWorkSheet.getListFeedUrl(), ListFeed.class);

        //headers.
        for (String s : listFeed.getEntries().get(0).getCustomElements().getTags()) {
            System.out.println(s);
        }

        //total counts.
        TOTAL_COUNT = listFeed.getEntries().size();

        Set<Integer> units = new HashSet<Integer>();

        //한줄씩 작업.
        for (ListEntry entry : listFeed.getEntries()) {
            CustomElementCollection c = entry.getCustomElements();
            System.out.println("시트 : " + entry.getPlainTextContent());

            String titleWord = LatinConverter.convert(c.getValue("titleword"));
            String korean = c.getValue("korean");
            String english = c.getValue("english");
            String derivation = LatinConverter.convert(c.getValue("derivation"));
            String audio = c.getValue("audio");

            int unit = 0;
            try {
                unit = Integer.parseInt(c.getValue("unit"));
                units.add(unit);
            } catch (NumberFormatException nfe) {

            }

            //생성.
            Word w = new Word(titleWord, english);
            w.setUnit(unit);
            w.setKorean(korean);
            w.setDerivation(derivation);
            w.setAudio(audio);
            //System.out.println("생성 : "+w);

            //저장.
            Word savedWord = wordRepository.save(w);
            //System.out.println("저장 : "+savedWord);
            //System.out.println(wordRepository.findAll());
        }
        MAX_UNIT = units.size();
        wordRepository.flush();
    }


    /**
     * 문장을 동기화한다.*
     */
    private void syncSentences() throws IOException, ServiceException {

        //get workd worksheet
        WorksheetEntry wordWorkSheet = worksheetMap.get("word");
        ListFeed listFeed = service.getFeed(wordWorkSheet.getListFeedUrl(), ListFeed.class);

        //headers.
        for (String s : listFeed.getEntries().get(0).getCustomElements().getTags()) {
            System.out.println(s);
        }

        //total counts.
        TOTAL_COUNT = listFeed.getEntries().size();

        Set<Integer> units = new HashSet<Integer>();

        //한줄씩 작업.
        for (ListEntry entry : listFeed.getEntries()) {
            CustomElementCollection c = entry.getCustomElements();
            System.out.println("시트 : " + entry.getPlainTextContent());

            String titleWord = LatinConverter.convert(c.getValue("titleword"));
            String korean = c.getValue("korean");
            String english = c.getValue("english");
            String derivation = LatinConverter.convert(c.getValue("derivation"));
            String audio = c.getValue("audio");

            int unit = 0;
            try {
                unit = Integer.parseInt(c.getValue("unit"));
                units.add(unit);
            } catch (NumberFormatException nfe) {

            }

            //생성.
            Word w = new Word(titleWord, english);
            w.setUnit(unit);
            w.setKorean(korean);
            w.setDerivation(derivation);
            w.setAudio(audio);
            //System.out.println("생성 : "+w);

            //저장.
            Word savedWord = wordRepository.save(w);
            //System.out.println("저장 : "+savedWord);
            //System.out.println(wordRepository.findAll());
        }
        MAX_UNIT = units.size();
        wordRepository.flush();
    }

}
