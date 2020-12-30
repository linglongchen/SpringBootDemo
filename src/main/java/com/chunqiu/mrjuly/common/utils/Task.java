package com.chunqiu.mrjuly.common.utils;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;


@SpringBootApplication
@EnableScheduling
@Transactional
public class Task {

    public static final ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap<>();
}

