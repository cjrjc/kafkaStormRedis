package com.storm.bolt;

import java.util.Map;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.task.TopologyContext;

public class SplitBolt implements IRichBolt {
    /**  
	 * serialVersionUID:
	 * @since: JDK 1.8  
	 */
	private static final long serialVersionUID = 5247940300866725897L;
	private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] words = sentence.split("\\s+");

        for(String word: words) {
            word = word.trim();

            if(!word.isEmpty()) {
                word = word.toLowerCase();
                collector.emit(new Values(word));
            }

        }

        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public void cleanup() {}

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}