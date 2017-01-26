var index = 0;
	var rowId = 0;
 var program_comp_ident=0;
	function findRow(){
		for (var counter=0; counter<document.frm.checkbox.length; counter++) {
			if (document.frm.checkbox[counter].value == user) {
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

	function add_onclick(){
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=0";
			document.frm.submit();
	}
	function modify_onclick() {
		var i = test();
		if(i>1) {
			if((checkEntries()) && (validate())){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user="+document.frm.id.value;
				document.frm.submit();
			}
		}
		if(i==1) {
			if(checkEntries()){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user="+document.frm.id.value;
				document.frm.submit();
			}
		}
	}
	function delete_onclick() {
		var i = test();
		if(i>1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Hierarchy?"); //Your question.

			if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user="+document.frm.id.value;
				document.frm.submit();
			}
			else {
			}
		}
		if(i==1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Program Hierarchy?"); //Your question.

			if (doyou == true) {
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user="+document.frm.id.value;
				document.frm.submit();
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
                             document.frm.content.value=document.frm.hcontentid[counter].value;
                             document.frm.layout.value= document.frm.hlayout[counter].value;
                             document.frm.style.value=document.frm.hstyle[counter].value;
                             document.frm.behaviour.value=document.frm.hbehaviour[counter].value;
					break;
				}
			}
		}
		if (i==1) {
			if(document.frm.checkbox.checked) {
                             document.frm.content.value=document.frm.hcontentid.value;
                             document.frm.layout.value= document.frm.hlayout.value;
                             document.frm.style.value=document.frm.hstyle.value;
                             document.frm.behaviour.value=document.frm.hbehaviour.value;
			}
		}
	}

	function checkID(){
		var i = test();
		if(i>1) {
			if(document.frm.checkbox[index].value!=document.frm.Program.value){
				alert("You cannot change the Program");
				document.frm.Program.value=document.frm.checkbox[index].value;
			}
		} 
		if(i==1) {
			if(document.frm.checkbox.value!=document.frm.Program.value){
				alert("You cannot change the Program");
				document.frm.Program.value=document.frm.checkbox.value;
			}
		}
		return true;
	}
	function validate(){
		if(!fnCheckZero(document.frm.user.value," ProgramUnit")){
			document.frm.user.focus();
			return false;
		}
		return true;
	}
	function select_user_onchange() {
		document.frm.method="post";
		document.frm.action="coreadministrationv2.sysmgmt.InterfaceRoleManagement?role="+document.frm.role.value;
		document.frm.submit();
	}
 function load() {
	}
	function newInterfaceRole() {
	document.frm.method="post";
 document.frm.action = "coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1 ";
 window.open("","newInterfaceRole","width=540,height=305,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no");
 document.frm.target="newInterfaceRole";
 document.frm.submit(); 
 }