package coreadministrationv2.sysmgmt.xml.util;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XSDCustomErrorHandler extends DefaultHandler {

	private boolean success;
	private String errorMessage;

	public XSDCustomErrorHandler() {
		errorMessage = "";
		success = true;
	}

	public void warning(SAXParseException e) throws SAXException {
		errorMessage = errorMessage.concat("Warning: <br/>");
		putErrorMessage(e, errorMessage);
	}

	public void error(SAXParseException e) throws SAXException {
		errorMessage = errorMessage.concat("Error: <br/>");
		success = false;
		putErrorMessage(e, errorMessage);
	}

	public void fatalError(SAXParseException e) throws SAXException {
		errorMessage = errorMessage.concat("Fattal error: <br/>");
		success = false;
		putErrorMessage(e, errorMessage);
	}

	private void putErrorMessage(SAXParseException e, String errorMessage2) {
		errorMessage = errorMessage.concat("   Public ID: " + e.getPublicId() + "<br/>");
		errorMessage = errorMessage.concat("   System ID: " + e.getSystemId() + "<br/>");
		errorMessage = errorMessage.concat("   Line number: " + e.getLineNumber() + "<br/>");
		errorMessage = errorMessage.concat("   Column number: " + e.getColumnNumber() + "<br/>");
		errorMessage = errorMessage.concat("   Message: " + e.getMessage() + "<br/>");
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
