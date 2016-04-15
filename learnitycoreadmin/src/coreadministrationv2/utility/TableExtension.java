package coreadministrationv2.utility;

import org.apache.ecs.Element;
import org.apache.ecs.html.A;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.NOBR;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.P;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

public class TableExtension extends Table
{
    public Table add() {
        final Table tbl = new Table().addElement((Element)new TBody().addElement((Element)new TR().addElement((Element)new TD().setWidth(160).addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth(160).addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif")))));
        return tbl;
    }
    
    public Table add(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().add(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
    
    public Table addwithbrowse(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String browse) {
        final A a = new A();
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)new Input().setType(type).setClassId("PPRField").setMaxlength(maxlength).setName(name).setSize(size).setTabindex(tabIndex)).addElement((Element)a.setHref("javascript:browse('" + browse + "');").addElement((Element)new Font().setSize(1).addElement("Browse"))))));
        return tbl;
    }
    
    public Table add3(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add3(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
    
    public Table add4(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add4(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
    
    public Table addTime(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addTime(txt, type, name, maxlength, size, tabIndex, strFormat)));
        return tbl;
    }
    
    public Table addCheckBox(final String txt, final String type, final String name, final String value, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addCheckBox(txt, type, name, value, tabIndex)));
        return tbl;
    }
    
