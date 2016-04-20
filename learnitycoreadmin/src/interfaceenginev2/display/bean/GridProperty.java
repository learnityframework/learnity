package interfaceenginev2.display.bean;

import comv2.aunwesha.lfutil.GenericUtil;

public class GridProperty {
	private boolean altRows;
	private boolean autowidth;
	private boolean ignoreCase;
	private boolean rowNumbers;

	private String altClass;

	public GridProperty(boolean altRows, boolean autowidth, boolean ignoreCase, boolean rowNumbers, String altClass) {
		super();
		this.altRows = altRows;
		this.autowidth = autowidth;
		this.ignoreCase = ignoreCase;
		this.rowNumbers = rowNumbers;
		this.altClass = altClass;
	}
	
	public GridProperty(){
		
	}
	
	public boolean isDataExist(){
		if(this.altRows){
			return true;
		}else if(this.autowidth){
			return true;
		}else if(this.ignoreCase){
			return true;
		}else if(this.rowNumbers){
			return true;
		}else if(GenericUtil.hasString(this.altClass)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isAltRows() {
		return altRows;
	}

	public void setAltRows(boolean altRows) {
		this.altRows = altRows;
	}

	public boolean isAutowidth() {
		return autowidth;
	}

	public void setAutowidth(boolean autowidth) {
		this.autowidth = autowidth;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public boolean isRowNumbers() {
		return rowNumbers;
	}

	public void setRowNumbers(boolean rowNumbers) {
		this.rowNumbers = rowNumbers;
	}

	public String getAltClass() {
		return altClass;
	}

	public void setAltClass(String altClass) {
		this.altClass = altClass;
	}

}
