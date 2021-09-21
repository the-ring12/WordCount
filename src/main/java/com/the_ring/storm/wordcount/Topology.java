package com.the_ring.storm.wordcount;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class Topology {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new TextSpout());
        builder.setBolt("split", new SplitBolt()).shuffleGrouping("spout");
        builder.setBolt("count", new CountBolt()).fieldsGrouping("split", new Fields("word"));

        Config config = new Config();
//        config.setDebug(true);
        try {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", config, builder.createTopology());
//            Utils.sleep(60*1000);
//            cluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
