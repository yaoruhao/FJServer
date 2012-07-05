package cn.com.fojiao.api.controller;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fojiao.api.manager.DataManager;
import cn.com.fojiao.api.model.Music;


/**
 * @author Ruhao Yao: yaoruhao@gmail.com 
 * This is the MusicController to handle music requests.
 * 
 */
@Controller
@RequestMapping("/music")
public class MusicController {
	private static Log logger = LogFactory.getLog(MusicController.class);
	private static HashMap<String, ArrayList<Music>> musicMap;
	
	public void init() {
		musicMap = DataManager.getInstance().getMusics();
	}

	@RequestMapping("type/{typeStr}/start/{startNum}/length/{lengthNum}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String typeStr,
			@PathVariable String startNum, @PathVariable String lengthNum)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		long endTime = System.currentTimeMillis();
		// logger.info("BoardController use:"+(endTime -
		// startTime)+"ms for visit board:"+boardId+" skipId:"+skipId);
		return new ModelAndView("result", "message", message);
	}

}
