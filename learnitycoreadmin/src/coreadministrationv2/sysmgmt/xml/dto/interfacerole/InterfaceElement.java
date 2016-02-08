package coreadministrationv2.sysmgmt.xml.dto.interfacerole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class InterfaceElement implements GenericDto {

	private static final long serialVersionUID = -5662648627140279996L;

	@XmlAttribute(required = true, name = "id")
	private String id;

	@XmlElement(required = true, name = "layout",namespace="http://learnityframework.org/")
	private LayoutElement layoutElement;

	@XmlElement(required = false, name = "content",namespace="http://learnityframework.org/")
	private ContentElement contentElement;

	@XmlElement(required = false, name = "style",namespace="http://learnityframework.org/")
	private StyleElement styleElement;

	@XmlElement(required = false, name = "behaviour",namespace="http://learnityframework.org/")
	private BehaviourElement behaviourElement;

	public InterfaceElement() {
	}

	public InterfaceElement(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LayoutElement getLayoutElement() {
		return layoutElement;
	}

	public void setLayoutElement(LayoutElement layoutElement) {
		this.layoutElement = layoutElement;
	}

	public ContentElement getContentElement() {
		return contentElement;
	}

	public void setContentElement(ContentElement contentElement) {
		this.contentElement = contentElement;
	}

	public StyleElement getStyleElement() {
		return styleElement;
	}

	public void setStyleElement(StyleElement styleElement) {
		this.styleElement = styleElement;
	}

	public BehaviourElement getBehaviourElement() {
		return behaviourElement;
	}

	public void setBehaviourElement(BehaviourElement behaviourElement) {
		this.behaviourElement = behaviourElement;
	}

}
