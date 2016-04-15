/*
 * Decompiled with CFR 0_102.
 */
package comv2.aunwesha.JSPGrid;

import java.util.Vector;

public class JSPCol2 {
    private String p = "";
    private String j = "";
    private String u = "";
    private String v = "";
    private String s = "";
    private String _fldbyte = "";
    private String a = "";
    private String _fldcase = "";
    private int o = 0;
    private boolean n = false;
    private boolean _fldif = false;
    private boolean _fldvoid = false;
    private String i = "";
    private String _fldtry = "";
    private boolean f = false;
    private String e = "";
    private String q = "__Field";
    private int c = -1;
    private int h = 0;
    private String _fldint = "";
    private String w = "";
    private String _flddo = "";
    private int t = 0;
    private int _fldlong = 0;
    private String d = "";
    private String l = "";
    private String b = "";
    private String _fldgoto = "";
    private boolean g = false;
    private boolean _fldfor = false;
    private int r = 0;
    private boolean _fldnew = false;
    private boolean _fldelse = false;
    private String m = "";
    private Vector _fldnull = null;
    private JSPHeader k = new JSPHeader();
    private int _fldchar = 0;

    public void setWidth(String s1) {
        this.p = s1;
        this._mthif("Width");
    }

    public String getWidth() {
        return this.p;
    }

    public void setFontColor(String s1) {
        this.a = s1;
        this._mthif("FontColor");
        this.Header().setFontColor(this.a);
    }

    public String getFontColor() {
        return this.a;
    }

    public void setFontFace(String s1) {
        this._fldcase = s1;
        this._mthif("FontFace");
        this.Header().setFontFace(this._fldcase);
    }

    public String getFontFace() {
        return this._fldcase;
    }

    public void setFontSize(int i1) {
        this.o = i1;
        this._mthif("FontSize");
        this.Header().setFontSize(this.o);
    }

    public int getFontSize() {
        return this.o;
    }

    public void setFontBold(boolean flag) {
        this.n = flag;
        this._mthif("Bold");
        this.Header().setFontBold(this.n);
    }

    public boolean isFontBold() {
        return this.n;
    }

    public void setFontItalic(boolean flag) {
        this._fldif = flag;
        this._mthif("Italic");
        this.Header().setFontItalic(this._fldif);
    }

    public boolean isFontItalic() {
        return this._fldif;
    }

    public void setFontUnderline(boolean flag) {
        this._fldvoid = flag;
        this._mthif("Underline");
        this.Header().setFontUnderline(this._fldvoid);
    }

    public boolean isFontUnderline() {
        return this._fldvoid;
    }

    public void setBgColor(String s1) {
        this.j = s1;
        this._mthif("BgColor");
        this.Header().setBgColor(this.j);
    }

    public String getBgColor() {
        return this.j;
    }

    public void setLookupText(String s1) {
        this.u = s1;
        this._mthif("LookupText");
    }

    public String getLookupText() {
        return this.u;
    }

    public void setLookupImageSource(String s1) {
        this.v = s1;
        this._mthif("LookupImageSource");
    }

    public String getLookupImageSource() {
        return this.v;
    }

    public void setLookupImageAltText(String s1) {
        this.s = s1;
        this._mthif("LookupImageAltText");
    }

    public String getLookupImageAltText() {
        return this.s;
    }

    public void setLookupURL(String s1) {
        this._fldbyte = s1;
        this._mthif("LookupURL");
    }

    public String getLookupURL() {
        return this._fldbyte;
    }

    public void setAlign(String s1) {
        this.i = s1;
        this._mthif("Align");
        this.Header().setAlign(this.i);
    }

    public String getAlign() {
        return this.i;
    }

    public void setVAlign(String s1) {
        this._fldtry = s1;
        this._mthif("VAlign");
        this.Header().setVAlign(this._fldtry);
    }

    public String getVAlign() {
        return this._fldtry;
    }

