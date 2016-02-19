package interfaceenginev2.bean;

public class StyleInformation {
	private String value;
	private String resourceId;
	private StyleType type;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public StyleType getType() {
		return type;
	}

	public void setType(StyleType type) {
		this.type = type;
	}

	public void setType(String type) {
		this.type = null;
		if (StyleType.INLINE.getType().equalsIgnoreCase(type)) {
			this.type = StyleType.INLINE;
		} else if (StyleType.REFERENCE.getType().equalsIgnoreCase(type)) {
			this.type = StyleType.REFERENCE;
		}
	}

}
