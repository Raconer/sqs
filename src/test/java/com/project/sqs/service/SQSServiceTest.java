package com.project.sqs.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by IntelliJ IDEA.
 * User: kimdongho
 * Date: 2023/02/10
 * DESC :
 */
@SpringBootTest()
class SQSServiceTest {

    @Autowired
    SQSService sqsService;

    @Test
    void sendMsg() {
        String msg = this.testMsg();
        this.sqsService.sendMsg(msg);
    }

    @Test
    void receiveMsg() {
        this.sqsService.receiveMsg();
    }

    @Test
    void asyncReceiveMsg() {
        this.sqsService.asyncReceiveMsg();
    }

    public String testMsg() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date()) + "_ 일때 보낸 sqs 메시지_Visibility test";

    }
}