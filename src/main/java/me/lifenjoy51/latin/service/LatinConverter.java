package me.lifenjoy51.latin.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifenjoy51 on 2015-03-23.
 */
public class LatinConverter {
    
    private static Map<String, String> map = new HashMap<String, String>();
    
    static{
        map.put("A:","Ā");
        map.put("E:","Ē");
        map.put("I:","Ī");
        map.put("O:","Ō");
        map.put("U:","Ū");
        map.put("Y:","Ȳ");
        map.put("AE","Æ");
        map.put("OE","Œ");
        map.put("a:","ā");
        map.put("e:","ē");
        map.put("i:","ī");
        map.put("o:","ō");
        map.put("u:","ū");
        map.put("y:","ÿ");
        map.put("ae","æ");
        map.put("oe","œ");
        map.put("`","");    //accent
    }
    
    public LatinConverter(){
    }
    
    public static String convert(String src){
        if(src == null) return null;
        String dst = src;
        for(Map.Entry<String, String> e : map.entrySet()){
            dst = dst.replaceAll(e.getKey(), e.getValue());
        }
        System.out.println(dst);
        return dst;
    }
    
    public static void main(String[] args){
        String s = LatinConverter.convert("lauda:s");
        System.out.println(s);
    }
}
