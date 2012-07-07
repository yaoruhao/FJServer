package cn.com.fojiao.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
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
 * @author Ruhao Yao : yaoruhao@gmail.com DataManager is used to fetch data from
 *         aliyun OSS.
 * 
 */
public class DataManager {
	private static Log logger = LogFactory.getLog(DataManager.class);
	private static String ossAccessID;
	private static String ossAccessKey;

	private static final String BUCKET_NAME = "fojiaoweibo";
	private static final String MUSIC_DIR = "music";
	private static final String BOOK_DIR = "book";
	private static final String PIC_DIR = "pic";
	private static final String HOST_PREFIX = "http://storage.aliyun.com/fojiaoweibo/";
	private static final String ZEN_WORD_URL = "http://storage.aliyun.com/fojiaoweibo/meiriyichan/每日一禅.txt";
	private static final String ZEN_IMAGE_URL_PREFIX = "http://storage.aliyun.com/fojiaoweibo/pic/meiriyichan/";
	private static final int MAXNUM_PER_FETCH = 1000;
	private static ArrayList<String> MUSIC_TYPE_LIST = new ArrayList<String>();
	private static ArrayList<String> BOOK_TYPE_LIST = new ArrayList<String>();
	private static HashMap<String, ArrayList<String>> PIC_TYPE_MAP = new HashMap<String, ArrayList<String>>();

	public static void setOssAccessID(String ossAccessID) {
		DataManager.ossAccessID = ossAccessID;
	}

	public static void setOssAccessKey(String ossAccessKey) {
		DataManager.ossAccessKey = ossAccessKey;
	}

	private static OSSClient ossClient;

	private DataManager() {
	}

	private static DataManager dataManager;

	public static synchronized DataManager getInstance() {
		if (dataManager == null) {
			dataManager = new DataManager();
		}
		return dataManager;
	}

	public void init() {
		if (ossAccessID != null && ossAccessKey != null) {
			ossClient = new OSSClient(ossAccessID, ossAccessKey);
			MUSIC_TYPE_LIST.add("fojingniansong");
			MUSIC_TYPE_LIST.add("foyigequ");
			MUSIC_TYPE_LIST.add("mingxingyanyi");
			BOOK_TYPE_LIST.add("jiaoyijingdian");
			BOOK_TYPE_LIST.add("fofajiedu");
			ArrayList<String> androidPicTypeList = new ArrayList<String>();
			androidPicTypeList.add("chantu");
			androidPicTypeList.add("foxiang");
			PIC_TYPE_MAP.put("android", androidPicTypeList);
			ArrayList<String> iosPicTypeList = new ArrayList<String>();
			iosPicTypeList.add("chantu/hengtu");
			iosPicTypeList.add("chantu/shutu");
			iosPicTypeList.add("foxiang/hengtu");
			iosPicTypeList.add("foxiang/shutu");
			PIC_TYPE_MAP.put("ios", iosPicTypeList);
		} else {
			logger.error("DataManager init failed, check ossAccessId and ossAcessKey");
			return;
		}
	}

	public HashMap<String, ArrayList<Book>> getBooks() {
		HashMap<String, ArrayList<Book>> bookMap = new HashMap<String, ArrayList<Book>>();
		ArrayList<Book> allTypeList = new ArrayList<Book>();
		for (String type : BOOK_TYPE_LIST) {
			String prefixStr = BOOK_DIR + "/" + type + "/";
			ArrayList<OSSObjectSummary> objectList = new ArrayList<OSSObjectSummary>();
			ObjectListing objectListing = null;
			String nextMarker = null;
			boolean fetchNextFlag = true;
			while (fetchNextFlag) {
				try {
					objectListing = ossClient
							.listObjects(new ListObjectsRequest(BUCKET_NAME,
									prefixStr, nextMarker, "/",
									MAXNUM_PER_FETCH));
					objectList.addAll(objectListing.getObjectSummaries());
					nextMarker = objectListing.getNextMarker();
					if (nextMarker == null) {
						fetchNextFlag = false;
					} else {
						fetchNextFlag = true;
					}
					logger.info("next marker:" + nextMarker);
				} catch (Exception e) {
					logger.error("getMusic objectListing throws exception.");
					e.printStackTrace();
				}
			}

			ArrayList<Book> tempList = new ArrayList<Book>();
			// start from 1 to ignore first item(which is dir)
			for (int i = 1; i < objectList.size(); i++) {
				OSSObjectSummary objectSummary = objectList.get(i);
				String key = objectSummary.getKey();
				String name = key.substring(prefixStr.length());
				String url = HOST_PREFIX + key;
				String size = String.valueOf(objectSummary.getSize());
				Book tempBook = new Book(name, type, size, url);
				tempList.add(tempBook);
				RandomInsert(allTypeList, tempBook);
			}
			bookMap.put(type, tempList);

		}
		bookMap.put("all", allTypeList);
		logger.info("All book map:" + bookMap);
		return bookMap;
	}

