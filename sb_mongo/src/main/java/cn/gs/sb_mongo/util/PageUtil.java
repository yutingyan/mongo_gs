package cn.gs.sb_mongo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import cn.gs.sb_mongo.domain.MyPage;

@Component
public class PageUtil {
	
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
	
	//查询首页数据
	public Map<String,Object> getHomePage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		int pageSize = page.getPageSize();
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find().sort(sortObj);
		//判断cName的表中数据少于pageSize的情况
		long cCount = dbCursor.count();
		page.setcCount(cCount);
		int thisPageSize = 0;
		int pageCount = 0;
		if(cCount<pageSize){
			thisPageSize = (int) cCount;
			pageCount = 1;
		}else{
			thisPageSize = pageSize;
			pageCount = (int) ((cCount-1)/pageSize+1);
		}
		DBCursor showCursor = dbCursor.limit(thisPageSize);
		List<DBObject> list = showCursor.toArray(); 
		map.put("list", list);
		//查询完毕设置cCount、pageNow、pageCount和lastData
		page.setPageNow(1);
		page.setPageCount(pageCount);
		page.setLastData((String) list.get(thisPageSize-1).get(sortField));
		map.put("page", page);
		return map;
	}
	
	//查询尾页数据
	public Map<String,Object> getEndPage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		//尾页的数据集合就是排序相反的首页的数据集合
		if(sortOrder==1){
			sortOrder = -1;
		}else{
			sortOrder = 1;
		}
		int pageSize = page.getPageSize();
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find();
		int count = dbCursor.count();
		//计算尾页有多少条数据
		int endSize = count%pageSize;
		DBCursor resultCursor = dbCursor.sort(sortObj).limit(endSize);
		List<DBObject> list = resultCursor.toArray();
		//数据按相反排序得到，再反转得到正常排序
		Collections.reverse(list);
		map.put("list", list);
		//查询完毕设置pageNow和firstData
		page.setPageNow((count-1)/pageSize+1);
		page.setFirstData((String) list.get(0).get(sortField));
		map.put("page", page);
		return map;
	}
	
	//查询上一页数据
	public Map<String,Object> getPrePage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		if(sortOrder==1){
			sortOrder = -1;
		}else{
			sortOrder = 1;
		}
		int pageSize = page.getPageSize();
		String firstData = page.getFirstData();
		DBObject findObj = new BasicDBObject();
		DBObject findSfObj = new BasicDBObject();
		if(sortOrder==-1){
			findSfObj.put("$lt", firstData);
		}else{
			findSfObj.put("$gt", firstData);
		}
		findObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj).limit(pageSize);
		List<DBObject> list = dbCursor.toArray();
		Collections.reverse(list);
		map.put("list", list);
		//设置pageNow,firstData和lastData
		page.setPageNow(page.getPageNow()-1);
		page.setFirstData((String) list.get(0).get(sortField));
		page.setLastData((String) list.get(pageSize-1).get(sortField));
		map.put("page", page);
		return map;
	}
	
	//查询下一页数据
	public Map<String,Object> getNextPage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		int pageSize = page.getPageSize();
		String lastData = page.getLastData();
		DBObject findObj = new BasicDBObject();
		DBObject findSfObj = new BasicDBObject();
		if(sortOrder==1){
			findSfObj.put("$gt", lastData);
		}else{
			findSfObj.put("$lt", lastData);
		}
		findObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		//判断下一页是尾页的情况
		int pageNow = page.getPageNow();
		int pageCount = page.getPageCount();
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj);
		int thisPageSize = 0;
		DBCursor resultCursor = null;
		if(pageNow==pageCount-1){
			thisPageSize = dbCursor.count();
			resultCursor = dbCursor.limit(thisPageSize);
		}else{
			thisPageSize = pageSize;
			resultCursor = dbCursor.limit(pageSize);
		}
		List<DBObject> list = resultCursor.toArray();
		map.put("list", list);
		//设置pageNow,firstData和lastData
		page.setPageNow(page.getPageNow()+1);
		page.setFirstData((String) list.get(0).get(sortField));
		page.setLastData((String) list.get(thisPageSize-1).get(sortField));
		map.put("page", page);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//ajax一系
	//换表查询出错!!!!
	//公共的ajax查询当前cCount的DBCursor方法
	public Map<String,Object> getAjax(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		int ajaxFlag = page.getAjaxFlag();
		if(ajaxFlag==1){//若ajaxCount==cCount
			//新增
			map.put("ajaxPage", page);
			return map;
		}
		long ajaxCount = page.getAjaxCount();
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		int ajaxJump = page.getAjaxJump();
		int pageSize = page.getPageSize();
		String ajaxValue = page.getAjaxValue();//考虑null的情况及刚好是最后一个值的情况
		BasicDBObject findObj = new BasicDBObject();
		BasicDBObject tSfObj = new BasicDBObject();
		if(ajaxValue!=null){//刚开始lastData为null，此时findObj为{ }
			if(sortOrder==1){
				tSfObj.put("$gt", ajaxValue);
			}else{
				tSfObj.put("$lt", ajaxValue);
			}
			findObj.put(sortField, tSfObj);
		}
		BasicDBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		
		Map<String,Object> gavMap = getAjaxValue(mongoTemplate,cName,findObj,sortObj,ajaxJump,sortField,ajaxValue);
		String value = (String) gavMap.get("value");
		int size = (int) gavMap.get("size");
		page.setAjaxValue(value);
		ajaxCount = ajaxCount+size;
		page.setAjaxCount(ajaxCount);
		//当count<ajaxJump时，证明ajaxCount==cCount,此时设置ajaxFlag、ajaxValue、cCount、pageCount,并在前台显示最后一页
		if(size<ajaxJump){
			page.setAjaxFlag(1);
			page.setAjaxValue(value);
			page.setcCount(ajaxCount);
			page.setPageCount((int) ((ajaxCount-1)/pageSize+1));
		}
		//只查询一次，之后不查
		//未查询之前lastDta为null
		List<DBObject> list = new ArrayList<DBObject>();
		if(null==page.getLastData()){
			//不考虑表为空的情况!!!!
			//用null测试下!!!
			list = getFirstList(mongoTemplate,cName,null,sortObj,pageSize);
			String lastData = (String) list.get(list.size()-1).get(sortField);
			page.setLastData(lastData);
		}
		
		map.put("ajaxPage", page);
		map.put("list", list);
		
		return map;
	}
	//取得首页数据放在list中
	private List<DBObject> getFirstList(MongoTemplate mongoTemplate,String cName,BasicDBObject selectObj,BasicDBObject sortObj,int pageSize){
		//不考虑mongo中没有名为cName的collection的情况!!!!!
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj).limit(pageSize);
		List<DBObject> list = dbCursor.toArray();
		return list;
	}
	private Map<String,Object> getAjaxValue(MongoTemplate mongoTemplate,String cName,BasicDBObject findObj,BasicDBObject sortObj,int ajaxJump,String sortField,String ajaxValue){
		Map<String,Object> gavMap = new HashMap<String,Object>();
		//考虑dbCursor到的数据不足ajaxJump的情况!!!!!!!!!
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj).limit(ajaxJump);
		List<DBObject> list = dbCursor.toArray();
		int size = list.size();
		//无满足条件的数据，则设为ajaxValue;有满足条件的数据，则赋值
		String value = ajaxValue;
		if(size>0){
			//默认sortField为_id,数据类型为String!!!!!
			value = (String) list.get(size-1).get(sortField);
		}
		gavMap.put("value", value);
		gavMap.put("size", size);
		return gavMap;
	}
	
	//查询index数据
	public Map<String,Object> getAjaxIndexPage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//暂时只查ajaxJump条数据
		map = getAjax(page,mongoTemplate);
		page = (MyPage) map.get("ajaxPage");
		//设置首页信息
		page.setPageNow(1);
		
		return map;
	}
	//查询首页数据
	public Map<String,Object> getAjaxHomePage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		BasicDBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		int pageSize = page.getPageSize();
		List<DBObject> list =  getFirstList(mongoTemplate,cName,null,sortObj,pageSize);
		map.put("list", list);
		//设置首页信息
		page.setPageNow(1);
		//未考虑满足条件的数据小于ajaxJump、pageSize的情况
		page.setLastData((String) list.get(pageSize-1).get(sortField));
		map.put("ajaxPage", page);
		return map;
	}
	
	//查询尾页数据
	public Map<String,Object> getAjaxEndPage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		
		String ajaxValue = page.getAjaxValue();
		DBObject findObj = new BasicDBObject();
		DBObject tSfObj = new BasicDBObject();
		//尾页的数据集合就是排序相反的首页的数据集合
		if(sortOrder==1){
			sortOrder = -1;
			tSfObj.put("$lte", ajaxValue);
		}else{
			sortOrder = 1;
			tSfObj.put("$gte", ajaxValue);
		}
		findObj.put(sortField, tSfObj);
		int pageSize = page.getPageSize();
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj);
		long cCount = page.getcCount();
		//计算尾页有多少条数据
		int endSize = (int) (cCount%pageSize);
		//若刚好整除，则尾页数据条数=pageSize
		if(endSize==0){
			endSize = pageSize;
		}
		DBCursor resultCursor = dbCursor.sort(sortObj).limit(endSize);
		List<DBObject> list = resultCursor.toArray();
		//数据按相反排序得到，再反转得到正常排序
		Collections.reverse(list);
		map.put("list", list);
		//查询完毕设置pageNow和firstData
		page.setPageNow((int) ((cCount-1)/pageSize+1));
		page.setFirstData((String) list.get(0).get(sortField));
		map.put("ajaxPage", page);
		return map;
	}
	
	public Map<String,Object> getAjaxPrePage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		if(sortOrder==1){
			sortOrder = -1;
		}else{
			sortOrder = 1;
		}
		int pageSize = page.getPageSize();
		String firstData = page.getFirstData();
		DBObject findObj = new BasicDBObject();
		DBObject findSfObj = new BasicDBObject();
		if(sortOrder==-1){
			findSfObj.put("$lt", firstData);
		}else{
			findSfObj.put("$gt", firstData);
		}
		findObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj).limit(pageSize);
		List<DBObject> list = dbCursor.toArray();
		Collections.reverse(list);
		map.put("list", list);
		//设置pageNow,firstData和lastData
		page.setPageNow(page.getPageNow()-1);
		page.setFirstData((String) list.get(0).get(sortField));
		page.setLastData((String) list.get(pageSize-1).get(sortField));
		map.put("ajaxPage", page);
		return map;
	}
	
	public Map<String,Object> getAjaxNextPage(MyPage page,MongoTemplate mongoTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
		String cName = page.getcName();
		String sortField = page.getSortField();
		int sortOrder = page.getSortOrder();
		int pageSize = page.getPageSize();
		String lastData = page.getLastData();
		DBObject findObj = new BasicDBObject();
		DBObject findSfObj = new BasicDBObject();
		if(sortOrder==1){
			findSfObj.put("$gt", lastData);
		}else{
			findSfObj.put("$lt", lastData);
		}
		findObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		//判断下一页是尾页的情况
		int pageNow = page.getPageNow();
		int pageCount = page.getPageCount();
		
		int thisPageSize = 0;
		DBCursor resultCursor = null;
		if(pageNow==pageCount-1){
			//>lastData <=ajaxValue
			//<lastData >=ajaxValue
			String ajaxValue = page.getAjaxValue();
			if(sortOrder==1){
				findSfObj.put("$lte", ajaxValue);
			}else{
				findSfObj.put("$gte", ajaxValue);
			}
			DBCursor endCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj);
			thisPageSize = endCursor.count();
			resultCursor = endCursor.limit(thisPageSize);
		}else{
			thisPageSize = pageSize;
			DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj);
			resultCursor = dbCursor.limit(pageSize);
		}
		List<DBObject> list = resultCursor.toArray();
		map.put("list", list);
		//设置pageNow,firstData和lastData
		page.setPageNow(page.getPageNow()+1);
		page.setFirstData((String) list.get(0).get(sortField));
		page.setLastData((String) list.get(thisPageSize-1).get(sortField));
		map.put("ajaxPage", page);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//带查询条件一系
