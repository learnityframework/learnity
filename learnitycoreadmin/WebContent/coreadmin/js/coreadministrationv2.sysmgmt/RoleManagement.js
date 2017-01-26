var index = 0;
	var rowId = 0;
 var role_id = "null";
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
				alert('You must select at least one file type');
				return false;
			}
		}
		if(i==1)  {
			if (document.frm.checkbox.checked == true) {
				filledIn = true;
			}
			if (filledIn == false){
				alert('You must select at least one file type');
				return false;
			}
		}
		return true;
	}

	function addCourse_onclick(){
		if(validate()){
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.RoleManagement?prmAddModify=0"
			document.frm.submit();
		}
	}
	function modifyCourse_onclick() {
		var i = test();
		if(i>1) {
			if((checkEntries()) && (validate())){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.RoleManagement?prmAddModify=1&role_id="+document.frm.checkbox[index].value;
				document.frm.submit();
			}
		}
		if(i==1) {
			if(checkEntries()){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.RoleManagement?prmAddModify=1&role_id="+document.frm.checkbox.value;
				document.frm.submit();
			}
		}
	}
	function deleteCourse_onclick() {
		var i = test();
		if(i>1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Role?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.RoleManagement?prmAddModify=2&role_id="+document.frm.checkbox[index].value;
			}
			else {
			}
		}
		if(i==1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Role?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.RoleManagement?prmAddModify=2&role_id="+document.frm.checkbox.value;
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
				document.frm.roletitle.value=document.frm.hiddentitle[counter].value;
					document.frm.idesc.value=document.frm.hiddendesc[counter].value;
					break;
				}
			}
		}
		if (i==1) {
			if(document.frm.checkbox.checked) {
				document.frm.roletitle.value=document.frm.hiddentitle.value;
					document.frm.idesc.value=document.frm.hiddendesc.value;
			}
		}
	}

	function checkID(){
		var i = test();
		if(i>1) {
			if(document.frm.checkbox[index].value!=document.frm.UnitId.value){
				alert("You cannot change the Unit Id");
				document.frm.UnitId.value=document.frm.checkbox[index].value;
			}
		} 
		if(i==1) {
			if(document.frm.checkbox.value!=document.frm.UnitId.value){
				alert("You cannot change the Unit Id");
				document.frm.UnitId.value=document.frm.checkbox.value;
			}
		}
		return true;
	}
	function validate(){
		if(!fnCheckNull(document.frm.roletitle.value," Role Title")){
			document.frm.roletitle.focus();
			return false;
		}
		return true;
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