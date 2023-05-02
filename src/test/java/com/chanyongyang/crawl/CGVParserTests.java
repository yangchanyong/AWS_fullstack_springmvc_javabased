package com.chanyongyang.crawl;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chanyongyang.mapper.CrawlMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class CGVParserTests {
	@Autowired
	private CrawlMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(mapper);
	}
	
	@Test
	public void parse() throws MalformedURLException, IOException {
		// (#{pidx},#{title}, #{info} ,#{href} ,#{date} ,#{thumb} ,#{thumb_alt} ,#{age})
		Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/"), 2000);
		
		Element ele = doc.selectFirst(".sect-movie-chart");
		
		Elements lis = ele.select("li");
		for(Element e : lis) {
			String href = e.selectFirst("a").attr("href");
			String midx = href.substring(href.lastIndexOf("=")+1);
			String date = e.selectFirst(".txt-info").text().replaceAll("개봉","").trim();
			String imgSrc = e.selectFirst(".thumb-image img").attr("src");
			String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
			String age = e.selectFirst("i.cgvIcon").text();
			String title = e.selectFirst(".box-contents strong.title").text();
			
			
			// map을 이용하여 cgv값을 db에 옮기기
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("midx", midx);
			map.put("title", title);
			map.put("href", href);
			map.put("date", date);
			map.put("thumb", imgSrc);
			map.put("thumb_alt", imgAlt);
			map.put("age", age);
			
			
			mapper.insert(map);
		}
	}
	
	@Test
	public void testList() {
		mapper.getList().forEach(log::info);
	}
	
	@Test
	public void testDownloadThumbs() throws MalformedURLException, IOException {
//		InputStream is = new URL("https://t1.daumcdn.net/daumtop_chanel/op/20200723055344399.png").openStream();
//		FileUtils.copyInputStreamToFile(is, new File("c:/crawl/img/tmp.png"));
		
		// Stream<Object>
		List<Map<String, Object>> thumbs = mapper.getList();
		for(Map<String, Object> m : thumbs) {
			String t = m.get("thumb").toString();
			InputStream is = new URL(m.get("thumb").toString()).openStream();
			File file = new File("c:/crawl/img", m.get("midx").toString());
			if(!file.exists()) {
				file.mkdirs();
			}
			file = new File(file, "index" + t.substring(t.lastIndexOf(".")));
			FileUtils.copyInputStreamToFile(is, file);
		}
	}
	
	@Test
	public void testInsertPerson() {
		Map<String, String> map = new HashMap<>();
		map.put("pidx", "1978");
		map.put("name", "벤 애플렉");
		map.put("href", "/movies/persons/?pidx=1978");
		
		mapper.insertPerson(map);
	}
	
	@Test
	public void testInsertDirector() {
		Map<String, String> map = new HashMap<>();
		map.put("midx", "86916");
		map.put("pidx", "1978");
		mapper.insertDirector(map);
	}
	
	@Test
	public void crawlDetail() throws MalformedURLException, IOException {
		List<Map<String, Object>> list = mapper.getList();
		for(Map<String, Object> m : list) {
			String url = "http://www.cgv.co.kr" + m.get("href");
			Document doc = Jsoup.parse(new URL(url), 2000);
			
			String midx = m.get("midx").toString();
			String info = doc.selectFirst(".sect-story-movie").text();
			String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
			// t_cgv 업데이트
			Map<String, String> m2 = new HashMap<>();
			m2.put("midx", midx);
			m2.put("info", info);
			m2.put("engtitle", engtitle);
			

			
			Elements els = doc.select(".sect-base-movie .spec dt");

			for (Element e : els) {
				Elements es = e.getElementsContainingText("감독").next().select("a");
				for (Element e2 : es) {
					Map<String, String> map = new HashMap<>();
					
					
					String director = e2.text();
					String directorHref = e2.attr("href");
					String pidx = directorHref.substring(directorHref.lastIndexOf("=") + 1);

					map.put("pidx", pidx);
					map.put("name", director);
					map.put("href", directorHref);
					
					mapper.insertPerson(map);
					
					map = new HashMap<>();
					map.put("midx", midx);
					map.put("pidx", pidx);
					mapper.insertDirector(map);
				}

				Elements es2 = e.getElementsContainingText("배우").next().select("a");
				for (Element e2 : es2) {
					Map<String, String> map = new HashMap<>();

					String actor = e2.text();
					String actorHref = e2.attr("href");
					String pidx = actorHref.substring(actorHref.lastIndexOf("=") + 1);

					map.put("pidx", pidx);
					map.put("name", actor);
					map.put("href", actorHref);
					
					mapper.insertPerson(map);
					
					map = new HashMap<>();
					map.put("midx", midx);
					map.put("pidx", pidx);
					mapper.insertActor(map);
					
				}
				Elements es3 = e.getElementsContainingText("장르");
				for (Element e2 : es3) {
					String genre = e2.text();
					m2.put("genre", genre);
				}
				Elements es4 = e.getElementsContainingText("기본").next();
				for (Element e2 : es4) {
					String runningtime = e2.text().split(", ")[1];
					String nation = e2.text().split(", ")[2];
					m2.put("runningtime", runningtime);
					m2.put("nation", nation);
				}
				
				mapper.updateCGV(m2);
			}
		}
		
	}
	
	
	@Test
	public void testDownloadStillcut() throws MalformedURLException, IOException {
		List<Map<String, Object>> list = mapper.getList();
		for(Map<String, Object> m : list) {
			String url = "http://www.cgv.co.kr" + m.get("href");
			Document doc = Jsoup.parse(new URL(url), 2000);
			// 문제 발생시 아래 코드 사용
//			Document doc = Jsoup.connect(url).get();
			
			String midx = m.get("midx").toString();
			Element ele = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
			Elements lis = ele.select("img");
			
			File file = new File("c:/crawl/img", midx);
			if(!file.exists()) {
				file.mkdirs();
			}
			int idx = 1;
			for (Element e : lis) {
				System.out.println(e.attr("data-src"));
				String src = e.attr("data-src");
				
				// 경로, 이미지 파일시스템에 다운로드 
				String ext = src.substring(src.lastIndexOf("."));
				File file2 = new File(file, idx + ext);
				
				URL url2 = new URL(src);
				FileUtils.copyURLToFile(url2, file2);
				
				// 데이터베이스에 정의
				Map<String, String> map = new HashMap<>();
				map.put("odr", idx+"");
				map.put("midx", midx);
				
				mapper.insertAttach(map);
				idx++;
			}
		}	
	}	
}
	
