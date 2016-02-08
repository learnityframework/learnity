package coreadministrationv2.sysmgmt.xml.dto.interfacerole;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements GenericDto {

	private static final long serialVersionUID = -9076847263259805388L;

	@XmlAttribute(required = true, name = "id")
	private String title;

	@XmlTransient
	private int id;

	@XmlElement(required = true, name = "interface",namespace="http://learnityframework.org/")
	private List<InterfaceElement> interfaceElementList;

	public Role() {
		interfaceElementList = new ArrayList<InterfaceElement>();
	}

	public Role addRole(InterfaceElement interfaceElement) {
		interfaceElementList.add(interfaceElement);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<InterfaceElement> getInterfaceElementList() {
		return interfaceElementList;
	}

	public void setInterfaceElementList(
			List<InterfaceElement> interfaceElementList) {
		this.interfaceElementList = interfaceElementList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
