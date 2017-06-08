function CCA1(x){
	if (document.frm.checkbox[x].checked)
		hL(document.frm.checkbox[x]);
	else
			dL(document.frm.checkbox[x]);
	}
		
function test() {
	var index = 0;
	for (var i=0;i<document.frm.elements.length;i++){
		var e = document.frm.elements[i];
		if (e.type=='radio'){
			index++;
			}
			}
		return index;
	}
function findRow(){
	for (var counter=0; counter<document.frm.checkbox.length; counter++) {
		if (document.frm.checkbox[counter].value == groupId) {
			rowId = counter;
			}
		}
	return true;
	}
function checkEntries(itemType){
	var filledIn = false;
	var n = test();
	if(n==1){
		if (document.frm.checkbox.checked == true) {
			filledIn = true;
			index = 0;
			}
		if (filledIn == false){
   		    alert('You must select at least one');
			return false;
			}
		}
	if(n>1){
		for (var counter=0; counter<document.frm.checkbox.length; counter++)
		if (document.frm.checkbox[counter].checked == true) {
			filledIn = true;
			index = counter;
			}
		if (filledIn == false){
			alert('You must select at least one');
			return false;
			}
		}
	return true;
	}
	
function add_onclick() {
	document.frm.method="post";
	document.frm.action = "./coreadministrationv2.sysmgmt.ResorceApplicationTemplate?prmAddModify=0";
	document.frm.encoding = "multipart/form-data";
	document.frm.submit();
	}
	
function uploadLayout_onclick() {
	var i = test();
	if(checkEntries('file')){
		 if(i>=1) {
		document.frm.method="post";
	    if(document.frm.type1.value=='Resources')
		document.frm.action = "coreadministrationv2.sysmgmt.ResourceApplicationTemplate?prmAddModify=0&template_id="+document.frm.template_id.value+"&type1="+document.frm.type1.value+"&file_name="+document.frm.selectedfilename.value+"&delivery_mode="+document.frm.selecteddeliverymode.value+"&asset_path="+document.frm.selectedassetpath.value;   
	    if(document.frm.type1.value=='Templatexml')
			document.frm.action = "coreadministrationv2.sysmgmt.ResourceApplicationTemplate?prmAddModify=0&template_id="+document.frm.template_id.value+"&type1="+document.frm.type1.value;
		document.frm.encoding = "multipart/form-data";
		document.frm.submit();
		
		 }
		}
	}

function viewLayout_onclick() {
	document.frm.method="post";
	document.frm.target="viewLayout";
	 if(document.frm.type1.value=='Templatexml')
	document.frm.action = "manageFileContent?operation=view&template_id="+document.frm.template_id.value+"&type1=template";
	 if(document.frm.type1.value=='Resources')
		 document.frm.action = "manageFileContent?operation=view&template_id="+document.frm.template_id.value+"&type1=templateresource&file_name="+document.frm.selectedfilename.value+"&delivery_mode="+document.frm.selecteddeliverymode.value+"&asset_path="+document.frm.selectedassetpath.value;
    window.open("","viewLayout","width=700,height=680,status=yes,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
	document.frm.submit();
}
	
function download_onclick() {
	var i = test();
	if(i>1) {
		
	document.frm.method="post";
	 if(document.frm.type1.value=='Templatexml')
	document.frm.action = "coreadministrationv2.sysmgmt.DownloadResourceTemplate?template_id="+document.frm.template_id.value+"&type1=template";
	 if(document.frm.type1.value=='Resources')
		 document.frm.action = "coreadministrationv2.sysmgmt.DownloadResourceTemplate?template_id="+document.frm.template_id.value+"&type1=templateresource&filename="+document.frm.selectedfilename.value+"&delivery_mode="+document.frm.selecteddeliverymode.value+"&asset_path="+document.frm.selectedassetpath.value;
	 document.frm.submit();
	}
	if(i==1) {
		
		document.frm.method="post";
		 if(document.frm.type1.value=='Templatexml')
		document.frm.action = "coreadministrationv2.sysmgmt.DownloadResourceTemplate?template_id="+document.frm.template_id.value+"&type1=template";
		 if(document.frm.type1.value=='Resources')
			 document.frm.action = "coreadministrationv2.sysmgmt.DownloadResourceTemplate?template_id="+document.frm.template_id.value+"&type1=templateresource&delivery_mode="+document.frm.selecteddeliverymode.value;
		 document.frm.submit();
		}
}
/*					
function validate(){
	if(!fnCheckNull(document.frm.styleid.value,\"Group Id\")){
     	document.frm.styleid.focus();
		return false;
		}
	return true;
	}
	*/
function type_onchange() {
	document.frm.method="post";
	document.frm.action="./coreadministrationv2.sysmgmt.ResourceApplicationTemplate?";
	document.frm.submit();
	}
	
function checkbox_onclick() {
	var i = test();
	if(i>1) {
	for(var counter=0; counter<document.frm.checkbox.length; counter++) {
		if(document.frm.checkbox[counter].checked) {
		document.frm.template_id.value = document.frm.checkbox[counter].value;
		document.frm.selectedfilename.value = document.frm.filename[counter].value;
		document.frm.selecteddeliverymode.value = document.frm.deliverymode[counter].value;
		document.frm.selectedassetpath.value = document.frm.assetpath[counter].value;
		break;
		}
	  }
	}
	if(i==1) {
	document.frm.template_id.value = document.frm.checkbox.value;
    //document.frm.selectedfilename.value = document.frm.hiddentitle.value;
    document.frm.selecteddeliverymode.value = document.frm.deliverymode.value;
    document.frm.selectedassetpath.value = document.frm.assetpath.value;
	}
  }
  
 function load() {
	if (window.parent.leftFrame1 != null) {
		window.parent.leftFrame1.location.reload();
		}
 }