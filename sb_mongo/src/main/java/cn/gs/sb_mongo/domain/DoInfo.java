package cn.gs.sb_mongo.domain;

//不停循环的ajax的信息
public class DoInfo {
	
	//本页从collection查到的数据条数，每ajaxJump一加，直到等于cCount
	private long ajaxCount;
	//ajaxCount<cCount则0，=则1
	private int ajaxFlag = 0;
	//ajaxCount的根据sortOrder排的sortField的最值
	private String ajaxValue;
	//collection的满足查询条件的数据条数
	private long cCount;
	//collection的名称
	private String cName;
	//本页有多少条数据
	private int pageCount;
	
	public long getAjaxCount() {
		return ajaxCount;
	}
	public void setAjaxCount(long ajaxCount) {
		this.ajaxCount = ajaxCount;
	}
	public int getAjaxFlag() {
		return ajaxFlag;
	}
	public void setAjaxFlag(int ajaxFlag) {
		this.ajaxFlag = ajaxFlag;
	}
	public String getAjaxValue() {
		return ajaxValue;
	}
	public void setAjaxValue(String ajaxValue) {
		this.ajaxValue = ajaxValue;
	}
	public long getcCount() {
		return cCount;
	}
	public void setcCount(long cCount) {
		this.cCount = cCount;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
