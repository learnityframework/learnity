/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  comv2.aunwesha.param.CoreAdminInitHostInfo
 *  javax.servlet.http.HttpServletRequest
 */
package comv2.aunwesha.JSPGrid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import comv2.aunwesha.param.CoreAdminInitHostInfo;

public class JSPGridPro
extends JSPGrid {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    public static DataSource ds = CoreAdminInitHostInfo.ds;
    private HttpServletRequest req;
    private String N = "";
    private String al = "";
    private int ab = -1;
    private int aj = -1;
    private int at = -1;
    private String url = "";
    private String driver = "";
    private String sql = "";
    private int numberOfColumns = 0;
    private int rowno = 0;
    private int rowno1 = 0;
    private int ag = 0;
    private int P1 = 1;
    private boolean du = false;
    private boolean am = false;
    private boolean au = false;
    private String an = "No Records Found";
    private String S = "";
    private boolean ac = false;
    private boolean X = false;
    private boolean ap = false;
    private int as = 10;
    private int H = 10;
    private int ae = 0;
    private String P = "";
    private String U = "";
    private String Y = "";
    private boolean ak = false;
    private String O = "Page :";
    private String K = "First";
    private String aa = "Previous";
    private String af = "Next";
    private String R = "Last";
    private String ad = "";
    private int Q = 0;
    private boolean J = false;
    private String ai = "";
    private int W = 1;
    private boolean I = false;
    private static final String Z = "1.2";
    private String referer_wt_param = "";
    private String referer = "";
    private CharSequence cs = null;
    private String gridpro1 = "1";

    @Override
    public String getVersion() {
        return "1.2";
    }

    public int getCurrentPageNo() {
        return this.ag;
    }

    public void setCurrentPageNo(int p) {
        this.P1 = this.du ? p : 1;
    }

    public boolean isLastPage() {
        int i1 = 0;
        i1 = (int)Math.ceil((float)this.rowno / (float)this.as);
        return i1 == this.ag;
    }

    public void hidePageNavigationHTML() {
        this.am = true;
    }

    @Override
    public void reset() throws Exception {
        boolean flag = false;
        this.au = true;
        for (int i1 = 0; i1 < this.numberOfColumns; ++i1) {
            super.Cols(i1).a(0);
        }
        super._mthif(0);
        this.buildGrid();
    }

    public void setDisplayedTextIfResultSetEmpty(String s) {
        this.an = s;
    }

    public String getDisplayedTextIfResultSetEmpty() {
        return this.an;
    }

    public void addQueryString(String s) {
        this.S = s;
    }

    public boolean isResultSetEmpty() {
        return this.rowno == 0;
    }

    public void setMaxRowsPerPage(int i1) {
        this.as = i1 >= 1 ? i1 : 1;
    }

    public int getMaxRowsPerPage() {
        return this.as;
    }

    public void setMaxResultPagesPerLoad(int i1) {
        this.H = i1 >= 0 ? i1 : 0;
    }

    public int getMaxResultPagesPerLoad() {
        return this.H;
    }

    @Override
    public void setMaxRows(int i1) {
        this.ae = i1 >= 0 ? i1 : 0;
    }

    @Override
    public int getMaxRows() {
        return this.ae;
    }

    public void canSort(int i1, boolean flag) {
        super.Cols(i1).a(flag);
    }

    public boolean isSortable(int i1) {
        return super.Cols(i1)._mthif();
    }

    public void canSort(boolean flag) {
        this.ak = flag;
        for (int i1 = 0; i1 < this.numberOfColumns; ++i1) {
            super.Cols(i1).a(this.ak);
        }
    }

    public void setSortableColumnsToolTip(String s) {
        this.P = s;
    }

    public String getSortableColumnsToolTip() {
        return this.P;
    }

    public void setASCImageSource(String s) {
        this.U = s;
    }

    public String getASCImageSource() {
        return this.U;
    }

    public void setDESCImageSource(String s) {
        this.Y = s;
    }

    public String getDESCImageSource() {
        return this.Y;
    }

    public void setPageNavigationPageLabel(String s) {
        this.O = s;
    }

    public String getPageNavigationPageLabel() {
        return this.O;
    }

    public void setPageNavigationFirstLabel(String s) {
        this.K = s;
    }

    public String getPageNavigationFirstLabel() {
        return this.K;
    }

    public void setPageNavigationPreviousLabel(String s) {
        this.aa = s;
    }

    public String getPageNavigationPreviousLabel() {
        return this.aa;
    }

    public void setPageNavigationNextLabel(String s) {
        this.af = s;
    }

    public String getPageNavigationNextLabel() {
        return this.af;
    }

    public void setPageNavigationLastLabel(String s) {
        this.R = s;
    }

    public String getPageNavigationLastLabel() {
        return this.R;
    }

    public void setPageNavigationFontFace(String s) {
        this.ad = s;
    }

    public String getPageNavigationFontFace() {
        return this.ad;
    }

    public void setPageNavigationFontSize(int i1) {
        this.Q = i1;
    }

    public int getPageNavigationFontSize() {
        return this.Q;
    }

    public void setPageNavigationFontBold(boolean flag) {
        this.J = flag;
    }

    public boolean isPageNavigationFontBold() {
        return this.J;
    }

    public void setPageNavigationFontColor(String s) {
        this.ai = s;
    }

    public String getPageNavigationFontColor() {
        return this.ai;
    }

    @Override
    public int getRows() {
        if (!this.sql.equals("")) {
            if (this.ap) {
                return this.rowno1;
            }
            return 0;
        }
        return super.getRows();
    }

    public int getPages() {
        if (this.ap) {
            return (int)Math.ceil((float)this.rowno / (float)this.as);
        }
        return 0;
    }

    public int getCurrentPageRows() {
        return super.getRows();
    }

    public void countResultSet() {
        this.ap = true;
    }

    public void showPageNavigationFirst() {
        this.ac = true;
    }

    public void showPageNavigationLast() {
        this.X = true;
    }

    public JSPGridPro() {
    }

    public JSPGridPro(int i1, int j1, HttpServletRequest request, String s) {
        super.initGrid(i1, j1, request, s);
        this.initGrid(request, s);
    }

    public JSPGridPro(int i1, int j1) {
        super.initGrid(i1, j1);
    }

    public JSPGridPro(HttpServletRequest request, String s) {
        this.initGrid(request, s);
    }

    public void initGrid(HttpServletRequest request, String s) {
        this.req = request;
        this.N = s;
        this.al = this.req.getRequestURI();
        super.setFormURL(this.al);
        this.referer = request.getHeader("referer");
        this.cs = new String("?");
        if (this.referer != null) {
            this.referer_wt_param = !this.referer.contains(this.cs) ? this.referer : this.referer.substring(0, this.referer.indexOf("?"));
        }
        if (request.getRequestURL().toString().equals(this.referer_wt_param)) {
            this.du = true;
        }
    }

    public void setConnectionParameters(String s, String s1, String s2) {
        this.driver = s;
        this.url = s1;
        this.sql = s2.trim();
        this._mthnull();
    }

    public void setConnectionParameters(String s2) {
        this.sql = s2.trim();
        this._mthnull();
    }

    private void _mthnull() {
        try {
            this.con = ds.getConnection();
            System.out.println("creating Grid connection");
            this.stmt = this.con.createStatement(1004, 1007);
            this.rs = this.stmt.executeQuery(this._mthnew(this.sql));
            this.rs.last();
            this.rowno1 = this.rs.getRow();
            this.numberOfColumns = this.rs.getMetaData().getColumnCount();
            super.initGrid(this.numberOfColumns, 0);
        }
        catch (Exception exp) {
            this.f();
            exp.printStackTrace();
            System.out.println(exp.toString() + " - makeConnection");
        }
    }

    private void _mthlong() {
        try {
            if (this.W != -1) {
                this.stmt.setMaxRows(this.a(false));
            } else {
                this.stmt.setMaxRows(this.a(true));
            }
            this.rs = this.stmt.executeQuery(this._mthcase(this.sql));
            this.rs.last();
            this.rowno = this.rs.getRow();
            this.rs.first();
            if (this.W == -1) {
                this.ag = (int)Math.ceil((float)this.rowno / (float)this.as);
                this.W = this.ag == 1 ? 1 : (this.ag - 1) * this.as + 1;
            }
        }
        catch (Exception exp) {
            this.f();
            System.out.println(exp.toString() + " - getResultSet");
        }
    }

    private void o() {
        boolean flag = false;
        try {
            int i1;
            this.rs.absolute(this.W);
            for (i1 = 0; i1 != this.as; ++i1) {
                if (this.rs.isAfterLast()) break;
                super.addRow();
                for (int j1 = 1; j1 <= this.numberOfColumns; ++j1) {
                    this.a(j1, this.rs.getMetaData().getColumnName(j1));
                    if (!super.Cols(j1 - 1).getFieldType().equals("IMAGE")) {
                        super.Cols(j1 - 1).Cells(i1).setFieldValue(this.rs.getString(j1));
                        continue;
                    }
                    super.Cols(j1 - 1).Cells(i1).setImageSource(this.rs.getString(j1));
                }
                if (this.rs.next()) continue;
            }
            super._mthtry(i1);
        }
        catch (Exception exp) {
            this.f();
            System.out.println(exp.toString() + " - processResultSet");
        }
    }

    public void f() {
        try {
            System.out.println("Closing Grid Connection");
            this.rs.close();
            this.stmt.close();
            this.con.close();
            this.rs = null;
            this.stmt = null;
            this.con = null;
        }
        catch (Exception exp) {
            // empty catch block
        }
    }

    private void a(int i1, String s) {
        String s1 = "";
        String s2 = "";
        String s4 = "";
        if (super.Cols(i1 - 1)._mthif()) {
            String s3 = "\"javascript:document." + this.N + ".__gridpro2.value='" + this.ab + "," + (i1 - 1) + "," + this.b() + "';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\"";
            if (this.ab == i1 - 1) {
                s4 = this.j();
            }
            s1 = "<a " + (this.P.equals("") ? "" : new StringBuilder().append("title=\"").append(this.P).append("\"").toString()) + " href=" + s3 + ">" + s4 + " " + s + "</a>";
        } else {
            s1 = s;
        }
        super.Cols(i1 - 1).Header().setCaption(s1);
    }

    private int b() {
        int byte0 = 0;
        if (this.at == -1) {
            byte0 = 1;
        } else if (this.aj != this.ab) {
            byte0 = 2;
        } else if (this.at == 1) {
            byte0 = 2;
        } else if (this.at == 2) {
            byte0 = 1;
        }
        return byte0;
    }

    private String j() {
        int i1 = 0;
        String s = "";
        i1 = this.m();
        if (i1 == 1) {
            s = this.U.equals("") ? " < " : "<img src=\"" + this.U + "\"" + " border=\"0\">";
        } else if (i1 == 2) {
            s = this.Y.equals("") ? " > " : "<img src=\"" + this.Y + "\"" + " border=\"0\">";
        }
        return s;
    }

    private int h() {
        int i1 = 0;
        try {
            this.gridpro1 = !this.du ? "1" : this.req.getParameter("__gridpro1");
            if (this.req.getParameter("__gridpro1") == null || this.gridpro1.equals("1") || this.au) {
                i1 = 1;
                this.ag = 1;
            } else {
                this.ag = Integer.parseInt(this.req.getParameter("__gridpro1"));
                i1 = this.ag == -1 ? -1 : (this.ag - 1) * this.as + 1;
            }
        }
        catch (Exception exp) {
            i1 = 1;
            this.ag = 1;
        }
        return i1;
    }

    public String getPageNavigationHTML() {
        String s = "";
        int i1 = 0;
        int j1 = 0;
        i1 = (int)Math.ceil((float)this.rowno / (float)this.as);
        j1 = this._mthbyte(i1);
        for (int k1 = 0; k1 < this.H && j1 + k1 <= i1; ++k1) {
            s = j1 + k1 != this.ag ? s + " " + "<a href=\"javascript:document." + this.N + ".__action.value='page';" + "document." + this.N + ".target='_self';" + "document." + this.N + ".__gridpro1.value='" + (j1 + k1) + "';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\">" + (j1 + k1) + "</a>" : s + " " + (j1 + k1);
        }
        if (i1 != this.ag) {
            s = s + " <a href=\"javascript:document." + this.N + ".__action.value='page';" + "document." + this.N + ".target='_self';" + "document." + this.N + ".__gridpro1.value='" + (this.ag + 1) + "';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\">" + this.af + "</a>" + this.l();
        }
        if (1 != this.ag) {
            s = this.n() + " <a href=\"javascript:document." + this.N + ".__action.value='page';" + "document." + this.N + ".target='_self';" + "document." + this.N + ".__gridpro1.value='" + (this.ag - 1) + "';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\">" + this.aa + "</a> " + s;
        }
        return this._mthchar(this.O + s);
    }

    public void buildGrid() throws Exception {
        if (!this.sql.equals("")) {
            this.g();
            this.W = this.h();
            this._mthlong();
            if (this.rowno > 0) {
                this.o();
                super._mthnew(this.W);
            }
        }
        this.I = true;
    }

    @Override
    public String getGrid() throws Exception {
        if (!this.sql.trim().equals("")) {
            if (!this.I) {
                this.buildGrid();
                if (this.rowno > 0) {
                    this.k();
                    this.f();
                    super.setNavigation(this.getPageNavigationHTML());
                    return super.getGrid() + this.e() + this._mthvoid();
                }
                this.f();
                return this.an;
            }
            if (this.rowno > 0) {
                this.k();
                this.f();
                super.setNavigation(this.getPageNavigationHTML());
                return super.getGrid() + this.e() + this._mthvoid();
            }
            this.f();
            return this.an;
        }
        super.setNavigation(this.getPageNavigationHTML());
        return super.getGrid();
    }

    private String e() {
        return "<INPUT TYPE=\"HIDDEN\" NAME=\"__gridpro1\" VALUE=\"" + this.P1 + "\"><INPUT TYPE=\"HIDDEN\" NAME=\"__gridpro2\" VALUE=\"" + this.aj + "," + this.ab + "," + this.at + "\">";
    }

    private String _mthvoid() {
        if (this.am) {
            return "";
        }
        return "\n<table>\n<tr>\n<td>" + this.getPageNavigationHTML() + "\n</td>\n</tr>\n</table>";
    }

    private String _mthchar(String s) {
        String s1 = "";
        String s2 = "";
        s1 = this.ad.equals("") ? "" : "<FONT FACE=" + super._mthint(this.ad) + ">";
        s1 = s1 + (this.Q == 0 ? "" : new StringBuilder().append("<FONT SIZE=").append(super._mthdo(this.Q)).append(">").toString());
        s1 = s1 + (this.J ? "<B>" : "");
        s1 = s1 + (this.ai.equals("") ? "" : new StringBuilder().append("<FONT COLOR=").append(super._mthint(this.ai)).append(">").toString());
        s2 = this.ad.equals("") ? "" : "</FONT>";
        s2 = s2 + (this.Q == 0 ? "" : "</FONT>");
        s2 = s2 + (this.J ? "</B>" : "");
        s2 = s2 + (this.ai.equals("") ? "" : "</FONT>");
        return s1 + s + s2;
    }

    private String n() {
        String s = "";
        if (this.ac) {
            s = " <a href=\"javascript:document." + this.N + ".__action.value='page';" + "document." + this.N + ".target='_self';" + "document." + this.N + ".__gridpro1.value='1';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\">" + this.K + "</a>";
        }
        return s;
    }

    private String l() {
        String s = "";
        if (this.X) {
            s = " <a href=\"javascript:document." + this.N + ".__action.value='page';" + "document." + this.N + ".target='_self';" + "document." + this.N + ".__gridpro1.value='-1';" + "document." + this.N + ".action='" + this.c() + "';" + "document." + this.N + ".submit();\">" + this.R + "</a>";
        }
        return s;
    }

    private int _mthbyte(int i1) {
        int j1 = 0;
        int k1 = 1;
        j1 = (int)Math.ceil((float)this.H / 2.0f);
        if (this.H >= i1) {
            k1 = 1;
        } else if (this.ag + j1 > i1) {
            k1 = i1 - this.H + 1;
        } else if (this.ag >= (int)Math.floor((float)this.H / 2.0f) + 1) {
            k1 = this.ag - (this.H - j1);
        }
        return k1;
    }

    private int a(boolean flag) {
        boolean flag1 = false;
        int j1 = 0;
        if (!(this.ap || flag)) {
            int i1 = (int)Math.ceil((float)this.H / 2.0f);
            i1 = i1 != 0 ? i1 : 1;
            j1 = this.ag <= this.H - i1 ? this.as * (this.ag + this.H - 1) : this.as * (this.ag + i1);
        }
        if (this.ae > 0) {
            if (j1 == 0) {
                j1 = this.ae;
            } else if (j1 > 0 && j1 > this.ae) {
                j1 = this.ae;
            }
        }
        return j1;
    }

    private String _mthtry(String s) {
        int byte0 = 32;
        int c2 = 32;
        String s1 = "";
        for (int i1 = 0; i1 < s.trim().length(); ++i1) {
            char c1 = s.trim().charAt(i1);
            if (i1 == 0) {
                s1 = s1 + String.valueOf(c1);
            } else if (c1 != ' ' || c2 != 32) {
                s1 = s1 + String.valueOf(c1);
            }
            c2 = c1;
        }
        return s1;
    }

    private String _mthnew(String s) {
        String s1 = "";
        int i1 = 0;
        s1 = this._mthtry(s);
        i1 = s1.lastIndexOf("order by");
        if (i1 == -1) {
            return s;
        }
        return s1.substring(0, i1);
    }

    private void g() {
        try {
            if (this.req.getParameter("__gridpro2") == null || this.au) {
                this.ab = -1;
                this.at = -1;
                this.aj = -1;
            } else {
                String[] as1 = super.a(this.req.getParameter("__gridpro2"), ',');
                this.aj = Integer.parseInt(as1[0]);
                this.ab = Integer.parseInt(as1[1]);
                this.at = Integer.parseInt(as1[2]);
            }
        }
        catch (Exception exp) {
            this.ab = -1;
            this.at = -1;
            this.aj = -1;
        }
    }

    private String _mthcase(String s) {
        String s1 = "";
        boolean flag = false;
        if (this.ab > -1) {
            s1 = " ORDER BY " + (this.ab + 1);
        }
        if (!s1.equals("")) {
            int i1 = this.m();
            if (i1 == 1) {
                s1 = s1 + " ASC";
            } else if (i1 == 2) {
                s1 = s1 + " DESC";
            }
        }
        if (s1.equals("")) {
            return s;
        }
        return this._mthnew(s) + s1;
    }

    private int m() {
        int byte0 = 0;
        if (this.aj != this.ab) {
            byte0 = 1;
        } else if (this.at == 1) {
            byte0 = 1;
        } else if (this.at == 2) {
            byte0 = 2;
        }
        return byte0;
    }

    private void k() {
        super.allowAddRow(false);
        for (int i1 = 0; i1 < this.numberOfColumns; ++i1) {
            if (super.Cols(i1).getFieldType().equals("") || super.Cols(i1).getFieldType().equals("TEXT") || super.Cols(i1).getFieldType().equals("TEXTAREA") || super.Cols(i1).getFieldType().equals("IMAGE") || super.Cols(i1).getFieldType().equals("HIDDEN") || super.Cols(i1).getFieldType().equals("CHECKBOX") || super.Cols(i1).getFieldType().equals("radio")) continue;
            super.Cols(i1).setFieldType("");
        }
    }

    private String c() {
        if (!this.S.trim().equals("")) {
            return super.getFormURL() + "?" + this.S;
        }
        return super.getFormURL();
    }

    private boolean _mthbyte(String s) {
        int[] ai1 = new int[]{50, 53, 57};
        int[] ai2 = new int[]{50, 52, 75, 89};
        int[] ai3 = new int[]{69, 68, 72, 80, 77, 88};
        int[] ai4 = new int[]{52, 48, 56};
        int[] ai5 = new int[]{66, 72, 89, 78};
        int[] ai6 = new int[]{84, 85, 90, 86};
        int[] ai7 = new int[]{81, 66, 82, 88};
        int[] ai8 = new int[]{52, 56, 48};
        int[] ai9 = new int[]{53, 55, 69, 76};
        int[] ai10 = new int[]{67, 70, 74, 83, 89, 90};
        int[] ai11 = new int[]{51, 55, 57};
        int[] ai12 = new int[]{65, 83, 51};
        int[] ai13 = new int[]{52, 85, 88, 57};
        int[] ai14 = new int[]{69, 82, 90, 50, 56, 78};
        int[] ai15 = new int[]{52, 87, 53, 80, 67};
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
        boolean flag13 = false;
        boolean flag14 = false;
        boolean flag15 = false;
        boolean flag16 = false;
        boolean flag17 = false;
        if (s.length() == 18) {
            block20 : for (int l4 = 0; l4 < s.length(); ++l4) {
                switch (l4) {
                    default: {
                        continue block20;
                    }
                    case 0: {
                        for (int i1 = 0; i1 < ai1.length; ++i1) {
                            if (ai1[i1] != s.charAt(l4)) continue;
                            flag = true;
                        }
                        continue block20;
                    }
                    case 1: {
                        for (int j1 = 0; j1 < ai2.length; ++j1) {
                            if (ai2[j1] != s.charAt(l4)) continue;
                            flag1 = true;
                        }
                        continue block20;
                    }
                    case 2: {
                        for (int k1 = 0; k1 < ai3.length; ++k1) {
                            if (ai3[k1] != s.charAt(l4)) continue;
                            flag2 = true;
                        }
                        continue block20;
                    }
                    case 3: {
                        for (int l1 = 0; l1 < ai4.length; ++l1) {
                            if (ai4[l1] != s.charAt(l4)) continue;
                            flag3 = true;
                        }
                        continue block20;
                    }
                    case 4: {
                        if (s.charAt(l4) != '-') continue block20;
                        flag15 = true;
                        continue block20;
                    }
                    case 5: {
                        for (int i2 = 0; i2 < ai5.length; ++i2) {
                            if (ai5[i2] != s.charAt(l4)) continue;
                            flag4 = true;
                        }
                        continue block20;
                    }
                    case 6: {
                        for (int j2 = 0; j2 < ai6.length; ++j2) {
                            if (ai6[j2] != s.charAt(l4)) continue;
                            flag5 = true;
                        }
                        continue block20;
                    }
                    case 7: {
                        for (int k2 = 0; k2 < ai7.length; ++k2) {
                            if (ai7[k2] != s.charAt(l4)) continue;
                            flag6 = true;
                        }
                        continue block20;
                    }
                    case 8: {
                        if (s.charAt(l4) != '-') continue block20;
                        flag16 = true;
                        continue block20;
                    }
                    case 9: {
                        for (int l2 = 0; l2 < ai8.length; ++l2) {
                            if (ai8[l2] != s.charAt(l4)) continue;
                            flag7 = true;
                        }
                        continue block20;
                    }
                    case 10: {
                        for (int i3 = 0; i3 < ai9.length; ++i3) {
                            if (ai9[i3] != s.charAt(l4)) continue;
                            flag8 = true;
                        }
                        continue block20;
                    }
                    case 11: {
                        for (int j3 = 0; j3 < ai10.length; ++j3) {
                            if (ai10[j3] != s.charAt(l4)) continue;
                            flag9 = true;
                        }
                        continue block20;
                    }
                    case 12: {
                        for (int k3 = 0; k3 < ai11.length; ++k3) {
                            if (ai11[k3] != s.charAt(l4)) continue;
                            flag10 = true;
                        }
                        continue block20;
                    }
                    case 13: {
                        if (s.charAt(l4) != '-') continue block20;
                        flag17 = true;
                        continue block20;
                    }
                    case 14: {
                        for (int l3 = 0; l3 < ai12.length; ++l3) {
                            if (ai12[l3] != s.charAt(l4)) continue;
                            flag11 = true;
                        }
                        continue block20;
                    }
                    case 15: {
                        for (int i4 = 0; i4 < ai13.length; ++i4) {
                            if (ai13[i4] != s.charAt(l4)) continue;
                            flag12 = true;
                        }
                        continue block20;
                    }
                    case 16: {
                        for (int j4 = 0; j4 < ai14.length; ++j4) {
                            if (ai14[j4] != s.charAt(l4)) continue;
                            flag13 = true;
                        }
                        continue block20;
                    }
                    case 17: {
                        for (int k4 = 0; k4 < ai15.length; ++k4) {
                            if (ai15[k4] != s.charAt(l4)) continue;
                            flag14 = true;
                        }
                    }
                }
            }
        }
        return flag && flag1 && flag2 && flag3 && flag15 && flag4 && flag5 && flag6 && flag16 && flag7 && flag8 && flag9 && flag10 && flag17 && flag11 && flag12 && flag13 && flag14;
    }

    private String i() {
        String s1;
        String s2 = "";
        String s3 = "";
        String s4 = "";
        int[] ai1 = new int[]{99, 111, 109, 47, 112, 111, 119, 101, 114, 111, 98, 106, 47, 74, 83, 80, 71, 114, 105, 100, 46, 99, 108, 97, 115, 115};
        int[] ai2 = new int[]{74, 83, 80, 71, 114, 105, 100, 80, 114, 111, 46, 106, 97, 114};
        int[] ai3 = new int[]{106, 115, 112, 103, 114, 105, 100, 46, 108, 105, 99, 101, 110, 115, 101};
        for (int i1 = 0; i1 < ai1.length; ++i1) {
            s2 = s2 + String.valueOf((char)ai1[i1]);
        }
        for (int j1 = 0; j1 < ai2.length; ++j1) {
            s3 = s3 + String.valueOf((char)ai2[j1]);
        }
        for (int k1 = 0; k1 < ai3.length; ++k1) {
            s4 = s4 + String.valueOf((char)ai3[k1]);
        }
        try {
            BufferedReader bufferedreader;
            String s = ClassLoader.getSystemResource(s2).getFile();
            s = s.substring(0, s.indexOf(s3)) + s4;
            if (s.length() > 5 && s.substring(0, 5).equalsIgnoreCase("file:")) {
                s = s.substring("file:".length(), s.length());
            }
            if ((s1 = (bufferedreader = new BufferedReader(new FileReader(s))).readLine()) == null) {
                s1 = "";
            }
            bufferedreader.close();
        }
        catch (Exception exp) {
            s1 = "";
        }
        return s1;
    }

    private String d() {
        int[] ai1 = new int[]{73, 110, 118, 97, 108, 105, 100, 32, 76, 105, 99, 101, 110, 115, 101, 32, 75, 101, 121, 33};
        String s = "";
        for (int i1 = 0; i1 < ai1.length; ++i1) {
            s = s + String.valueOf((char)ai1[i1]);
        }
        return s;
    }
}

