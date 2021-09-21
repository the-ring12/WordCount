package com.the_ring.storm.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.StringTokenizer;

public class SplitBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String s = tuple.getStringByField("value");
        System.out.println(s);
//        String ss[] = s.split(" ");
//        System.out.println(ss.length + ":" + ss.toString());
//        for (String s1 : ss) {
//            collector.emit(new Values(s1));
//            System.out.println("split ==> " + s1);
//        }
        StringTokenizer tokenizer = new StringTokenizer(s, " ,.?!'\"\n[]");
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            collector.emit(new Values(word));
            System.out.println("split ==> " + word);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
