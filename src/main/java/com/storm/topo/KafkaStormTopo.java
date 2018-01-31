package com.storm.topo;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import com.storm.bolt.RedisBolt;
import com.storm.bolt.SplitBolt;
import com.storm.utils.ConfigUtils;

public class KafkaStormTopo {
    public static void main(String[] args) throws Exception{
        Config config = new Config();
        config.setDebug(true);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        String topic = ConfigUtils.defaultTopic;
        String zkRoot = ConfigUtils.zkRoot;
        String id = topic;
        BrokerHosts brokerHosts = new ZkHosts(ConfigUtils.ZKS);

		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        spoutConfig.bufferSizeBytes = 1024 * 1024 * 4;
        spoutConfig.fetchSizeBytes = 1024 * 1024 * 4;
		spoutConfig.ignoreZkOffsets = true;
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-spout", new KafkaSpout(spoutConfig));
        builder.setBolt("word-splitter", new SplitBolt()).shuffleGrouping("kafka-spout");
//        builder.setBolt("word-counter", new CountBolt()).shuffleGrouping("word-splitter");
        builder.setBolt("word-counter", new RedisBolt()).shuffleGrouping("word-splitter");

        StormSubmitter.submitTopology("KafkaStormTopo", config, builder.createTopology());
    }
}