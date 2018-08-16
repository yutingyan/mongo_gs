package cn.gs.sb_mongo.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//不会改变的分页信息
@Component
@ConfigurationProperties(prefix="page")
public class StaticInfo {
	
	//ajax每次调用后查询满足条件的数据条数
	@Value("${page.ajaxJump}")
	private int ajaxJump;
	//每页数据条数
	@Value("${page.pageSize}")
	private int pageSize;
	//排序字段,默认为_id
	@Value("${page.sortField}")
	private String sortField;
	//排序方式，默认为1，升序
	@Value("${page.sortOrder}")
	private int sortOrder;
	
	public int getAjaxJump() {
		return ajaxJump;
	}
	public int getPageSize() {
		return pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public int getSortOrder() {
		return sortOrder;
	}

}
