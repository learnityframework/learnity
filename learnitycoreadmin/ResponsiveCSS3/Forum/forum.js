	function showReply(replyData)
	{
		setFragment("Forum_popup",replyData);
		popupContainer = document.getElementById("Forum_popupContainer");
		popupContainer.classList.add("forumafterPopup");
	};

	function getReply()
	{
		PortalEngine.getInterfaceFragment("LMSPortal","ForumReply",showReply);
	}

	function flat_reply(replyId){
		ForumUtil.setReplyThreadId(replyId,getReply);
	}


	function closeReply()
	{
		popupContainer = document.getElementById("Forum_popupContainer");
		popupContainer.classList.remove("forumafterPopup");
	};

	
	function showThread(threadData)
	{
		setFragment("Forum_popup",threadData);
		popupContainer = document.getElementById("Forum_popupContainer");
		popupContainer.classList.add("forumafterPopup");
	};

	function start_onclick(){
		PortalEngine.getInterfaceFragment("LMSPortal","Thread",showThread);
	}


 	function quit_onclick() {
		if( confirm("Do you wish to quit?") ) {
				ForumUtil.clearForumDynamicInfo();
				showForumGrid();    //defined in PortalForum interface fragment
				closeForum(); 		//defined in PortalForum interface fragment
		}
 	}
 	
 	
	function gridforum(data) {
			setValue('Forum_textbox1',data);
	}

	function get_forum_messages(){
		ForumUtil.forumGrid2(gridforum);	
		ForumUtil.detailmessage(function(data){setValue('Forum_lowerportion2',data);});		
	}

	function forum_display(data) {
			setValue('Forum_forumheader2',data);
	}
	

	function forum_onload_click(){
		dwr.engine.beginBatch();
		ForumUtil.displayforumname(forum_display);
		
		get_forum_messages();
				
		dwr.engine.endBatch();	   
	}


forum_onload_click();	