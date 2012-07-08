package cn.com.fojiao.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fojiao.api.manager.DataManager;
import cn.com.fojiao.api.model.Music;

/**
 * @author Ruhao Yao: yaoruhao@gmail.com This is the MusicController to handle
 *         music requests.
 * 
 */
@Controller
@RequestMapping("/music")
public class MusicController {
	private static Log logger = LogFactory.getLog(MusicController.class);
	private static HashMap<String, ArrayList<Music>> musicMap;
	private static HashMap<String, Integer> summaryMap = new HashMap<String, Integer>();

	public void init() {
		musicMap = DataManager.getInstance().getMusics();
		for (String key : musicMap.keySet()) {
			summaryMap.put(key, musicMap.get(key).size());
		}
	}

	@RequestMapping("/type/{typeStr}/start/{startNum}/length/{lengthNum}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String typeStr,
			@PathVariable String startNum, @PathVariable String lengthNum)
			throws Exception {
		String message = "[]";
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		int start = 0;
		int length = 0;
		ArrayList<Music> musicList = musicMap.get(typeStr);
		if (musicList == null) {
			return new ModelAndView("result", "message", message);
		}
		try {
			start = Integer.valueOf(startNum);
			length = Integer.valueOf(lengthNum);
		} catch (NumberFormatException e) {
			logger.warn("MusicController parse int failed.");
			e.printStackTrace();
			return new ModelAndView("result", "message", message);
		}
		if (start < 0 || start >= musicList.size() || length <= 0) {
			return new ModelAndView("result", "message", message);
		}
		int finish = Math.min(musicList.size(), start + length);
		List<Music> resultList = musicList.subList(start, finish);
		message = JSONArray.fromObject(resultList).toString();
		return new ModelAndView("result", "message", message);
	}

	@RequestMapping("/summary")
	public ModelAndView handleSummaryRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = JSONObject.fromObject(summaryMap).toString();
		return new ModelAndView("result", "message", message);
	}

}
