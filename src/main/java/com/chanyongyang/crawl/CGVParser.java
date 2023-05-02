package com.chanyongyang.crawl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CGVParser {
	public static void main(String[] args) throws MalformedURLException, IOException {
//		Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/"), 2000);
		String 슬램덩크 = "http://www.cgv.co.kr/movies/detail-view/default.aspx?midx=86720";
		String 매트릭스 = "http://www.cgv.co.kr/movies/detail-view/?midx=85541";
		Document doc = Jsoup.parse(new URL(매트릭스), 2000);
		
		String info = doc.selectFirst(".sect-story-movie").text();
		String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
		
		Element ele = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
		Elements els = doc.select(".sect-base-movie .spec dt");
		
		for(Element e : els) {
			Elements es = e.getElementsContainingText("감독").next().select("a");
			for(Element e2 : es) {
				String director = e2.text();
				String directorHref = e2.attr("href");
				String pidx = directorHref.substring(directorHref.lastIndexOf("=")+1);
				
			}
			
			System.out.println("==============");
			Elements es2 = e.getElementsContainingText("배우").next().select("a");
			for(Element e2 : es2) {
				String actor = e2.text();
				String actorHref = e2.attr("href");
				String pidx = actorHref.substring(actorHref.lastIndexOf("=")+1);
			}
			Elements es3 = e.getElementsContainingText("장르");
			for(Element e2 : es3) {
				String genre = e2.text();
			}
			Elements es4 = e.getElementsContainingText("기본").next();
			for(Element e2 : es4) {
				String runningTime = e2.text().split(", ")[1];
				String nation = e2.text().split(", ")[2];
			}
//			String str = e.text();
//			if(str.trim().length() != 0) {
//				System.out.println(e.text());
//			}
		}
		Elements lis = ele.select("img");
		for(Element e : lis) {
//			System.out.println(e.attr("data-src"));
//			System.out.println(e.selectFirst("a").attr("href"));
//			String href = e.selectFirst("a").attr("href");
//			String pidx = href.substring(href.lastIndexOf("=")+1);
//			String date = e.selectFirst(".txt-info").text().replaceAll("개봉","").trim();
//			String imgSrc = e.selectFirst(".thumb-image img").attr("src");
//			String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
//			String age = e.selectFirst("i.cgvIcon").text();
//			String title = e.selectFirst(".box-contents strong.title").text();
			
//			System.out.println(href);
//			System.out.println(date);
//			System.out.println(imgSrc);
//			System.out.println(imgAlt);
//			System.out.println(e.selectFirst(".box-contents strong.title").text());
//			System.out.println(doc);
//			break;
		}
//		Elements els = doc.select(".sect-movie-chart");
//		System.out.println(els.size());
//		
//		for(int i=0; i < els.size(); i++) {
//			Element e = els.get(i);
//			System.out.println(e);
//		}
		
//		URL url = new URL("http://www.cgv.co.kr/movies/");
//		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
//		String str = "";
//		while((str = br.readLine()) != null) {
//			System.out.println(str);
//		}
	}
}
