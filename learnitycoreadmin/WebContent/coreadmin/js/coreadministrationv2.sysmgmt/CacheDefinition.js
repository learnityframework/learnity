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
				alert('You must select at least one Template');
				return false;
			}
		}
		if(i==1)  {
			if (document.frm.checkbox.checked == true) {
				filledIn = true;
			}
			if (filledIn == false){
				alert('You must select at least one Template');
				return false;
			}
		}
		return true;
	}

	function addCourse_onclick(){
		if(validate()){
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.CacheDefinition?prmAddModify=0"
			document.frm.submit();
		}
	}
	function modifyCourse_onclick() {
		var i = test();
		if(i>1) {
			if((checkEntries()) && (validate())){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.CacheDefinition?prmAddModify=1&cacheid="+document.frm.checkbox[index].value;
				document.frm.submit();
			}
		}
		if(i==1) {
			if(checkEntries()){
				document.frm.method="post";
				document.frm.action = "coreadministrationv2.sysmgmt.CacheDefinition?prmAddModify=1&cacheid="+document.frm.checkbox.value;
				document.frm.submit();
			}
		}
	}
	function deleteCourse_onclick() {
		var i = test();
		if(i>1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Cache?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.CacheDefinition?prmAddModify=2&cacheid="+document.frm.checkbox[index].value;
			}
			else {
			}
		}
		if(i==1) {
			if(!checkEntries())
				return false;
			doyou = confirm("Are you Sure to Delete The Selected Cache?"); //Your question.

			if (doyou == true) {
				location.href = "coreadministrationv2.sysmgmt.CacheDefinition?prmAddModify=2&cacheid="+document.frm.checkbox.value;
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
				document.frm.cachename.value=document.frm.hiddenname[counter].value;
					document.frm.cache_type.value=document.frm.hiddentype[counter].value;
					document.frm.maxelem.value=document.frm.hiddenmaxelem[counter].value;
					document.frm.overflowtodisk.value=document.frm.hiddenoverflow[counter].value;
					document.frm.timetolive.value=document.frm.hiddentimelive[counter].value;
					document.frm.timetoidle.value=document.frm.hiddentimeidle[counter].value;
					document.frm.eternal.value=document.frm.hiddeneternal[counter].value;
					document.frm.evictionpolicy.value=document.frm.hiddeneviction[counter].value;
					document.frm.diskstorepath.value=document.frm.hiddendiskstorepath[counter].value;
					document.frm.timetothread.value=document.frm.hiddenthreadinterval[counter].value;
					document.frm.diskpersistent.value=document.frm.hiddendiskpersistent[counter].value;
					document.frm.default_cache.value=document.frm.hiddendefault[counter].value;
					break;
				}
			}
		}
		if (i==1) {
			if(document.frm.checkbox.checked) {
				document.frm.cachename.value=document.frm.hiddenname.value;
					document.frm.cache_type.value=document.frm.hiddentype.value;
					document.frm.maxelem.value=document.frm.hiddenmaxelem.value;
					document.frm.overflowtodisk.value=document.frm.hiddenoverflow.value;
					document.frm.timetolive.value=document.frm.hiddentimelive.value;
					document.frm.timetoidle.value=document.frm.hiddentimeidle.value;
					document.frm.eternal.value=document.frm.hiddeneternal.value;
					document.frm.evictionpolicy.value=document.frm.hiddeneviction.value;
					document.frm.diskstorepath.value=document.frm.hiddendiskstorepath.value;
					document.frm.timetothread.value=document.frm.hiddenthreadinterval.value;
					document.frm.diskpersistent.value=document.frm.hiddendiskpersistent.value;
					document.frm.default_cache.value=document.frm.hiddendefault.value;
			}
		}
	}

	function validate(){
		if(!fnCheckNull(document.frm.cachename.value," Cache Name")){
			document.frm.cachename.focus();
			return false;
		}
		if(!fnCheckNull(document.frm.maxelem.value," Maximum Element")){
			document.frm.maxelem.focus();
			return false;
		}
		if(!fnCheckNull(document.frm.timetolive.value," Time to Live")){
			document.frm.timetoliveseconds.focus();
			return false;
		}
		if(!fnCheckNull(document.frm.timetoidle.value," Time to Idle")){
			document.frm.timetoliveseconds.focus();
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