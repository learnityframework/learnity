
function getValue(variable)
{ 
var v1 = dwr.util.getValue(variable);
 return v1;
}
function setValue(variable,data)
{
dwr.util.setValue(variable, data, { escapeHtml:false});
}

function setFragment(part_id,data)
{
	
	var e=data;
	//alert(data);
	var start=e.indexOf("<BODY>");
	var end=e.indexOf("</BODY>");
	var subcontent=e.substring(start+6,end);
	//alert(subcontent);
	var x=document.getElementById(part_id);
	$(x).html(subcontent);
	
}

function setReloadGrid(variable)
{
	$("#"+variable).trigger('reloadGrid');			
}


function  setDropDown(partid,value)
{
	var x=document.getElementById(partid);
	$(x).html("<option value='0'>Choose One</option>"+value);
}

function getSelectedRow(partid,row_id)
{
	var sr=jQuery("#"+partid).getGridParam('selrow');
	rowdat=jQuery("#"+partid).getRowData(sr);
	var v1=eval('rowdat.'+row_id);
	return v1;
}
function getSelectedTreeNode(variable)
{
	var dtnode = $("#"+variable+"").dynatree("getSelectedNode");		
	return dtnode;		
}


function validateform(variable)
{
	var i='validator'+variable;
	var c =eval(i+'.form()');	
	return c;		
}


function CallInterface(IID)
{
	location.href='./interfaceenginev2.PortalServlet?IID='+IID;
}

function setWindowInterface(IID,part_id)
{
	document.getElementById(part_id).src='../servlet/interfaceenginev2.PortalServlet?IID='+IID;
}




function functionhover(id)
{
	jQuery("#"+id).hover(
			function () 
	      {
				$("#"+id).css('text-decoration','underline');
			},
			
			function () 
			{
				$("#"+id).css('text-decoration','underline');
			}
	);

}

//DONE BY PARTHA
// function resetGrid(interfaceid,partid)
// {
// 	
// 	var page_num = '1';
// 	var sidx_num = jQuery("#"+partid).getGridParam("sortname");
// 	
// 	var sord_num = jQuery("#"+partid).getGridParam("sortorder");
// 
// 	var search_string = "false";
// 	var row_num = jQuery("#"+partid).getGridParam("rowNum");
// 	
// 	var url = './interfaceenginev2.xmlcreator';
// 	HTTPPost(url,{interface_id:interfaceid,part_id:partid,_search:search_string,rows:row_num,page:page_num,sidx:sidx_num,sord:sord_num},function(data){
// 		setGridDataFromXml(partid,data)
// 	},'xml');
// 	 
// }


function getGridPageNoFromSession(interfaceid,partid)
{
	PortalEngine.getFromSessionForReset(interfaceid,partid,'page_no',setPageNoFromReset);
}

function setPageNoFromReset(data)
{
	var page_num;
	var resetdetails=new Array(3);
	resetdetails=data;
	var grid_page_no=resetdetails[2];
	var partid=resetdetails[1];
	var interfaceid=resetdetails[0];
	
	
	if(grid_page_no==null)
	{
		page_num=jQuery("#"+partid).getGridParam("page");
	}
	else
	{
		page_num=grid_page_no;
	}
	var sidx_num = jQuery("#"+partid).getGridParam("sortname");
	var sord_num = jQuery("#"+partid).getGridParam("sortorder");
	var search_string = "false";
	var row_num = jQuery("#"+partid).getGridParam("rowNum");
	var url = './interfaceenginev2.xmlcreator?search='+search_string;
	HTTPPost(url,{interface_id:interfaceid,part_id:partid,_search:search_string,rows:row_num,page:page_num,sidx:sidx_num,sord:sord_num},function(data){
		setGridDataFromXml(partid,data)
	},'xml');
	 
}



function resetGrid(interfaceid,partid)
{
	getGridPageNoFromSession(interfaceid,partid);
}



function HTTPPost(url,postDataArray,callbackfunction,expectedreturntype)
{
	jQuery.post(url,postDataArray,callbackfunction,expectedreturntype);
	
}

function HTTPGet(url,postDataArray,callbackfunction,expectedreturntype)
{
	jQuery.get(url,postDataArray,callbackfunction,expectedreturntype);
	
}


function setGridDataFromXml(gridid,data)
{
		
	jQuery("#"+gridid)[0].addXmlData(data);

}

function setXmlGridData(gridid,data)
{
 	//alert(data);
	//var rowdata=eval(data);
	jQuery("#"+gridid)[0].addXmlData(data);
	setReloadGrid(gridid);
}

function setArrayGridRowData(gridid,content)
{
	var len=content.length;
	var c=new Array(len);
	c=content;
	var rowdata;
	var counter=1;
	for(u=0;u<c.length;u++)
	{
		rowdata=c[u];
		var datastring = eval( "(" + rowdata + ")" );
		//$("#"+gridid).addRowData("\""+counter+"\"",datastring);
		$("#"+gridid).addRowData(counter,datastring);

		counter++;
	}
	setReloadGrid(gridid);
}

// function setArrayGridData(gridid,len,content)
// {
// 	var c=new Array(len);
// 	c=content;
// 	var rowdata;
// 	rowdata1=c[0];
// 	var myObj = eval( "(" + rowdata1 + ")" );
// 	$("#"+gridid).addRowData("1",myObj);
// }

function setArrayGridData(gridid,col_id,content)
{
	var c=content;
	c=content;
// 	alert(content);
	var rowdata=eval(c);
	$("#"+gridid).addRowData(col_id,rowdata);
	setReloadGrid(gridid);
}

function numbercheck(value)
{
	if(value!=='')
	{
		var IntegerCheck = /^-?\d+$/.test(value);
		if (IntegerCheck===false) 
		return [false,"Please insert integer value "];
		else 
			if(value>24 || value<0)
				return[false,"Please enter proper value."];
			else 				
				return [true,""];
	}
	else
		return [true,""];
}