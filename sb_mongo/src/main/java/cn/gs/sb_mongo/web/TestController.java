package cn.gs.sb_mongo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import cn.gs.sb_mongo.domain.MyPage;
import cn.gs.sb_mongo.domain.MyQuery;
import cn.gs.sb_mongo.util.MapObjUtil;
import cn.gs.sb_mongo.util.PageUtil;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MyPage page;
	@Autowired
	private PageUtil pageUtil;
	
	@RequestMapping("/findGsAll")
	public List<DBObject> findGsAll(){
		List<DBObject> list = new ArrayList<DBObject>();
		DBCursor dbCursor = mongoTemplate.getCollection("gs").find();
		list = dbCursor.toArray();
		return list;
	}
	
	@RequestMapping("/findAllByTemplate")
	public List<DBObject> findAllByTemplate(String cName){
		List<DBObject> list = new ArrayList<DBObject>();
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find();
		list = dbCursor.toArray();
		return list;
	}
	
	@RequestMapping("/findById")
	public DBObject findById(String cName,String id){
		DBObject obj = new BasicDBObject();
		//注意写为_id
		obj.put("_id", id);
		DBObject result = mongoTemplate.getCollection(cName).findOne(obj);
		return result;
	}
	
	@RequestMapping("/pageInfo")
	public String pageInfo(String cName,int pageSize){
		int count = mongoTemplate.getCollection(cName).find().count();
		int pageCount = (count-1)/pageSize+1;
		String str = cName+"表中的"+count+"条数据每页"+pageSize+"条可分"+pageCount+"页";
		return str;
	}
	
	//skip+limit的方式效率低
	@RequestMapping("/pageQuery")
	public List<DBObject> pageQuery(String cName,int pageSize,int pageNow){
		long start = System.currentTimeMillis();
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject sortObj = new BasicDBObject();
		sortObj.put("_id", 1);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find().sort(sortObj).skip((pageNow-1)*pageSize).limit(pageSize);
		list = dbCursor.toArray();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		return list;
	}
	
	@RequestMapping("/pageFastQuery")
	public List<DBObject> pageFastQuery(String cName,String id,int pageSize,int pageNow){
		long start = System.currentTimeMillis();
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject findObj = new BasicDBObject();
		DBObject findIdObj = new BasicDBObject();
		findIdObj.put("$gt", id);
		findObj.put("_id", findIdObj);
		DBObject sortObj = new BasicDBObject();
		sortObj.put("_id", 1);
		DBCursor dbCursor = mongoTemplate.getCollection(cName).find(findObj).sort(sortObj).limit(pageSize);
		list = dbCursor.toArray();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		return list;
	}
	
	@RequestMapping("/utilIndex")
	public ModelAndView utilIndex(String cName){
		page.setcName(cName);
		Map<String,Object> map = pageUtil.getHomePage(page, mongoTemplate);
		ModelAndView mv = new ModelAndView("test/index");
		mv.addObject("list", map.get("list"));
		mv.addObject("page", map.get("page"));
		return mv;
	}
	
	@RequestMapping("/utilHome")
	public ModelAndView utilHome(){
		Map<String,Object> map = pageUtil.getHomePage(page, mongoTemplate);
		ModelAndView mv = new ModelAndView("test/index");
		mv.addObject("list", map.get("list"));
		mv.addObject("page", map.get("page"));
		return mv;
	}
	@RequestMapping("/utilPre")
	public ModelAndView utilPre(){
		Map<String,Object> map = pageUtil.getPrePage(page, mongoTemplate);
		ModelAndView mv = new ModelAndView("test/index");
		mv.addObject("list", map.get("list"));
		mv.addObject("page", map.get("page"));
		return mv;
	}
	@RequestMapping("/utilNext")
	public ModelAndView utilNext(){
		Map<String,Object> map = pageUtil.getNextPage(page, mongoTemplate);
		ModelAndView mv = new ModelAndView("test/index");
		mv.addObject("list", map.get("list"));
		mv.addObject("page", map.get("page"));
		return mv;
	}
	@RequestMapping("/utilEnd")
	public ModelAndView utilEnd(){
		Map<String,Object> map = pageUtil.getEndPage(page, mongoTemplate);
		ModelAndView mv = new ModelAndView("test/index");
		mv.addObject("list", map.get("list"));
		mv.addObject("page", map.get("page"));
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/utilAjaxIndex")
	public ModelAndView utilAjaxIndex(String cName){
		ModelAndView mv = new ModelAndView("test/testAjax");
		String oriCName = page.getcName();
//		if(oriCName==null){//首次查询的情况(cName==null)
//			page.setcName(cName);
//		}else if(oriCName.equals(cName)){//同表刷新的情况
//			page.setAjaxFlag(0);
//			page.setcCount(0);
//			page.setFirstData(null);
//			page.setLastData(null);
//			page.setPageCount(0);
//			page.setPageNow(1);
//		}else if(!oriCName.equals(cName)){//异表查询的情况
//			page.setAjaxFlag(0);
//			page.setcCount(0);
//			page.setFirstData(null);
//			page.setLastData(null);
//			page.setPageCount(0);
//			page.setPageNow(1);
//			page.setAjaxCount(0);
//			page.setAjaxValue(null);
//			page.setcName(cName);
//		}
		//新写法不再区分同表刷新和异表查询的情况!!!
		if(oriCName==null){
			page.setcName(cName);
		}else{
			page.setAjaxFlag(0);
			page.setcCount(0);
			page.setFirstData(null);
			page.setLastData(null);
			page.setPageCount(0);
			page.setPageNow(1);
			page.setAjaxCount(0);
			page.setAjaxValue(null);
			page.setcName(cName);
		}
		Map<String,Object> map = pageUtil.getAjaxIndexPage(page, mongoTemplate);
		mv.addObject("map", map);
		return mv;
	}
//	@RequestMapping("/utilAjaxIndex")
//	public ModelAndView utilAjaxIndex(String cName){
//		ModelAndView mv = new ModelAndView("test/testAjax");
//		String oriCName = page.getcName();
////		if(oriCName==null){//首次查询的情况(cName==null)
////			page.setcName(cName);
////		}else if(oriCName.equals(cName)){//同表刷新的情况
////			page.setAjaxFlag(0);
////			page.setcCount(0);
////			page.setFirstData(null);
////			page.setLastData(null);
////			page.setPageCount(0);
////			page.setPageNow(1);
////		}else if(!oriCName.equals(cName)){//异表查询的情况
////			page.setAjaxFlag(0);
////			page.setcCount(0);
////			page.setFirstData(null);
////			page.setLastData(null);
////			page.setPageCount(0);
////			page.setPageNow(1);
////			page.setAjaxCount(0);
////			page.setAjaxValue(null);
////			page.setcName(cName);
////		}
//		//新写法不再区分同表刷新和异表查询的情况!!!
//		if(oriCName==null){
//			page.setcName(cName);
//		}else{
//			page.setAjaxFlag(0);
//			page.setcCount(0);
//			page.setFirstData(null);
//			page.setLastData(null);
//			page.setPageCount(0);
//			page.setPageNow(1);
//			page.setAjaxCount(0);
//			page.setAjaxValue(null);
//			page.setcName(cName);
//		}
//		//查询trace157表中requSize大于4000的数据
//		BasicDBObject selectObj = new BasicDBObject();
//		BasicDBObject requSizeObj = new BasicDBObject();
//		requSizeObj.put("$gt", 4000);
//		selectObj.put("requSize", requSizeObj);
////		Map<String,Object> map = pageUtil.getAjaxIndexPage(page, mongoTemplate);
//		Map<String,Object> map = pageUtil.getAjaxIndexSelectPage(page,mongoTemplate,selectObj);
//		mv.addObject("map", map);
//		return mv;
//	}
	
	@RequestMapping("/utilAjax")
	public Map<String,Object> utilAjax(@RequestBody MyPage ajaxPage){
		Map<String,Object> map = pageUtil.getAjax(ajaxPage, mongoTemplate);
		return map;
	}
	
	@RequestMapping("/utilAjaxHome")
	public Map<String,Object> utilAjaxHome(@RequestBody MyPage ajaxPage){
		Map<String,Object> map = pageUtil.getAjaxHomePage(ajaxPage, mongoTemplate);
		return map;
	}
	@RequestMapping("/utilAjaxPre")
	public Map<String,Object> utilAjaxPre(@RequestBody MyPage ajaxPage){
		Map<String,Object> map = pageUtil.getAjaxPrePage(ajaxPage, mongoTemplate);
		return map;
	}
	@RequestMapping("/utilAjaxNext")
	public Map<String,Object> utilAjaxNext(@RequestBody MyPage ajaxPage){
		Map<String,Object> map = pageUtil.getAjaxNextPage(ajaxPage, mongoTemplate);
		return map;
	}
	@RequestMapping("/utilAjaxEnd")
	public Map<String,Object> utilAjaxEnd(@RequestBody MyPage ajaxPage){
		Map<String,Object> map = pageUtil.getAjaxEndPage(ajaxPage, mongoTemplate);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//带查询条件一系
//	@RequestMapping("/utilAjaxSelectIndex")
//	public ModelAndView utilAjaxSelectIndex(String cName){
//		ModelAndView mv = new ModelAndView("test/testAjaxSelect");
//		String oriCName = page.getcName();
//		//新写法不再区分同表刷新和异表查询的情况!!!
//		if(oriCName==null){
//			page.setcName(cName);
//			page.setPageNow(1);
//		}else{
//			page.setAjaxFlag(0);
//			page.setcCount(0);
//			page.setFirstData(null);
//			page.setLastData(null);
//			page.setPageCount(0);
//			page.setPageNow(1);
//			page.setAjaxCount(0);
//			page.setAjaxValue(null);
//			page.setcName(cName);
//		}
//		BasicDBObject selectObj = new BasicDBObject();
//		Map<String,Object> map = pageUtil.getAjaxIndexSelectPage(page,mongoTemplate,selectObj);
//		mv.addObject("map", map);
//		return mv;
//	}
	@RequestMapping("/utilAjaxSelectIndex")
	public ModelAndView utilAjaxSelectIndex(String cName){
		ModelAndView mv = new ModelAndView("test/testAjaxSelect");
		int cCount = 0;
		int pageCount = 0;
		int ajaxCount = 0;
		int ajaxFlag = 0;
		String ajaxValue = null;
		//String indexLastData = null;
		String lastData = null;
		
		BasicDBObject selectObj = new BasicDBObject();
		Map<String,Object> map = pageUtil.getAjaxIndexSelectPage(cName,cCount,pageCount,ajaxCount,ajaxFlag,ajaxValue,lastData,mongoTemplate,selectObj);
		
		map.put("cName", cName);
		
		mv.addObject("map", map);
		return mv;
	}
	
	//将MyQuery对象转为BasicDBObject对象
	public BasicDBObject queryToObj(MyQuery ajaxQuery){
		BasicDBObject selectObj = new BasicDBObject();
		String srchGtrs = ajaxQuery.getSrchGtrs();
		String srchLtrs = ajaxQuery.getSrchLtrs();
		if(!("").equals(srchGtrs) && !("").equals(srchLtrs) && null!=srchGtrs && null!=srchLtrs ){
			//一种写法
//			BasicDBList rsList = new BasicDBList();
//			BasicDBObject gtrsListObj = new BasicDBObject();
//			gtrsListObj.put("requSize", new BasicDBObject("$gt", Integer.parseInt(srchGtrs)));
//			rsList.add(gtrsListObj);
//			BasicDBObject ltrsListObj = new BasicDBObject();
//			ltrsListObj.put("requSize", new BasicDBObject("$lt", Integer.parseInt(srchLtrs)));
//			rsList.add(ltrsListObj);
//			selectObj.put("$and", rsList);							//{ "$and" : [ { "requSize" : { "$gt" : 3800}} , { "requSize" : { "$lt" : 4000}}]}
			//另一种写法
			Map<String,Integer> rsMap = new HashMap<String,Integer>();
			rsMap.put("$gt", Integer.parseInt(srchGtrs));
			rsMap.put("$lt", Integer.parseInt(srchLtrs));
			selectObj.put("requSize", new BasicDBObject(rsMap));	//{ "requSize" : { "$gt" : 3800 , "$lt" : 4000}}
		}else{
			if(!("").equals(srchGtrs) && null!=srchGtrs ){
				selectObj.put("requSize", new BasicDBObject("$gt", Integer.parseInt(srchGtrs)));
			}
			if(!("").equals(srchLtrs) && null!=srchLtrs ){
				selectObj.put("requSize", new BasicDBObject("$lt", Integer.parseInt(srchLtrs)));
			}
		}
		return selectObj;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/utilAjaxSearch")
	public Map<String,Object> utilAjaxSearch(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		Thread.sleep(3000);
		long time = (long) ajaxMap.get("time");
		
		String cName = (String) ajaxMap.get("cName");
		int cCount = (int) ajaxMap.get("cCount");
		int pageCount = (int) ajaxMap.get("pageCount");
		String lastData = (String) ajaxMap.get("lastData");
		int ajaxCount = (int) ajaxMap.get("ajaxCount");
		String ajaxValue = (String) ajaxMap.get("ajaxValue");
		int ajaxFlag = (int) ajaxMap.get("ajaxFlag");
		
		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
		BasicDBObject selectObj = queryToObj(ajaxQuery);
		Map<String,Object> map = pageUtil.getAjaxSelect(cName,cCount,pageCount,ajaxCount,ajaxFlag,ajaxValue,lastData, mongoTemplate,selectObj);
		map.put("time", time);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/utilAjaxSelect")
	public Map<String,Object> utilAjaxSelect(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		MyPage ajaxPage = (MyPage) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("page"), cn.gs.sb_mongo.domain.MyPage.class);
		
		String cName = (String) ajaxMap.get("cName");
		int pageNow = (int) ajaxMap.get("pageNow");
		String firstData = (String) ajaxMap.get("firstData");
		String lastData = (String) ajaxMap.get("lastData");
		String flag = (String) ajaxMap.get("flag");
		
		int pageCount = (int) ajaxMap.get("pageCount");
		String ajaxValue = (String) ajaxMap.get("ajaxValue");
		
		int cCount = (int) ajaxMap.get("cCount");
		
		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
		BasicDBObject selectObj = queryToObj(ajaxQuery);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		switch (flag) {
		case "Home":
			map = pageUtil.getAjaxHomeSelectPage(cName, mongoTemplate,selectObj);
			break;
		case "Pre":
			map = pageUtil.getAjaxPreSelectPage(cName,pageNow,firstData, mongoTemplate,selectObj);
			break;
		case "Next":
			map = pageUtil.getAjaxNextSelectPage(cName,pageNow,lastData,pageCount,ajaxValue, mongoTemplate,selectObj);
			break;
		case "End":
			map = pageUtil.getAjaxEndSelectPage(cName,ajaxValue,cCount, mongoTemplate,selectObj);
			break;
		}
		
		
		return map;
	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/utilAjaxSelectHome")
//	public Map<String,Object> utilAjaxSelectHome(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		MyPage ajaxPage = (MyPage) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("page"), cn.gs.sb_mongo.domain.MyPage.class);
//		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
//		BasicDBObject selectObj = queryToObj(ajaxQuery);
//		Map<String,Object> map = pageUtil.getAjaxHomeSelectPage(ajaxPage, mongoTemplate,selectObj);
//		return map;
//	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/utilAjaxSelectPre")
//	public Map<String,Object> utilAjaxSelectPre(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		MyPage ajaxPage = (MyPage) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("page"), cn.gs.sb_mongo.domain.MyPage.class);
//		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
//		BasicDBObject selectObj = queryToObj(ajaxQuery);
//		Map<String,Object> map = pageUtil.getAjaxPreSelectPage(ajaxPage, mongoTemplate,selectObj);
//		return map;
//	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/utilAjaxSelectNext")
//	public Map<String,Object> utilAjaxSelectNext(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		MyPage ajaxPage = (MyPage) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("page"), cn.gs.sb_mongo.domain.MyPage.class);
//		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
//		BasicDBObject selectObj = queryToObj(ajaxQuery);
//		Map<String,Object> map = pageUtil.getAjaxNextSelectPage(ajaxPage, mongoTemplate,selectObj);
//		return map;
//	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/utilAjaxSelectEnd")
//	public Map<String,Object> utilAjaxSelectEnd(@RequestBody Map<String,Object> ajaxMap) throws Exception{
//		MyPage ajaxPage = (MyPage) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("page"), cn.gs.sb_mongo.domain.MyPage.class);
//		MyQuery ajaxQuery = (MyQuery) MapObjUtil.mapToObject((Map<String, Object>) ajaxMap.get("query"), cn.gs.sb_mongo.domain.MyQuery.class);
//		BasicDBObject selectObj = queryToObj(ajaxQuery);
//		Map<String,Object> map = pageUtil.getAjaxEndSelectPage(ajaxPage, mongoTemplate,selectObj);
//		return map;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//删除整个 cName collection 的数据
	@RequestMapping("/removeByTemplate1")
	public void removeByTemplate1(int age,String cName){
		WriteResult writeResult = mongoTemplate.remove(new Query(), cName);
		System.out.println("删除了"+writeResult.getN()+"条记录");
	}
	
	@RequestMapping("/removeByTemplate2")
	public void removeByTemplate2(String name,int age,String cName){
		//注意删除的写法，和1  与  或  完全不同
		//和1	删除name且>age的数据
//		WriteResult writeResult = mongoTemplate.remove(new Query(Criteria.where("name").is(name).and("age").gt(age)), cName);
		//和2	删除name且>age的数据
//		WriteResult writeResult = mongoTemplate.remove(new Query(new Criteria().andOperator(Criteria.where("name").is(name),Criteria.where("age").gt(age))), cName);
		//或		删除name或>age的数据
		WriteResult writeResult = mongoTemplate.remove(new Query(new Criteria().orOperator(Criteria.where("name").is(name),Criteria.where("age").gt(age))), cName);
		System.out.println("删除了"+writeResult.getN()+"条记录");
	}
	
	//新增BasicDBObject或Document有很多额外属性
	//额外属性在mongodb中可见且查询(如findGsAll)可见的有_class;_id(仅BasicDBObject)
	//额外属性在mongodb中可见但查询不可见的有_id(仅Document)
	//额外属性在mongodb中不可见但查询可见的有timestamp、machineIdentifier、processIdentifier、counter、date、time、timeSecond
	@RequestMapping("/insertByTemplate")
	public void insertByTemplate(String name,int age,String cName){
		BasicDBObject obj = new BasicDBObject();
		obj.append("name", name);
		obj.append("age", age);
//		Document doc = new Document();
//		doc.append("name", name);
//		doc.append("age", age);
//		Map map = new HashMap();
//		map.put("name", name);
//		map.put("age", age);
		mongoTemplate.insert(obj, cName);
//		mongoTemplate.insert(doc, cName);
//		mongoTemplate.insert(map, cName);
	}
	
	//固定向gs添加3条数据
	@RequestMapping("/insertDefaultValuesToGs")
	public void insertByTemplate(){
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		BasicDBObject zs = new BasicDBObject();
		zs.append("_id", "55D832962E800.00000000000");
		zs.append("name", "zhangsan");
		zs.append("age", 33);
		BasicDBObject ls = new BasicDBObject();
		ls.append("_id", "55D8329816C80.00000000000");
		ls.append("name", "lisi");
		ls.append("age", 44);
		BasicDBObject ww = new BasicDBObject();
		ww.append("_id", "55D8329722A40.00000000000");
		ww.append("name", "wangwu");
		ww.append("age", 55);
		list.add(zs);
		list.add(ls);
		list.add(ww);
		//若新增的数据中存在主键,insert会提示错误,而save则更改原来的内容为新内容
		//若新增的数据中没有主键时，insert和save均会增加一条记录
		//save保存list或saveList有错误！！！
		mongoTemplate.insert(list, "gs");
//		BasicDBList saveList = new BasicDBList();
//		saveList.add(zs);
//		saveList.add(ls);
//		saveList.add(ww);
//		mongoTemplate.save(saveList, "gs");
//		mongoTemplate.save(list, "gs");
	}
}
