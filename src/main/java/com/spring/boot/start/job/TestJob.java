package com.spring.boot.start.job;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("testJob")
public class TestJob implements  Job {


    @Override
    public void exe() {

        System.out.println("test");

    }
}
