
 
    function onloadnotice_click() {
          
	
		dwr.engine.beginBatch();
		NoticePOJO.setNoticeHead(function(data) {
			setValue('NoticeBoardView_headingContent',data);
		});
	 
		NoticePOJO.setNoticeBody(function(data) {
			setValue('NoticeBoardView_noticeBodyContent',data);
 		});
		  
		 
		NoticePOJO.setNoticePostedBy(function(data) {
			setValue('NoticeBoardView_postedByContent',data);
		});
		NoticePOJO.setNoticePostedOn(function(data) {
			setValue('NoticeBoardView_postedOnContent',data);
		});
		NoticePOJO.setAttachFileName(function(data) {
 			
			if(data=="")
			{}
			else{
			setValue('NoticeBoardView_attachmentContent',data);
			}
		});

		dwr.engine.endBatch();
	
	  };
	  
  function done_onclick() {
		  closeNotice();
	  };
	  
	  
