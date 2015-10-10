package coreadministrationv2.utility;

import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class TRExtension extends TR
{
    public TR add() {
        final TR tr = new TR().addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("160").addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("100%").addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif")));
        return tr;
    }
    
    public TR add1() {
        final TR tr = new TR().addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("160").addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif")));
        return tr;
    }
    
    public TR add2() {
        final TR tr = new TR().addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("160").addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif")));
        return tr;
    }
    
    public TR addRowImage() {
        final TR tr = new TR().addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif")));
        return tr;
    }
    
    public TR add(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)));
        return tr;
    }
    
    public TR add3(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("100").addElement(txt)).addElement((Element)new TD().setWidth("150").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)));
        return tr;
    }
    
    public TR add4(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("75").addElement(txt)).addElement((Element)new TD().setWidth("100").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)));
        return tr;
    }
    
    public TR addTime(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("75").addElement(txt)).addElement((Element)new TD().setWidth("100").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)new Span().setClassId("PPRSmallLabelText").addElement(strFormat)));
        return tr;
    }
    
    public TR addCheckBox(final String txt, final String type, final String name, final String value, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setName(name).setValue(value).setTabindex(tabIndex)));
        return tr;
    }
    
    public TR addCheckBox3(final String txt, final String type, final String name, final String value, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("100").addElement(txt)).addElement((Element)new TD().setWidth("150").addElement((Element)new Input().setType(type).setName(name).setValue(value).setTabindex(tabIndex)));
        return tr;
    }
    
    public TR addText(final String text) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setColSpan(2).addElement(text));
        return tr;
    }
    
    public TR addText4(final String text, final String text1) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("75").addElement(text)).addElement((Element)new TD().setClassId("PPRSmallLabelText").addElement(text1));
        return tr;
    }
    
    public TR addcheckbox(final String txt, final String type, final String name, final String tabIndex, final String value, final boolean check) {
        final Input checkButton = new Input();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)checkButton.setType(type).setName(name).setValue(value).setTabindex(tabIndex)));
        checkButton.setChecked(check);
        return tr;
    }
    
    public TR addcheckboxSelf(final String txt, final String type, final String name, final String tabIndex, final String value, final boolean check) {
        final Input checkButton = new Input();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)checkButton.setType(type).setName(name).setValue(value).setTabindex(tabIndex)));
        checkButton.setChecked(check);
        return tr;
    }
    
    public TR addRequired(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex).addElement((Element)new Font().setColor("FF0000").addElement("*"))));
        return tr;
    }
    
    public TR addRequiredDisabled(final String txt, final String type, final String name, final String maxlength, final String size, final boolean disabled, final String tabIndex) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex).setDisabled(disabled).addElement((Element)new Font().setColor("FF0000").addElement("*"))));
        return tr;
    }
    
    public TR addRadio(final String gender, final String type, final String name) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(gender)).addElement((Element)new TD().setWidth("336").setClassId("PPCheckRadio").addElement((Element)new Input(type, name, "m").setTabindex("1").setChecked(true).addElement("Male")).addElement((Element)new Input(type, name, "f").setTabindex("1").setChecked(false).addElement("Female")));
        return tr;
    }
    
    public TR addRadio5(final String BasePost, final String type, final String name) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(BasePost)).addElement((Element)new TD().setWidth("336").setClassId("PPCheckRadio").addElement((Element)new Input(type, name, "y").setTabindex("1").setChecked(true).addElement("Yes")).addElement((Element)new Input(type, name, "n").setTabindex("1").setChecked(false).addElement("No")));
        return tr;
    }
    
    public TR addRadio4(final String gender, final String type, final String name) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(gender)).addElement((Element)new TD().setWidth("336").setClassId("PPCheckRadio").addElement((Element)new Input(type, name, "rural").setTabindex("1").setChecked(true).addElement("Rural")).addElement((Element)new Input(type, name, "urban").setTabindex("1").setChecked(false).addElement("Urban")));
        return tr;
    }
    
    public TR addRadio1(final String gender, final String type, final String name) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(gender)).addElement((Element)new TD().setWidth("336").setClassId("PPCheckRadio").addElement((Element)new Input(type, name, "Y").setTabindex("1").setChecked(true).addElement("Appeared")).addElement((Element)new Input(type, name, "N").setTabindex("1").setChecked(false).addElement("Did not appeared")));
        return tr;
    }
    
    public TR addRadio(final String gender, final String type, final String name, final String radio1, final String radio2) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(gender)).addElement((Element)new TD().setWidth("336").setClassId("PPCheckRadio").addElement((Element)new Input(type, name, "m").setTabindex("1").setChecked(false).addElement(radio1)).addElement((Element)new Input(type, name, "f").setTabindex("1").setChecked(false).addElement(radio2)));
        return tr;
    }
    
    public TR addEmail(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)new Span().setClassId("PPRSmallLabelText").addElement((Element)new BR().addElement(strFormat))));
        return tr;
    }
    
    public TR addCalender(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final A a = new A();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)a.setHref("javascript:doNothing()").addElement((Element)new Font().setSize(1).addElement((Element)new IMG().setSrc("../coreadmin/images/calendar.gif").setBorder(0).setHeight(16).setWidth(16).setAlign("MIDDLE").addElement("Calendar")))).addElement((Element)new Span().setClassId("PPRSmallLabelText").addElement((Element)new BR().addElement(strFormat))));
        a.setOnClick("showCal(frm." + name + ", 'Y');");
        return tr;
    }
    
    public TR addCalender1(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final Option[] option, final String btn, final String val) {
        final A a = new A();
        final IMG in = new IMG();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").addElement(txt)).addElement((Element)new TD().addElement((Element)new Select().setName(btn).setClassId("drpdwn").setTabindex(tabIndex).addElement(option))).addElement((Element)new TD().addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)a.setHref("javascript:doNothing()").addElement((Element)new Font().setSize(1).addElement((Element)new IMG().setSrc("../coreadmin/images/calendar.gif").setBorder(0).setHeight(16).setWidth(16).setAlign("MIDDLE").addElement("Calendar"))))).addElement((Element)new TD().addElement((Element)in.setWidth(21).setHeight(16).setSrc("../coreadmin/images/btn_go_off.gif")));
        in.setOnClick("document.frm.prmGo.value=" + val + ";Go_onclick()");
        a.setOnClick("showCal(frm." + name + ", 'Y');");
        return tr;
    }
    
    public TR addCalender2(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final String obj) {
        final A a = new A();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)a.setHref("javascript:popup('" + obj + "');").addElement((Element)new Font().setSize(1).addElement((Element)new IMG().setSrc("../coreadmin/images/calendar.gif").setBorder(0).setHeight(16).setWidth(16).setAlign("MIDDLE").addElement("Calendar")))).addElement((Element)new Span().setClassId("PPRSmallLabelText").addElement((Element)new BR().addElement(strFormat))));
        return tr;
    }
    
    public TR addCalender4(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final String obj) {
        final A a = new A();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("75").addElement(txt)).addElement((Element)new TD().setWidth("100").addElement((Element)new NOBR().addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)a.setHref("javascript:popup('" + obj + "');").addElement((Element)new Font().setSize(1).addElement((Element)new IMG().setSrc("../coreadmin/images/calendar.gif").setBorder(0).setHeight(16).setWidth(16).setAlign("middle").addElement("Calendar"))))).addElement((Element)new Span().setClassId("PPRSmallLabelText").addElement((Element)new BR()).addElement((Element)new NOBR().addElement(strFormat))));
        return tr;
    }
    
    public TR addSelect(final String txt, final String name, final String tabIndex, final Option[] option) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Select().setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option)));
        return tr;
    }
    
    public TR addSelectR(final String txt, final String name, final String tabIndex, final Option[] option) {
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Select().setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option)).addElement((Element)new Font().setColor("FF0000").addElement("*")));
        return tr;
    }
    
    public TR addSelect2(final String txt, final String name, final String tabIndex, final Option[] option, final String btn, final String val) {
        final IMG in = new IMG();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().addElement((Element)new Select().setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option))).addElement((Element)new TD().addElement((Element)in.setName(btn).setWidth(21).setHeight(16).setSrc("../coreadmin/images/btn_go_off.gif")));
        in.setOnClick("document.frm.prmGo.value=" + val + ";Go_onclick()");
        return tr;
    }
    
    public TR addSelect(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Select sel = new Select();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)sel.setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option)));
        sel.setOnChange(select);
        return tr;
    }
    
    public TR addSelect3(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Select sel = new Select();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("100").addElement(txt)).addElement((Element)new TD().setWidth("150").addElement((Element)sel.setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option)));
        sel.setOnChange(select);
        return tr;
    }
    
    public TR addSelect4(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Select sel = new Select();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("75").addElement(txt)).addElement((Element)new TD().setWidth("100").addElement((Element)sel.setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option)));
        sel.setOnChange(select);
        return tr;
    }
    
    public TR addSelectButton(final String txt, final String name, final String tabIndex, final Option[] option, final String btnName, final String btnValue, final String title, final String click) {
        final Input inputButton = new Input();
        final TR tr = new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().addElement((Element)new Select().setClassId("drpdwn").setName(name).setTabindex(tabIndex).addElement(option))).addElement((Element)new TD().setWidth("5")).addElement((Element)new TD().addElement((Element)new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TR().addElement((Element)new TD().addElement((Element)inputButton.setClassId("sbttn").setName(btnName).setTabindex(1).setType("button").setTitleValue(title).setValue(btnValue))))));
        inputButton.setOnClick(click);
        return tr;
    }
    
    public TR addRowImage1() {
        final TR tr = new TR().addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth(160).addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif")));
        return tr;
    }
    
    public TR addLine() {
        final TR tr = new TR().addElement((Element)new TD().setBgColor("023264").setHeight(1).setWidth(1)).addElement((Element)new TD().setBgColor("023264").setHeight(1).setWidth(1)).addElement((Element)new TD().setBgColor("023264").setHeight(1).setWidth(1));
        return tr;
    }
    
    public TR addRowUser(final String strUserId, final String strName, final String strCreadtedDate, final String strCreatedBy, final String strLastModDate, final String strStatus) {
        final Input inputCheck1 = new Input();
        String colour = "";
        if (strStatus.equals("Active")) {
            colour = "3366FF";
        }
        else {
            colour = "FF0000";
        }
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strName + "&nbsp;"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCreadtedDate + "&nbsp;"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCreatedBy + "&nbsp;"))).addElement((Element)new TD().setWidth("18%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLastModDate + "&nbsp;"))).addElement((Element)new TD().setWidth("7%").addElement((Element)new NOBR().addElement((Element)new Font().setColor(colour).addElement("&nbsp;&nbsp;" + strStatus + "&nbsp;"))));
        inputCheck1.setOnClick("CCA();");
        return tr;
    }
    
    public TR addRowUser(final String strUserId, final String strName, final String strCreadtedDate, final String strCreatedBy, final String strLastModDate, final String modifiedBy, final String no, final String strStatus) {
        final Input inputCheck1 = new Input();
        String colour = "";
        if (strStatus.equals("Active")) {
            colour = "3366FF";
        }
        else {
            colour = "FF0000";
        }
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strName + "&nbsp;").addElement((Element)new Input().setType("hidden").setName("hiddenUnit").setValue(strName)))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCreadtedDate + "&nbsp;"))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCreatedBy + "&nbsp;"))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLastModDate + "&nbsp;"))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + modifiedBy + "&nbsp;"))).addElement((Element)new TD().setWidth("12%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + no + "&nbsp;"))).addElement((Element)new TD().setWidth("8%").addElement((Element)new NOBR().addElement((Element)new Font().setColor(colour).addElement("&nbsp;&nbsp;" + strStatus + "&nbsp;").addElement((Element)new Input().setType("hidden").setName("hiddenStatus").setValue(strStatus)))));
        inputCheck1.setOnClick("CCA();checkbox_onclick();");
        return tr;
    }
    
    public TR addRowUser(final String strUserId, final String strName, final String strLogin, final String strCConnectedFor) {
        final Input inputCheck1 = new Input();
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("30%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;").addElement((Element)new Font().setColor("#0000FF").addElement((Element)new A().setHref("AdministrativeManagement.AdministratorModify?user_id=" + strUserId).addElement(strName))).addElement("&nbsp;"))).addElement((Element)new TD().setWidth("40%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCConnectedFor + "&nbsp;")));
        inputCheck1.setOnClick("CCA();");
        return tr;
    }
    
    public TR addRowUser(final String strUserId, final String strName, final String strLogin, final String strFun, final String strCConnectedFor) {
        final Input inputCheck1 = new Input();
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strName + "&nbsp;"))).addElement((Element)new TD().setWidth("30%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strFun + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCConnectedFor + "&nbsp;")));
        inputCheck1.setOnClick("CCA();");
        return tr;
    }
    
    public TR addDynamicInformationByUnit(final String strUserId, final String strName, final String strLogin, final String strFun, final String strCConnectedFor) {
        final Input inputCheck1 = new Input();
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;").addElement((Element)new Font().setColor("#0000FF").addElement((Element)new A().setHref("./AdministrativeManagement.UnitAdministration?unit_id=" + strUserId).addElement(strName))).addElement("&nbsp;"))).addElement((Element)new TD().setWidth("30%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strFun + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCConnectedFor + "&nbsp;")));
        inputCheck1.setOnClick("CCA();");
        return tr;
    }
    
    public TR addRowUser1(final String strUserId, final String strName, final String strLogin, final String strFun, final String strCConnectedFor, final String click) {
        final Input inputCheck1 = new Input();
        final TR tr = new TR().setBgColor("FFEEBB").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)inputCheck1.setType("radio").setName("checkbox").setValue(strUserId).setTabindex("1"))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strName + "&nbsp;"))).addElement((Element)new TD().setWidth("30%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strFun + "&nbsp;"))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement("&nbsp;&nbsp;" + strCConnectedFor + "&nbsp;")));
        inputCheck1.setOnClick("CCA();" + click + ";");
        return tr;
    }
    
    public TR addHR() {
        final TR tr = new TR().addElement((Element)new TD().setColSpan(6).addElement((Element)new HR().setSize(1)));
        return tr;
    }
    
    public TR addCurrentUser(final String str, final int i) {
        final TR tr = new TR().addElement((Element)new TD().setColSpan(6).addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setSize(2).addElement((Element)new B().addElement("&nbsp;&nbsp;" + str + "&nbsp;"))).addElement("" + i))));
        return tr;
    }
    
    public TR addHR(final int colSpan) {
        final TR tr = new TR().addElement((Element)new TD().setColSpan(colSpan).addElement((Element)new HR().setSize(1)));
        return tr;
    }
    
    public TR addHR1() {
        final TR tr = new TR().addElement((Element)new TD().setWidth(160).addElement((Element)new HR().setSize(1))).addElement((Element)new TD().setWidth(336).addElement((Element)new HR().setSize(1)));
        return tr;
    }
    
    public TR addHeader(final String strHeader) {
        final TR tr = new TR().addElement((Element)new TR().addElement((Element)new TD().setBgColor("990000").setColSpan(6).setHeight(30).setWidth("100%").addElement((Element)new Font().setColor("#FFFFFF").addElement((Element)new B().addElement("&nbsp;&nbsp;" + strHeader + "&nbsp;")))));
        return tr;
    }
    
    public TR addHeader(final String strHeader, final int colSpan) {
        final TR tr = new TR().addElement((Element)new TR().addElement((Element)new TD().setBgColor("990000").setColSpan(colSpan).setHeight(30).setWidth("100%").addElement((Element)new Font().setColor("#FFFFFF").addElement((Element)new B().addElement("&nbsp;&nbsp;" + strHeader + "&nbsp;")))));
        return tr;
    }
    
    public TR addHeaderNames(final String strSelect, final String strAdminName, final String strCreatedDate, final String strCreatedBy, final String strLastMod, final String strStatus) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("swb").setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strAdminName + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strCreatedDate + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strCreatedBy + "&nbsp;")))).addElement((Element)new TD().setWidth("18%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLastMod + "&nbsp;")))).addElement((Element)new TD().setWidth("7%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strStatus + "&nbsp;"))));
        return tr;
    }
    
    public TR addHeaderNames(final String strSelect, final String strAdminName, final String strCreatedDate, final String strCreatedBy, final String strLastMod, final String modifiedBy, final String No, final String strStatus) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strAdminName + "&nbsp;")))).addElement((Element)new TD().setWidth("15%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strCreatedDate + "&nbsp;")))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strCreatedBy + "&nbsp;")))).addElement((Element)new TD().setWidth("15%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLastMod + "&nbsp;")))).addElement((Element)new TD().setWidth("15%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + modifiedBy + "&nbsp;")))).addElement((Element)new TD().setWidth("12%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + No + "&nbsp;")))).addElement((Element)new TD().setWidth("8%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strStatus + "&nbsp;"))));
        return tr;
    }
    
    public TR addHeaderNames(final String strSelect, final String strAdmin, final String strLogin, final String strConnectedFor) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("swb").setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("30%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;")))).addElement((Element)new TD().setWidth("40%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strConnectedFor + "&nbsp;"))));
        return tr;
    }
    
    public TR addHeaderNames(final String strSelect, final String strAdmin, final String strLogin, final String strConnectedFor, final String strFunction) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("swb").setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;")))).addElement((Element)new TD().setWidth("30%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strFunction + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strConnectedFor + "&nbsp;"))));
        return tr;
    }
    
    public TR addHeaderNamesforUnit(final String strSelect, final String strAdmin, final String strLogin, final String strConnectedFor, final String strFunction) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("swb").setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;")))).addElement((Element)new TD().setWidth("30%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLogin + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strFunction + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strConnectedFor + "&nbsp;"))));
        return tr;
    }
    
    public TR addHeaderNames1(final String strSelect, final String strName, final String strCreatedDate, final String strCreatedBy, final String strLModDate) {
        final TR tr = new TR().setBgColor("990000").addElement((Element)new TD().setWidth("5%").setHeight(23).addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("swb").setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strSelect + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strName + "&nbsp;")))).addElement((Element)new TD().setWidth("30%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strCreatedDate + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").addElement((Element)new NOBR().addElement((Element)new Font().setColor("#FFFFFF").setFontClass("swb").addElement("&nbsp;&nbsp;" + strCreatedBy + "&nbsp;")))).addElement((Element)new TD().setWidth("20%").setBgColor("660000").addElement((Element)new NOBR().addElement((Element)new Font().setFontClass("sbbd").setColor("003366").addElement("&nbsp;&nbsp;" + strLModDate + "&nbsp;"))));
        return tr;
    }
    
    public Table headerTable(final String strAdmin, final String strDate, final String strTime, final String strFnc) {
        final Table tbl = new Table().setCellPadding(2).setCellSpacing(1).setWidth("75%").addElement((Element)new TR().setBgColor("990000").addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement("&nbsp;&nbsp;<b>Date : </b>" + strDate + "&nbsp;")))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement("&nbsp;&nbsp;<b>Time : </b>" + strTime + "&nbsp;"))))).addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement("&nbsp;&nbsp;" + strFnc + "&nbsp;")))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%")));
        return tbl;
    }
}