//	public Map<String,Object> getAjaxSelect(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		//未查询之前lastDta为null
//		List<DBObject> list = new ArrayList<DBObject>();
//		int ajaxFlag = page.getAjaxFlag();
//		if(ajaxFlag==1){//若ajaxCount==cCount
//			//新增
//			map.put("ajaxPage", page);
//			map.put("list", list);
//			return map;
//		}
//		long ajaxCount = page.getAjaxCount();
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		int ajaxJump = page.getAjaxJump();
//		int pageSize = page.getPageSize();
//		String ajaxValue = page.getAjaxValue();//考虑null的情况及刚好是最后一个值的情况
////		BasicDBObject findObj = new BasicDBObject();
//		BasicDBObject tSfObj = new BasicDBObject();
//		if(ajaxValue!=null){//刚开始lastData为null，此时findObj为{ }
//			if(sortOrder==1){
//				tSfObj.put("$gt", ajaxValue);
//			}else{
//				tSfObj.put("$lt", ajaxValue);
//			}
//			selectObj.put(sortField, tSfObj);
//		}
//		BasicDBObject sortObj = new BasicDBObject();
//		sortObj.put(sortField, sortOrder);
//		
//		Map<String,Object> gavMap = getAjaxValue(mongoTemplate,cName,selectObj,sortObj,ajaxJump,sortField,ajaxValue);
//		String value = (String) gavMap.get("value");
//		int size = (int) gavMap.get("size");
//		page.setAjaxValue(value);
//		ajaxCount = ajaxCount+size;
//		page.setAjaxCount(ajaxCount);
//		//当count<ajaxJump时，证明ajaxCount==cCount,此时设置ajaxFlag、ajaxValue、cCount、pageCount,并在前台显示最后一页
//		if(size<ajaxJump){
//			page.setAjaxFlag(1);
//			page.setAjaxValue(value);
//			page.setcCount(ajaxCount);
//			page.setPageCount((int) ((ajaxCount-1)/pageSize+1));
//		}
//		//只查询一次，之后不查
//		if(null==page.getLastData()){
//			//考虑为空的情况
//			list = getFirstList(mongoTemplate,cName,selectObj,sortObj,pageSize);
//			int firstSize = list.size();
//			if(firstSize==0){
//				page.setAjaxFlag(1);
//				page.setcCount(0);
//				page.setPageCount(0);
//			}else{
//				String lastData = (String) list.get(firstSize-1).get(sortField);
//				page.setLastData(lastData);
//			}
//		}
//		
//		map.put("ajaxPage", page);
//		map.put("list", list);
//		
//		return map;
//	}
	public Map<String,Object> getAjaxSelect(String cName,long cCount,int pageCount,long ajaxCount,int ajaxFlag,String ajaxValue,String lastData,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
		//未查询之前lastDta为null
		List<DBObject> list = new ArrayList<DBObject>();
//		int ajaxFlag = page.getAjaxFlag();
		if(ajaxFlag==1){//若ajaxCount==cCount
			//新增
//			map.put("ajaxPage", page);
//			map.put("cName", cName);
			map.put("cCount", cCount);
			map.put("pageCount", pageCount);
			map.put("ajaxCount", ajaxCount);
			map.put("ajaxFlag", ajaxFlag);
			map.put("ajaxValue", ajaxValue);
			map.put("list", list);
			return map;
		}
//		long ajaxCount = page.getAjaxCount();
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		int ajaxJump = page.getAjaxJump();
//		int pageSize = page.getPageSize();
//		String ajaxValue = page.getAjaxValue();//考虑null的情况及刚好是最后一个值的情况
//		BasicDBObject findObj = new BasicDBObject();
		BasicDBObject tSfObj = new BasicDBObject();
		if(ajaxValue!=null){//刚开始lastData为null，此时findObj为{ }
			if(sortOrder==1){
				tSfObj.put("$gt", ajaxValue);
			}else{
				tSfObj.put("$lt", ajaxValue);
			}
			selectObj.put(sortField, tSfObj);
		}
		BasicDBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		
		Map<String,Object> gavMap = getAjaxValue(mongoTemplate,cName,selectObj,sortObj,ajaxJump,sortField,ajaxValue);
		String value = (String) gavMap.get("value");
		int size = (int) gavMap.get("size");
//		page.setAjaxValue(value);
		ajaxValue = value;
		ajaxCount = ajaxCount+size;
//		page.setAjaxCount(ajaxCount);
		//当count<ajaxJump时，证明ajaxCount==cCount,此时设置ajaxFlag、ajaxValue、cCount、pageCount,并在前台显示最后一页
		if(size<ajaxJump){
//			page.setAjaxFlag(1);
//			page.setAjaxValue(value);
//			page.setcCount(ajaxCount);
//			page.setPageCount((int) ((ajaxCount-1)/pageSize+1));
			ajaxFlag = 1;
			ajaxValue = value;//与上4行重复???
			cCount = ajaxCount;
			pageCount = (int) ((ajaxCount-1)/pageSize+1);
		}
		//只查询一次，之后不查
//		if(null==page.getLastData()){
//			//考虑为空的情况
//			list = getFirstList(mongoTemplate,cName,selectObj,sortObj,pageSize);
//			int firstSize = list.size();
//			if(firstSize==0){
//				page.setAjaxFlag(1);
//				page.setcCount(0);
//				page.setPageCount(0);
//			}else{
//				String lastData = (String) list.get(firstSize-1).get(sortField);
//				page.setLastData(lastData);
//			}
//		}
		if(null==lastData){
			//考虑为空的情况
			list = getFirstList(mongoTemplate,cName,selectObj,sortObj,pageSize);
			int firstSize = list.size();
			if(firstSize==0){
				ajaxFlag = 1;
				cCount = 0L;
				pageCount = 0;
			}else{
				lastData = (String) list.get(firstSize-1).get(sortField);
				map.put("lastData", lastData);
			}
		}
//		map.put("ajaxPage", page);
//		map.put("cName", cName);
		map.put("cCount", cCount);
		map.put("pageCount", pageCount);
		map.put("ajaxCount", ajaxCount);
		map.put("ajaxFlag", ajaxFlag);
		map.put("ajaxValue", ajaxValue);
//		map.put("lastData", lastData);
		map.put("list", list);
		
		return map;
	}
	
