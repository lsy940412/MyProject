package com.itany.service.impl;

import com.itany.service.IMytest;
import org.springframework.stereotype.Service;

@Service
public class MyTestServiceImpl implements IMytest {
    public void find(){
        System.out.println("======================find======================");
    }
}
