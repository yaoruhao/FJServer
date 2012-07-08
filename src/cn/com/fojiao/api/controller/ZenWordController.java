package cn.com.fojiao.api.controller;

import java.util.ArrayList;
import java.util.List;

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
import cn.com.fojiao.api.model.ZenWord;

/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com
 * ZenWordController handles zen word request
 *
 */
@Controller
@RequestMapping("/zenword")
public class ZenWordController {
	private static Log logger = LogFactory.getLog(ZenWordController.class);
	private static ArrayList<ZenWord> zenWordList;

	
	public void init() {
		zenWordList = DataManager.getInstance().getZenWords();
	}

	@RequestMapping("/start/{startNum}/length/{lengthNum}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String startNum, @PathVariable String lengthNum)
			throws Exception {
		String message = "[]";
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		int start = 0;
		int length = 0;
		try {
			start = Integer.valueOf(startNum);
			length = Integer.valueOf(lengthNum);
		} catch (NumberFormatException e) {
			logger.warn("ZenWordController parse int failed.");
			e.printStackTrace();
			return new ModelAndView("result", "message", message);
		}
		if (start < 0 || start >= zenWordList.size() || length <= 0) {
			return new ModelAndView("result", "message", message);
		}
		int finish = Math.min(zenWordList.size(), start + length);
		List<ZenWord> resultList = zenWordList.subList(start, finish);
		message = JSONArray.fromObject(resultList).toString();

		return new ModelAndView("result", "message", message);
	}

}
