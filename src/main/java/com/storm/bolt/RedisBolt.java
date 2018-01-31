package com.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.storm.utils.ConfigUtils;

import redis.clients.jedis.Jedis;

/**
 * 
 * ClassName: RedisBolt <br/>  
 * Function:  ADD FUNCTION. <br/>  
 * Created on: 2018年1月31日 下午4:24:15 <br/>  
 *  
 * @author: Wender
 * @version:   
 * @since: JDK 1.8
 */
public class RedisBolt implements IRichBolt {

	/**  
	 * serialVersionUID: 
	 * @since: JDK 1.8  
	 */
	private static final long serialVersionUID = -5697614986535880962L;
	
	private Jedis _jedis = null;
	
	private OutputCollector _collector;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this._collector = collector;
		try {
			String host = ConfigUtils.redisHost;
			int port = ConfigUtils.redisPort;
//			String password = ConfigUtils.redisPassword;
//			int db_index = 0;
			_jedis = new Jedis(host, port, 1000);
//			_jedis.auth(password);
//			_jedis.select(db_index);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void execute(Tuple input) {
		String word = input.getStringByField("word");
		try {
			_jedis.incr(word);
		} catch (Exception e) {
			e.printStackTrace();
			_collector.fail(input);
		}
		
		_collector.emit(input, new Values(word));
		_collector.ack(input);
	}

	@Override
	public void cleanup() {
		_jedis.close();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
