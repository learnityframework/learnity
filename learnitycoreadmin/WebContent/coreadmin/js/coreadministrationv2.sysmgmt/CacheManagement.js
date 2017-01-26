 var index = 0;
	function clear_onclick() {
		document.frm.method="post";
		document.frm.action = "./coreadministrationv2.sysmgmt.CacheManagement?prmAddModify=0";
		document.frm.submit();
	}
	function shutdown_onclick() {
			document.frm.method="post";
			document.frm.action = "coreadministrationv2.sysmgmt.CacheManagement?prmAddModify=1";
			document.frm.submit();
	}

	function validate(){
		if(!fnCheckZero(document.frm.cacheName.value,"Cache Name")){
			document.frm.cacheName.focus();
			return false;
		}
		return true;
	}
	function cache_onchange() {
		document.frm.method="post";
		document.frm.action="coreadministrationv2.sysmgmt.CacheManagement;"
		document.frm.submit();
	}
	function refresh_onclick() {
		document.frm.method="post";
		document.frm.action="coreadministrationv2.sysmgmt.CacheManagement;"
		document.frm.submit();
	}