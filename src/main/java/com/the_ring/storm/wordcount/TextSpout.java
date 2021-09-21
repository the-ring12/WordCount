package com.the_ring.storm.wordcount;

import org.apache.commons.io.FileUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TextSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Random random;
    private String[] values = new String[]{"the cow jumped over the moon",
                                            "an apple a day the doctor away",
                                            "four score and seven years ago",
                                            "snow white and seven dwarfs",
                                            "i am at two with nature"};

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
        random = new Random();
    }

    @Override
    public void nextTuple() {
//        Utils.sleep(1000);
//        int num = random.nextInt(values.length);
//        System.out.println("Spout ==> " + values[num]);
//        collector.emit(new Values(values[num]));

        Collection<File> files = FileUtils.listFiles(new File("C:\\Users\\Tan\\Desktop\\stormData"),
                new String[]{"txt"}, true);
        for (File file : files) {

//        File file = FileUtils.getFile("C:\\Users\\Tan\\Desktop\\stormData\\test.txt");
            try {
                List<String> strings = FileUtils.readLines(file);
                for (String string : strings) {
                    System.out.println(string);
                    collector.emit(new Values(string));
                }

                FileUtils.moveFile(file,new File(file.getAbsolutePath()+System.currentTimeMillis()));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("value"));
    }
}
