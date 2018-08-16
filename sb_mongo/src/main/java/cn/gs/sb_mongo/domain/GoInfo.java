package cn.gs.sb_mongo.domain;

//goToPage的ajax信息
public class GoInfo {
	
	//本页第一条数据的sortField的内容
	private String firstData;
	//本页最后一条数据sortField的内容
	private String lastData;
	//当前是第几页
	private int pageNow;
	
	public String getFirstData() {
		return firstData;
	}
	public void setFirstData(String firstData) {
		this.firstData = firstData;
	}
	public String getLastData() {
		return lastData;
	}
	public void setLastData(String lastData) {
		this.lastData = lastData;
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

}
