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
import cn.com.fojiao.api.model.Image;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 * 
 */
@Controller
@RequestMapping("/image")
public class ImageController {

	private static Log logger = LogFactory.getLog(ImageController.class);
	private static HashMap<String, ArrayList<Image>> imageMap;
	private static HashMap<String, Integer> summaryMap = new HashMap<String, Integer>();

	public void init() {
		imageMap = DataManager.getInstance().getImages();
		for (String key : imageMap.keySet()) {
			summaryMap.put(key, imageMap.get(key).size());
		}
	}

	@RequestMapping("/platform/{platformStr}/type/{typeStr}/start/{startNum}/length/{lengthNum}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String platformStr,
			@PathVariable String typeStr, @PathVariable String startNum,
			@PathVariable String lengthNum) throws Exception {
		String message = "[]";
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		int start = 0;
		int length = 0;
		ArrayList<Image> imageList = imageMap.get(platformStr + "-" + typeStr);
		if (imageList == null) {
			return new ModelAndView("result", "message", message);
		}
		try {
			start = Integer.valueOf(startNum);
			length = Integer.valueOf(lengthNum);
		} catch (NumberFormatException e) {
			logger.warn("ImageController parse int failed.");
			e.printStackTrace();
			return new ModelAndView("result", "message", message);
		}
		if (start < 0 || start >= imageList.size() || length <= 0) {
			return new ModelAndView("result", "message", message);
		}
		int finish = Math.min(imageList.size(), start + length);
		List<Image> resultList = imageList.subList(start, finish);
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
