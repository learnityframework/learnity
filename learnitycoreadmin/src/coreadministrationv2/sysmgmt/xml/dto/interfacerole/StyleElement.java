package coreadministrationv2.sysmgmt.xml.dto.interfacerole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class StyleElement implements GenericDto {

	private static final long serialVersionUID = -9076847263259805388L;

	@XmlAttribute(required = true, name = "id")
	private String id;

	public StyleElement() {
	}

	public StyleElement(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
