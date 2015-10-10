/*
 * Decompiled with CFR 0_102.
 */
package comv2.aunwesha.JSPGrid;

public class JSPHeader {
    private String _fldgoto = "";
    private String _fldchar = "";
    private int d = 0;
    private boolean _flddo = false;
    private boolean _fldfor = false;
    private boolean _fldif = false;
    private String _fldnew = "";
    private String _fldcase = "";
    private String e = "";
    private String _fldlong = "";
    private String c = "";
    private String _fldtry = "";
    private boolean _fldelse = false;
    private String _fldvoid = "";
    private String _fldint = "";
    private String a = "";
    private String _fldbyte = "";
    private String _fldnull = "";
    private String b = "";
    private String classid = "";

    public void setCaption(String s) {
        this._fldgoto = s;
    }

    public String getCaption() {
        return this._fldgoto;
    }

    public void setFontFace(String s) {
        this._fldchar = s;
    }

    public String getFontFace() {
        return this._fldchar;
    }

    public void setFontSize(int i) {
        this.d = i;
    }

    public int getFontSize() {
        return this.d;
    }

    public void setFontBold(boolean flag) {
        this._flddo = flag;
    }

    public boolean isFontBold() {
        return this._flddo;
    }

    public void setFontItalic(boolean flag) {
        this._fldfor = flag;
    }

    public boolean isFontItalic() {
        return this._fldfor;
    }

    public void setFontUnderline(boolean flag) {
        this._fldif = flag;
    }

    public boolean isFontUnderline() {
        return this._fldif;
    }

    public void setFontColor(String s) {
        this._fldnew = s;
    }

    public String getFontColor() {
        return this._fldnew;
    }

    public void setBgColor(String s) {
        this._fldcase = s;
    }

    public String getBgColor() {
        return this._fldcase;
    }

    public void setClassID(String s) {
        this.classid = s;
    }

    public String getClassID() {
        return this.classid;
    }

    public void setHeight(String s) {
        this.e = s;
    }

    public String getHeight() {
        return this.e;
    }

    public void setWidth(String s) {
        this._fldlong = s;
    }

    public String getWidth() {
        return this._fldlong;
    }

    public void setAlign(String s) {
        this.c = s;
    }

    public String getAlign() {
        return this.c;
    }

    public void setVAlign(String s) {
        this._fldtry = s;
    }

    public String getVAlign() {
        return this._fldtry;
    }

    public void setNoWrap(boolean flag) {
        this._fldelse = flag;
    }

    public boolean isNoWrap() {
        return this._fldelse;
    }

    public void setImageSource(String s) {
        this._fldvoid = s;
    }

    public String getImageSource() {
        return this._fldvoid;
    }

    public void setImageAltText(String s) {
        this.a = s;
    }

    public String getImageAltText() {
        return this.a;
    }

    public void setURL(String s) {
        this._fldbyte = s;
    }

    public String getURL() {
        return this._fldbyte;
    }

    public void insertScriptAfterTDStartTag(String s) {
        this._fldnull = s;
    }

    public String getInsertedScriptAfterTDStartTag() {
        return this._fldnull;
    }

    public void insertScriptBeforeTDEndTag(String s) {
        this.b = s;
    }

    public String getInsertedScriptBeforeTDEndTag() {
        return this.b;
    }
}

