
function click_submit(){
			var topic_name=getValue("Thread_threadSubject");
			if(topic_name=="") {
				alert(" You must give a Subject ");
				return false;
			}
			var attachmentfile = getValue("Thread_attachmentfile");
			var message =  getValue("Thread_bodytextarea");
			
			ForumUtil.insertValues(topic_name,message,attachmentfile, updateForum);


}


function click_cancel(){
	if( confirm("Do you wish to quit?") ) {
		ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
		);
	}
}

function closeAttachment()
{
	popupContainer = document.getElementById("Thread_popupContainer");
	popupContainer.classList.remove("threadafterPopup");
	updateThreadAttachments();
};

function showAttachment(attachmentData)
{
	setFragment("Thread_popup",attachmentData);
	popupContainer = document.getElementById("Thread_popupContainer");
	popupContainer.classList.add("threadafterPopup");
};

function AttachFiles() {
		PortalEngine.getInterfaceFragment("LMSPortal","ForumAttachment",showAttachment);
}


function attachment(data) {
				setValue('Thread_attachmentfile',data);
}

function updateThreadAttachments(){
			ForumUtil.ShowAttachment(attachment);
}

function updateForum() {

	forum_onload_click(); //defined in Forum interface fragment
	
	ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
	);
}
