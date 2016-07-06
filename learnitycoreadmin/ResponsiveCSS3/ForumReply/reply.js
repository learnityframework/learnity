

function reply_onload_click(){

	ForumUtil.getReplySubject(showSubject);
			
	function showSubject(data) {
		setValue('ForumReply_replysubject',data);
	};
};


function click_submit(){
 	topic_name=getValue("ForumReply_replysubject");
 	message=getValue("ForumReply_bodytextarea");

	ForumUtil.submitReply(message,topic_name, updateForumMessages)
	
}


function click_cancel()
{
	if( confirm("Do you wish to quit?") ) {
		ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
		);
	}
}

function showAttachment(attachmentData)
{
	setFragment("ForumReply_popup",attachmentData);
	popupContainer = document.getElementById("ForumReply_popupContainer");
	popupContainer.classList.add("replyafterPopup");
};

function AttachFiles() {
		PortalEngine.getInterfaceFragment("LMSPortal","ForumAttachment",showAttachment);
}


function updateForumMessages() {

	get_forum_messages(); //defined in Forum interface fragment
	
	ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
	);
}

function closeAttachment()
{
	popupContainer = document.getElementById("ForumReply_popupContainer");
	popupContainer.classList.remove("replyafterPopup");
	updateReplyAttachments();
};

function attachment(data) {
				setValue('ForumReply_attachment',data);
}

function updateReplyAttachments(){
			ForumUtil.ShowAttachment(attachment);
}


reply_onload_click();

