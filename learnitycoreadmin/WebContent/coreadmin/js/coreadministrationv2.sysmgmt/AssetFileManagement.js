
var index = 0;
	var rowId = 0;
	function findRow(){
		for (var counter=0; counter<document.frm.checkbox.length; counter++) {
			if (document.frm.checkbox[counter].value == role_id) {
				rowId = counter;
			}
		}
	return true;
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
	function checkEntries(){
		var filledIn = false;
		var i = test();
		if(i>1) {
			for (var counter=0; counter<document.frm.checkbox.length; counter++)
				if (document.frm.checkbox[counter].checked == true) {
					index = counter;
					filledIn = true;
				}
			if (filledIn == false){
				alert('You must select at least one CSS File');
				return false;
			}
		}
		if(i==1)  {
			if (document.frm.checkbox.checked == true) {
				filledIn = true;
			}
			if (filledIn == false){
				alert('You must select at least one CSS File');
				return false;
			}
		}
		return true;
	}

	function addLayout_onclick() {
      if(document.frm.asset_type.value=='0' || document.frm.asset_type.value==' ')
      {
        alert('Please Select Type');return false;
      }
      else{
				document.frm.method="post";
				document.frm.action = "./coreadministrationv2.sysmgmt.AssetFileManagement?prmAddModify=0";
				document.frm.encoding = "multipart/form-data";
				document.frm.submit();
			  }
	}
	function download_onclick() {
		var i = test();
		if(i==1) {
			if(!checkEntries())
			return false;
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.DownloadAsset?asset_id="+document.frm.asset_id.value;
			document.frm.submit();
		}
		if(i>1) {
			if(!checkEntries())
			return false;
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.DownloadAsset?asset_id="+document.frm.asset_id.value;
			document.frm.submit();
	} 
	} 
	function deleteLayout_onclick() {
		var i = test();
		if(i>1) {
			if(!checkEntries())
			return false;
			doyou = confirm("Are you Sure to Delete The Selected  File?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.AssetFileManagement?prmAddModify=2&asset_id="+document.frm.asset_id.value;
			}
			else {
			}
		}
		if(i==1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected  File?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.AssetFileManagement?prmAddModify=2&asset_id="+document.frm.asset_id.value;
			}
			else {
			}
		}
		return true;
	}
	function checkbox_onclick() {
		var i = test();
		if(i>1) {
			for(var counter=0; counter<document.frm.checkbox.length; counter++) {
				if(document.frm.checkbox[counter].checked) {
				   document.frm.asset_id.value=document.frm.checkbox[counter].value;
				   document.frm.asset_type.value=document.frm.hiddentype[counter].value;
				   document.frm.asset_file_name.value=document.frm.cssfilename[counter].value;
					break;
				}
			}
		}
		if (i==1) {
			if(document.frm.checkbox.checked) {
				   document.frm.asset_id.value=document.frm.checkbox.value;
				   document.frm.asset_file_name.value=document.frm.cssfilename.value;
				   document.frm.asset_type.value=document.frm.hiddentype.value;
			}
		}
	}

 function load() {
		var i = test();
		if(i>1) {
 		findRow();
			document.frm.checkbox[rowId].checked=true;
			CCA();
		}
		if(i==1) {
			document.frm.checkbox.checked=true;
			CCA();
		}
	}