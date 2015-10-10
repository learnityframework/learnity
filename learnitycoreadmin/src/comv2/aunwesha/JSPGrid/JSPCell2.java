/*
 * Decompiled with CFR 0_102.
 */
package comv2.aunwesha.JSPGrid;

public class JSPCell2 {
    private String h = "";
    private String q = "";
    private String b = "";
    private String _fldvoid = "";
    private String m = "";
    private String _fldnull = "";
    private String _fldtry = "";
    private String _fldchar = "";
    private String d = "";
    private int p = 0;
    private boolean _fldnew = false;
    private boolean _fldbyte = false;
    private boolean _fldint = false;
    private String n = "";
    private String _fldelse = "";
    private boolean e = false;
    private String c = "";
    private String k = "__Field";
    private int a = -1;
    private int _fldgoto = 0;
    private String j = "";
    private String _fldfor = "";
    private String _fldlong = "";
    private int _fldif = 0;
    private int _flddo = 0;
    private String o = "";
    private String i = "";
    private String l = "";
    private String _fldcase = "";
    private int f = 0;
    private int g = 0;

    public void setWidth(String s) {
        this.h = s;
    }

    public String getWidth() {
        return this.h;
    }

    public void setHeight(String s) {
        this.q = s;
    }

    public String getHeight() {
        return this.q;
    }

    public void setFontColor(String s) {
        this._fldchar = s;
    }

    public String getFontColor() {
        return this._fldchar;
    }

    public void setFontFace(String s) {
        this.d = s;
    }

    public String getFontFace() {
        return this.d;
    }

    public void setFontSize(int i1) {
        this.p = i1;
    }

    public int getFontSize() {
        return this.p;
    }

    public void setFontBold(boolean flag) {
        this._fldnew = flag;
    }

    public boolean isFontBold() {
        return this._fldnew;
    }

    public void setFontItalic(boolean flag) {
        this._fldbyte = flag;
    }

    public boolean isFontItalic() {
        return this._fldbyte;
    }

    public void setFontUnderline(boolean flag) {
        this._fldint = flag;
    }

    public boolean isFontUnderline() {
        return this._fldint;
    }

    public void setBgColor(String s) {
        this.b = s;
    }

    public String getBgColor() {
        return this.b;
    }

    public void setLookupText(String s) {
        this._fldvoid = s;
    }

    public String getLookupText() {
        return this._fldvoid;
    }

    public void setLookupImageSource(String s) {
        this.m = s;
    }

    public String getLookupImageSource() {
        return this.m;
    }

    public void setLookupImageAltText(String s) {
        this._fldnull = s;
    }

    public String getLookupImageAltText() {
        return this._fldnull;
    }

    public void setLookupURL(String s) {
        this._fldtry = s;
    }

    public String getLookupURL() {
        return this._fldtry;
    }

    public void setAlign(String s) {
        this.n = s;
    }

    public String getAlign() {
        return this.n;
    }

    public void setVAlign(String s) {
        this._fldelse = s;
    }

    public String getVAlign() {
        return this._fldelse;
    }

    public void setNoWrap(boolean flag) {
        this.e = flag;
    }

    public boolean isNoWrap() {
        return this.e;
    }

    void a(String s) {
        this.k = s;
    }

    public String getFieldName() {
        if (this.k.equalsIgnoreCase("__Field")) {
            return this.k + "_" + this.f + "_" + this.g;
        }
        return this.k + "_" + this.g;
    }

    public void setFieldValue(String s) {
        this.c = s != null ? s : "";
    }

    public String getFieldValue() {
        return this.c;
    }

    public void setFieldLength(int i1) {
        this.a = i1;
    }

    public int getFieldLength() {
        return this.a;
    }

    public void setFieldSize(int i1) {
        this._fldgoto = i1;
    }

    public int getFieldSize() {
        return this._fldgoto;
    }

    public void setImageSource(String s) {
        this.j = s;
    }

    public String getImageSource() {
        return this.j;
    }

    public void setImageAltText(String s) {
        this._fldfor = s;
    }

    public String getImageAltText() {
        return this._fldfor;
    }

    public void setURL(String s) {
        this._fldlong = s;
    }

    public String getURL() {
        return this._fldlong;
    }

    public void setTextAreaRows(int i1) {
        this._fldif = i1;
    }

    public int getTextAreaRows() {
        return this._fldif;
    }

    public void setTextAreaCols(int i1) {
        this._flddo = i1;
    }

    public int getTextAreaCols() {
        return this._flddo;
    }

    public void insertFieldScript(String s) {
        this.o = s;
    }

    public String getInsertedFieldScript() {
        return this.o;
    }

    public void insertScriptAfterTDStartTag(String s) {
        this.i = s;
    }

    public String getInsertedScriptAfterTDStartTag() {
        return this.i;
    }

    public void insertScriptBeforeTDEndTag(String s) {
        this.l = s;
    }

    public String getInsertedScriptBeforeTDEndTag() {
        return this.l;
    }

    public void setSelectedText(String s) {
        this._fldcase = s;
    }

    public String getSelectedText() {
        return this._fldcase;
    }

    void a(int i1) {
        this.f = i1;
    }

    void _mthif(int i1) {
        this.g = i1;
    }
}

