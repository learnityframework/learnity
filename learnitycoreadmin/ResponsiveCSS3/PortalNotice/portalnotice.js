function showNoticeGrid()
{
	NoticePOJO.getNotice(setNoticeData);
}

function setNoticeData(data)
{
	setArrayGridRowData("PortalNotice_NoticeGrid",data);
}

function lunchNotice(strnotice_id)
{
	NoticePOJO.setSessionNoticeId(strnotice_id);
	PortalEngine.getInterfaceFragment("LMSPortal","NoticeBoardView",showNotice);
}
 
function showNotice(noticeData)
{
	setFragment("PortalNotice_popup",noticeData);
	popupContainer = document.getElementById("PortalNotice_popupContainer");
	popupContainer.classList.add("noticeafterPopup");
}

function closeNotice()
{
	popupContainer = document.getElementById("PortalNotice_popupContainer");
	popupContainer.classList.remove("noticeafterPopup");
}

showNoticeGrid();