    public void setNoWrap(boolean flag) {
        this.f = flag;
        this._mthif("NoWrap");
        this.Header().setNoWrap(this.f);
    }

    public boolean isNoWrap() {
        return this.f;
    }

    public void setFieldName(String s1) {
        this.q = s1.indexOf(34) != -1 || s1.indexOf(32) != -1 ? this.a(s1) : s1;
        this._mthif("FieldName");
    }

    public String getFieldName() {
        if (this.q.equalsIgnoreCase("__Field")) {
            return this.q + "_" + this.r;
        }
        return this.q;
    }

    public void setDefaultValue(String s1) {
        this.e = s1;
        this._mthif("FieldValue");
    }

    public String getDefaultValue() {
        return this.e;
    }

    public void setFieldLength(int i1) {
        this.c = i1;
        this._mthif("FieldLength");
    }

    public int getFieldLength() {
        return this.c;
    }

    public void setFieldSize(int i1) {
        this.h = i1;
        this._mthif("FieldSize");
    }

    public int getFieldSize() {
        return this.h;
    }

    public void setImageSource(String s1) {
        this._fldint = s1;
        this._mthif("ImageSource");
    }

    public String getImageSource() {
        return this._fldint;
    }

    public void setImageAltText(String s1) {
        this.w = s1;
        this._mthif("ImageAltText");
    }

    public String getImageAltText() {
        return this.w;
    }

    public void setURL(String s1) {
        this._flddo = s1;
        this._mthif("URL");
    }

    public String getURL() {
        return this._flddo;
    }

    public void setTextAreaRows(int i1) {
        this.t = i1;
        this._mthif("TextAreaRows");
    }

    public int getTextAreaRows() {
        return this.t;
    }

    public void setTextAreaCols(int i1) {
        this._fldlong = i1;
        this._mthif("TextAreaCols");
    }

    public int getTextAreaCols() {
        return this._fldlong;
    }

    public void setFieldType(String s1) {
        this.d = s1;
    }

    public String getFieldType() {
        return this.d;
    }

    public void insertFieldScript(String s1) {
        this.l = s1;
        this._mthif("FieldScript");
    }

    public String getInsertedFieldScript() {
        return this.l;
    }

    public void insertScriptAfterTDStartTag(String s1) {
        this.b = s1;
        this._mthif("ScriptAfterTDStartTag");
    }

    public String getInsertedScriptAfterTDStartTag() {
        return this.b;
    }

    public void insertScriptBeforeTDEndTag(String s1) {
        this._fldgoto = s1;
        this._mthif("ScriptBeforeTDEndTag");
    }

    public String getInsertedScriptBeforeTDEndTag() {
        return this._fldgoto;
    }

    public void setDefaultSelectedText(String s1) {
        this.m = s1;
        this._mthif("SelectedText");
    }

    public String getDefaultSelectedText() {
        return this.m;
    }

    void _mthif(int i1) {
        this.r = i1;
    }

    void a(boolean flag, boolean flag1, String s1) {
        this._fldnew = flag;
        this._fldelse = flag1;
        if (!this._fldnew) {
            this.d = s1;
        }
    }

    void _mthdo(int i1, int j1) {
        this._fldnull = new Vector();
        for (int k1 = 0; k1 < j1; ++k1) {
            JSPCell2 JSPCell2 = new JSPCell2();
            JSPCell2.a(i1);
            JSPCell2._mthif(k1 + 1);
            this._fldnull.addElement(JSPCell2);
        }
        Object obj = null;
        this._fldchar = j1;
    }

    void a(int i1, int j1) {
        for (int k1 = 0; k1 < j1; ++k1) {
            JSPCell2 JSPCell2 = new JSPCell2();
            JSPCell2.a(i1);
            JSPCell2._mthif(this._fldchar + k1 + 1);
            this._fldnull.addElement(JSPCell2);
        }
        Object obj = null;
        this._fldchar+=j1;
    }

