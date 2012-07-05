package cn.com.fojiao.api.model;

/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com 
 * This class store the Book model. 
 */
public class Book {
	private String name;
	private String type;
	private String size;
	private String url;
	public Book(String name, String type, String size, String url) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", type=" + type + ", size=" + size
				+ ", url=" + url + "]";
	}
	

}
