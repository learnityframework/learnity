/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  comv2.aunwesha.param.CoreAdminInitHostInfo
 *  javax.servlet.http.HttpServletRequest
 */
package comv2.aunwesha.JSPGrid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.param.CoreAdminInitHostInfo;

public class JSPGridPro2
extends JSPGrid2 {
    private String search = "";
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    public static DataSource ds = CoreAdminInitHostInfo.ds;
    private HttpServletRequest req;
    private String form = "";
    private String al = "";
    private int ab = -1;
    private int aj = -1;
    private int at = -1;
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
    private String referer_wt_param = "";
    private String referer = "";
    private CharSequence cs = null;
    private String gridpro1 = "1";
    private String targetScript=null;

    public JSPGridPro2() {
    	this.targetScript="document." + this.form + ".target='_self';";
    }

    public JSPGridPro2(int i1, int j1, HttpServletRequest request, String s) {
        super.initGrid(i1, j1, request, s);
        this.initGrid(request, s);
    }

    public JSPGridPro2(int i1, int j1) {
        super.initGrid(i1, j1);
    }

    public JSPGridPro2(HttpServletRequest request, String s) {
        this.initGrid(request, s);
    }
    
    public JSPGridPro2(HttpServletRequest request, String form,String target) {
        this.initGrid(request, form);
        
        if(GenericUtil.hasString(target)){
        	this.targetScript="document." + this.form + ".target='"+target+"';";
        }
    }
    
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

   

    public void initGrid(HttpServletRequest request, String form) {
        this.req = request;
        this.form = form;
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
        this.targetScript="document." + this.form + ".target='_self';";
    }

    public void setConnectionParameters(String s, String s2) {
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
        try {
            int i1;
            this.rs.absolute(this.W);
            for (i1 = 0; i1 != this.as; ++i1) {
                if (this.rs.isAfterLast()) break;
                super.addRow();
                for (int j1 = 1; j1 <= this.numberOfColumns; ++j1) {
                    //this.a(j1, this.rs.getMetaData().getColumnName(j1));
                	this.a(j1, this.rs.getMetaData().getColumnLabel(j1));
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

    private void f() {
        try {
            this.rs.close();
            this.stmt.close();
            this.rs = null;
            this.stmt = null;
        }
        catch (Exception exp) {
            // empty catch block
        }
    }

    private void a(int i1, String s) {
        String s1 = "";
        String s4 = "";
        if (super.Cols(i1 - 1)._mthif()) {
            String s3 = "\"javascript:document." + this.form + ".__gridpro2.value='" + this.ab + "," + (i1 - 1) + "," + this.b() + "';" + "document." + this.form + ".action='" + this.c() + "';" +targetScript+ "document." + this.form + ".submit();\"";
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
            
            
            if (GenericUtil.hasString(this.req.getParameter("__gridpro_search"))) {
                i1 = this.processSearch(this.req.getParameter("__gridpro_search"));
                this.ag = (int)Math.ceil((float)i1 / (float)this.as);
                i1 = (this.ag - 1) * this.as + 1;
            }
            else{
            	this.gridpro1 = !this.du ? "1" : this.req.getParameter("__gridpro1");
            	this.ag = Integer.parseInt(this.gridpro1);
                i1 = this.ag == -1 ? -1 : (this.ag - 1) * this.as + 1;
            }
            /*if (this.req.getParameter("__gridpro1") == null || this.gridpro1.equals("1") || this.au) {
                if (GenericUtil.hasString(this.req.getParameter("__gridpro_search"))) {
                    i1 = this.processSearch(this.req.getParameter("__gridpro_search"));
                    this.ag = (int)Math.ceil((float)i1 / (float)this.as);
                    i1 = (this.ag - 1) * this.as + 1;
                } else {
                    i1 = 1;
                    this.ag = 1;
                }
            } else if (GenericUtil.hasString(this.req.getParameter("__gridpro_search"))) {
                if (!this.req.getParameter("__gridpro_search").equals("1")) {
                    i1 = this.processSearch(this.req.getParameter("__gridpro_search"));
                    this.ag = (int)Math.ceil((float)i1 / (float)this.as);
                    i1 = (this.ag - 1) * this.as + 1;
                } else {
                    this.ag = Integer.parseInt(this.req.getParameter("__gridpro1"));
                    i1 = this.ag == -1 ? -1 : (this.ag - 1) * this.as + 1;
                }
            } else {
                this.ag = Integer.parseInt(this.req.getParameter("__gridpro1"));
                i1 = this.ag == -1 ? -1 : (this.ag - 1) * this.as + 1;
            }*/
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
            s = j1 + k1 != this.ag ? s + " " + "<a href=\"javascript:document." + this.form + ".__action.value='page';" + targetScript + "document." + this.form + ".__gridpro1.value='" + (j1 + k1) + "';" + "document." + this.form + ".action='" + this.c() + "';" + "document." + this.form + ".submit();\">" + (j1 + k1) + "</a>" : s + " " + (j1 + k1);
        }
        if (i1 != this.ag) {
            s = s + " <a href=\"javascript:document." + this.form + ".__action.value='page';" + targetScript + "document." + this.form + ".__gridpro1.value='" + (this.ag + 1) + "';" + "document." + this.form + ".action='" + this.c() + "';" + "document." + this.form + ".submit();\">" + this.af + "</a>" + this.l();
        }
        if (1 != this.ag) {
            s = this.n() + " <a href=\"javascript:document." + this.form + ".__action.value='page';" + targetScript + "document." + this.form + ".__gridpro1.value='" + (this.ag - 1) + "';" + "document." + this.form + ".action='" + this.c() + "';" + "document." + this.form + ".submit();\">" + this.aa + "</a> " + s;
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

    public int processSearch(String search_string) {
        boolean p;
        int j;
        j = 0;
        String str = "";
        String ignoreCaseStr = "";
        p = false;
        String ignoreCaseSearch = search_string.toLowerCase();
        try {
            this.rs = this.stmt.executeQuery(this._mthcase(this.sql));
            while (this.rs.next()) {
                for (int j1 = 2; j1 <= this.numberOfColumns; ++j1) {
                    str = this.rs.getString(j1);
                    ignoreCaseStr = str.toLowerCase();
                    p = ignoreCaseStr.startsWith(ignoreCaseSearch);
                    if (p) break;
                }
                ++j;
                if (!ignoreCaseStr.startsWith(ignoreCaseSearch)) continue;
                break;
            }
        }
        catch (SQLException e) {
            System.out.println("SQL exceptionin processSearch()");
        }
        if (p) {
            return j;
        }
        return 1;
    }

    @Override
    public String getGrid() throws Exception {
        if (!this.sql.trim().equals("")) {
            String si = this.setSearchInterface();
            super.getSearchInterface(si);
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
        return "<INPUT TYPE=\"HIDDEN\" NAME=\"__gridpro1\" VALUE=\"" + this.P1 + "\"><INPUT TYPE=\"HIDDEN\" NAME=\"__gridpro_search\" VALUE=\"\"><INPUT TYPE=\"HIDDEN\" NAME=\"__gridpro2\" VALUE=\"" + this.aj + "," + this.ab + "," + this.at + "\">";
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
            s = " <a href=\"javascript:document." + this.form + ".__action.value='page';" + targetScript + "document." + this.form + ".__gridpro1.value='1';" + "document." + this.form + ".action='" + this.c() + "';" + "document." + this.form + ".submit();\">" + this.K + "</a>";
        }
        return s;
    }

    private String l() {
        String s = "";
        if (this.X) {
            s = " <a href=\"javascript:document." + this.form + ".__action.value='page';" + targetScript + "document." + this.form + ".__gridpro1.value='-1';" + "document." + this.form + ".formction='" + this.c() + "';" + "document." + this.form + ".submit();\">" + this.R + "</a>";
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
        return s;
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




    private String setSearchInterface() {
        this.search = this.search + "<tr><td colspan='" + this.numberOfColumns + "'><table align='center'><tr><td class='PPRLabelText'><b>";
        this.search = this.search + "Search:";
        this.search = this.search + "&nbsp;&nbsp;";
        this.search = this.search + "<td><input type='text' size='20' class='PPRField' name='searchstring'></input></td>";
        this.search = this.search + "<td><input type='button' value='Go!' class='sbttn' onClick=\"javascript:document." + this.form + ".__action.value='page';" + "document." + this.form + ".__gridpro_search.value=document." + this.form + ".searchstring.value;" + "document." + this.form + ".action='" + this.c() + "';" +targetScript+ "document." + this.form + ".submit();\"></input>";
        this.search = this.search + "</td></tr></table></td></tr>";
        return this.search;
    }
    
    public void closeConnection() throws SQLException {
		if(rs!=null){
			rs.close();
		}
		if(stmt!=null){
			stmt.close();
		}
		if(con!=null){
			con.close();
		}
	}
}

