package comv2.aunwesha.lfutil;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LFResource {

	PORTAL("portal"), DISPLAY_ENGINE("display_engine");

	private String name;

	private LFResource(String name) {
		this.name = name;
	}

	public String retriveResourceValue(String key) {
		final ResourceBundle rb = ResourceBundle.getBundle(name, Locale.getDefault());
		return rb.getString(key);
	}

}
