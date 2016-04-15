/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package comv2.aunwesha.JSPGrid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class JSPGrid2 {
    private int s = 4;
    private int b = 4;
    private String C = "";
    private int B = -1;
    private int _fldbyte = -1;
    private int _fldvoid = -1;
    private String m = "";
    private String r = "";
    private String e = "";
    private String _fldnull = "";
    private String E = "";
    private String k = "";
    private String u = "";
    private int h = 0;
    private int z = 0;
    private int x = 0;
    private String a = "";
    private String _fldgoto = "";
    private int A = 0;
    private boolean w = false;
    private boolean _fldif = false;
    private boolean i = false;
    private String p = "";
    private String _fldcase = "";
    private boolean l = false;
    private String q = "";
    private boolean f = false;
    private boolean F = false;
    private boolean _fldlong = false;
    public static final String FIELD_TEXT = "TEXT";
    public static final String FIELD_HIDDEN = "HIDDEN";
    public static final String FIELD_TEXTAREA = "TEXTAREA";
    public static final String FIELD_CHECKBOX = "CHECKBOX";
    public static final String FIELD_SELECT = "SELECT";
    public static final String FIELD_LABEL = "";
    public static final String FIELD_IMAGE = "IMAGE";
    public static final String FIELD_BUTTON = "BUTTON";
    public static final String FIELD_RADIO = "radio";
    private static final String G = "radio";
    private static final String D = "SUBMIT";
    private static final String _fldnew = "RESET";
    private static final String d = "1.2";
    private int o = 0;
    private boolean _fldtry = false;
    private String[] j;
    public static final String CHECKED = "CHECKED";
    public static final String NOT_CHECKED = "";
    private String n = "";
    private String c = "./addrow.gif";
    private String cap;
    private String navigation;
    private String _fldint = "";
    private String _fldfor = "";
    private String _fldelse = "";
    private boolean _flddo = true;
    private HttpServletRequest t;
    private Vector y = null;
    private static final int _fldchar = 1;
    private static final int v = 2;
    private static final int g = 3;
    private String search = "";

    public JSPGrid2(int i1, int j1, HttpServletRequest request, String s1) {
        this.initGrid(i1, j1, request, s1);
    }

    public JSPGrid2(int i1, int j1) {
        this.initGrid(i1, j1);
    }

    public JSPGrid2() {
    }

    public void initGrid(int i1, int j1, HttpServletRequest request, String s1) {
        this.s = i1;
        this.b = j1;
        this.o = j1;
        this.t = request;
        this.n = this.t.getRequestURI();
        this._fldelse = s1;
        if (!(this.t.getParameter("__rows") == null || this.t.getParameter("__rows").equals(""))) {
            this.b = Integer.parseInt(this.t.getParameter("__rows"));
        }
        if (this.t.getParameter("__action") != null && !this.t.getParameter("__action").equals("") && this.t.getParameter("__action").equalsIgnoreCase("addrow")) {
            ++this.b;
            this.f = true;
        }
        if (this.t.getParameter("__rows") == null) {
            this._fldtry = true;
        } else {
            this.j = this.a(this.t.getParameter("__field_info"), ' ');
        }
        this._mthif(this.s, this.b);
        if (!this._fldtry) {
            this.a();
        }
    }

    public void initGrid(int i1, int j1) {
        this.s = i1;
        this.b = j1;
        this.o = j1;
        this._fldtry = true;
        this._mthif(this.s, this.b);
        this._flddo = false;
    }

    public int getCols() {
        return this.s;
    }

    public int getRows() {
        return this.b;
    }

    public void setFontColor(String s1) {
        this.a = s1;
        this._mthif("FontColor");
    }

    public String getFontColor() {
        return this.a;
    }

    public void setFontFace(String s1) {
        this._fldgoto = s1;
        this._mthif("FontFace");
    }

    public String getFontFace() {
        return this._fldgoto;
    }

    public void setFontSize(int i1) {
        this.A = i1;
        this._mthif("FontSize");
    }

    public int getFontSize() {
        return this.A;
    }

    public void setFontBold(boolean flag) {
        this.w = flag;
        this._mthif("Bold");
    }

    public boolean isFontBold() {
        return this.w;
    }

    public void setFontItalic(boolean flag) {
        this._fldif = flag;
        this._mthif("Italic");
    }

    public boolean isFontItalic() {
        return this._fldif;
    }

    public void setFontUnderline(boolean flag) {
        this.i = flag;
        this._mthif("Underline");
    }

    public boolean isFontUnderline() {
        return this.i;
    }

    public void setAlign(String s1) {
        this.p = s1;
        this._mthif("Align");
    }

    public String getAlign() {
        return this.p;
    }

    public void setVAlign(String s1) {
        this._fldcase = s1;
        this._mthif("VAlign");
    }

    public String getVAlign() {
        return this._fldcase;
    }

    public void insertScriptInTableStartTag(String s1) {
        this.q = s1;
    }

    public String getInsertedScriptInTableStartTag() {
        return this.q;
    }

    public void setNoWrap(boolean flag) {
        this.l = flag;
        this._mthif("NoWrap");
    }

    public boolean isNoWrap() {
        return this.l;
    }

    public void setWidth(String s1) {
        this.C = s1;
    }

    public String getWidth() {
        return this.C;
    }

    public void setBorder(int i1) {
        this.B = i1;
    }

    public int getBorder() {
        return this.B;
    }

    public void setCellPadding(int i1) {
        this._fldbyte = i1;
    }

    public int getCellPadding() {
        return this._fldbyte;
    }

    public void setCellSpacing(int i1) {
        this._fldvoid = i1;
    }

    public int getCellSpacing() {
        return this._fldvoid;
    }

    public void setHeight(String s1) {
        this.m = s1;
    }

    public String getHeight() {
        return this.m;
    }

    public void setBgColor(String s1) {
        this.r = s1;
    }

    public String getBgColor() {
        return this.r;
    }

    public void setLineNoHeaderBgColor(String s1) {
        this.e = s1;
    }

    public String getLineNoHeaderBgColor() {
        return this.e;
    }

    public void setEvenRowBgColor(String s1) {
        this._fldnull = s1;
    }

    public String getEvenRowBgColor() {
        return this._fldnull;
    }

    public void setOddRowBgColor(String s1) {
        this.E = s1;
    }

    public String getOddRowBgColor() {
        return this.E;
    }

    public void setEachRowHeight(String s1) {
        this.k = s1;
    }

    public String getEachRowHeight() {
        return this.k;
    }

    public void setMaxRows(int i1) {
        this.h = i1;
    }

    public int getMaxRows() {
        return this.h;
    }

    public void setFormURL(String s1) {
        this.n = s1;
    }

    public String getFormURL() {
        return this.n;
    }

    public void setCaption(String caption) {
        this.cap = caption;
    }

    public String getCaption() {
        return this.cap;
    }

    public void setNavigation(String s) {
        this.navigation = s;
    }

    public String getNavigation() {
        return this.navigation;
    }

    public void getSearchInterface(String search1) {
        this.search = search1;
    }

    public void setAddRowImageSource(String s1) {
        this.c = s1;
    }

    public String getAddRowImageSource() {
        return this.c;
    }

    public void setAddRowImageAltText(String s1) {
        this._fldint = s1;
    }

    public String getAddRowImageAltText() {
        return this._fldint;
    }

    public void setAddRowText(String s1) {
        this._fldfor = s1;
    }

    public String getAddRowText() {
        return this._fldfor;
    }

    public boolean isAddRowClicked() {
        return this.f;
    }

    public void allowAddRow(boolean flag) {
        this.F = flag;
    }

    public boolean isAddRowAllowed() {
        return this.F;
    }

    public void showLineNo() {
        this._fldlong = true;
    }

    public JSPCol2 Cols(int i1) {
        return (JSPCol2)this.y.elementAt(i1);
    }

    public void addRow() {
        this.addRow(1);
    }

    public void addRow(int i1) {
        for (int k1 = 0; k1 < i1; ++k1) {
            if (this.b >= this.h && this.h > 0) break;
            for (int j1 = 0; j1 < this.s; ++j1) {
                this.Cols(j1).a(j1 + 1, 1);
                this.Cols(j1)._mthif(this.b, 1);
            }
            ++this.b;
        }
    }

    private void _mthif(int i1, int j1) {
        this.y = new Vector();
        for (int k1 = 0; k1 < i1; ++k1) {
            JSPCol2 JSPCol2 = new JSPCol2();
            JSPCol2._mthif(k1 + 1);
            JSPCol2.a(this._fldtry, this.f, this._mthint(k1));
            this.y.addElement(JSPCol2);
            this.Cols(k1)._mthdo(k1 + 1, j1);
        }
        Object obj = null;
    }

    private void _mthif(String s1) {
        for (int i1 = 0; i1 < this.s; ++i1) {
            if (s1.equalsIgnoreCase("FontColor")) {
                this.Cols(i1).setFontColor(this.a);
                continue;
            }
            if (s1.equalsIgnoreCase("FontFace")) {
                this.Cols(i1).setFontFace(this._fldgoto);
                continue;
            }
            if (s1.equalsIgnoreCase("FontSize")) {
                this.Cols(i1).setFontSize(this.A);
                continue;
            }
            if (s1.equalsIgnoreCase("Bold")) {
                this.Cols(i1).setFontBold(this.w);
                continue;
            }
            if (s1.equalsIgnoreCase("Italic")) {
                this.Cols(i1).setFontItalic(this._fldif);
                continue;
            }
            if (s1.equalsIgnoreCase("Underline")) {
                this.Cols(i1).setFontUnderline(this.i);
                continue;
            }
            if (s1.equalsIgnoreCase("Align")) {
                this.Cols(i1).setAlign(this.p);
                continue;
            }
            if (s1.equalsIgnoreCase("VAlign")) {
                this.Cols(i1).setVAlign(this._fldcase);
                continue;
            }
            if (!s1.equalsIgnoreCase("NoWrap")) continue;
            this.Cols(i1).setNoWrap(this.l);
        }
    }

    public String getGrid() throws Exception {
        int i1 = 0;
        int byte0 = 2;
        int j1 = 4;
        i1 = byte0 * j1;
        j1+=byte0;
        if (i1 == byte0) {
            return this._mthdo(i1, byte0);
        }
        if (j1 == byte0) {
            return this._mthnew();
        }
        return this._mthint();
    }

    private String _mthint() {
        int byte0 = 2;
        String s1 = "";
        int[] ai = new int[]{73, 110, 118, 97, 108, 105, 100, 32, 76, 105, 99, 101, 110, 115, 101, 32, 75, 101, 121, 33};
        int i1 = this._mthif();
        if (i1 == 2) {
            this.h = byte0 * 2;
            s1 = this._mthelse();
        } else if (i1 == 1) {
            for (int j1 = 0; j1 < ai.length; ++j1) {
                s1 = s1 + String.valueOf((char)ai[j1]);
            }
        } else if (i1 == 3) {
            s1 = this._mthelse();
        }
        return s1;
    }

    public void reset() throws Exception {
        this._fldtry = true;
        for (int i1 = 0; i1 < this.s; ++i1) {
            this.Cols(i1).a(this._fldtry, this.f, this._mthint(i1));
            this.Cols(i1).a(this.o);
        }
        this.b = this.o;
    }

    public String getVersion() {
        return "1.2";
    }

    private String _mthelse() {
        String s1 = this._mthfor() + "\n<table " + (this.B == -1 ? "" : new StringBuilder().append("border=").append(this._mthdo(this.B)).append(" ").toString()) + (this._fldbyte == -1 ? "" : new StringBuilder().append("cellPadding=").append(this._mthdo(this._fldbyte)).append(" ").toString()) + (this._fldvoid == -1 ? "" : new StringBuilder().append("cellSpacing=").append(this._mthdo(this._fldvoid)).append(" ").toString()) + (this.m.equals("") ? "" : new StringBuilder().append("height=").append(this._mthint(this.m)).append(" ").toString()) + (this.C.equals("") ? "" : new StringBuilder().append("width=").append(this._mthint(this.C)).append(" ").toString()) + (this.r.equals("") ? "" : new StringBuilder().append("bgColor=").append(this._mthint(this.r)).append(" ").toString()) + this.q + ">\n";
        s1 = s1 + this.search;
        if (this._mthbyte()) {
            s1 = s1 + this._mthchar();
        }
        for (int i1 = 0; i1 < this.b; ++i1) {
            s1 = s1 + this.a(i1 + 1);
            s1 = s1 + this._mthfor(i1 + 1);
            for (int j1 = 0; j1 < this.s; ++j1) {
                s1 = s1 + "<td " + (this.Cols(j1).Cells(i1).getBgColor().equals("") ? "" : new StringBuilder().append(" BGCOLOR=").append(this._mthint(this.Cols(j1).Cells(i1).getBgColor())).toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getAlign().equals("") ? "" : new StringBuilder().append(" ALIGN=").append(this._mthint(this.Cols(j1).Cells(i1).getAlign())).append(" ").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getVAlign().equals("") ? "" : new StringBuilder().append(" VALIGN=").append(this._mthint(this.Cols(j1).Cells(i1).getVAlign())).append(" ").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getWidth().equals("") ? "" : new StringBuilder().append(" WIDTH=").append(this._mthint(this.Cols(j1).Cells(i1).getWidth())).append(" ").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getHeight().equals("") ? "" : new StringBuilder().append(" HEIGHT=").append(this._mthint(this.Cols(j1).Cells(i1).getHeight())).append(" ").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).isNoWrap() ? " NOWRAP" : "") + ">" + this.Cols(j1).Cells(i1).getInsertedScriptAfterTDStartTag();
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontColor().equals("") ? "" : new StringBuilder().append("<FONT COLOR=").append(this._mthint(this.Cols(j1).Cells(i1).getFontColor())).append(">").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontFace().equals("") ? "" : new StringBuilder().append("<FONT FACE=").append(this._mthint(this.Cols(j1).Cells(i1).getFontFace())).append(">").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontSize() == 0 ? "" : new StringBuilder().append("<FONT SIZE=").append(this._mthdo(this.Cols(j1).Cells(i1).getFontSize())).append(">").toString());
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontBold() ? "<B>" : "");
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontItalic() ? "<I>" : "");
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontUnderline() ? "<U>" : "");
                s1 = s1 + this._mthdo(i1, j1);
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontUnderline() ? "</U>" : "");
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontItalic() ? "</I>" : "");
                s1 = s1 + (this.Cols(j1).Cells(i1).isFontBold() ? "</B>" : "");
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontSize() == 0 ? "" : "</FONT>");
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontFace().equals("") ? "" : "</FONT>");
                s1 = s1 + (this.Cols(j1).Cells(i1).getFontColor().equals("") ? "" : "</FONT>");
                s1 = s1 + this.Cols(j1).Cells(i1).getInsertedScriptBeforeTDEndTag() + "</td>\n";
            }
            s1 = s1 + "\n</tr>\n";
        }
        s1 = !this.Cols(0).Header().getClassID().equals("") ? s1 + "<tr HEIGHT=" + this._mthint(this.k) + "><td colspan=\"" + this.s + "\" class=" + this._mthint(this.Cols(0).Header().getClassID()) + "><b>" + this.navigation + "</b></td></tr>" : s1 + "<tr HEIGHT=\"" + this._mthint(this.k) + "\"><td colspan=\"" + this.s + "\" " + (this.Cols(0).Header().getBgColor().equals("") ? "" : new StringBuilder().append(" bgcolor=").append(this._mthint(this.Cols(0).Header().getBgColor())).toString()) + "><font color=\"#FFFFFF\"><b>" + this.navigation + "</b></font></td></tr>";
        if ((this.b < this.h || this.h <= 0) && this.F) {
            s1 = s1 + this._mthdo();
        }
        s1 = s1 + "\n</table>\n";
        return s1;
    }

    private String _mthfor() {
        return "<INPUT TYPE=" + this._mthint("HIDDEN") + " NAME=\"__rows\" VALUE=" + this._mthdo(this.b) + ">" + "<INPUT TYPE=" + this._mthint("HIDDEN") + " NAME=\"__action\" VALUE=\"\">" + "<INPUT TYPE=" + this._mthint("HIDDEN") + " NAME=\"__field_info\" VALUE=" + this._mthint(this._mthnew()) + ">";
    }

    private String _mthdo(int i1, int j1) {
        String s1 = "";
        s1 = this.Cols(j1).getFieldType().equalsIgnoreCase("") ? s1 + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(j1).Cells(i1).getURL())).append(" ").append(this.Cols(j1).Cells(i1).getInsertedFieldScript()).append(">").toString()) + this.a(this.Cols(j1).Cells(i1).getFieldValue(), j1) + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : "</a>") : (this.Cols(j1).getFieldType().equalsIgnoreCase("TEXTAREA") ? s1 + "<TEXTAREA " + (this.Cols(j1).Cells(i1).getFieldName().equals("") ? "" : new StringBuilder().append(" NAME=").append(this._mthint(this.Cols(j1).Cells(i1).getFieldName())).toString()) + " " + (this.Cols(j1).Cells(i1).getTextAreaRows() == 0 ? "" : new StringBuilder().append("ROWS=").append(this._mthdo(this.Cols(j1).Cells(i1).getTextAreaRows())).toString()) + " " + (this.Cols(j1).Cells(i1).getTextAreaCols() == 0 ? "" : new StringBuilder().append("COLS=").append(this._mthdo(this.Cols(j1).Cells(i1).getTextAreaCols())).toString()) + " " + this.Cols(j1).Cells(i1).getInsertedFieldScript() + ">" + this._mthfor(this.Cols(j1).Cells(i1).getFieldValue()) + "</TEXTAREA>" : (this.Cols(j1).getFieldType().equalsIgnoreCase("HIDDEN") ? s1 + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(j1).Cells(i1).getURL())).append(" ").append(this.Cols(j1).Cells(i1).getInsertedFieldScript()).append(">").toString()) + this.a(this.Cols(j1).Cells(i1).getFieldValue(), j1) + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : "</a>") + "<INPUT TYPE=" + this._mthint(this.Cols(j1).getFieldType()) + " " + (this.Cols(j1).Cells(i1).getFieldName().equals("") ? "" : new StringBuilder().append(" NAME=").append(this._mthint(this.Cols(j1).getFieldName())).toString()) + (this.Cols(j1).Cells(i1).getFieldValue().equals("") || this.Cols(j1).getFieldType().equalsIgnoreCase("CHECKBOX") || this.Cols(j1).getFieldType().equalsIgnoreCase("radio") ? new StringBuilder().append(" VALUE=").append(this._mthint(this.a(this.Cols(j1).Cells(i1).getFieldValue(), j1))).toString() : new StringBuilder().append(" VALUE=").append(this._mthint(this._mthfor(this.Cols(j1).Cells(i1).getFieldValue()))).toString()) + ">" : (this.Cols(j1).getFieldType().equalsIgnoreCase("SELECT") ? s1 + "<SELECT " + (this.Cols(j1).Cells(i1).getFieldName().equals("") ? "" : new StringBuilder().append(" NAME=").append(this._mthint(this.Cols(j1).Cells(i1).getFieldName())).toString()) + (this.Cols(j1).Cells(i1).getFieldLength() == -1 ? "" : new StringBuilder().append(" MAXLENGTH=").append(this._mthdo(this.Cols(j1).Cells(i1).getFieldLength())).append(" ").toString()) + (this.Cols(j1).Cells(i1).getFieldSize() == 0 ? "" : new StringBuilder().append("SIZE=").append(this._mthdo(this.Cols(j1).Cells(i1).getFieldSize())).toString()) + " " + this.Cols(j1).Cells(i1).getInsertedFieldScript() + ">" + this.a(this.Cols(j1).getDefaultValue(), this.Cols(j1).Cells(i1).getSelectedText()) + "</SELECT>" : (this.Cols(j1).getFieldType().equalsIgnoreCase("IMAGE") ? s1 + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(j1).Cells(i1).getURL())).append(" ").append(this.Cols(j1).Cells(i1).getInsertedFieldScript()).append(">").toString()) + "<img src=" + this._mthint(this.Cols(j1).Cells(i1).getImageSource()) + " BORDER=\"0\" " + (this.Cols(j1).Cells(i1).getImageAltText().equals("") ? "" : new StringBuilder().append("ALT=").append(this._mthint(this.Cols(j1).Cells(i1).getImageAltText())).toString()) + ">" + (this.Cols(j1).Cells(i1).getURL().equals("") ? "" : "</a>") : s1 + "<INPUT TYPE=" + this._mthint(this.Cols(j1).getFieldType()) + " " + (!this.Cols(j1).getFieldType().equalsIgnoreCase("CHECKBOX") || !this.Cols(j1).getFieldType().equalsIgnoreCase("radio") || !this.Cols(j1).Cells(i1).getFieldValue().equals("CHECKED") ? "" : " CHECKED ") + (this.Cols(j1).Cells(i1).getFieldName().equals("") ? "" : new StringBuilder().append(" NAME=").append(this.Cols(j1).getFieldType().equalsIgnoreCase("radio") || this.Cols(j1).getFieldType().equalsIgnoreCase("CHECKBOX") ? this._mthint(this.Cols(j1).getFieldName()) : this._mthint(this.Cols(j1).Cells(i1).getFieldName())).toString()) + (this.Cols(j1).Cells(i1).getFieldValue().equals("") || this.Cols(j1).getFieldType().equalsIgnoreCase("CHECKBOX") || this.Cols(j1).getFieldType().equalsIgnoreCase("radio") ? new StringBuilder().append(" VALUE=").append(this._mthint(this.a(this.Cols(j1).Cells(i1).getFieldValue(), j1))).toString() : new StringBuilder().append(" VALUE=").append(this._mthint(this._mthfor(this.Cols(j1).Cells(i1).getFieldValue()))).toString()) + (this.Cols(j1).Cells(i1).getFieldLength() == -1 ? "" : new StringBuilder().append(" MAXLENGTH=").append(this._mthdo(this.Cols(j1).Cells(i1).getFieldLength())).append(" ").toString()) + (this.Cols(j1).Cells(i1).getFieldSize() == 0 ? "" : new StringBuilder().append(" SIZE=").append(this._mthdo(this.Cols(j1).Cells(i1).getFieldSize())).append(" ").toString()) + " " + this.Cols(j1).Cells(i1).getInsertedFieldScript() + ">"))));
        s1 = s1 + (this.Cols(j1).Cells(i1).getLookupURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(j1).Cells(i1).getLookupURL())).append(">").toString()) + this.Cols(j1).Cells(i1).getLookupText() + (this.Cols(j1).Cells(i1).getLookupImageSource().equals("") ? "" : new StringBuilder().append("<img src=").append(this._mthint(this.Cols(j1).Cells(i1).getLookupImageSource())).append(" BORDER=\"0\" ").toString()) + (this.Cols(j1).Cells(i1).getLookupImageAltText().equals("") ? "" : new StringBuilder().append("ALT=").append(this._mthint(this.Cols(j1).Cells(i1).getLookupImageAltText())).toString()) + (this.Cols(j1).Cells(i1).getLookupImageSource().equals("") ? "" : "> ") + (this.Cols(j1).Cells(i1).getLookupURL().equals("") ? "" : "</a>");
        return s1;
    }

    private String _mthdo() {
        String s1 = "";
        if (!this.n.equals("")) {
            s1 = "<tr>";
            s1 = s1 + (this._fldlong ? "<td>&nbsp</td>" : "");
            for (int i1 = 0; i1 < this.s; ++i1) {
                s1 = s1 + "<td " + (this.Cols(i1).getAlign().equals("") ? "" : new StringBuilder().append(" ALIGN=").append(this._mthint(this.Cols(i1).getAlign())).append(" ").toString());
                s1 = s1 + (this.Cols(i1).getVAlign().equals("") ? "" : new StringBuilder().append(" VALIGN=").append(this._mthint(this.Cols(i1).getVAlign())).append(" ").toString()) + ">";
                if (i1 == 0 || i1 == this.s - 1) {
                    s1 = s1 + "<a href=\"javascript:document." + this._fldelse + ".__action.value='addrow';document." + this._fldelse + ".action='" + this.n + "';document." + this._fldelse + ".submit();\">";
                    s1 = s1 + (this.c.equals("") ? " " : new StringBuilder().append("<IMG SRC=").append(this._mthint(this.c)).append(" BORDER=\"0\" ").toString()) + (this._fldint.equals("") ? "" : new StringBuilder().append("ALT=").append(this._mthint(this._fldint)).toString()) + (!this.c.equals("") || !this._fldint.equals("") ? ">" : "") + this._fldfor;
                } else {
                    s1 = s1 + "&nbsp";
                }
                s1 = s1 + "</td>";
            }
            s1 = s1 + "</tr>";
        }
        return s1;
    }

    private String _mthchar() {
        String s1 = "";
        if (!this.Cols(0).Header().getClassID().equals("")) {
            s1 = s1 + "<tr HEIGHT=" + this._mthint(this.k) + "><td colspan=\"" + this.s + "\"><hr size=\"1\"></td></tr>";
            s1 = s1 + "<tr HEIGHT=" + this._mthint(this.k) + "><td colspan=\"" + this.s + "\" class=" + this._mthint(this.Cols(0).Header().getClassID()) + "><b>" + this.cap + "</b></td></tr>";
        } else {
            s1 = s1 + "<tr HEIGHT=" + this._mthint(this.k) + "><td colspan=\"" + this.s + "\"><hr size=\"1\"></td></tr>";
            s1 = s1 + "<tr HEIGHT=\"" + this._mthint(this.k) + "\"><td colspan=\"" + this.s + "\" " + (this.Cols(0).Header().getBgColor().equals("") ? "" : new StringBuilder().append(" bgcolor=").append(this._mthint(this.Cols(0).Header().getBgColor())).toString()) + "><font color=\"#FFFFFF\"><b>" + this.cap + "</b></font></td></tr>";
        }
        s1 = s1 + "<tr" + (this.k.equals("") ? "" : new StringBuilder().append(" HEIGHT=").append(this._mthint(this.k)).toString()) + ">";
        if (this._fldlong) {
            s1 = s1 + "<td " + (this.e.equals("") ? "" : new StringBuilder().append(" bgcolor=").append(this._mthint(this.e)).toString()) + ">&nbsp</td>";
        }
        for (int i1 = 0; i1 < this.s; ++i1) {
            s1 = !this.Cols(i1).Header().getClassID().equals("") ? s1 + "<td class = " + this._mthint(this.Cols(i1).Header().getClassID()) + " " + (this.Cols(i1).Header().getWidth().equals("") ? "" : new StringBuilder().append(" width=").append(this._mthint(this.Cols(i1).Header().getWidth())).toString()) + (this.Cols(i1).Header().getHeight().equals("") ? "" : new StringBuilder().append(" height=").append(this._mthint(this.Cols(i1).Header().getHeight())).toString()) + (this.Cols(i1).Header().getAlign().equals("") ? "" : new StringBuilder().append(" ALIGN=").append(this._mthint(this.Cols(i1).Header().getAlign())).append(" ").toString()) + (this.Cols(i1).Header().getVAlign().equals("") ? "" : new StringBuilder().append(" VALIGN=").append(this._mthint(this.Cols(i1).Header().getVAlign())).append(" ").toString()) + (this.Cols(i1).Header().isNoWrap() ? " nowrap" : "") + ">" + this.Cols(i1).Header().getInsertedScriptAfterTDStartTag() + (this.Cols(i1).Header().getURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(i1).Header().getURL())).append(">").toString()) + (this.Cols(i1).Header().getCaption().equals("") ? "" : this.Cols(i1).Header().getCaption()) + (this.Cols(i1).Header().getImageSource().equals("") ? "" : new StringBuilder().append("<img src=").append(this._mthint(this.Cols(i1).Header().getImageSource())).append(" BORDER=\"0\" ").toString()) + (this.Cols(i1).Header().getImageAltText().equals("") ? "" : new StringBuilder().append("ALT=").append(this._mthint(this.Cols(i1).Header().getImageAltText())).toString()) + (this.Cols(i1).Header().getImageSource().equals("") ? "" : ">") + (this.Cols(i1).Header().getURL().equals("") ? "" : "</a>") + this.Cols(i1).Header().getInsertedScriptBeforeTDEndTag() + "</td>" : s1 + "<td " + (this.Cols(i1).Header().getBgColor().equals("") ? "" : new StringBuilder().append(" bgcolor=").append(this._mthint(this.Cols(i1).Header().getBgColor())).toString()) + (this.Cols(i1).Header().getWidth().equals("") ? "" : new StringBuilder().append(" width=").append(this._mthint(this.Cols(i1).Header().getWidth())).toString()) + (this.Cols(i1).Header().getHeight().equals("") ? "" : new StringBuilder().append(" height=").append(this._mthint(this.Cols(i1).Header().getHeight())).toString()) + (this.Cols(i1).Header().getAlign().equals("") ? "" : new StringBuilder().append(" ALIGN=").append(this._mthint(this.Cols(i1).Header().getAlign())).append(" ").toString()) + (this.Cols(i1).Header().getVAlign().equals("") ? "" : new StringBuilder().append(" VALIGN=").append(this._mthint(this.Cols(i1).Header().getVAlign())).append(" ").toString()) + (this.Cols(i1).Header().isNoWrap() ? " nowrap" : "") + ">" + this.Cols(i1).Header().getInsertedScriptAfterTDStartTag() + (this.Cols(i1).Header().getURL().equals("") ? "" : new StringBuilder().append("<a href=").append(this._mthint(this.Cols(i1).Header().getURL())).append(">").toString()) + (this.Cols(i1).Header().getFontFace().equals("") ? "" : new StringBuilder().append("<font face=").append(this._mthint(this.Cols(i1).Header().getFontFace())).append(">").toString()) + (this.Cols(i1).Header().getFontSize() == 0 ? "" : new StringBuilder().append("<font size=").append(this._mthdo(this.Cols(i1).Header().getFontSize())).append(">").toString()) + (this.Cols(i1).Header().getFontColor().equals("") ? "" : new StringBuilder().append("<font color=").append(this._mthint(this.Cols(i1).Header().getFontColor())).append(">").toString()) + (this.Cols(i1).Header().isFontBold() ? "<B>" : "") + (this.Cols(i1).Header().isFontItalic() ? "<I>" : "") + (this.Cols(i1).Header().isFontUnderline() ? "<U>" : "") + (this.Cols(i1).Header().getCaption().equals("") ? "" : this.Cols(i1).Header().getCaption()) + (this.Cols(i1).Header().isFontItalic() ? "</I>" : "") + (this.Cols(i1).Header().isFontUnderline() ? "</U>" : "") + (this.Cols(i1).Header().isFontBold() ? "</B>" : "") + (this.Cols(i1).Header().getFontColor().equals("") ? "" : "</font>") + (this.Cols(i1).Header().getFontSize() == 0 ? "" : "</font>") + (this.Cols(i1).Header().getFontFace().equals("") ? "" : "</font>") + (this.Cols(i1).Header().getImageSource().equals("") ? "" : new StringBuilder().append("<img src=").append(this._mthint(this.Cols(i1).Header().getImageSource())).append(" BORDER=\"0\" ").toString()) + (this.Cols(i1).Header().getImageAltText().equals("") ? "" : new StringBuilder().append("ALT=").append(this._mthint(this.Cols(i1).Header().getImageAltText())).toString()) + (this.Cols(i1).Header().getImageSource().equals("") ? "" : ">") + (this.Cols(i1).Header().getURL().equals("") ? "" : "</a>") + this.Cols(i1).Header().getInsertedScriptBeforeTDEndTag() + "</td>";
        }
        s1 = s1 + "</tr>" + "\n";
        return s1;
    }

    private void a() {
        String s2 = this.t.getParameter("__action");
        block0 : for (int j1 = 0; j1 < this.s; ++j1) {
            String s1 = this._mthint(j1);
            for (int i1 = 0; i1 < this.b; ++i1) {
                if (s1.equalsIgnoreCase("radio") || s1.equalsIgnoreCase("CHECKBOX")) {
                    if (this.t.getParameter(this.a(j1, i1)) == null) {
                        if (s2 != null && s2.equalsIgnoreCase("addrow")) {
                            if (i1 == this.b - 1) continue;
                            this.Cols(j1).Cells(i1).setFieldValue("");
                            continue;
                        }
                        if (s2 == null) continue;
                        this.Cols(j1).Cells(i1).setFieldValue("");
                        continue;
                    }
                    this.Cols(j1).Cells(i1).setFieldValue("CHECKED");
                    continue;
                }
                if (s1.equalsIgnoreCase("SELECT")) {
                    if (this.t.getParameter(this.a(j1, i1)) == null) continue;
                    this.Cols(j1).Cells(i1).setSelectedText(this.t.getParameter(this.a(j1, i1)));
                    continue;
                }
                if (!s1.equalsIgnoreCase("TEXT") && !s1.equalsIgnoreCase("HIDDEN") && !s1.equalsIgnoreCase("TEXTAREA")) continue block0;
                if (this.t.getParameter(this.a(j1, i1)) == null) continue;
                this.Cols(j1).Cells(i1).setFieldValue(this.t.getParameter(this.a(j1, i1)));
            }
        }
    }

    private String _mthint(int i1) {
        String s1 = "";
        if (!this._fldtry) {
            if (this.j[i1].substring(0, 2).equalsIgnoreCase("te")) {
                s1 = "TEXT";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("hi")) {
                s1 = "HIDDEN";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("ta")) {
                s1 = "TEXTAREA";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("cb")) {
                s1 = "CHECKBOX";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("rd")) {
                s1 = "radio";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("se")) {
                s1 = "SELECT";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("la")) {
                s1 = "";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("im")) {
                s1 = "IMAGE";
            } else if (this.j[i1].substring(0, 2).equalsIgnoreCase("bu")) {
                s1 = "BUTTON";
            }
        }
        return s1;
    }

    private String a(int i1, int j1) {
        return this.j[i1].substring(3, this.j[i1].length()) + "_" + (j1 + 1);
    }

    private String a(String s1, String s2) {
        String s3 = "";
        String[] as = this.a(s1, '|');
        for (int i1 = 0; i1 < as.length; ++i1) {
            s3 = s3 + "<OPTION" + (as[i1].equals(s2) ? " SELECTED " : "") + ">" + this._mthfor(as[i1]) + "</OPTION>";
        }
        return s3;
    }

    private String a(int i1) {
        String s1 = i1 % 2 == 0 ? "\n<tr" + (this._fldnull.equals("") ? "" : new StringBuilder().append(" BGCOLOR=").append(this._mthint(this._fldnull)).toString()) : "\n<tr" + (this.E.equals("") ? "" : new StringBuilder().append(" BGCOLOR=").append(this._mthint(this.E)).toString());
        return s1 + (this.k.equals("") ? "" : new StringBuilder().append(" HEIGHT=").append(this._mthint(this.k)).toString()) + ">\n";
    }

    private String _mthfor(int i1) {
        String s1 = "";
        String s2 = "";
        String s3 = "";
        s2 = this._fldgoto.equals("") ? "" : "<FONT FACE=" + this._mthint(this._fldgoto) + ">";
        s2 = s2 + (this.A == 0 ? "" : new StringBuilder().append("<FONT SIZE=").append(this._mthdo(this.A)).append(">").toString());
        s2 = s2 + (this.w ? "<B>" : "");
        s2 = s2 + (this.a.equals("") ? "" : new StringBuilder().append("<FONT COLOR=").append(this._mthint(this.a)).append(">").toString());
        s3 = this._fldgoto.equals("") ? "" : "</FONT>";
        s3 = s3 + (this.A == 0 ? "" : "</FONT>");
        s3 = s3 + (this.w ? "</B>" : "");
        s3 = s3 + (this.a.equals("") ? "" : "</FONT>");
        if (this._fldlong) {
            s1 = this.z == 0 ? "<td align=\"right\">" + s2 + i1 + s3 + "</td>" : (i1 > this.x ? "<td></td>" : "<td align=\"right\">" + s2 + (this.z + i1 - 1) + s3 + "</td>");
        }
        return s1;
    }

    void _mthnew(int i1) {
        this.z = i1;
    }

    void _mthtry(int i1) {
        this.x = i1;
    }

    void _mthif(int i1) {
        this.b = i1;
    }

    private boolean _mthbyte() {
        boolean flag = false;
        for (int i1 = 0; i1 < this.s; ++i1) {
            if (!(this.Cols(i1).Header().getCaption() == null || this.Cols(i1).Header().getCaption().equals(""))) {
                flag = true;
                break;
            }
            if (this.Cols(i1).Header().getImageSource() == null) continue;
            if (this.Cols(i1).Header().getImageSource().equals("")) continue;
            flag = true;
            break;
        }
        return flag;
    }

    private String _mthnew() {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i1 = 0; i1 < this.s; ++i1) {
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("TEXT")) {
                stringbuffer.append("te_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("HIDDEN")) {
                stringbuffer.append("hi_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("TEXTAREA")) {
                stringbuffer.append("ta_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("CHECKBOX")) {
                stringbuffer.append("cb_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("radio")) {
                stringbuffer.append("rd_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("SELECT")) {
                stringbuffer.append("se_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("")) {
                stringbuffer.append("la_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (this.Cols(i1).getFieldType().equalsIgnoreCase("IMAGE")) {
                stringbuffer.append("im_").append(this.Cols(i1).getFieldName()).append(" ");
                continue;
            }
            if (!this.Cols(i1).getFieldType().equalsIgnoreCase("BUTTON")) continue;
            stringbuffer.append("bu_").append(this.Cols(i1).getFieldName()).append(" ");
        }
        return stringbuffer.toString().trim();
    }

    String[] a(String s1, char c1) {
        int k1 = 0;
        int l1 = 1;
        int i2 = 0;
        int i1 = s1.indexOf(c1);
        while (i1 != -1) {
            i1 = s1.indexOf(c1, i1 + 1);
            ++l1;
        }
        String[] as = new String[l1];
        if (l1 == 1) {
            as[0] = s1;
        } else {
            int j1 = s1.indexOf(c1);
            while (j1 != -1) {
                as[i2] = i2 == 0 ? s1.substring(k1, j1) : s1.substring(k1 + 1, j1);
                k1 = j1;
                j1 = s1.indexOf(c1, j1 + 2);
                ++i2;
            }
            as[i2] = s1.substring(k1 + 1, s1.length());
        }
        return as;
    }

    String _mthint(String s1) {
        return "\"" + s1 + "\"";
    }

    String _mthdo(int i1) {
        return "\"" + i1 + "\"";
    }

    private String _mthfor(String s1) {
        if (s1.indexOf(60) != -1 || s1.indexOf(62) != -1 || s1.indexOf(34) != -1 || s1.indexOf(38) != -1) {
            return this.a(s1);
        }
        return s1;
    }

    private String a(String s1, int i1) {
        if (s1.indexOf(60) != -1 || s1.indexOf(62) != -1 || s1.indexOf(34) != -1 || s1.indexOf(38) != -1) {
            if (!this.Cols(i1).a()) {
                return this.a(s1);
            }
            return s1;
        }
        return s1;
    }

    private String a(String s1) {
        StringBuffer stringbuffer = new StringBuffer(s1.length());
        for (int i1 = 0; i1 < s1.length(); ++i1) {
            char c1 = s1.charAt(i1);
            if (c1 == '<') {
                stringbuffer.append("&lt;");
                continue;
            }
            if (c1 == '>') {
                stringbuffer.append("&gt;");
                continue;
            }
            if (c1 == '\"') {
                stringbuffer.append("&quot;");
                continue;
            }
            if (c1 == '&') {
                stringbuffer.append("&amp;");
                continue;
            }
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }

    private String a(String s1, String s2, String s3) {
        String s4 = s1;
        int i1 = 0;
        while (i1 != -1) {
            i1 = s4.indexOf(s2);
            if (i1 == -1) continue;
            if (i1 == 0) {
                s4 = s3 + s4.substring(s2.length());
                continue;
            }
            s4 = s4.substring(0, i1) + s3 + s4.substring(i1 + s2.length());
        }
        return s4;
    }

    private boolean _mthdo(String s1) {
        int[] ai = new int[]{50, 53, 57};
        int[] ai1 = new int[]{50, 52, 75, 89};
        int[] ai2 = new int[]{69, 68, 72, 80, 77, 88};
        int[] ai3 = new int[]{52, 48, 56};
        int[] ai4 = new int[]{66, 72, 89, 78};
        int[] ai5 = new int[]{84, 85, 90, 86};
        int[] ai6 = new int[]{81, 66, 82, 88};
        int[] ai7 = new int[]{52, 56, 48};
        int[] ai8 = new int[]{53, 55, 69, 76};
        int[] ai9 = new int[]{67, 70, 74, 83, 89, 90};
        int[] ai10 = new int[]{51, 55, 57};
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        boolean flag9 = false;
        boolean flag10 = false;
        boolean flag11 = false;
        boolean flag12 = false;
        if (s1.length() == 13 || s1.length() == 18) {
            block15 : for (int l3 = 0; l3 < 13; ++l3) {
                switch (l3) {
                    default: {
                        continue block15;
                    }
                    case 0: {
                        for (int i1 = 0; i1 < ai.length; ++i1) {
                            if (ai[i1] != s1.charAt(l3)) continue;
                            flag = true;
                        }
                        continue block15;
                    }
                    case 1: {
                        for (int j1 = 0; j1 < ai1.length; ++j1) {
                            if (ai1[j1] != s1.charAt(l3)) continue;
                            flag1 = true;
                        }
                        continue block15;
                    }
                    case 2: {
                        for (int k1 = 0; k1 < ai2.length; ++k1) {
                            if (ai2[k1] != s1.charAt(l3)) continue;
                            flag2 = true;
                        }
                        continue block15;
                    }
                    case 3: {
                        for (int l1 = 0; l1 < ai3.length; ++l1) {
                            if (ai3[l1] != s1.charAt(l3)) continue;
                            flag3 = true;
                        }
                        continue block15;
                    }
                    case 4: {
                        if (s1.charAt(l3) != '-') continue block15;
                        flag11 = true;
                        continue block15;
                    }
                    case 5: {
                        for (int i2 = 0; i2 < ai4.length; ++i2) {
                            if (ai4[i2] != s1.charAt(l3)) continue;
                            flag4 = true;
                        }
                        continue block15;
                    }
                    case 6: {
                        for (int j2 = 0; j2 < ai5.length; ++j2) {
                            if (ai5[j2] != s1.charAt(l3)) continue;
                            flag5 = true;
                        }
                        continue block15;
                    }
                    case 7: {
                        for (int k2 = 0; k2 < ai6.length; ++k2) {
                            if (ai6[k2] != s1.charAt(l3)) continue;
                            flag6 = true;
                        }
                        continue block15;
                    }
                    case 8: {
                        if (s1.charAt(l3) != '-') continue block15;
                        flag12 = true;
                        continue block15;
                    }
                    case 9: {
                        for (int l2 = 0; l2 < ai7.length; ++l2) {
                            if (ai7[l2] != s1.charAt(l3)) continue;
                            flag7 = true;
                        }
                        continue block15;
                    }
                    case 10: {
                        for (int i3 = 0; i3 < ai8.length; ++i3) {
                            if (ai8[i3] != s1.charAt(l3)) continue;
                            flag8 = true;
                        }
                        continue block15;
                    }
                    case 11: {
                        for (int j3 = 0; j3 < ai9.length; ++j3) {
                            if (ai9[j3] != s1.charAt(l3)) continue;
                            flag9 = true;
                        }
                        continue block15;
                    }
                    case 12: {
                        for (int k3 = 0; k3 < ai10.length; ++k3) {
                            if (ai10[k3] != s1.charAt(l3)) continue;
                            flag10 = true;
                        }
                    }
                }
            }
        }
        return flag && flag1 && flag2 && flag3 && flag11 && flag4 && flag5 && flag6 && flag12 && flag7 && flag8 && flag9 && flag10;
    }

    private int _mthif() {
        String s1 = "";
        boolean flag = false;
        return 2;
    }

    private String _mthtry() {
        String s1 = this._mthgoto();
        if (s1.equals("")) {
            s1 = this._mthcase();
        }
        return s1;
    }

    private String _mthgoto() {
        String s2;
        String s3 = "";
        String s4 = "";
        String s5 = "";
        int[] ai = new int[]{99, 111, 109, 47, 112, 111, 119, 101, 114, 111, 98, 106, 47, 74, 83, 80, 71, 114, 105, 100, 46, 99, 108, 97, 115, 115};
        int[] ai1 = new int[]{74, 83, 80, 71, 114, 105, 100, 46, 106, 97, 114};
        int[] ai2 = new int[]{106, 115, 112, 103, 114, 105, 100, 46, 108, 105, 99, 101, 110, 115, 101};
        for (int i1 = 0; i1 < ai.length; ++i1) {
            s3 = s3 + String.valueOf((char)ai[i1]);
        }
        for (int j1 = 0; j1 < ai1.length; ++j1) {
            s4 = s4 + String.valueOf((char)ai1[j1]);
        }
        for (int k1 = 0; k1 < ai2.length; ++k1) {
            s5 = s5 + String.valueOf((char)ai2[k1]);
        }
        try {
            BufferedReader bufferedreader;
            String s1 = ClassLoader.getSystemResource(s3).getFile();
            s1 = s1.substring(0, s1.indexOf(s4)) + s5;
            if (s1.length() > 5 && s1.substring(0, 5).equalsIgnoreCase("file:")) {
                s1 = s1.substring("file:".length(), s1.length());
            }
            if ((s2 = (bufferedreader = new BufferedReader(new FileReader(s1))).readLine()) == null) {
                s2 = "";
            }
            bufferedreader.close();
        }
        catch (Exception exception) {
            s2 = "";
        }
        return s2;
    }

    private String _mthcase() {
        String s2;
        String s3 = "";
        String s4 = "";
        String s5 = "";
        int[] ai = new int[]{99, 111, 109, 47, 112, 111, 119, 101, 114, 111, 98, 106, 47, 74, 83, 80, 71, 114, 105, 100, 46, 99, 108, 97, 115, 115};
        int[] ai1 = new int[]{74, 83, 80, 71, 114, 105, 100, 80, 114, 111, 46, 106, 97, 114};
        int[] ai2 = new int[]{106, 115, 112, 103, 114, 105, 100, 46, 108, 105, 99, 101, 110, 115, 101};
        for (int i1 = 0; i1 < ai.length; ++i1) {
            s3 = s3 + String.valueOf((char)ai[i1]);
        }
        for (int j1 = 0; j1 < ai1.length; ++j1) {
            s4 = s4 + String.valueOf((char)ai1[j1]);
        }
        for (int k1 = 0; k1 < ai2.length; ++k1) {
            s5 = s5 + String.valueOf((char)ai2[k1]);
        }
        try {
            BufferedReader bufferedreader;
            String s1 = ClassLoader.getSystemResource(s3).getFile();
            s1 = s1.substring(0, s1.indexOf(s4)) + s5;
            if (s1.length() > 5 && s1.substring(0, 5).equalsIgnoreCase("file:")) {
                s1 = s1.substring("file:".length(), s1.length());
            }
            if ((s2 = (bufferedreader = new BufferedReader(new FileReader(s1))).readLine()) == null) {
                s2 = "";
            }
            bufferedreader.close();
        }
        catch (Exception exception) {
            s2 = "";
        }
        return s2;
    }
}

