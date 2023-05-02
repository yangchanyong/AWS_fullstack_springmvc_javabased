package com.chanyongyang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanyongyang.domain.SampleVO;

import lombok.extern.log4j.Log4j;

/**
 * @author ycy
 * controller를 쓰는 이유
 * 	요청(request)과 응답(response)처리 제어 / server : 제공자 / 사용자가 요청을 했을 때 어떠한 응답처리를 해줘야하는지 정의해줘야함
 * 	request : 1. URL(포트 이후) / mapping : 경로처리 (예 / 아래 코드는 sample/getText르 호출했을 때 뭘 해줄것이냐)
 *  		  2. 파라미터 수집 : 이전페이지에서 가지고있던 정보를 다음페이지에서 알게 해주는것 (대표적으로 쿼리스트링, form), post는 requestbody에 딸려옴
 *  		  3. HTTP Method : get, post, put, delete / 어노테이션으로 처리하거나 ???
 *  		  4. attr(어트리뷰트)
 *  		  5. session
 *  		  6. cookie 
 *  response : 1. MIME-TYPE(content-type / Default : JSP(view resolver) / JSP의 MIME-TYPE은 text/html (jsp의 최상단에 선언되어 있음)
 *  			 : 모든 파일들의 대한 파일의 타입을 정의하는 문자열정보, 어떤타입이든간에 마임타입이 없는 파일은 거의 없다
 *  				servlet에서는 forward로 처리했고, 스프링에서는 viewResolver로 처리함
 *	  				viewResolver는 servlet-context에 정의되어 있음 이건 viewResolver를 통해서 forwarding을 하겠다는 의미이다.
 *					* POI 라이브러리(excel 파일
 *					responseBody는 응답에 대한 타입을 지정해주는것 (선택이 아닌 필수)
 *					RestController는 모든 메서드를 responseBody화 해주는것이다.
 *				2. header : text/html, application/json, text/xml, application/octet-stream(파일 다운로드)
 *							
 */
@RestController
@RequestMapping("sample")
@Log4j
public class SampleController {
	@GetMapping(value="getText", produces="text/plain; charset=utf-8")
	public String getText() {
		return "안녕하세요";
	}
	
	@GetMapping("getSample")
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	}
	
	@GetMapping("getList")
	public List<SampleVO> getList() {
		
		// IntStream의 rageClosed는 범위를 뜻함, mapObj는 obj로 맵핑을 하겠다는 뜻 (오브젝트타입의 제너릭으로 바꿈), 
		// SampleVO타입으로 변환후 반환하겠다는뜻
		return IntStream.rangeClosed(1, 10).mapToObj(i -> new SampleVO(i, "first "+i, "last " +i)).collect(Collectors.toList());
	}
	
	@GetMapping("getMap")
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		return map;
	}
	
	@GetMapping("check")
	public ResponseEntity<SampleVO> check(double height, double weight) {
		SampleVO vo = new SampleVO(0, String.valueOf(height), String.valueOf(weight));
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}
		else {
			result = ResponseEntity.ok(vo);
		}
		return result;
	}
	
	@GetMapping("product/{cat}/{pid}")
	public String[] getPath (@PathVariable String cat, @PathVariable String pid) {
		return new String[] {"category : " + cat, "productid : " + pid};
	}
	@GetMapping("product/{cat2}/{test}")
	public String[] getPath2 (@PathVariable String cat2, @PathVariable String pid2) {
		return new String[] {"category : " + cat2, "productid : " + pid2};
	}
	
	@GetMapping("sample")
	public SampleVO convert(@RequestBody SampleVO sampleVO) {
		log.warn(sampleVO);
		return sampleVO;
	}
	@PostMapping("sample")
	public SampleVO convert2(@RequestBody SampleVO sampleVO) {
		log.warn(sampleVO);
		return sampleVO;
	}
	
}
