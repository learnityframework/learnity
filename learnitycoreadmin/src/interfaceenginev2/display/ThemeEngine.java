package interfaceenginev2.display;

import interfaceenginev2.NewDataBaseLayer;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.Pair;


public class ThemeEngine {
	public static Pair<String, String> retrieveThemeIdAndCommnts(String interfaceId,String applicationTemplateId){
		boolean themeExist=false;
		String themeIdComments="";
		String themeId=null;

		/*
		 * Fetching theme from interface table and checking if it exists.
		 */
		String themeName=NewDataBaseLayer.getThemeFromInterfaceId(interfaceId);

		if(GenericUtil.hasString(themeName)){
			themeExist=NewDataBaseLayer.isThemeExist(themeName);
			if(!themeExist){
				themeIdComments=themeIdComments+"Theme is mentioned in the interface as '"+themeName+"' but it does not exist.\n";
			}else{
				themeIdComments=themeIdComments+"Theme is mentioned in the interface as '"+themeName+"'. It also exists.\n";
				themeId=themeName;
			}
		}else{
			themeIdComments=themeIdComments+"Theme is not mentioned in the interface.\n";
		}

		/*
		 * If interface does not contain theme then fetching theme from application template table and checking if it exists.
		 */
		if(GenericUtil.isEmptyString(themeId)){
			themeName=NewDataBaseLayer.getThemeFromApplicationTemplate(applicationTemplateId);
			if(GenericUtil.hasString(themeName)){
				themeExist=NewDataBaseLayer.isThemeExist(themeName);
				if(!themeExist){
					themeIdComments=themeIdComments+"Application template contains theme name as '"+themeName+"' but it does not exist.\n";
				}else{
					themeIdComments=themeIdComments+"Application template contains theme name as '"+themeName+"'. It also exists.\n";
					themeId=themeName;
				}
			}else{
				themeIdComments=themeIdComments+"Theme is not mentioned in the application template.\n";
			}
		}

		/*
		 * If interface and application template does not contain theme then fetching default theme.
		 */
		if(GenericUtil.isEmptyString(themeId)){
			themeName=NewDataBaseLayer.getDefaultTheme();
			if(GenericUtil.hasString(themeName)){
				themeIdComments=themeIdComments+"Default theme is used. Theme name is '"+themeName+"'.\n";
				themeId=themeName;
			}else{
				themeIdComments=themeIdComments+"No default theme is mentioned.\n";
			}
		}
		
		Pair<String, String> returnPair=new Pair<String, String>(themeId, themeIdComments);
		return returnPair;

	}
	
	public static String generateClassFromThemes(String themeId,String partclass)
	{
		String themesClass=NewDataBaseLayer.getThemesClass(themeId,partclass,"reference");
		return themesClass;
	}
}