//	public Map<String,Object> getAjaxIndexSelectPage(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		//暂时只查ajaxJump条数据
////			map = getAjax(page,mongoTemplate);
//		map = getAjaxSelect(page,mongoTemplate,selectObj);
//		page = (MyPage) map.get("ajaxPage");
//		//设置首页信息
//		page.setPageNow(1);
//		
//		return map;
//	}
	public Map<String,Object> getAjaxIndexSelectPage(String cName,long cCount,int pageCount,long ajaxCount,int ajaxFlag,String ajaxValue,String lastData,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
		map = getAjaxSelect(cName,cCount,pageCount,ajaxCount,ajaxFlag,ajaxValue,lastData,mongoTemplate,selectObj);
//		page = (MyPage) map.get("ajaxPage");
		//设置首页信息
//		page.setPageNow(1);
		map.put("indexPageNow", 1);
		
		map.put("pageNow", 1);
		map.put("firstData", null);
		
		map.put("ajaxJump", ajaxJump);
		map.put("pageSize", pageSize);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		
		return map;
	}
	
	public void ssss(){
		System.out.println("ssss");
	}
	
	//查询首页数据
	public Map<String,Object> getAjaxHomeSelectPage(String cName,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
		BasicDBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
//		int pageSize = page.getPageSize();
		List<DBObject> list =  getFirstList(mongoTemplate,cName,selectObj,sortObj,pageSize);
		map.put("list", list);
		//设置首页信息
//		page.setPageNow(1);
		map.put("pageNow", 1);
		//首页不需要记录firstData
		map.put("firstData", null);
		String lastData = (String) list.get(pageSize-1).get(sortField);
		map.put("lastData", lastData);
		return map;
	}
