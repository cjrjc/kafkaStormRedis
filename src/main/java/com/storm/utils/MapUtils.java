package com.storm.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * ClassName: MapUtils <br/>  
 * Function:  ADD FUNCTION. <br/>  
 * Created on: 2018年1月31日 下午5:28:56 <br/>  
 *  
 * @author: Wender
 * @version:   
 * @since: JDK 1.8
 */
public class MapUtils {
	
	public static void printKV(Map<String, Object> map) {
		if (null == map || map.isEmpty()) {
			return ;
		}
		
		System.out.println("---------------------------------------");
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println("<"+key+", "+map.get(key)+">");
		}
		System.out.println("---------------------------------------");
	}

}
