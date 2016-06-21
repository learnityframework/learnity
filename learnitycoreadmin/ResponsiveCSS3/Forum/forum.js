function callbackfunction(dtnodevar)
{
	ForumUtil.detailmessage1(dtnodevar.data.key,function(data)
	{
		setValue('Forum_lowerportion2',data);
	});
}

function f12(dtnode)
{
	ForumUtil.setSubject(dtnode.data.key, callbackfunction(dtnode));
}
	
function show_thread(threadid,replyid){
	ForumUtil.setParam(threadid,replyid);
	GenerateTree();
	
	var view_type_id=getValue('Forum_middledropdown1');
	
	if(view_type_id=='0' || view_type_id=='')
	{
		view_type_id = '2';
	}
	else
	{
		view_type_id = view_type_id;
	} 
	
	if(view_type_id=='1'){

		var treediv=document.getElementById("Forum_lowerportion1");
		treediv.style.visibility="visible";
		
		ForumUtil.detailmessage2(function(data){setValue('Forum_lowerportion2',data);});
		
// 		$('#lowerportion1').show();
		$('#Forum_lowerportion1').css('left','70px').css('top','320px');
		$('#Forum_lowerportion2').css('top','420px').css('height','200px');
		GenerateTree();
	}
	else{

		var treediv=document.getElementById("Forum_lowerportion1");
		treediv.style.visibility="hidden";
		ForumUtil.detailmessage(function(data){setValue('Forum_lowerportion2',data);});
		$('#Forum_lowerportion2').css('top','320px').css('height','300px');
	}
}


	function reply_onclick(){
		var threadid="1";
		var subject="null";
		var strforum="1111";
		var replyid="1";
		var forumname="Football";
		var view_type="2";
		document.forumform.method="post";
		document.forumform.action = "./forum.discussionforum.PostReply?&thread_id="+threadid+"&forum_id="+strforum+"&replyId="+replyid+"&strforum="+forumname+"&subject="+subject+"&view_type="+view_type+"";
		document.forumform.target="page1";
		document.forumform.submit();
	}
	
	function flat_reply(replyId){
		ForumUtil.setReplyThreadId(replyId, function(){location.href = "./interfaceenginev2.PortalServlet?IID=ForumReply"});
	}
	
     function populate(subject) {
			document.forumform.method="post";
			document.forumform.action = "./forum.discussionforum.DiscussionForum?&subject="+encodeURIComponent(subject);
			document.forumform.submit();
	} 

	function start_onclick(){
		CallInterface("Thread");
	}

	function post_onclick(){
		var strforum="1111";
		var forumname="Football";
		var view_type="2";
		document.forumform.method="post";
		document.forumform.action = "./forum.discussionforum.PostReply?&forum_id="+strforum+"&strforum="+forumname+"&view_type="+view_type+"";
		window.open("","page2","width=650,height=620,status=yes,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
		document.forumform.target="page2";
		document.forumform.submit();
	}

	function next_onclick(){
		var strforum="Football";
		var thread_id="1";
		var message_id="null";
		document.forumform.method="post";
		document.forumform.action="./forum.discussionforum.Next?strforum="+strforum+"&thread_id="+thread_id+"&message_id="+message_id+"";
		document.forumform.submit();
	}

	function prev_onclick(){
		var strforum="1111";
		var thread_id="1";
		var message_id="null";
		document.forumform.method="post";
		document.forumform.action="./forum.discussionforum.Previous?strforum="+strforum+"&thread_id="+thread_id+"&message_id="+message_id+"";
		document.forumform.submit();
	}

	function quote_onclick(){
		var subject="null";
		var replyid="1";
		var threadid="1";
		var strforum="1111";
		var forumname="Football";
		var view_type="2";
		document.forumform.method="post";
		document.forumform.action = "./forum.discussionforum.PostReply?thread_id="+threadid+"&replyId="+replyid+"&quote=1&forum_id="+strforum+"&strfname="+forumname+"&view_type="+view_type+"&subject="+subject+"";
		window.open("","page2","width=650,height=620,status=yes,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
		document.forumform.target="page2";
		document.forumform.submit();
	}

	function flat_quote(replyId){
	
		 ForumUtil.setQuoteSubject(replyId);
		 location.href = "./interfaceenginev2.PortalServlet?IID=ForumReply";
	}
	
	
 	function quit_onclick() {
		ForumUtil.clearForumDynamicInfo();
		closeForum(); 		
	}
 	
 	
 	
	function forum_onload_click(){
		dwr.engine.beginBatch();
		ForumUtil.displayforumname(forum_display);
		ForumUtil.forumGrid2(gridforum);	
		
		function forum_display(data) {
			setValue('Forum_forumheader2',data);
		}
		function gridforum(data) {
			setValue('Forum_textbox1',data);
		}

		ForumUtil.detailmessage(function(data){setValue('Forum_lowerportion2',data);});
				
		dwr.engine.endBatch();	   
	}
	
	function clear(){ForumUtil.clearForumDynamicInfo();}
			