    public Table addCheckBox3(final String txt, final String type, final String name, final String value, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addCheckBox3(txt, type, name, value, tabIndex)));
        return tbl;
    }
    
    public Table addEmail(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addEmail(txt, type, name, maxlength, size, tabIndex, strFormat)));
        return tbl;
    }
    
    public Table addText(final String txt) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addText(txt)));
        return tbl;
    }
    
    public Table addText4(final String txt, final String txt1) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addText4(txt, txt1)));
        return tbl;
    }
    
    public Table addRadio(final String txt, final String type, final String name, final String radio1, final String radio2) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addRadio(txt, type, name, radio1, radio2)));
        return tbl;
    }
    
    public Table addRadio(final String txt, final String type, final String name) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addRadio5(txt, type, name)));
        return tbl;
    }
    
    public Table addRadioType(final String txt, final String type, final String name, final String radio1, final String radio2) {
        final Input input1 = new Input();
        input1.setOnClick("javascript:settype1()");
        final Input input2 = new Input();
        input2.setOnClick("javascript:settype2()");
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TR().addElement((Element)new TD().setClassId("PPRLabelText").setWidth("160").addElement(txt)).addElement((Element)new TD().setWidth("336").addElement((Element)input2.setType(type).setName(radio1).setValue("L")).setClassId("PPRLabelText").addElement("&nbsp;&nbsp;").addElement(radio1).addElement((Element)input1.setType(type).setName(radio2).setValue("R")).setClassId("PPRLabelText").addElement("&nbsp;&nbsp;").addElement(radio2))));
        return tbl;
    }
    
    public Table addHR1() {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addHR1()));
        return tbl;
    }
    
    public Table addCalender(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addCalender(txt, type, name, maxlength, size, tabIndex, strFormat)));
        return tbl;
    }
    
    public Table addCalender1(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final Option[] option, final String btn, final String val) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add1()).addElement((Element)new TRExtension().addCalender1(txt, type, name, maxlength, size, tabIndex, strFormat, option, btn, val)));
        return tbl;
    }
    
    public Table addCalender2(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final String obj) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addCalender2(txt, type, name, maxlength, size, tabIndex, strFormat, obj)));
        return tbl;
    }
    
    public Table addCalender4(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex, final String strFormat, final String obj) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addCalender4(txt, type, name, maxlength, size, tabIndex, strFormat, obj)));
        return tbl;
    }
    
    public Table addRequired(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addRequired(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
    
    public Table addSelect(final String txt, final String name, final String tabIndex, final Option[] option) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addSelect(txt, name, tabIndex, option)));
        return tbl;
    }
    
    public Table addSelectR(final String txt, final String name, final String tabIndex, final Option[] option) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addSelectR(txt, name, tabIndex, option)));
        return tbl;
    }
    
    public Table addSelect1(final String txt, final String name, final String tabIndex, final Option[] option, final String btn, final String val) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add2()).addElement((Element)new TRExtension().addSelect2(txt, name, tabIndex, option, btn, val)));
        return tbl;
    }
    
    public Table addSelect(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addSelect(txt, name, tabIndex, option, select)));
        return tbl;
    }
    
    public Table addSelect3(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addSelect3(txt, name, tabIndex, option, select)));
        return tbl;
    }
    
    public Table addSelect4(final String txt, final String name, final String tabIndex, final Option[] option, final String select) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().addSelect4(txt, name, tabIndex, option, select)));
        return tbl;
    }
    
    public Table addHR() {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addHR(8)));
        return tbl;
    }
    
    public Table addSelectButton(final String txt, final String name, final String tabIndex, final Option[] option, final String btnName, final String btnValue, final String title, final String click) {
        final Table tbl = new Table().setBorder(0).setCellPadding(2).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TR().addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("160").addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(160).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().addElement((Element)new IMG().setBorder(0).setHeight(5).setWidth(1).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setRowSpan(3).addElement((Element)new IMG().setBorder(0).setHeight(8).setWidth(10).setSrc("../coreadmin/images/T.gif")))).addElement((Element)new TRExtension().addSelectButton(txt, name, tabIndex, option, btnName, btnValue, title, click)));
        return tbl;
    }
    
    public Table headerTable(final String strAdmin, final String strDate, final String strTime, final String strFnc) {
        final Table tbl = new Table().setCellPadding(2).setCellSpacing(1).setWidth("75%").addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;"))))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;<b>Date : </b>" + strDate + "&nbsp;"))))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;<b>Time : </b>" + strTime + "&nbsp;")))))).addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strFnc + "&nbsp;"))))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%")));
        return tbl;
    }
    
    public Table headerTable(final String str, final String strAdmin, final String strDate, final String strTime, final String strFnc, final String administration, final String no) {
        final Table tbl = new Table().setCellPadding(2).setCellSpacing(1).setWidth("75%").addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;"))))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;<b>Date : </b>" + strDate + "&nbsp;"))))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;<b>Time : </b>" + strTime + "&nbsp;")))))).addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strFnc + "&nbsp;"))))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").addElement((Element)new IMG().setWidth("160").setHeight("5").setBorder(0).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").setClassId("swb").setHeight(23).addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + str + "&nbsp;"))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + administration + "&nbsp;"))).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").setClassId("swb").setHeight(23).addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;Average login time&nbsp;"))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + no + "&nbsp;"))).addElement((Element)new TD().setWidth("25%")));
        return tbl;
    }
    
    public Table headerTable(final String str, final String strAdmin, final String strDate, final String strTime, final String strFnc, final String administration, final String no, final String misc) {
        final Table tbl = new Table().setCellPadding(2).setCellSpacing(1).setWidth("75%").addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strAdmin + "&nbsp;"))))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;<b>Date : </b>" + strDate + "&nbsp;"))))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("center").addElement((Element)new Font().setColor("#FFFFFF").addElement("<b>&nbsp;&nbsp;Time : </b>" + strTime + "&nbsp;")))))).addElement((Element)new TR().addElement((Element)new TD().setClassId("swb").setHeight(23).setWidth("50%").addElement((Element)new NOBR().addElement((Element)new P().setAlign("left").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + strFnc + "&nbsp;"))))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").addElement((Element)new IMG().setWidth("160").setHeight("5").setBorder(0).setSrc("../coreadmin/images/T.gif"))).addElement((Element)new TD().setWidth("25%")).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").setClassId("swb").setHeight(23).addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + str + "&nbsp;"))).addElement((Element)new TD().setWidth("25%").setClassId("swb").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + administration + "&nbsp;"))).addElement((Element)new TD().setWidth("25%"))).addElement((Element)new TR().addElement((Element)new TD().setWidth("50%").setClassId("swb").setHeight(23).addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + misc + "&nbsp;"))).addElement((Element)new TD().setClassId("swb").setWidth("25%").addElement((Element)new Font().setColor("#FFFFFF").addElement("&nbsp;&nbsp;" + no + "&nbsp;"))).addElement((Element)new TD().setWidth("25%")));
        return tbl;
    }
    
    public Table addRequiredNew(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(0).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().addRequired(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
    
    public Table addNew(final String txt, final String type, final String name, final String maxlength, final String size, final String tabIndex) {
        final Table tbl = new Table().setBorder(0).setCellPadding(0).setCellSpacing(0).addElement((Element)new TBody().addElement((Element)new TRExtension().add()).addElement((Element)new TRExtension().add(txt, type, name, maxlength, size, tabIndex)));
        return tbl;
    }
}
