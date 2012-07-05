package cn.com.fojiao.api.model;

/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com 
 * This class store the Image model. 
 */
public class Image {
	private String name;
	private String smallPicUrl;
	private String bigPicUrl;
	private String type;
	private String orientation;
	private String bigPicSize;
	private String smallPicSize;
	public Image(String name, String smallPicUrl, String bigPicUrl,
			String type, String orientation, String bigPicSize,
			String smallPicSize) {
		this.name = name;
		this.smallPicUrl = smallPicUrl;
		this.bigPicUrl = bigPicUrl;
		this.type = type;
		this.orientation = orientation;
		this.bigPicSize = bigPicSize;
		this.smallPicSize = smallPicSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmallPicUrl() {
		return smallPicUrl;
	}
	public void setSmallPicUrl(String smallPicUrl) {
		this.smallPicUrl = smallPicUrl;
	}
	public String getBigPicUrl() {
		return bigPicUrl;
	}
	public void setBigPicUrl(String bigPicUrl) {
		this.bigPicUrl = bigPicUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getBigPicSize() {
		return bigPicSize;
	}
	public void setBigPicSize(String bigPicSize) {
		this.bigPicSize = bigPicSize;
	}
	public String getSmallPicSize() {
		return smallPicSize;
	}
	public void setSmallPicSize(String smallPicSize) {
		this.smallPicSize = smallPicSize;
	}
	@Override
	public String toString() {
		return "Image [name=" + name + ", smallPicUrl=" + smallPicUrl
				+ ", bigPicUrl=" + bigPicUrl + ", type=" + type
				+ ", orientation=" + orientation + ", bigPicSize=" + bigPicSize
				+ ", smallPicSize=" + smallPicSize + "]";
	}
	

}
