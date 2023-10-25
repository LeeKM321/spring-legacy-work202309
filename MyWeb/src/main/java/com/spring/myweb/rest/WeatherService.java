package com.spring.myweb.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
	
	private final IWeatherMapper mapper;
	
	public void getShortTermForecast(String area1, String area2) {
		
		LocalDateTime ldt = LocalDateTime.now();
		String baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(ldt);
		log.info("baseDate: {}", baseDate);
		
		Map<String, String> map = mapper.getCoord(area1.trim(), area2.trim());
		log.info("좌표 결과: {}", map);
		
		try {
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
		    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=p4SaDox6EHmd8yPfl%2FwCZt5xmeQ%2BOz%2FjTAP1dwLKLHM548RPfvEDl%2Bu98Tj6yGYUZX1oKey7QEZ0Ou5CPcmZEA%3D%3D"); /*Service Key*/
		    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수*/
		    urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
		    urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
		    urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8")); /*06시 발표(정시단위) */
		    urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NX")), "UTF-8")); /*예보지점의 X 좌표값*/
		    urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NY")), "UTF-8")); /*예보지점의 Y 좌표값*/
		    
		    log.info("완성된 url: {}", urlBuilder.toString());
		    
		    URL url = new URL(urlBuilder.toString());
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("GET");
		    conn.setRequestProperty("Content-type", "application/json");
		    System.out.println("Response code: " + conn.getResponseCode());
		    BufferedReader rd;
		    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    } else {
		        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		    }
		    StringBuilder sb = new StringBuilder();
		    String line;
		    while ((line = rd.readLine()) != null) {
		        sb.append(line);
		    }
		    rd.close();
		    conn.disconnect();
//		    System.out.println(sb.toString());
		    
		    //StringBuilder 객체를 String으로 변환
		    String jsonString = sb.toString();
		    
		    JSONParser parser = new JSONParser();
		    
		    JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
		    
		    //"response" 라는 이름의 키에 해당하는 JSON 데이터를 가져옵니다.
		    JSONObject response = (JSONObject) jsonObject.get("response");
		    
		    //response 안에서 body 키에 해당하는 JSON 데이터를 가져옵니다.
		    JSONObject body = (JSONObject) response.get("body");
		    
		    //body 안에서 item 키에 해당하는 JSON 데이터 중 item 키를 가진 JSON 데이터를 가져옵니다.
		    //item 키에 해당하는 JSON 데이터는 여러 값이기 때문에 배열의 문법을 제공하는 객체로 받습니다.
		    JSONArray itemArray = (JSONArray) ((JSONObject) body.get("items")).get("item");
		    
		    //item 내부의 각각의 객체에 대한 반복문을 작성합니다.
		    for(Object obj : itemArray) {
		    	
		    	//형변환 진행.
		    	JSONObject item = (JSONObject) obj;
		    	//"category" 키에 해당하는 단일 값을 가져옵니다.
		    	String category = (String) item.get("category");
		    	//"fcstValue" 키에 해당하는 단일 값을 가져옵니다.
		    	String fcstValue = (String) item.get("fcstValue");
		    	
		    	if(category.equals("TMX") || category.equals("TMN")) {
		    		log.info("category: {}, fcstValue: {}", category, fcstValue);
		    	}
		    	
		    }
		    
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}













