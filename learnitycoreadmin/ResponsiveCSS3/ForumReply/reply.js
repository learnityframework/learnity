

function reply_onload_click(){

	ForumUtil.getReplySubject(showSubject);
			
	function showSubject(data) {
		setValue('ForumReply_replysubject',data);
	};
		
//	$("#textarea").replaceWith("<textarea class=\"post\" onkeyup=\"storeCaret(this);\"  onclick=\"storeCaret(this);\" tabindex=\"3\" name=\"textarea\" rows=\"14\" wrap=\"virtual\" cols=\"70\" onselect=\"storeCaret(this);\"></textarea>");

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

function AttachFiles() {
	CallInterface("ForumAttachment");
}


function updateForumMessages() {

	get_forum_messages(); //defined in Forum interface fragment
	
	ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
	);
}

reply_onload_click();

