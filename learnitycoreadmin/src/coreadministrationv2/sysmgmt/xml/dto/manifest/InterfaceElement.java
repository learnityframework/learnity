package coreadministrationv2.sysmgmt.xml.dto.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class InterfaceElement implements GenericDto {

	private static final long serialVersionUID = -8780064918181417893L;

	@XmlAttribute(required = true, name = "id")
	private String id;

	@XmlAttribute(required = true, name = "title")
	private String title;

	@XmlAttribute(required = true, name = "type")
	private String type;

	@XmlAttribute(required = true, name = "zip")
	private String zip;

	public InterfaceElement(String id, String title, String type, String zip) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.zip = zip;
	}

	public InterfaceElement() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
