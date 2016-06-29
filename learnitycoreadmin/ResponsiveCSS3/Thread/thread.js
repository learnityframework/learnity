
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

function AttachFiles() {
//			location.href="./interfaceenginev2.PortalServlet?IID=forumattachment";
			CallInterface("ForumAttachment")
}


function attachment(data) {
				setValue('attachmentfile',data);
}

function thread_onload_click(){
//			dwr.engine.beginBatch();
			ForumUtil.ShowAttachment(attachment);
//			dwr.engine.endBatch();	      
}

function updateForum() {

	forum_onload_click(); //defined in Forum interface fragment
	
	ForumUtil.destroyAttachment(
		closeReply() //defined in Forum interface fragment
	);
}