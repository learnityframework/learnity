package coreadministrationv2.bean;

public class InterfaceUpdateStatus {
	private String interfaceId;
	private String lastUpdated;
	private String applicationTemplate;
	private String applicationTemplateUploaded;
	private String theme;
	private String themeUploaded;
	private Boolean refreshRequired;

	
	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getApplicationTemplate() {
		return applicationTemplate;
	}

	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}

	public String getApplicationTemplateUploaded() {
		return applicationTemplateUploaded;
	}

	public void setApplicationTemplateUploaded(String applicationTemplateUploaded) {
		this.applicationTemplateUploaded = applicationTemplateUploaded;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getThemeUploaded() {
		return themeUploaded;
	}

	public void setThemeUploaded(String themeUploaded) {
		this.themeUploaded = themeUploaded;
	}

	public Boolean getRefreshRequired() {
		return refreshRequired;
	}

	public void setRefreshRequired(Boolean refreshRequired) {
		this.refreshRequired = refreshRequired;
	}

}