    private void _mthif(String s1) {
        for (int i1 = 0; i1 < this._fldchar; ++i1) {
            if (s1.equalsIgnoreCase("FontColor")) {
                this.Cells(i1).setFontColor(this.a);
                continue;
            }
            if (s1.equalsIgnoreCase("FontFace")) {
                this.Cells(i1).setFontFace(this._fldcase);
                continue;
            }
            if (s1.equalsIgnoreCase("FontSize")) {
                this.Cells(i1).setFontSize(this.o);
                continue;
            }
            if (s1.equalsIgnoreCase("Bold")) {
                this.Cells(i1).setFontBold(this.n);
                continue;
            }
            if (s1.equalsIgnoreCase("Italic")) {
                this.Cells(i1).setFontItalic(this._fldif);
                continue;
            }
            if (s1.equalsIgnoreCase("Underline")) {
                this.Cells(i1).setFontUnderline(this._fldvoid);
                continue;
            }
            if (s1.equalsIgnoreCase("BgColor")) {
                this.Cells(i1).setBgColor(this.j);
                continue;
            }
            if (s1.equalsIgnoreCase("Align")) {
                this.Cells(i1).setAlign(this.i);
                continue;
            }
            if (s1.equalsIgnoreCase("VAlign")) {
                this.Cells(i1).setVAlign(this._fldtry);
                continue;
            }
            if (s1.equalsIgnoreCase("NoWrap")) {
                this.Cells(i1).setNoWrap(this.f);
                continue;
            }
            if (s1.equalsIgnoreCase("FieldName")) {
                this.Cells(i1).a(this.q);
                continue;
            }
            if (s1.equalsIgnoreCase("FieldValue")) {
                if (this.d.equalsIgnoreCase("TEXT") || this.d.equalsIgnoreCase("HIDDEN") || this.d.equalsIgnoreCase("TEXTAREA") || this.d.equalsIgnoreCase("CHECKBOX") || this.d.equalsIgnoreCase("radio") || this.d.equalsIgnoreCase("SELECT")) {
                    if (this._fldnew) {
                        this.Cells(i1).setFieldValue(this.e);
                        continue;
                    }
                    if (!this._fldelse || i1 != this._fldchar - 1) continue;
                    this.Cells(i1).setFieldValue(this.e);
                    continue;
                }
                this.Cells(i1).setFieldValue(this.e);
                continue;
            }
            if (s1.equalsIgnoreCase("FieldLength")) {
                this.Cells(i1).setFieldLength(this.c);
                continue;
            }
            if (s1.equalsIgnoreCase("FieldSize")) {
                this.Cells(i1).setFieldSize(this.h);
                continue;
            }
            if (s1.equalsIgnoreCase("TextAreaRows")) {
                this.Cells(i1).setTextAreaRows(this.t);
                continue;
            }
            if (s1.equalsIgnoreCase("TextAreaCols")) {
                this.Cells(i1).setTextAreaCols(this._fldlong);
                continue;
            }
            if (s1.equalsIgnoreCase("SelectedText")) {
                if (this._fldnew) {
                    this.Cells(i1).setSelectedText(this.m);
                    continue;
                }
                if (!this._fldelse || i1 != this._fldchar - 1) continue;
                this.Cells(i1).setSelectedText(this.m);
                continue;
            }
            if (s1.equalsIgnoreCase("Width")) {
                this.Cells(i1).setWidth(this.p);
                continue;
            }
            if (s1.equalsIgnoreCase("ImageSource")) {
                this.Cells(i1).setImageSource(this._fldint);
                continue;
            }
            if (s1.equalsIgnoreCase("ImageAltText")) {
                this.Cells(i1).setImageAltText(this.w);
                continue;
            }
            if (s1.equalsIgnoreCase("URL")) {
                this.Cells(i1).setURL(this._flddo);
                continue;
            }
            if (s1.equalsIgnoreCase("LookupText")) {
                this.Cells(i1).setLookupText(this.u);
                continue;
            }
            if (s1.equalsIgnoreCase("LookupImageSource")) {
                this.Cells(i1).setLookupImageSource(this.v);
                continue;
            }
            if (s1.equalsIgnoreCase("LookupURL")) {
                this.Cells(i1).setLookupURL(this._fldbyte);
                continue;
            }
            if (s1.equalsIgnoreCase("LookupImageAltText")) {
                this.Cells(i1).setLookupImageAltText(this.s);
                continue;
            }
            if (s1.equalsIgnoreCase("FieldScript")) {
                this.Cells(i1).insertFieldScript(this.l);
                continue;
            }
            if (s1.equalsIgnoreCase("ScriptAfterTDStartTag")) {
                this.Cells(i1).insertScriptAfterTDStartTag(this.b);
                continue;
            }
            if (!s1.equalsIgnoreCase("ScriptBeforeTDEndTag")) continue;
            this.Cells(i1).insertScriptBeforeTDEndTag(this._fldgoto);
        }
    }

