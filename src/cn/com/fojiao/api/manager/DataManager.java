package cn.com.fojiao.api.manager;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.fojiao.api.model.Book;
import cn.com.fojiao.api.model.Image;
import cn.com.fojiao.api.model.Music;
import cn.com.fojiao.api.model.ZenWord;

import com.aliyun.openservices.oss.OSSClient;

import com.aliyun.openservices.oss.model.ListObjectsRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;

/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com
 * DataManager is used to fetch data from aliyun OSS.
 *
 */
public class DataManager {
	private static Log logger = LogFactory.getLog(DataManager.class);
	private static String ossAccessID;
	private static String ossAccessKey;
	
	private static final String BUCKET_NAME = "fojiaoweibo";
	private static final String MUSIC_DIR = "music";
	private static final String HOST_PREFIX = "http://storage.aliyun.com/fojiaoweibo/";
	private static final int MAXNUM_PER_FETCH = 1000;
	private static ArrayList<String> MUSIC_TYPE_LIST = new ArrayList<String>();;
	
	public static void setOssAccessID(String ossAccessID) {
		DataManager.ossAccessID = ossAccessID;
	}
	public static void setOssAccessKey(String ossAccessKey) {
		DataManager.ossAccessKey = ossAccessKey;
	}
	private static OSSClient ossClient;
	private DataManager(){}
	private static DataManager dataManager;
	public static synchronized DataManager getInstance(){
		if (dataManager == null) {
			dataManager = new DataManager();
		}
		return dataManager;
	}
	public void init(){
		if (ossAccessID != null && ossAccessKey != null) {
			ossClient = new OSSClient(ossAccessID, ossAccessKey);
			MUSIC_TYPE_LIST.add("fojingniansong");
			MUSIC_TYPE_LIST.add("foyigequ");
			MUSIC_TYPE_LIST.add("mingxingyanyi");
		} else {
			logger.error("DataManager init failed, check ossAccessId and ossAcessKey");
			return;
		}
	}
	public HashMap<String, ArrayList<Book>> getBooks()
	{
		HashMap<String, ArrayList<Book>> bookMap = new HashMap<String,ArrayList<Book>>();
		return bookMap;
	}
	
	public HashMap<String, ArrayList<Music>> getMusics()
	{
		HashMap<String, ArrayList<Music>> musicMap = new HashMap<String, ArrayList<Music>>();
		ArrayList<Music> allTypeList = new ArrayList<Music>();
		for (String type : MUSIC_TYPE_LIST) {
			String prefixStr = MUSIC_DIR + "/" + type + "/";
			List<OSSObjectSummary> objectList = null;
			ObjectListing objectListing = null;
			try {
				objectListing = ossClient.listObjects(new ListObjectsRequest(BUCKET_NAME, prefixStr, null, "/", MAXNUM_PER_FETCH));
				objectList = objectListing.getObjectSummaries();
				logger.info("next marker:"+objectListing.getNextMarker());
			} catch (Exception e) {
				logger.error("getMusic objectListing throws exception.");
				e.printStackTrace();
			}
			for (OSSObjectSummary objectSummary : objectList) {
				long size = objectSummary.getSize();
				String name = objectSummary.getKey();
				
			}
			ArrayList<Music> tempList = new ArrayList<Music>();
			//start from 1 to ignore first item(which is dir)
			for (int i = 1; i < objectList.size(); i++) {				
				OSSObjectSummary objectSummary = objectList.get(i);
				String key = objectSummary.getKey();
				String name = key.substring(prefixStr.length());
				String url = HOST_PREFIX + key;
				String size = String.valueOf(objectSummary.getSize());
				Music tempMusic = new Music(url, name, size, type);
				logger.info("Add music:"+tempMusic+" to "+type+" list.");
				tempList.add(tempMusic);
				RandomInsert(allTypeList, tempMusic);
			}
			musicMap.put(type, tempList);
		}
		musicMap.put("all", allTypeList);
		logger.info("All music list:"+allTypeList);
		return musicMap;
	}
	public ArrayList<ZenWord> getZenWords() {
		ArrayList<ZenWord> zenWordList = new ArrayList<ZenWord>();
		return zenWordList;
	}
	
	public HashMap<String, ArrayList<Image>> getImages() {
		HashMap<String, ArrayList<Image>> imageMap = new HashMap<String, ArrayList<Image>>();
		
		return imageMap;
	}
	
	private void RandomInsert(ArrayList array,  Object obj) {
		int randomLoc = RandomUtils.nextInt(array.size()+1);
		array.add(randomLoc, obj);
	}

}
