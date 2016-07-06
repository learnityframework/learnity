
    function attach_onclick() {
		var sMessage = "",
		oFiles = document.getElementById("ForumAttachment_fileupload").files,
		nFiles = oFiles.length;
		for (var nFileId = 0; nFileId < nFiles; nFileId++) {
					uploadFile(oFiles[nFileId]);
		};	
		document.getElementById("ForumAttachment_attachfilebutton").blur();
    }

     function done_onclick(){
/*		if (typeof thread_onload_click == 'function') { 
				thread_onload_click(); //defined in Thread interfacefragment
		}; 
 		if (typeof updateReplyAttachments == 'function') { 
				updateReplyAttachments(); //defined in ForumReply interfacefragment
		}; 
 */		closeAttachment(); //defined in both Thread as well as ForumReply interfacefragments
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
			nFileSize = 0;
			nTotalUploadSize = 0;
			oFiles = document.getElementById("ForumAttachment_fileupload").files,
			nFiles = oFiles.length;
		for (var nFileId = 0; nFileId < nFiles; nFileId++) {
				nFileSize = Math.trunc((oFiles[nFileId].size)/1024);
				sFileInfo += "File Name: " + oFiles[nFileId].name + "   --------- File Size: " + nFileSize + " KB <br>";
				nTotalUploadSize += nFileSize;
		}
		var sTotalSizeInfo = "<br> Total upload size: " + Math.trunc(nTotalUploadSize/1024) + " MB <br>"
		document.getElementById("ForumAttachment_fileinfo").innerHTML = "<br>" + sFileInfo + sTotalSizeInfo;
	}
  
	
function uploadFile(file){
 		var url = './learnityforum.UploadForumAttachment';
		var xhr = new XMLHttpRequest();
		var fd = new FormData();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				// Every thing ok, file uploaded
				console.log(xhr.responseText); // handle response.
				document.getElementById("ForumAttachment_message").innerHTML += xhr.responseText + "<br>";
			}
		};
		xhr.upload.progressId = "progress_" + Math.floor((Math.random() * 100000));
		var node = document.createElement("DIV");
		node.setAttribute("id",xhr.upload.progressId);
		var progressNode = document.getElementById("ForumAttachment_progress");
		progressNode.appendChild(node);
 		xhr.upload.addEventListener("progress", function(event){
			var progress = Math.round(event.loaded / event.total * 100);
			document.getElementById(this.progressId).innerHTML = "Upload progress for file   " + file.name + ":     "+ progress + "%";
			}, false);
		xhr.open("POST", url, true);
		fd.append("upload_file", file);
		xhr.send(fd);
	}

	
onload_click();	