package comv2.aunwesha.lfutil;

public enum GridModificationStatus {
	ERROR_STATUS("error"), SUCCESS_STATUS("success");

	private String status;

	private GridModificationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
