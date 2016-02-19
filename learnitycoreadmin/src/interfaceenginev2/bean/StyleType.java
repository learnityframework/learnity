package interfaceenginev2.bean;

public enum StyleType {
	REFERENCE("reference"), INLINE("inline");

	private String type;

	private StyleType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