	public HashMap<String, ArrayList<Music>> getMusics() {
		HashMap<String, ArrayList<Music>> musicMap = new HashMap<String, ArrayList<Music>>();
		ArrayList<Music> allTypeList = new ArrayList<Music>();
		for (String type : MUSIC_TYPE_LIST) {
			String prefixStr = MUSIC_DIR + "/" + type + "/";
			ArrayList<OSSObjectSummary> objectList = new ArrayList<OSSObjectSummary>();
			ObjectListing objectListing = null;
			String nextMarker = null;
			boolean fetchNextFlag = true;
			while (fetchNextFlag) {
				try {
					objectListing = ossClient
							.listObjects(new ListObjectsRequest(BUCKET_NAME,
									prefixStr, nextMarker, "/",
									MAXNUM_PER_FETCH));
					objectList.addAll(objectListing.getObjectSummaries());
					nextMarker = objectListing.getNextMarker();
					if (nextMarker == null) {
						fetchNextFlag = false;
					} else {
						fetchNextFlag = true;
					}
					logger.info("next marker:" + nextMarker);
				} catch (Exception e) {
					logger.error("getMusic objectListing throws exception.");
					e.printStackTrace();
				}
			}
			ArrayList<Music> tempList = new ArrayList<Music>();
			// start from 1 to ignore first item(which is dir)
			for (int i = 1; i < objectList.size(); i++) {
				OSSObjectSummary objectSummary = objectList.get(i);
				String key = objectSummary.getKey();
				String name = key.substring(prefixStr.length());
				String url = HOST_PREFIX + key;
				String size = String.valueOf(objectSummary.getSize());
				Music tempMusic = new Music(url, name, size, type);
				tempList.add(tempMusic);
				RandomInsert(allTypeList, tempMusic);
			}
			musicMap.put(type, tempList);
		}
		musicMap.put("all", allTypeList);
		logger.info("All music map:" + musicMap);
		return musicMap;
	}

	public ArrayList<ZenWord> getZenWords() {
		ArrayList<ZenWord> zenWordList = new ArrayList<ZenWord>();
		List<String> zenList = null;
		try {
			InputStream inputStream = new URL(ZEN_WORD_URL).openStream();
			zenList = IOUtils.readLines(inputStream, "gbk");
			logger.info(zenList);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (zenList != null && !zenList.isEmpty()) {
			for (String eachZen : zenList) {
				String[] splitResult = eachZen.split("\t");
				if (splitResult != null && splitResult.length == 2) {
					String zenContent = splitResult[0].trim();
					String zenImage = splitResult[1].trim();
					Image tempImage = new Image(zenImage, ZEN_IMAGE_URL_PREFIX
							+ "small/" + zenImage, ZEN_IMAGE_URL_PREFIX
							+ "big/" + zenImage, "zenimage", null, null, null);
					ZenWord tempZenWord = new ZenWord(zenContent, tempImage);
					zenWordList.add(tempZenWord);
				}
			}
		}

		logger.info("Zenword List:" + zenWordList);
		return zenWordList;
	}

	private void fetchImages(ArrayList<Image> imageList,
			ArrayList<Image> totalImageList, String prefixUrl, String type,
			String orientation) {
		ArrayList<OSSObjectSummary> objectList = new ArrayList<OSSObjectSummary>();
		ObjectListing objectListing = null;
		String nextMarker = null;
		boolean fetchNextFlag = true;
		while (fetchNextFlag) {
			try {
				objectListing = ossClient.listObjects(new ListObjectsRequest(
						BUCKET_NAME, prefixUrl + "big/", nextMarker, "/",
						MAXNUM_PER_FETCH));
				objectList.addAll(objectListing.getObjectSummaries());
				nextMarker = objectListing.getNextMarker();
				if (nextMarker == null) {
					fetchNextFlag = false;
				} else {
					fetchNextFlag = true;
				}
				logger.info("next marker:" + nextMarker);
			} catch (Exception e) {
				logger.error("getMusic objectListing throws exception.");
				e.printStackTrace();
			}
		}
		for (int i = 1; i < objectList.size(); i++) {
			OSSObjectSummary ossObjectSummary = objectList.get(i);
			String key = ossObjectSummary.getKey();
			String name = key.substring((prefixUrl + "big/").length());
			String size = String.valueOf(ossObjectSummary.getSize());
			Image image = new Image(name, HOST_PREFIX + prefixUrl + "small/"
					+ name, HOST_PREFIX + prefixUrl + "big/" + name, type,
					orientation, size, null);
			RandomInsert(imageList, image);
			RandomInsert(totalImageList, image);
		}
	}

	public HashMap<String, ArrayList<Image>> getImages() {
		HashMap<String, ArrayList<Image>> imageMap = new HashMap<String, ArrayList<Image>>();
		for (String platform : PIC_TYPE_MAP.keySet()) {
			ArrayList<String> typeList = PIC_TYPE_MAP.get(platform);
			ArrayList<Image> platformTotalImageList = new ArrayList<Image>();
			for (String typeStr : typeList) {
				String prefixStr = PIC_DIR + "/" + platform + "/" + typeStr
						+ "/";
				String exactTypeStr = typeStr.split("/")[0];
				String oritation = "hengtu";
				if (platform.equals("ios")) {
					oritation = typeStr.split("/")[1];
				}
				String imageListKey = platform + "-" + exactTypeStr;
				ArrayList<Image> tempList = imageMap.get(imageListKey);
				if (tempList == null) {
					tempList = new ArrayList<Image>();
				}
				fetchImages(tempList, platformTotalImageList, prefixStr,
						exactTypeStr, oritation);
				imageMap.put(imageListKey, tempList);
			}
			imageMap.put(platform + "-all", platformTotalImageList);
		}

		logger.info("imageMap:" + imageMap);
		return imageMap;
	}

	private void RandomInsert(ArrayList array, Object obj) {
		int randomLoc = RandomUtils.nextInt(array.size() + 1);
		array.add(randomLoc, obj);
	}

}
