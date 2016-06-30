
    function attach_onclick() {
			var sMessage = "",
			oFiles = document.getElementById("ForumAttachment_fileupload").files,
			nFiles = oFiles.length;
		for (var nFileId = 0; nFileId < nFiles; nFileId++) {
					uploadFile(oFiles[nFileId]);
		};		
    }

     function done_onclick(){
		thread_onload_click(); //defined in Thread interfacefragment
		closeAttachment(); //defined in Thread interfacefragment
     }

	 
     function onload_click(){
		 
		 var fileInput = document.getElementById("ForumAttachment_fileupload");
		 fileInput.setAttribute("multiple","multiple");
		 
/*	     ForumUtil.ShowAttachment(click_onload);
	     function click_onload(data) {
		     setValue('ForumAttachment_step3',data);
	     }
*/     }   

	function listfiles(){
		 
		var sFileInfo = "";
			oFiles = document.getElementById("ForumAttachment_fileupload").files,
			nFiles = oFiles.length;
		for (var nFileId = 0; nFileId < nFiles; nFileId++) {
				sFileInfo += "File Name: " + oFiles[nFileId].name + " File Size: " + oFiles[nFileId].size + " bytes <br>";
		}
		document.getElementById("ForumAttachment_step3").innerHTML = sFileInfo;
	}
  
	function uploadFile(file){
		var url = './learnityforum.UploadForumAttachment';
		var xhr = new XMLHttpRequest();
		var fd = new FormData();
		xhr.open("POST", url, true);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				// Every thing ok, file uploaded
				console.log(xhr.responseText); // handle response.
				document.getElementById("ForumAttachment_message").innerHTML += xhr.responseText + "<br>";
			}
		};
		fd.append("upload_file", file);
		xhr.send(fd);
	}

onload_click();	