package interfaceenginev2.display;

import org.w3c.dom.Element;

import interfaceenginev2.NewDataBaseLayer;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.Pair;

public class ThemeEngine {
	public static Pair<String, String> retrieveThemeIdAndCommnts(String interfaceId, String applicationTemplateId) {
		boolean themeExist = false;
		String themeIdComments = "";
		String themeId = null;

		/*
		 * Fetching theme from interface table and checking if it exists.
		 */
		String themeName = NewDataBaseLayer.getThemeFromInterfaceId(interfaceId);

		if (GenericUtil.hasString(themeName)) {
			themeExist = NewDataBaseLayer.isThemeExist(themeName);
			if (!themeExist) {
				themeIdComments = themeIdComments + "Theme is mentioned in the interface as '" + themeName + "' but it does not exist.\n";
			} else {
				themeIdComments = themeIdComments + "Theme is mentioned in the interface as '" + themeName + "'. It also exists.\n";
				themeId = themeName;
			}
		} else {
			themeIdComments = themeIdComments + "Theme is not mentioned in the interface.\n";
		}

		/*
		 * If interface does not contain theme then fetching theme from
		 * application template table and checking if it exists.
		 */
		if (GenericUtil.isEmptyString(themeId)) {
			themeName = NewDataBaseLayer.getThemeFromApplicationTemplate(applicationTemplateId);
			if (GenericUtil.hasString(themeName)) {
				themeExist = NewDataBaseLayer.isThemeExist(themeName);
				if (!themeExist) {
					themeIdComments = themeIdComments + "Application template contains theme name as '" + themeName + "' but it does not exist.\n";
				} else {
					themeIdComments = themeIdComments + "Application template contains theme name as '" + themeName + "'. It also exists.\n";
					themeId = themeName;
				}
			} else {
				themeIdComments = themeIdComments + "Theme is not mentioned in the application template.\n";
			}
		}

		/*
		 * If interface and application template does not contain theme then
		 * fetching default theme.
		 */
		if (GenericUtil.isEmptyString(themeId)) {
			themeName = NewDataBaseLayer.getDefaultTheme();
			if (GenericUtil.hasString(themeName)) {
				themeIdComments = themeIdComments + "Default theme is used. Theme name is '" + themeName + "'.\n";
				themeId = themeName;
			} else {
				themeIdComments = themeIdComments + "No default theme is mentioned.\n";
			}
		}

		Pair<String, String> returnPair = new Pair<String, String>(themeId, themeIdComments);
		return returnPair;

	}

	public static Pair<String, String> generateClassFromThemes(String themeId, String partclass) {
		String themesClass = NewDataBaseLayer.getThemesClass(themeId, partclass, "reference");
		String themesStyle = NewDataBaseLayer.getThemesProperty(themeId, partclass, "inline");
		return new Pair<String, String>(themesClass, themesStyle);
	}

	public static void setStyleClassAttribute(Element element, Pair<String, String> classStylePair) {
		String themesClass = classStylePair.getFirst();
		String themesStyle = classStylePair.getSecond();
		if (GenericUtil.hasString(themesClass)) {
			element.setAttribute("class", themesClass);
		}
		if (GenericUtil.hasString(themesStyle)) {
			element.setAttribute("style", themesStyle);
		}
	}

	public static void setStyleClassAttribute(Element element, Pair<String, String> classStylePair, String extraStyle, String extraClass) {
		String themesClass = classStylePair.getFirst();
		String themesStyle = classStylePair.getSecond();
		if (GenericUtil.hasString(themesClass) && GenericUtil.hasString(extraClass)) {
			element.setAttribute("class", themesClass + " " + extraClass);
		} else if (GenericUtil.hasString(themesClass)) {
			element.setAttribute("class", themesClass);
		} else if (GenericUtil.hasString(extraClass)) {
			element.setAttribute("class", extraClass);
		}

		if (GenericUtil.hasString(themesStyle) && GenericUtil.hasString(extraStyle)) {
			element.setAttribute("style", themesStyle+";"+extraStyle);
		} else if (GenericUtil.hasString(extraStyle)) {
			element.setAttribute("style", extraStyle);
		} else if (GenericUtil.hasString(themesStyle)) {
			element.setAttribute("style", themesStyle);
		}
	}
}