//	public Map<String,Object> getAjaxHomeSelectPage(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		BasicDBObject sortObj = new BasicDBObject();
//		sortObj.put(sortField, sortOrder);
//		int pageSize = page.getPageSize();
//		List<DBObject> list =  getFirstList(mongoTemplate,cName,selectObj,sortObj,pageSize);
//		map.put("list", list);
//		//设置首页信息
//		page.setPageNow(1);
//		//未考虑满足条件的数据小于ajaxJump、pageSize的情况
//		page.setLastData((String) list.get(pageSize-1).get(sortField));
//		map.put("ajaxPage", page);
//		return map;
//	}
	
	//查询尾页数据
	public Map<String,Object> getAjaxEndSelectPage(String cName,String ajaxValue,long cCount,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
		
//		String ajaxValue = page.getAjaxValue();
//		DBObject findObj = new BasicDBObject();
		DBObject tSfObj = new BasicDBObject();
		//尾页的数据集合就是排序相反的首页的数据集合
		int endSortOrder;
		if(sortOrder==1){
			endSortOrder = -1;
			tSfObj.put("$lte", ajaxValue);
		}else{
			endSortOrder = 1;
			tSfObj.put("$gte", ajaxValue);
		}
//		findObj.put(sortField, tSfObj);
		selectObj.put(sortField, tSfObj);
//		int pageSize = page.getPageSize();
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, endSortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj);
//		long cCount = page.getcCount();
		//计算尾页有多少条数据
		int endSize = (int) (cCount%pageSize);
		//若刚好整除，则尾页数据条数=pageSize
		if(endSize==0){
			endSize = pageSize;
		}
		DBCursor resultCursor = dbCursor.sort(sortObj).limit(endSize);
		List<DBObject> list = resultCursor.toArray();
		//数据按相反排序得到，再反转得到正常排序
		Collections.reverse(list);
		map.put("list", list);
		//查询完毕设置pageNow和firstData
		int pageNow = (int) ((cCount-1)/pageSize+1);
		map.put("pageNow", pageNow);
		String firstData = (String) list.get(0).get(sortField);
		map.put("firstData", firstData);
		map.put("lastData", null);
