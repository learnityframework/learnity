package coreadministrationv2.sysmgmt.xml.dto.manifest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlRootElement(name = "manifest",namespace="http://learnityframework.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManifestContainer implements GenericDto {

	private static final long serialVersionUID = 3279122703908840660L;

	@XmlAttribute(required = true, name = "id")
	private String id;

	@XmlAttribute(required = true, name = "title")
	private String title;

	@XmlElement(required = true, name = "interface",namespace="http://learnityframework.org/")
	private List<InterfaceElement> interfaceElements;

	public ManifestContainer() {
		interfaceElements=new ArrayList<>();
	}

	public ManifestContainer(String id, String title) {
		interfaceElements=new ArrayList<>();
		this.id = id;
		this.title = title;
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

	public List<InterfaceElement> getInterfaceElements() {
		return interfaceElements;
	}

	public void setInterfaceElements(List<InterfaceElement> interfaceElements) {
		this.interfaceElements = interfaceElements;
	}
	
	public void addInterfaceElement(InterfaceElement interfaceElement){
		this.interfaceElements.add(interfaceElement);
	}

}
