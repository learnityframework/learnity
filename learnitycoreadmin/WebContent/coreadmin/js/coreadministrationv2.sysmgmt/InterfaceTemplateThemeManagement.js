function refreshAll_onclick() {
			doyou = confirm("Are you Sure to refresh all interface and interface fragements?"); 
			if (doyou == true) {
				document.interfaceTemplateThemeManagementForm.method="post";
				document.interfaceTemplateThemeManagementForm.action = "coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation=REFRESH_ALL&type=REFRESH_ALL"
             document.interfaceTemplateThemeManagementForm.target="bodyFrame";
				document.interfaceTemplateThemeManagementForm.submit();
			}
	}
	function refresh_onclick() {
                    if(document.interfaceTemplateThemeManagementForm.interface_id.value==''){
                    	alert('Please select one item');return false;}
                   else{
				doyou = confirm("Are you Sure to refresh selected item ?"); //Your question.
				if (doyou == true) {
				document.interfaceTemplateThemeManagementForm.method="post";
             document.interfaceTemplateThemeManagementForm.target="bodyFrame";
				document.interfaceTemplateThemeManagementForm.action = "coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation=REFRESH&interface_id="+document.interfaceTemplateThemeManagementForm.interface_id.value
				document.interfaceTemplateThemeManagementForm.submit();
				}
                   else{
				}
				}
	}
	function checkbox_onclick() {
 for (var i=0;i<document.interfaceTemplateThemeManagementForm.elements.length;i++){
	var e = document.interfaceTemplateThemeManagementForm.elements[i];
	if (e.type=='radio'){
		if (e.checked)
			hL(e);
		else
			dL(e);
	 }
 }
		var i = test();
		if(i>1) {
			for(var counter=0; counter<document.interfaceTemplateThemeManagementForm.checkbox.length; counter++) {
				if(document.interfaceTemplateThemeManagementForm.checkbox[counter].checked) {
				document.interfaceTemplateThemeManagementForm.interface_id.value = document.interfaceTemplateThemeManagementForm.checkbox[counter].value;
					break;
				}
			}
		}
		if(i==1) {
			document.interfaceTemplateThemeManagementForm.interface_id.value = document.interfaceTemplateThemeManagementForm.checkbox.value;
		}
	}
	function test() {
		var index = 0;
		for (var i=0;i<document.interfaceTemplateThemeManagementForm.elements.length;i++){
			var e = document.interfaceTemplateThemeManagementForm.elements[i];
			if (e.type=='radio'){
				index++;
			}
		}
		return index;
	}
	function applicationTemplateThemeChangeProcess() {
	document.interfaceTemplateThemeManagementForm.method="post";
	document.interfaceTemplateThemeManagementForm.action = "coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation=APPLICATION_TEMPLATE_THEME_CHANGE&interface_id="+document.interfaceTemplateThemeManagementForm.interface_id.value;
 window.open("","application_template_theme_change","width=500,height=230,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no");
 document.interfaceTemplateThemeManagementForm.target="application_template_theme_change";
 document.interfaceTemplateThemeManagementForm.submit(); 
 }