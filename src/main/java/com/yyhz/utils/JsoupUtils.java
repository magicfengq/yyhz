package com.yyhz.utils;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {
	
	public static String getTextFromTHML(String htmlStr) {
		Document doc = Jsoup.parse(htmlStr);
		String text = doc.text();
		// remove extra white space
		StringBuilder builder = new StringBuilder(text);
		int index = 0;
		while(builder.length()>index){
			char tmp = builder.charAt(index);
			if(Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)){
				builder.setCharAt(index, ' ');
			}
			index++;
		}
		text = builder.toString().replaceAll(" +", " ").trim();
		return text;
	}
	
	public static String removeStyle(String htmlStr) {
		Document doc = Jsoup.parse(htmlStr);
		Elements els = doc.getElementsByTag("p");
		for(Iterator<Element> elIter = els.iterator();elIter.hasNext();){
			Element el = elIter.next();
			el.removeAttr("style");
		}
		els = doc.getElementsByTag("span");
		for(Iterator<Element> elIter = els.iterator();elIter.hasNext();){
			Element el = elIter.next();
			el.removeAttr("style");
		}
		String text = doc.html();
		return text;
	}

}