//		page.setPageNow((int) ((cCount-1)/pageSize+1));
//		page.setFirstData((String) list.get(0).get(sortField));
//		map.put("ajaxPage", page);
		return map;
	}
//	public Map<String,Object> getAjaxEndSelectPage(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		
//		String ajaxValue = page.getAjaxValue();
////		DBObject findObj = new BasicDBObject();
//		DBObject tSfObj = new BasicDBObject();
//		//尾页的数据集合就是排序相反的首页的数据集合
//		if(sortOrder==1){
//			sortOrder = -1;
//			tSfObj.put("$lte", ajaxValue);
//		}else{
//			sortOrder = 1;
//			tSfObj.put("$gte", ajaxValue);
//		}
////		findObj.put(sortField, tSfObj);
//		selectObj.put(sortField, tSfObj);
//		int pageSize = page.getPageSize();
//		DBObject sortObj = new BasicDBObject();
//		sortObj.put(sortField, sortOrder);
//		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj);
//		long cCount = page.getcCount();
//		//计算尾页有多少条数据
//		int endSize = (int) (cCount%pageSize);
//		//若刚好整除，则尾页数据条数=pageSize
//		if(endSize==0){
//			endSize = pageSize;
//		}
//		DBCursor resultCursor = dbCursor.sort(sortObj).limit(endSize);
//		List<DBObject> list = resultCursor.toArray();
//		//数据按相反排序得到，再反转得到正常排序
//		Collections.reverse(list);
//		map.put("list", list);
//		//查询完毕设置pageNow和firstData
//		page.setPageNow((int) ((cCount-1)/pageSize+1));
//		page.setFirstData((String) list.get(0).get(sortField));
//		map.put("ajaxPage", page);
//		return map;
//	}
	
	public Map<String,Object> getAjaxPreSelectPage(String cName,int pageNow,String firstData,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
		int preSortOrder;
		if(sortOrder==1){
			preSortOrder = -1;
		}else{
			preSortOrder = 1;
		}
//		int pageSize = page.getPageSize();
//		String firstData = page.getFirstData();
		DBObject findSfObj = new BasicDBObject();
		if(preSortOrder==-1){
			findSfObj.put("$lt", firstData);
		}else{
			findSfObj.put("$gt", firstData);
		}
//		findObj.put(sortField, findSfObj);
		selectObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, preSortOrder);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj).limit(pageSize);
		List<DBObject> list = dbCursor.toArray();
		Collections.reverse(list);
		map.put("list", list);
		//设置pageNow,firstData和lastData
		map.put("pageNow", pageNow-1);
		firstData = (String) list.get(0).get(sortField);
		map.put("firstData", firstData);
		String lastData = (String) list.get(pageSize-1).get(sortField);
		map.put("lastData", lastData);
