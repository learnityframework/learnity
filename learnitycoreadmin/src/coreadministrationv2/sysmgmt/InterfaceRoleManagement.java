package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ecs.html.Body;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.apache.ecs.html.Title;

import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.LfServlet;
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.sysmgmt.xml.dto.manifest.InterfaceElement;
import coreadministrationv2.utility.TRExtension;
import coreadministrationv2.utility.TableExtension;

public class InterfaceRoleManagement extends LfServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = -1173999778119581324L;
	private static final String _ROLE_SELECT_NAME = "roleSelect";
	private static final String _INTERFACE_SELECT_NAME = "interfaceSelect";
	private static final String _LAYOUT_INPUT_NAME = "layoutInput";
	private static final String _STYLE_INPUT_NAME = "styleInput";
	private static final String _CONTENT_INPUT_NAME = "contentInput";
	private static final String _BEHAVIOUR_INPUT_NAME = "behaviourInput";
	private static final String _ROLE_NEW_ONCHANGE_FUNCTION = "roleNewOnChange()";
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        super.doGet(request, response);

    	String strAdminId = this.getLoggedInUserName(request);
    	PrintWriter out = response.getWriter();
    	String strPrmAddModDel = request.getParameter("prmAddModify");

    	
        	if (strPrmAddModDel!=null) {
	        	int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
		        switch(iPrmAddModify) {

		        	case 0:
			        	
		        		try {
		        			add(request, strAdminId, out);
		        			generateMainPage(request, strAdminId, out);
		        		} catch (ServletException e) {
		        			e.printStackTrace();
		        		}
			        	        break;
		        	case 1:
		        		
		        			generateNewPopUpPage(request, response, "");
		        		    break;
		        	case 2:
		        		
		        			String statusMessage=addInterfaceRole(request);
		        			generateNewPopUpPage(request, response, statusMessage);
		        		    break;
		        		
		        	
				}
			}else{
				generateMainPage(request, strAdminId, out);
			}
        
    	
    
   
        
        
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
    private void add(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
        	throws IOException, ServletException {
                String role = request.getParameter("role");
                String interfaceid = request.getParameter("interfaceid");
		String layout = request.getParameter("layout");
		String content = request.getParameter("content");
		String style = request.getParameter("style");
		String behaviour = request.getParameter("behaviour");
                DataBaseLayer.UpdateInterfaceRoleAssociation(role,interfaceid,layout,content,style,behaviour);
    }
     public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        	throws IOException, ServletException {
        	
        	/*
                String user = request.getParameter("user");
                String roleid = request.getParameter("roleid");
                String role = request.getParameter("role");
                DataBaseLayer.modifyuserrole(user,roleid,role,strModBy);*/
        
    }
    public void delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
        	// String user = request.getParameter("user");
               // String roleid = request.getParameter("roleid");
        	//System.out.println("................................user................."+user);
               
        	//System.out.println("................................functionid................"+functionid);
        	//DataBaseLayer.deleteuserrole(user,roleid);
        
    }
    private void generateMainPage(HttpServletRequest request,String strAdminId,PrintWriter out){
    	Pair<String, String> dateTime = this.retrieveDateTime();
    	String role=request.getParameter("role");
    	if(role==null)
    	{
    		role="";
    	}
    	String interfaceid=request.getParameter("interfaceid");
    	if(interfaceid==null)
    	{
    		interfaceid="";
    	}
    	String javaScript = "\n	var index = 0;"+
    			"\n	var rowId = 0;"+
    			//"\n var ProgramID = \""+strProgramID+"\";"+
    			"\n var program_comp_ident=0;"+
    			"\n	function findRow(){"+
    			"\n		for (var counter=0; counter<document.frm.checkbox.length; counter++) {"+
    			"\n			if (document.frm.checkbox[counter].value == user) {"+
    			"\n				rowId = counter;"+
    			"\n			}"+
    			"\n		}"+
    			"\n	return true;"+
    			"\n	}"+
    			"\n	function test() {"+
    			"\n		var index = 0;"+
    			"\n		for (var i=0;i<document.frm.elements.length;i++){"+
    			"\n			var e = document.frm.elements[i];"+
    			"\n			if (e.type=='radio'){"+
    			"\n				index++;"+
    			"\n			}"+
    			"\n		}"+
    			"\n		return index;"+
    			"\n	}"+
    			"\n	function checkEntries(){"+
    			"\n		var filledIn = false;"+
    			"\n		var i = test();"+
    			"\n		if(i>1) {"+
    			"\n			for (var counter=0; counter<document.frm.checkbox.length; counter++)"+
    			"\n				if (document.frm.checkbox[counter].checked == true) {"+
    			"\n					index = counter;"+
    			"\n					filledIn = true;"+
    			"\n				}"+
    			"\n			if (filledIn == false){"+
    			"\n				alert(\'You must select at least one file type\');"+
    			"\n				return false;"+
    			"\n			}"+
    			"\n		}"+
    			"\n		if(i==1)  {"+
    			"\n			if (document.frm.checkbox.checked == true) {"+
    			"\n				filledIn = true;"+
    			"\n			}"+
    			"\n			if (filledIn == false){"+
    			"\n				alert(\'You must select at least one file type\');"+
    			"\n				return false;"+
    			"\n			}"+
    			"\n		}"+
    			"\n		return true;"+
    			"\n	}"+
    			"\n"+
    			"\n	function add_onclick(){"+

        					"\n			document.frm.method=\"post\";"+
        					"\n			document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=0\";" + 
        					"\n			document.frm.submit();"+

        					"\n	}"+
        					"\n	function modify_onclick() {"+
        					"\n		var i = test();"+
        					"\n		if(i>1) {"+

							"\n			if((checkEntries()) && (validate())){"+
							"\n				document.frm.method=\"post\";"+

							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+
							"\n		}"+
							"\n		if(i==1) {"+

							"\n			if(checkEntries()){"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+

        					"\n		}"+
        					"\n	}"+


							"\n	function delete_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i>1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
							"\n			doyou = confirm(\"Are you Sure to Delete The Selected Hierarchy?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+
							"\n			else {"+
							"\n			}"+
							"\n		}"+
							"\n		if(i==1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
							"\n			doyou = confirm(\"Are you Sure to Delete The Selected Program Hierarchy?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+
							"\n			else {"+
							"\n			}"+

        					"\n		}"+
        					"\n		return true;"+
        					"\n	}"+



							"\n	function checkbox_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i>1) {"+
							"\n			for(var counter=0; counter<document.frm.checkbox.length; counter++) {"+
							"\n				if(document.frm.checkbox[counter].checked) {"+
							// "\n                             var select = document.frm.checkbox[counter].value;"+
							"\n                             document.frm.content.value=document.frm.hcontentid[counter].value;"+
							"\n                             document.frm.layout.value= document.frm.hlayout[counter].value;"+
							"\n                             document.frm.style.value=document.frm.hstyle[counter].value;"+
							"\n                             document.frm.behaviour.value=document.frm.hbehaviour[counter].value;"+
							"\n					break;"+
							"\n				}"+
							"\n			}"+
							"\n		}"+
							"\n		if (i==1) {"+
							"\n			if(document.frm.checkbox.checked) {"+
							"\n                             document.frm.content.value=document.frm.hcontentid.value;"+
							"\n                             document.frm.layout.value= document.frm.hlayout.value;"+
							"\n                             document.frm.style.value=document.frm.hstyle.value;"+
							"\n                             document.frm.behaviour.value=document.frm.hbehaviour.value;"+

							"\n			}"+
							"\n		}"+
							"\n	}"+
							"\n"+

        					"\n	function checkID(){"+
        					"\n		var i = test();"+
        					"\n		if(i>1) {"+
        					"\n			if(document.frm.checkbox[index].value!=document.frm.Program.value){"+
        					"\n				alert(\"You cannot change the Program\");"+
        					"\n				document.frm.Program.value=document.frm.checkbox[index].value;"+
        					"\n			}"+
        					"\n		} "+
        					"\n		if(i==1) {"+
        					"\n			if(document.frm.checkbox.value!=document.frm.Program.value){"+
        					"\n				alert(\"You cannot change the Program\");"+
        					"\n				document.frm.Program.value=document.frm.checkbox.value;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		return true;"+
        					"\n	}"+

        					"\n	function validate(){"+

   							"\n		if(!fnCheckZero(document.frm.user.value,\" ProgramUnit\")){"+
   							"\n			document.frm.user.focus();"+
   							"\n			return false;"+
   							"\n		}"+
   							"\n		return true;"+
   							"\n	}"+

   						"\n	function select_user_onchange() {"+

        					       "\n		document.frm.method=\"post\";"+

        					       "\n		document.frm.action=\"coreadministrationv2.sysmgmt.InterfaceRoleManagement?role=\"+document.frm.role.value;" + 

        					       "\n		document.frm.submit();"+
        					       "\n	}"+


   							"\n function load() {"+

							"\n	}"+
							"\n	function newInterfaceRole() {" + "\n	document.frm.method=\"post\";"
							+"\n document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1 \";"
							+ "\n window.open(\"\",\"newInterfaceRole\",\"width=540,height=305,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"
							+ "\n document.frm.target=\"newInterfaceRole\";\n document.frm.submit(); \n }" + "\n";


    	Option[] option1 = null;
    	Vector<Vector<String>> parentrole = DataBaseLayer.getRole();
    	if(parentrole==null) {
    		option1 = new Option[1];
    		option1[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option1 = new Option[parentrole.size()+1];
    		option1[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<parentrole.size(); i++) {
    			Vector<String> parentrolesub = parentrole.elementAt(i);
    			String roleid = (String) parentrolesub.elementAt(0);
    			String rolename = (String) parentrolesub.elementAt(1);
    			option1[i+1] = new Option(roleid).addElement(rolename);
    			if(role.equals(roleid))
    			{
    				option1[i + 1].setSelected(true);
    			}
    		}
    	}							



    	Option[] option2 = null;
    	Vector<Vector<String>> interfacev = DataBaseLayer.getInterface(role);
    	if(interfacev==null) {
    		option2 = new Option[1];
    		option2[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option2 = new Option[interfacev.size()+1];
    		option2[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<interfacev.size(); i++) {
    			Vector<String> interfacevsub = interfacev.elementAt(i);
    			String Interfaceid = (String) interfacevsub.elementAt(0);

    			String interfacetitle = (String) interfacevsub.elementAt(1);

    			option2[i+1] = new Option(Interfaceid).addElement(interfacetitle);
    			if(interfaceid.equals(Interfaceid))
    			{
    				option2[i + 1].setSelected(true);
    			}

    		}
    	}							

    	Option[] option3 = null;
    	Vector<Vector<String>> layoutv = DataBaseLayer.getLayout(interfaceid);
    	if(layoutv==null) {
    		option3 = new Option[1];
    		option3[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option3 = new Option[layoutv.size()+1];
    		option3[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<layoutv.size(); i++) {
    			Vector<String> layoutvsub = layoutv.elementAt(i);
    			String layoutid = (String) layoutvsub.elementAt(0);

    			String layouttitle = (String) layoutvsub.elementAt(1);

    			option3[i+1] = new Option(layoutid).addElement(layouttitle);


    		}
    	}								

    	Option[] option4 = null;
    	Vector<Vector<String>> contentv = DataBaseLayer.getContent(interfaceid);
    	if(contentv==null) {
    		option4 = new Option[1];
    		option4[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option4 = new Option[contentv.size()+1];
    		option4[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<contentv.size(); i++) {
    			Vector<String> contentvsub = contentv.elementAt(i);
    			String contentid = (String) contentvsub.elementAt(0);

    			String contenttitle = (String) contentvsub.elementAt(1);

    			option4[i+1] = new Option(contentid).addElement(contenttitle);


    		}
    	}								


    	Option[] option5 = null;
    	Vector<Vector<String>> stylev = DataBaseLayer.getStyle(interfaceid);
    	if(stylev==null) {
    		option5 = new Option[1];
    		option5[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option5 = new Option[stylev.size()+1];
    		option5[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<stylev.size(); i++) {
    			Vector<String> stylevsub = stylev.elementAt(i);
    			String styleid = (String) stylevsub.elementAt(0);

    			String styletitle = (String) stylevsub.elementAt(1);

    			option5[i+1] = new Option(styleid).addElement(styletitle);
    		}
    	}																

    	Option[] option6 = null;
    	Vector<Vector<String>> behaviourv = DataBaseLayer.getBehaviour(interfaceid);
    	if(behaviourv==null) {
    		option6 = new Option[1];
    		option6[0] = new Option("0").addElement("[Choose One]");
    	}
    	else {
    		option6 = new Option[behaviourv.size()+1];
    		option6[0] = new Option("0").addElement("[Choose One]");
    		for(int i=0; i<behaviourv.size(); i++) {
    			Vector<String> behaviourvsub =  behaviourv.elementAt(i);
    			String behaviourid = (String) behaviourvsub.elementAt(0);

    			String behaviourtitle = (String) behaviourvsub.elementAt(1);

    			option6[i+1] = new Option(behaviourid).addElement(behaviourtitle);
    		}
    	}							

    	Body body = new Body();
    	Form form = new Form();
    	Input inputButton1 = new Input();
    	Input inputButton2 = new Input();
    	//Input inputButton3 = new Input();

    	Html html = new Html()
    	.addElement(new Head()
    	.addElement(new Title("Master Administration"))
    	.addElement(new Link()
    	.setHref("../coreadmin/js/stylesheet.css")
    	.setRel("stylesheet"))
    	.addElement(new Script()
    	.setLanguage("JavaScript")
    	.setSrc("../coreadmin/js/check.js"))
    	.addElement(new Script()
    	.setLanguage("JavaScript")
    	.addElement(javaScript)))

    	.addElement(body
    			.addElement(form
    					.setName("frm")
    					.addElement(new TableExtension()
    					.headerTable("<b>System Administrator: </b>"+strAdminId, dateTime.getFirst(), dateTime.getSecond(), "<b>System Administration: </b>Interface Role Management"))));





    	form.addElement(new Table()
    	.setBorder(0)
    	.setCellPadding(0)
    	.setCellSpacing(0)
    	.setWidth("100%")
    	.addElement(new TR()
    	.addElement(new TD()
    	.addElement(new Table()
    	.setBorder(0)
    	.setCellPadding(0)
    	.setCellSpacing(0)
    	.setWidth("100%")
    	.addElement(new TBody()
    	.addElement(new TR()
    	.addElement(new TD()
    	.addElement(new IMG()
    	.setBorder(0)
    	.setHeight(8)
    	.setWidth(10)
    	.setSrc("../coreadmin/images/T.gif")))


    			)))


    			.addElement(new TD()
    			.addElement(new Table()
    			.setBorder(0)
    			.setCellPadding(0)
    			.setCellSpacing(0)
    			.addElement(new TBody()
    			.addElement(new TRExtension()
    			.add())
    			.addElement(new TRExtension()
    			.addSelect("Select Role","role","1", option1,"select_user_onchange()"))

    			.addElement(new TRExtension()
    			.addSelect("Select Interface","interfaceid","2", option2,"select_user_onchange()"))
    					)))



    			)));



    	String sql = "select  a.role_id as \"Select\",a.role_id as \"Role ID\",a.interface_id as \"Interface ID\",a.content_id as \"Content ID\",a.layout_id as \"Layout ID\",a.style_id as \"Style ID\",a.behaviour_id as \"Behaviour ID\" from roleassociation a  where a.role_id='"+role+"' and a.interface_id='"+interfaceid+"'";
    	try {
    		JSPGridPro2 grid1 = new JSPGridPro2(request,"frm"); 
    		grid1.setConnectionParameters(sql);		

    		grid1.setWidth("100%");
    		grid1.setCellPadding(2);
    		grid1.setCellSpacing(1);
    		grid1.setFontFace("Arial");
    		grid1.setFontSize(2);
    		grid1.setEvenRowBgColor("#C0C0C0"); 
    		grid1.setOddRowBgColor("#F0F0F0");
    		grid1.setCaption("Currently Defined Interface role Association");
    		grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
    		grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
    		grid1.setLineNoHeaderBgColor("#48E6F7");
    		grid1.Cols(0).setFieldType(JSPGridPro2.FIELD_RADIO);
    		grid1.Cols(1).setFieldType(JSPGridPro2.FIELD_HIDDEN);		
    		grid1.Cols(2).setFieldType(JSPGridPro2.FIELD_HIDDEN);
    		grid1.Cols(3).setFieldType(JSPGridPro2.FIELD_HIDDEN);
    		grid1.Cols(4).setFieldType(JSPGridPro2.FIELD_HIDDEN);
    		grid1.Cols(5).setFieldType(JSPGridPro2.FIELD_HIDDEN);
    		grid1.Cols(6).setFieldType(JSPGridPro2.FIELD_HIDDEN);

    		grid1.Cols(0).setFieldName("checkbox");
    		grid1.Cols(1).setFieldName("hroleid");
    		grid1.Cols(2).setFieldName("hinterfaceid");
    		grid1.Cols(3).setFieldName("hcontentid");
    		grid1.Cols(4).setFieldName("hlayout");
    		grid1.Cols(5).setFieldName("hstyle");
    		grid1.Cols(6).setFieldName("hbehaviour");

    		grid1.Cols(0).Header().setClassID("swb");
    		grid1.Cols(1).Header().setClassID("swb");
    		grid1.Cols(2).Header().setClassID("swb");
    		grid1.Cols(3).Header().setClassID("swb");
    		grid1.Cols(4).Header().setClassID("swb");
    		grid1.Cols(5).Header().setClassID("swb");
    		grid1.Cols(6).Header().setClassID("swb");

    		grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
    		grid1.setEachRowHeight("20");
    		grid1.canSort(0, false);
    		grid1.canSort(1, true);

    		grid1.setSortableColumnsToolTip("Click to Sort");
    		grid1.showPageNavigationFirst();
    		grid1.showPageNavigationLast();
    		grid1.hidePageNavigationHTML();
    		grid1.setPageNavigationFontFace("Arial");
    		grid1.setPageNavigationFontSize(2);
    		grid1.setASCImageSource("../coreadmin/images/asc.gif");
    		grid1.setDESCImageSource("../coreadmin/images/desc.gif");
    		grid1.buildGrid(); //result set being processed, and cell values are available			

    		if (grid1.isResultSetEmpty()) {
    			form.addElement("<p id=\"record\">No Records Found");
    		}
    		else {						
    			grid1.countResultSet();				
    			form.addElement("<p>Total No. Of  Interface Role Association : " +grid1.getRows());
    			form.addElement(grid1.getGrid());
    		}	
    		//Added by Diptendu 29-Oct-2015

    		grid1.closeConnection();


    	}
    	catch (Exception exp) {

    	}	






    	form.addElement("<input type=\"hidden\" name=\"id\">");
    	form.addElement("<input type=\"hidden\" name=\"roleid\">");





    	form.addElement(new TableExtension()
    	.add()
    	.addElement(new Table()
    	.setBorder(0)
    	.setCellPadding(0)
    	.setCellSpacing(0)
    	.setWidth("100%")
    	.addElement(new TRExtension()
    	.addLine())
    	.addElement(new TR()
    	.addElement(new TD()
    	.setBgColor("023264")
    	.setWidth(1))
    	.addElement(new TD()

    	.addElement(new TableExtension()
    	.addSelect("Select Layout", "layout", "1", option3))

    	.addElement(new TableExtension()
    	.addSelect("Select Content", "content", "1", option4))
    	.addElement(new TableExtension()
    	.addSelect("Select Style", "style", "1", option5))
    	.addElement(new TableExtension()
    	.addSelect("Select Behaviour", "behaviour", "1", option6))

    	.addElement(new TableExtension()
    	.add())



    	.addElement(new Table()
    	.setBorder(0)
    	.setCellPadding(0)
    	.setCellSpacing(0)
    	.addElement(new TBody()
    	.addElement(new TR()
    	.addElement(new TD()
    	.setRowSpan(3)
    	.addElement(new IMG()
    	.setBorder(0)
    	.setHeight(8)
    	.setWidth(10)
    	.setSrc("../coreadmin/images/T.gif")))
    	.addElement(new TD()
    	.setHeight(5)))

    			))


    			.addElement(new TableExtension()
    			.add())
    			.addElement(new Table()
    			.setBorder(0)
    			.setCellPadding(0)
    			.setCellSpacing(0)
    			.setWidth("%100")
    			.addElement(new TBody()
    			.addElement(new TR()
    			.addElement("<TD><IMG border=0 height=5 src=\"../images/T.gif\" width=10></TD>")
    			.addElement("<TD width=\"100%\"><font color=\"#FF0000\">* Required Fields.</font></TD>"))))
    			)
    			.addElement(new TD()
    			.setBgColor("023264")
    			.setWidth("1")))
    			.addElement(new TRExtension()
    			.addLine()))
    			.addElement(new TableExtension()
    			.add()));
    	form.addElement(new Table()
    	.setBorder(0)
    	.setCellPadding(0)
    	.setCellSpacing(0)
    	.addElement(new TR()
    	.addElement(new TD()
    	.addElement(inputButton1
    			.setClassId("sbttn")
    			.setName("add")
    			.setTabindex(2)
    			.setTitleValue("SET")
    			.setType("button")
    			.setValue("SET")))
    			.addElement(new TD()
    			.setWidth(5))
    			.addElement(new TD()
    			.addElement(inputButton2
    					.setClassId("sbttn")
    					.setName("new")
    					.setTabindex(2)
    					.setTitleValue("New")
    					.setType("button")
    					.setValue("New")))
    					// 						.addElement(new TD()
    					// 							.setWidth(5))
    					// 						.addElement(new TD()
    					// 							.addElement(inputButton3
    					// 								.setClassId("sbttn")
    					// 								.setName("delete")
    					// 								.setTabindex(2)
    					// 								.setTitleValue("Delete")
    					// 								.setType("button")
    					// 								.setValue("Delete")))
    					// 						.addElement(new TD()
    					// 							.setWidth(5))
    					// 						
    					.addElement(new TD()
    					.setWidth(5))


    			));
    	body.setOnLoad("scrollit(100);load()");
    	inputButton1.setOnClick("add_onclick();");
    	inputButton2.setOnClick("newInterfaceRole();");
    	//         inputButton3.setOnClick("delete_onclick();");
    	out.println(html.toString());
    }

    private void generateNewPopUpPage(HttpServletRequest request, HttpServletResponse response, String statusMessage) throws IOException {
    	String formName = "newManifestForm";
    	Pair<String, String> dateTime = this.retrieveDateTime();

    	Html html = new Html().addElement(new Head().addElement(new Title("Interface Role Management"))
    			.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
    			.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
    			);

    	Form form = new Form().setName(formName).setMethod("post");
    	
    	Body body=new Body();
		body.addAttribute("onload", "opener.location.reload();");
		body.addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request),
    			dateTime.getFirst(), dateTime.getSecond(), "<b>Portal Administration:</b> New Interface Role Add"));

    	Table newInterfaceRoleTable = new Table().setCellPadding(2).setCellSpacing(1).setBorder(1);

    	{
    		String selectedRoleId = request.getParameter(_ROLE_SELECT_NAME);
    		Option[] roleOptionArray = null;
    		Vector<Vector<String>> parentrole = DataBaseLayer.getRole();
    		if(parentrole==null) {
    			roleOptionArray = new Option[1];
    			roleOptionArray[0] = new Option("0").addElement("[Choose One]");
    		}
    		else {
    			roleOptionArray = new Option[parentrole.size()+1];
    			roleOptionArray[0] = new Option("0").addElement("[Choose One]");
    			for(int i=0; i<parentrole.size(); i++) {
    				Vector<String> parentrolesub = parentrole.elementAt(i);
    				String roleId = (String) parentrolesub.elementAt(0);
    				String roleName = (String) parentrolesub.elementAt(1);
    				roleOptionArray[i+1] = new Option(roleId).addElement(roleName);
    				if(roleId.equals(selectedRoleId))
        			{
    					roleOptionArray[i + 1].setSelected(true);
        			}
    			}
    		}	
    		
    		TR roleSelectRow = new TR();

    		TD roleColumn = new TD();
    		roleColumn = roleColumn.addElement("Role");

    		TD roleSelectColumn = new TD().setColSpan(2);
    		Select roleSelect = new Select().setName(_ROLE_SELECT_NAME).addElement(roleOptionArray);
    		roleSelect.setOnChange(_ROLE_NEW_ONCHANGE_FUNCTION);
    		roleSelectColumn = roleSelectColumn.addElement(roleSelect);

    		roleSelectRow.addElement(roleColumn).addElement(roleSelectColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(roleSelectRow);
    		
    		
    	}
    	{
    		Option[] interfceOptionArray = null;
    		List<InterfaceElement> interfaceList = retrieveAvailaibleInterfaceForRoleID(request);
    		if(GenericUtil.isListNullOrEmpty(interfaceList)) {
    			interfceOptionArray = new Option[1];
    			interfceOptionArray[0] = new Option("0").addElement("[Choose One]");
    		}
    		else {
    			interfceOptionArray = new Option[interfaceList.size()+1];
    			interfceOptionArray[0] = new Option("0").addElement("[Choose One]");
    			int interfaceElementItr=1;
    			for(InterfaceElement interfaceElement:interfaceList) {
    				String interfaceId = interfaceElement.getId();
    				String interfcaeTitle = interfaceElement.getTitle();
    				interfceOptionArray[interfaceElementItr] = new Option(interfaceId).addElement(interfcaeTitle);
    				interfaceElementItr++;
    			}
    		}	
    		
    		TR interfaceSelectRow = new TR();

    		TD interfaceColumn = new TD();
    		interfaceColumn = interfaceColumn.addElement("Interface");

    		TD interfaceSelectColumn = new TD().setColSpan(2);
    		Select interfaceSelect = new Select().setName(_INTERFACE_SELECT_NAME).addElement(interfceOptionArray);
    		interfaceSelectColumn = interfaceSelectColumn.addElement(interfaceSelect);

    		interfaceSelectRow.addElement(interfaceColumn).addElement(interfaceSelectColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(interfaceSelectRow);
    	}

    	{
    		TR layoutNameRow = new TR();

    		TD layoutNameColumn = new TD();
    		layoutNameColumn = layoutNameColumn.addElement("Layout");

    		TD layoutInputColumn = new TD().setColSpan(2);
    		Input layoutInput = new Input().setName(_LAYOUT_INPUT_NAME).setType("text").setTitleValue(_LAYOUT_INPUT_NAME);
    		layoutInputColumn = layoutInputColumn.addElement(layoutInput);

    		layoutNameRow.addElement(layoutNameColumn).addElement(layoutInputColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(layoutNameRow);
    	}

    	{
    		TR contentNameRow = new TR();

    		TD contentNameColumn = new TD();
    		contentNameColumn = contentNameColumn.addElement("Content");

    		TD contentNameInputColumn = new TD().setColSpan(2);
    		Input contentNameInput = new Input().setName(_CONTENT_INPUT_NAME).setType("text").setTitleValue(_CONTENT_INPUT_NAME);
    		contentNameInputColumn = contentNameInputColumn.addElement(contentNameInput);

    		contentNameRow.addElement(contentNameColumn).addElement(contentNameInputColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(contentNameRow);
    	}

    	{
    		TR behaviourNameRow = new TR();

    		TD behaviourNameColumn = new TD();
    		behaviourNameColumn = behaviourNameColumn.addElement("Behaviour");

    		TD behaviourNameInputColumn = new TD().setColSpan(2);
    		Input behaviourNameInput = new Input().setName(_BEHAVIOUR_INPUT_NAME).setType("text").setTitleValue(_BEHAVIOUR_INPUT_NAME);
    		behaviourNameInputColumn = behaviourNameInputColumn.addElement(behaviourNameInput);

    		behaviourNameRow.addElement(behaviourNameColumn).addElement(behaviourNameInputColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(behaviourNameRow);
    	}
    	
    	{
    		TR styleNameRow = new TR();

    		TD styleNameColumn = new TD();
    		styleNameColumn = styleNameColumn.addElement("Style");

    		TD styleNameInputColumn = new TD().setColSpan(2);
    		Input styleNameInput = new Input().setName(_STYLE_INPUT_NAME).setType("text").setTitleValue(_STYLE_INPUT_NAME);
    		styleNameInputColumn = styleNameInputColumn.addElement(styleNameInput);

    		styleNameRow.addElement(styleNameColumn).addElement(styleNameInputColumn);

    		newInterfaceRoleTable = newInterfaceRoleTable.addElement(styleNameRow);
    	}

    	form.addElement(newInterfaceRoleTable);

    	createNewInterfcaeRoleOperations(html, form, formName);
    	form.addElement("<div id=\"status-message\" style=\"color:red;\">" + statusMessage + "</div>");
    	body.addElement(form);
    	html.addElement(body.setClass("bodyadmin"));

    	PrintWriter out = response.getWriter();
    	out.print(html.toString());
    }
    
    private String addInterfaceRole(HttpServletRequest request) {
		String roleId = request.getParameter(_ROLE_SELECT_NAME);
		String interfaceId = request.getParameter(_INTERFACE_SELECT_NAME);
		String layout = request.getParameter(_LAYOUT_INPUT_NAME);
		String content = request.getParameter(_CONTENT_INPUT_NAME);
		String behaviour = request.getParameter(_BEHAVIOUR_INPUT_NAME);
		String style = request.getParameter(_STYLE_INPUT_NAME);
		String checkId = DataBaseLayer.checkInterfaceRoleID(interfaceId, roleId, layout, content, behaviour, style);
		String statusMessage = "";
		if (GenericUtil.isEmptyString(checkId)) {
			DataBaseLayer.addInterfaceRole(roleId,interfaceId,layout,content,behaviour,style);
			statusMessage = "Role added. Please uplaod from interface Management!";
		} else {
			statusMessage = "Interface already exists!";
		}
		return statusMessage;
	}
    
    private List<InterfaceElement> retrieveAvailaibleInterfaceForRoleID(HttpServletRequest request) {
		String roleId = request.getParameter(_ROLE_SELECT_NAME);
		List<InterfaceElement> interfaceElements = DataBaseLayer.retrieveAvailaibleInterfaceForRoleID(roleId);
		return interfaceElements;
	}
    
    private void createNewInterfcaeRoleOperations(Html html, Form form, String formName) {
		String javaScript =

		"function saveInterfaceRoleDetails() {"
				+ "\n		var role = document.getElementsByName(\""+_ROLE_SELECT_NAME+"\")[0].value.trim();"
				+ "\nif (role != 0) {"
				+ "\n	var interfaceSelect = document.getElementsByName(\""+_INTERFACE_SELECT_NAME+"\")[0].value.trim();"
				+ "\n	if (interfaceSelect != 0) {"
				+ "\n		var layout = document.getElementsByName(\""+_LAYOUT_INPUT_NAME+"\")[0].value.trim();"
				+ "\n		if (layout) {"
				+ "\n			var style = document.getElementsByName(\""+_STYLE_INPUT_NAME+"\")[0].value.trim();"
				+ "\n			if (style) {"
				+ "\n				var content = document.getElementsByName(\""+_CONTENT_INPUT_NAME+"\")[0].value.trim();"
				+ "\n				if (content) {"
				+ "\n					var behaviour = document.getElementsByName(\""+_BEHAVIOUR_INPUT_NAME+"\")[0].value.trim();"
				+ "\n					if (behaviour) {"
				+ "\n						document.newManifestForm.method = \"post\";"
				+ "\n						document.newManifestForm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2\";"
				+ "\n						document.newManifestForm.submit();"
				+ "\n					} else {"
				+ "\n						alert(\"Please provide behaviour name.\");"
				+ "\n					}"
				+ "\n				} else {"
				+ "\n					alert(\"Please provide content name.\");"
				+ "\n				}"
				+ "\n			} else {"
				+ "\n				alert(\"Please provide style name.\");"
				+ "\n			}"
				+ "\n		} else {"
				+ "\n			alert(\"Please provide layout name.\");"
				+ "\n		}"
				+ "\n	} else {"
				+ "\n		alert(\"Please select a interface.\");"
				+ "\n	}"
				+ "\n} else {"
				+ "\n	alert(\"Please select a role.\");"
				+ "\n}"
				+ "\n}"
				+"\nfunction "+_ROLE_NEW_ONCHANGE_FUNCTION+" {"
				+"\n	document.newManifestForm.method = \"post\";"
				+"\n	document.newManifestForm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1\";"
				+"\n	document.newManifestForm.submit();"
				+"\n}";

		Input newInterfaceRoleButton = new Input();
		newInterfaceRoleButton.setOnClick("saveInterfaceRoleDetails();");

		Input closeButton = new Input();
		closeButton.setOnClick("window.close();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(
								new TD().addElement(newInterfaceRoleButton.setClassId("sbttn").setName("Save").setTabindex(1).setTitleValue("Save")
										.setType("button").setValue("Save")))
								.addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(closeButton.setClassId("sbttn").setName("Close").setTabindex(3).setTitleValue("Close")
												.setType("button").setValue("Close")))));

		Table table = new Table();
		table.addElement(new TR().addElement(new TD().addElement(operationTable)));
		form.addElement(table);

		html.addElement(new Script().setLanguage("JavaScript").addElement(javaScript));
	}
}
