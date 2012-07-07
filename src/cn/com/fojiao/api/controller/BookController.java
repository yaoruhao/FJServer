package cn.com.fojiao.api.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import cn.com.fojiao.api.manager.DataManager;
import cn.com.fojiao.api.model.Book;


/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com
 * BookController receive book request, then return book info.
 *
 */
@Controller
@RequestMapping("/book")
public class BookController {
	private static Log logger = LogFactory.getLog(BookController.class);
	private static HashMap<String, ArrayList<Book>> bookMap;
	
	public void init() {
		bookMap = DataManager.getInstance().getBooks();
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
		return new ModelAndView("result", "message", message);
	}
}
