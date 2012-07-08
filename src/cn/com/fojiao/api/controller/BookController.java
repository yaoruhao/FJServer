package cn.com.fojiao.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
		String message = "[]";
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		int start = 0;
		int length = 0;
		ArrayList<Book> bookList = bookMap.get(typeStr);
		if (bookList == null) {
			return new ModelAndView("result", "message", message);
		}
		try {
			start = Integer.valueOf(startNum);
			length = Integer.valueOf(lengthNum);
		} catch (NumberFormatException e) {
			logger.warn("BookController parse int failed.");
			e.printStackTrace();
			return new ModelAndView("result", "message", message);
		}
		if (start < 0 || start >= bookList.size() || length <= 0) {
			return new ModelAndView("result", "message", message);
		}
		int finish = Math.min(bookList.size(), start + length);
		List<Book> resultList = bookList.subList(start, finish);
		message = JSONArray.fromObject(resultList).toString();
		return new ModelAndView("result", "message", message);
	}
}
