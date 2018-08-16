package cn.gs.sb_mongo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

//map转obj and obj转map
//两者互相转换有3种方法
//见http://www.open-open.com/code/view/1423280939826
public class MapObjUtil {
	
	//map转obj
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
        Object obj = beanClass.newInstance();  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }
        return obj;    
    }
	
	//obj转map
	public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
        Map<String, Object> map = new HashMap<String, Object>();    
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));  
        }    
        return map;  
    }  

}
