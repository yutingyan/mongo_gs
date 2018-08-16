package cn.gs.sb_mongo.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix="page")
@Scope("prototype")
public class MyPage {
	
	//collection的名称
	private String cName;
	//collection的满足查询条件的数据条数?????好像没什么用
	private long cCount;
	//每页数据条数,默认100
	@Value("${page.pageSize}")
	private int pageSize;
	//排序字段,默认为_id
	@Value("${page.sortField}")
	private String sortField;
	//排序方式，默认为1，升序
	@Value("${page.sortOrder}")
	private int sortOrder;
	//当前是第几页
	private int pageNow;
	//本页有多少条数据
	private int pageCount;
	//本页第一条数据的sortField的内容
	private String firstData;
	//本页最后一条数据sortField的内容
	private String lastData;
	//本页从collection查到的数据条数，每ajaxJump一加，直到等于cCount
	private long ajaxCount;
	//ajaxCount的根据sortOrder排的sortField的最值
	private String ajaxValue;
	//ajax每次调用后查询满足条件的数据条数
	@Value("${page.ajaxJump}")
	private int ajaxJump;
	//ajaxCount<cCount则0，=则1
	private int ajaxFlag = 0;
	
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public long getcCount() {
		return cCount;
	}
	public void setcCount(long cCount) {
		this.cCount = cCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
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
	public long getAjaxCount() {
		return ajaxCount;
	}
	public void setAjaxCount(long ajaxCount) {
		this.ajaxCount = ajaxCount;
	}
	public String getAjaxValue() {
		return ajaxValue;
	}
	public void setAjaxValue(String ajaxValue) {
		this.ajaxValue = ajaxValue;
	}
	public int getAjaxJump() {
		return ajaxJump;
	}
	public void setAjaxJump(int ajaxJump) {
		this.ajaxJump = ajaxJump;
	}
	public int getAjaxFlag() {
		return ajaxFlag;
	}
	public void setAjaxFlag(int ajaxFlag) {
		this.ajaxFlag = ajaxFlag;
	}

}
