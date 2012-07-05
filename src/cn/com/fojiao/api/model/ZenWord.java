package cn.com.fojiao.api.model;


/**
 * 
 * @author Ruhao Yao : yaoruhao@gmail.com 
 * This class store the ZenWord model. 
 */
public class ZenWord {
	private String content;
	private Image image;
	public ZenWord(String content, Image image) {
		this.content = content;
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "ZenWord [content=" + content + ", image=" + image + "]";
	}
	

}
