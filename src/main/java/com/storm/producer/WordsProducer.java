package com.storm.producer;

import java.util.Properties;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.storm.utils.ConfigUtils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
  
/**
 * 
 * ClassName: WordsProducer <br/>  
 * Function:  ADD FUNCTION. <br/>  
 * Created on: 2018年1月31日 下午5:29:28 <br/>  
 *  
 * @author: Wender
 * @version:   
 * @since: JDK 1.8
 */
public class WordsProducer {
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {
    	WordsProducer wp = new WordsProducer();
    	String topicName = ConfigUtils.defaultTopic;
        wp.sendByTopic(topicName);
    }

	private static Producer<String, String> getProducer() {
		Properties props = new Properties();  
        props.put("serializer.class", "kafka.serializer.StringEncoder");  
        props.put("request.required.acks", "1");
        props.put("metadata.broker.list", ConfigUtils.METADATA_BROKER_LIST);  
          
        ProducerConfig config = new ProducerConfig(props);  
        Producer<String, String> producer = new Producer<String, String>(config);
		return producer;
	}
    
	private void sendByTopic(String topicName) {
		Producer<String, String> producer = getProducer();  
        System.out.println("send begin ------------------");
        for(String word: MSG.split("\\s+")){
        	if (StringUtils.isBlank(word)) {
        		continue;
        	}
        	KeyedMessage<String, String> data = new KeyedMessage<String, String>(topicName, word);
            producer.send(data);
            System.out.println(topicName+":"+word);
        }
        System.out.println("send over ------------------");  
        producer.close();
	}
    
    @Test
    public void testSendKafkaStorm() {
    	String topicName = ConfigUtils.defaultTopic;
        sendByTopic(topicName);
    }
    
    
    private static String MSG = " The new Java consumer is no longer in beta and"
    		+ " we recommend it for all new development. The old Scala consumers"
    		+ " are still supported, but they will be deprecated in the next "
    		+ "release and will be removed in a future major release. "
    		+ "The consumer switch is no longer required to"
    		+ " use tools like MirrorMaker and the Console Consumer with the new"
    		+ " consumer; one simply needs to pass a Kafka broker to connect to "
    		+ "instead of the ZooKeeper ensemble. In addition, usage of the "
    		+ "Console Consumer with the old consumer has been deprecated and it"
    		+ " will be removed in a future major release. Kafka clusters can now"
    		+ " be uniquely identified by a cluster id. It will be automatically"
    		+ " generated when a broker is upgraded to 0.10.1.0. The cluster id "
    		+ "is available via the kafka server type KafkaServer,name ClusterId"
    		+ " metric and it is part of the Metadata response. Serializers, "
    		+ "client interceptors and metric reporters can receive the cluster "
    		+ "id by implementing the ClusterResourceListener interface. The "
    		+ "BrokerState RunningAsController value 4 has been removed. Due"
    		+ " to a bug, a broker would only be in this state briefly before "
    		+ "transitioning out of it and hence the impact of the removal "
    		+ "should be minimal. The recommended way to detect if a given broker"
    		+ " is the controller is via the kafka controller type KafkaController,"
    		+ "name ActiveControllerCount metric. ";
}