//		page.setPageNow(page.getPageNow()-1);
//		page.setFirstData((String) list.get(0).get(sortField));
//		page.setLastData((String) list.get(pageSize-1).get(sortField));
//		map.put("ajaxPage", page);
		return map;
	}
//	public Map<String,Object> getAjaxPreSelectPage(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		if(sortOrder==1){
//			sortOrder = -1;
//		}else{
//			sortOrder = 1;
//		}
//		int pageSize = page.getPageSize();
//		String firstData = page.getFirstData();
////		DBObject findObj = new BasicDBObject();
//		DBObject findSfObj = new BasicDBObject();
//		if(sortOrder==-1){
//			findSfObj.put("$lt", firstData);
//		}else{
//			findSfObj.put("$gt", firstData);
//		}
////		findObj.put(sortField, findSfObj);
//		selectObj.put(sortField, findSfObj);
//		DBObject sortObj = new BasicDBObject();
//		sortObj.put(sortField, sortOrder);
//		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj).limit(pageSize);
//		List<DBObject> list = dbCursor.toArray();
//		Collections.reverse(list);
//		map.put("list", list);
//		//设置pageNow,firstData和lastData
//		page.setPageNow(page.getPageNow()-1);
//		page.setFirstData((String) list.get(0).get(sortField));
//		page.setLastData((String) list.get(pageSize-1).get(sortField));
//		map.put("ajaxPage", page);
//		return map;
//	}
	
	public Map<String,Object> getAjaxNextSelectPage(String cName,int pageNow,String lastData,int pageCount,String ajaxValue,MongoTemplate mongoTemplate,BasicDBObject selectObj){
		Map<String,Object> map = new HashMap<String,Object>();
		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		int pageSize = page.getPageSize();
//		String lastData = page.getLastData();
		DBObject findSfObj = new BasicDBObject();
		if(sortOrder==1){
			findSfObj.put("$gt", lastData);
		}else{
			findSfObj.put("$lt", lastData);
		}
		selectObj.put(sortField, findSfObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put(sortField, sortOrder);
		//判断下一页是尾页的情况
//		int pageNow = page.getPageNow();
//		int pageCount = page.getPageCount();
		
		int thisPageSize = 0;
		DBCursor resultCursor = null;
		if(pageNow==pageCount-1){
			//>lastData <=ajaxValue
			//<lastData >=ajaxValue
//			String ajaxValue = page.getAjaxValue();
			if(sortOrder==1){
				findSfObj.put("$lte", ajaxValue);
			}else{
				findSfObj.put("$gte", ajaxValue);
			}
			DBCursor endCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj);
			thisPageSize = endCursor.count();
			resultCursor = endCursor.limit(thisPageSize);
		}else{
			thisPageSize = pageSize;
			DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj);
			resultCursor = dbCursor.limit(pageSize);
		}
		List<DBObject> list = resultCursor.toArray();
		map.put("list", list);
		//设置pageNow,firstData和lastData
		map.put("pageNow", pageNow+1);
		String firstData = (String) list.get(0).get(sortField);
		map.put("firstData", firstData);
		lastData = (String) list.get(thisPageSize-1).get(sortField);
		map.put("lastData", lastData);
