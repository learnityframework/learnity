package interfaceenginev2.bean;

public class ApplicationTemplate {
	private String id;
	private String uiFramework;
	private Integer blockUiTimeout;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUiFramework() {
		return uiFramework;
	}

	public void setUiFramework(String uiFramework) {
		this.uiFramework = uiFramework;
	}

	public Integer getBlockUiTimeout() {
		return blockUiTimeout;
	}

	public void setBlockUiTimeout(Integer blockUiTimeout) {
		this.blockUiTimeout = blockUiTimeout;
	}

}
