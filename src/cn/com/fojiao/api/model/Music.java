package cn.com.fojiao.api.model;

/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com 
 * This class store the Music model. 
 */
public class Music {
	
	private String url;

	private String name;
	private String size;
	private String type;
	public Music(String url, String name, String size, String type) {
		this.url = url;
		this.name = name;
		this.size = size;
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Music [url=" + url + ", name=" + name + ", size=" + size
				+ ", type=" + type + "]";
	}

}