//		page.setPageNow(page.getPageNow()+1);
//		page.setFirstData((String) list.get(0).get(sortField));
//		page.setLastData((String) list.get(thisPageSize-1).get(sortField));
//		map.put("ajaxPage", page);
		return map;
	}
//	public Map<String,Object> getAjaxNextSelectPage(MyPage page,MongoTemplate mongoTemplate,BasicDBObject selectObj){
//		Map<String,Object> map = new HashMap<String,Object>();
//		//得到分页数据
//		String cName = page.getcName();
//		String sortField = page.getSortField();
//		int sortOrder = page.getSortOrder();
//		int pageSize = page.getPageSize();
//		String lastData = page.getLastData();
////		DBObject findObj = new BasicDBObject();
//		DBObject findSfObj = new BasicDBObject();
//		if(sortOrder==1){
//			findSfObj.put("$gt", lastData);
//		}else{
//			findSfObj.put("$lt", lastData);
//		}
////		findObj.put(sortField, findSfObj);
//		selectObj.put(sortField, findSfObj);
//		DBObject sortObj = new BasicDBObject();
//		sortObj.put(sortField, sortOrder);
//		//判断下一页是尾页的情况
//		int pageNow = page.getPageNow();
//		int pageCount = page.getPageCount();
//		
//		int thisPageSize = 0;
//		DBCursor resultCursor = null;
//		if(pageNow==pageCount-1){
//			//>lastData <=ajaxValue
//			//<lastData >=ajaxValue
//			String ajaxValue = page.getAjaxValue();
//			if(sortOrder==1){
//				findSfObj.put("$lte", ajaxValue);
//			}else{
//				findSfObj.put("$gte", ajaxValue);
//			}
//			DBCursor endCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj);
//			thisPageSize = endCursor.count();
//			resultCursor = endCursor.limit(thisPageSize);
//		}else{
//			thisPageSize = pageSize;
//			DBCursor dbCursor = mongoTemplate.getCollection(cName).find(selectObj).sort(sortObj);
//			resultCursor = dbCursor.limit(pageSize);
//		}
//		List<DBObject> list = resultCursor.toArray();
//		map.put("list", list);
//		//设置pageNow,firstData和lastData
//		page.setPageNow(page.getPageNow()+1);
//		page.setFirstData((String) list.get(0).get(sortField));
//		page.setLastData((String) list.get(thisPageSize-1).get(sortField));
//		map.put("ajaxPage", page);
//		return map;
//	}
}
