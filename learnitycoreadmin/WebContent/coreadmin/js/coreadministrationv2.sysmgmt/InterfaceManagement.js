 var index = 0;
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

	function checkEntries(){
		var filledIn = false;
		var n = test();
		if(n==1){
			if (document.frm.checkbox.checked == true) {
				filledIn = true;
				index = 0;
			}
			if (filledIn == false){
				alert('You must select one Style');
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
				alert('You must select  one Style');
				return false;
			}
		}
		return true;
	}

	function addLayout_onclick() {
            if(document.frm.type.value=='0' || document.frm.type.value==' '){
            alert('Please select Type');
             return false;
	         }
            else{
		document.frm.method="post";
		document.frm.action = "./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=0&interface_id="+document.frm.interface_id.value+"&type="+document.frm.type.value;
		document.frm.encoding = "multipart/form-data";
     document.frm.target="bodyFrame";
		document.frm.submit();
	}
	}
	function showLayout_onclick() {
		var i = test();
		if(i==1) {
         if(document.frm.type.value=='InterfaceFragment' || document.frm.type.value=='Interface'){
					document.frm.method="post";
			        document.frm.action = "coreadministrationv2.sysmgmt.ResourceInterface?interface_id="+document.frm.interface_id.value;
		        	window.open("","new11","width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no");
                 document.frm.target="new11";
			        document.frm.submit();
			}
           	else{
			        alert("Please select an 'Interface' or 'Interface Fragment'");
			}
		}
			if(i>1) {
         if(document.frm.type.value=='InterfaceFragment' || document.frm.type.value=='Interface'){
					document.frm.method="post";
			        document.frm.action = "coreadministrationv2.sysmgmt.ResourceInterface?interface_id="+document.frm.interface_id.value;
		        	window.open("","new11","width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no");
                 document.frm.target="new11";
			        document.frm.submit();
			}
           	else{
			       alert("Please select an 'Interface' or 'Interface Fragment'");
			}
	   	}
	   }

	function download_onclick() {
		var i = test();
		if(i==1) {
                    if(document.frm.size.value=='0'){
                    alert('The Zip is Empty');return false;}
                   else{
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.DownloadInterface?interface_id="+document.frm.interface_id.value+"&filename="+document.frm.filename1.value+"&type="+document.frm.type.value;
             document.frm.target="bodyFrame";
			document.frm.submit();
		}
		}
		if(i>1) {
                    if(document.frm.size.value=='0'){
                    alert('The Zip is Empty');return false;}
                   else{
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.DownloadInterface?interface_id="+document.frm.interface_id.value+"&filename="+document.frm.filename1.value+"&type="+document.frm.type.value;
             document.frm.target="bodyFrame";
			document.frm.submit();
		}
	} 
	} 
	function deleteLayout_onclick() {
		var i = test();
		if(i==1) {
			if(checkEntries()){
				doyou = confirm("Are you Sure to Delete ?"); //Your question.
				if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&interface_id="+document.frm.interface_id.value+"&type="+document.frm.type.value;
             document.frm.target="bodyFrame";
				document.frm.submit();
				}
				else {
				}
			}
		}
		if(i>1) {
			for(var counter=0; counter<document.frm.checkbox.length; counter++) {
				if(document.frm.checkbox[counter].checked) {
			if(checkEntries()){
				doyou = confirm("Are you Sure to Delete The ?"); //Your question.
				if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&interface_id="+document.frm.interface_id.value+"&type="+document.frm.type.value;
             document.frm.target="bodyFrame";
				document.frm.submit();
				}
					else {
				}
				}
				}
			}
		}
	}
	function deleteAllLayout_onclick() {
				doyou = confirm("Are you Sure to Delete All ?"); //Your question.
				if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=4"
             document.frm.target="bodyFrame";
				document.frm.submit();
				}
				else {
				}
	}
	function refresh_onclick() {
		var i = test();
		if(i==1) {
                    if(document.frm.interface_id.value==''){
                    	alert('Please select one item');return false;}
                   else{
				doyou = confirm("Are you Sure to refresh selected item ?"); //Your question.
				if (doyou == true) {
					document.frm.method="post";
                 document.frm.target="bodyFrame";
					document.frm.action = "coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&interface_id="+document.frm.interface_id.value+"&filename="+document.frm.filename1.value+"&type="+document.frm.type.value;
					document.frm.submit();
				}
                   else{
				}
				}
		}
		if(i>1) {
                    if(document.frm.interface_id.value==''){
                    	alert('Please select one item');return false;}
                   else{
				doyou = confirm("Are you Sure to refresh selected item ?"); //Your question.
				if (doyou == true) {
				document.frm.method="post";
             document.frm.target="bodyFrame";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&interface_id="+document.frm.interface_id.value+"&filename="+document.frm.filename1.value+"&type="+document.frm.type.value;
				document.frm.submit();
				}
                   else{
				}
				}
		} 
	}

	function refreshAll_onclick() {
			doyou = confirm("Are you Sure to refresh all interface and interface fragements?"); 
			if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&type=REFRESH_ALL"
             document.frm.target="bodyFrame";
				document.frm.submit();
			}
	}
	function generateRoleXml_onclick() {
		document.frm.method="post";
		document.frm.action = "coreadministrationv2.sysmgmt.DownloadInterface?type=GenerateRoleXML"
             document.frm.target="bodyFrame";
		document.frm.submit();
	}
	function generateManifestXml_onclick() {
		document.frm.method="post";
		document.frm.action = "coreadministrationv2.sysmgmt.DownloadInterface?type=GenerateManifestXML"
             document.frm.target="bodyFrame";
		document.frm.submit();
	}
	function download_allInterface() {
		document.frm.method="post";
		document.frm.action = "coreadministrationv2.sysmgmt.DownloadInterface?type=DownloadAll"
             document.frm.target="bodyFrame";
		document.frm.submit();
	}
	function validate(){
		if(!fnCheckNull(document.frm.styleid.value,"Group Id")){
			document.frm.styleid.focus();
			return false;
		}
		return true;
	}
	function checkbox_onclick() {
		var i = test();
		if(i>1) {
			for(var counter=0; counter<document.frm.checkbox.length; counter++) {
				if(document.frm.checkbox[counter].checked) {
				document.frm.interface_id.value = document.frm.checkbox[counter].value;
			        document.frm.filename1.value = document.frm.hiddenname[counter].value;
			        document.frm.type.value = document.frm.hiddentype[counter].value;
			        document.frm.size.value = document.frm.hiddensize[counter].value;
			        document.frm.imagepath.value = document.frm.hiddenimage[counter].value;
                        if(document.frm.hiddencss[counter].value=='yes'){
									document.frm.inlinecss.checked=true;
								}
                        if(document.frm.hiddenjs[counter].value=='yes'){
									document.frm.inlinejs.checked=true;
								}
					break;
				}
			}
		}
		if(i==1) {
			document.frm.interface_id.value = document.frm.checkbox.value;
			document.frm.filename1.value = document.frm.hiddenname.value;
			document.frm.type.value = document.frm.hiddentype.value;
			document.frm.size.value = document.frm.hiddensize.value;
			document.frm.imagepath.value = document.frm.hiddenimage.value;
                        if(document.frm.hiddencss.value=='yes'){
									document.frm.inlinecss.checked=true;
								}
                        if(document.frm.hiddenjs.value=='yes'){
									document.frm.inlinejs.checked=true;
								}
		}
	}

	function load() {
		if (window.parent.leftFrame1 != null) {
			window.parent.leftFrame1.location.reload();
		}
 }