    public JSPCell2 Cells(int i1) {
        return (JSPCell2)this._fldnull.elementAt(i1);
    }

    public JSPHeader Header() {
        return this.k;
    }

    void _mthif(int i1, int j1) {
        for (int k1 = i1; k1 < i1 + j1; ++k1) {
            this.Cells(k1).setFontColor(this.a);
            this.Cells(k1).setFontFace(this._fldcase);
            this.Cells(k1).setFontSize(this.o);
            this.Cells(k1).setFontBold(this.n);
            this.Cells(k1).setFontItalic(this._fldif);
            this.Cells(k1).setFontUnderline(this._fldvoid);
            this.Cells(k1).setBgColor(this.j);
            this.Cells(k1).setAlign(this.i);
            this.Cells(k1).setVAlign(this._fldtry);
            this.Cells(k1).setNoWrap(this.f);
            this.Cells(k1).a(this.q);
            this.Cells(k1).setFieldValue(this.e);
            this.Cells(k1).setFieldLength(this.c);
            this.Cells(k1).setFieldSize(this.h);
            this.Cells(k1).setTextAreaRows(this.t);
            this.Cells(k1).setTextAreaCols(this._fldlong);
            this.Cells(k1).setSelectedText(this.m);
            this.Cells(k1).setWidth(this.p);
            this.Cells(k1).setImageSource(this._fldint);
            this.Cells(k1).setImageAltText(this.w);
            this.Cells(k1).setURL(this._flddo);
            this.Cells(k1).setLookupText(this.u);
            this.Cells(k1).setLookupImageSource(this.v);
            this.Cells(k1).setLookupURL(this._fldbyte);
            this.Cells(k1).setLookupImageAltText(this.s);
            this.Cells(k1).insertFieldScript(this.l);
            this.Cells(k1).insertScriptAfterTDStartTag(this.b);
            this.Cells(k1).insertScriptBeforeTDEndTag(this._fldgoto);
        }
    }

    void a(int i1) {
        boolean flag = false;
        if (i1 == 0) {
            this._fldnull.removeAllElements();
            this._fldchar = 0;
        } else {
            int j1 = this._fldnull.size();
            for (int k1 = j1 - 1; k1 > i1 - 1; --k1) {
                this._fldnull.remove(k1);
            }
            this._fldchar = this._fldnull.size();
            this._mthif("FieldValue");
            this._mthif("SelectedText");
        }
    }

    void a(boolean flag) {
        this.g = flag;
    }

    boolean _mthif() {
        return this.g;
    }

    public void keepHTMLTags() {
        this._fldfor = true;
    }

    boolean a() {
        return this._fldfor;
    }

    private String a(String s1) {
        StringBuffer stringbuffer = new StringBuffer(s1.length());
        for (int i1 = 0; i1 < s1.length(); ++i1) {
            char c1 = s1.charAt(i1);
            if (c1 == '\"' || c1 == ' ') continue;
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }
}

