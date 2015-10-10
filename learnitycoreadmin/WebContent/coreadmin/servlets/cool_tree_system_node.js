var db = document.all ? '<div style="width:143px">&nbsp;&nbsp;&nbsp;' : '&nbsp;&nbsp;&nbsp;';
var de = document.all ? '</div>' : '&nbsp;&nbsp;&nbsp;';

var f215 = db+'Application Template Management'+de;
/*var f216 = db+'Configuration Template Definition'+de;*/
var f208 = db+'Interface Management'+de;
var f111  = db+'Role Management'+de;
var f112= db+'Themes Management'+de;
var f113= db+'Asset File Management'+de;
var f114= db+'Interface Role Management'+de;
//var f3 = db+'Self Test Management'+de;
var f4  = db+'Schemaspy'+de;
var f5  = db+'Webstatistics'+de;
var f6  = db+'Cache Definition'+de;
var f7  = db+'Cache Management'+de;

var COOL_NODES_System = [
		
	['<img src="../images/coreadmin.png" width=240 height=30 border=0>', null, null,
	[f215, "../../servlet/coreadministrationv2.sysmgmt.ApplicationTemplateManagement", "bodyFrame"],
// 	[f215, "../../servlet/coreadministrationv2.sysmgmt.TemplateDefinition", "bodyFrame"],
	[f208, "../../servlet/coreadministrationv2.sysmgmt.InterfaceManagement", "bodyFrame"],
	[f111, "../../servlet/coreadministrationv2.sysmgmt.RoleManagement", "bodyFrame"],
	[f112, "../../servlet/coreadministrationv2.sysmgmt.ThemesManagement", "bodyFrame"],
	[f113, "../../servlet/coreadministrationv2.sysmgmt.AssetFileManagement", "bodyFrame"],
	[f114, "../../servlet/coreadministrationv2.sysmgmt.InterfaceRoleManagement", "bodyFrame"],
	[f6, "../../servlet/coreadministrationv2.sysmgmt.CacheDefinition", "bodyFrame"],
	[f7, "../../servlet/coreadministrationv2.sysmgmt.CacheManagement", "bodyFrame"],
// 	[f555, "../../UserRole", "bodyFrame"],
	//[f3, "../../servlet/coreadministrationv2.sysmgmt.RoleManagement", "bodyFrame"],
	[f4, "./Loading.html", "bodyFrame"],
	[f5, "../../cgi-bin/awstats.cgi?config=localhost", "bodyFrame"],
	],
	['<center><font size="1">&nbsp;</FONT></center>', null, null],
	
	['<img src="../images/toolbox_signout1.gif" border=0>', "../../servlet/coreadministrationv2.Logout", "_parent"],

	['</a><font color=white>LearnITy Administrator</FONT></a>', null, null],

	['</a><img src="../images/book.gif" width=160 height=115 border=0><a>', null, null]
	
	
];

