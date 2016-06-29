function showForumGrid()
{
	clearGridData( "PortalForum_ForumGrid" );
	Portal.forumGrid(setForumGridData);
};

function setForumGridData(forumGridData)
{
	setArrayGridRowData("PortalForum_ForumGrid",forumGridData);
};


function showForum(forumData)
{
	setFragment("PortalForum_popup",forumData);
	popupContainer = document.getElementById("PortalForum_popupContainer");
	popupContainer.classList.add("afterPopup");
};

function closeForum()
{
	popupContainer = document.getElementById("PortalForum_popupContainer");
	popupContainer.classList.remove("afterPopup");
};

function forum(strforum,strfname)
{
	Portal.setForumId(strforum);
	PortalEngine.getInterfaceFragment("LMSPortal","Forum",showForum);
}

showForumGrid();