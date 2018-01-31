package com.storm.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * ClassName: ConfigUtils <br/>  
 * Function:  ADD FUNCTION. <br/>  
 * Created on: 2018年1月31日 下午5:28:30 <br/>  
 *  
 * @author: Wender
 * @version:   
 * @since: JDK 1.8
 */
public class ConfigUtils {
	
	public final static String ZKS = "CentOS65M1:2181,CentOS65M2:2181,CentOS65-3:2181";
	
	public final static List<String> ZK_SERVERS = Arrays.asList(new String[] {"CentOS65M1","CentOS65M2","CentOS65-3"});
	
	public final static int ZK_PORT = 2181;
	
	public final static String METADATA_BROKER_LIST = "CentOS65M1:9092,CentOS65M2:9092,CentOS65-3:9092";
	
	public final static String nimbusHost = "CentOS65APP";
	
	public final static String redisHost = "CentOS65APP";
	
	public final static int redisPort = 6379;
	
	public final static String redisPassword = "inputYourPasswordHere";
	
	public final static String zkRoot = "/kafka";
	
	public final static String defaultTopic = "kafkaStormRedis";
	
}
