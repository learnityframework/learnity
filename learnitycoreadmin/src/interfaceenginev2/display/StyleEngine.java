package interfaceenginev2.display;

import interfaceenginev2.NewDataBaseLayer;
import interfaceenginev2.bean.StyleInformation;
import interfaceenginev2.bean.StyleType;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import comv2.aunwesha.lfutil.GenericUtil;

public class StyleEngine {

	List<String> addedCssResources;

	public StyleEngine() {
		addedCssResources = new ArrayList<>();
	}

	public void createStyle(String layout, String style, String part_id, String interface_id, String themeId, String partclass, String position,
			String x, String y, String width, String height, Element item, Element itemhead, Element itembody, Document doc) {

		String positionStyling = "";
		if (GenericUtil.hasString(position)) {
			positionStyling = "position:" + position + ";";
		}
		if (GenericUtil.hasString(x)) {
			positionStyling = positionStyling + "left :" + x + ";";
		}
		if (GenericUtil.hasString(y)) {
			positionStyling = positionStyling + "top :" + y + ";";
		}
		if (GenericUtil.hasString(width)) {
			positionStyling = positionStyling + "width :" + width + ";";
		}
		if (GenericUtil.hasString(height)) {
			positionStyling = positionStyling + "height :" + height + ";";
		}
		ThemeEngine.setCssStyleAttribute(item, positionStyling);
		
		if (GenericUtil.hasString(themeId) && GenericUtil.hasString(partclass)) {
			ThemeEngine.setStyleClassFromThemes(themeId, partclass,item);
		}

		if (GenericUtil.hasString(layout) && GenericUtil.hasString(style) && GenericUtil.hasString(part_id) && GenericUtil.hasString(interface_id)) {
			List<StyleInformation> styles = NewDataBaseLayer.getStyleValue(layout, style, part_id, interface_id);
			for (StyleInformation styleInfo : styles) {
				StyleType styleType = styleInfo.getType();
				String styleResourceId = styleInfo.getResourceId();
				String styleValue = styleInfo.getValue();
				if (StyleType.REFERENCE.equals(styleType)) {
					ThemeEngine.setCssClassAttribute(item, styleValue);
					createReferenceStyle(item, itemhead, itembody, interface_id, styleResourceId, addedCssResources, doc);

				} else if (StyleType.INLINE.equals(styleType)) {
					ThemeEngine.setCssStyleAttribute(item, styleValue);
				}
				// From Themes

			}
		}

	}

	private void createReferenceStyle(Element itemmain, Element itemhead, Element itembody, String interface_id, String resourceId,
			List<String> addedCssResources, Document doc) {

		String inlinecss = NewDataBaseLayer.checkinlinecss(interface_id);
		String resource_location = NewDataBaseLayer.Getresourcelocation(interface_id, resourceId);
		if (resource_location == null) {
			resource_location = "";
		}
		String checkInterfaceType = NewDataBaseLayer.GetInterfaceType(interface_id);
		if (checkInterfaceType.equals("InterfaceFragment")) {

			String vectorcss = NewDataBaseLayer.getcssandjs(resourceId, interface_id);
			if (GenericUtil.hasString(vectorcss)) {
				if (!GenericUtil.inList(resourceId, addedCssResources)) {
					Element stylehead = doc.createElement("style");
					stylehead.setAttribute("type", "text/css");
					stylehead.appendChild(doc.createTextNode(vectorcss));
					itembody.appendChild(stylehead);
					addedCssResources.add(resourceId);
				}

			}
		} else {

			if (inlinecss.equals("yes")) {
				String vectorcss = NewDataBaseLayer.getcssandjs(resourceId, interface_id);

				if (GenericUtil.hasString(vectorcss)) {
					if (!GenericUtil.inList(resourceId, addedCssResources)) {
						Element stylehead = doc.createElement("style");
						stylehead.setAttribute("type", "text/css");
						stylehead.appendChild(doc.createTextNode(vectorcss));
						if (resource_location.equalsIgnoreCase("body")) {
							itembody.appendChild(stylehead);
						} else {
							itemhead.appendChild(stylehead);
						}
						addedCssResources.add(resourceId);
					}

				}

			} else {
				if (!GenericUtil.inList(resourceId, addedCssResources) && GenericUtil.hasString(resourceId)) {
					Element linkstyle = doc.createElement("link");
					linkstyle.setAttribute("type", "text/css");
					linkstyle.setAttribute("rel", "stylesheet");
					linkstyle.setAttribute("href", "./interfaceenginev2.ResourceCss?resource_id=" + resourceId + "&interface_id=" + interface_id);
					if (resource_location.equalsIgnoreCase("body")) {
						itembody.appendChild(linkstyle);
					} else {
						itemhead.appendChild(linkstyle);
					}
				}

			}
		}

	}

}
