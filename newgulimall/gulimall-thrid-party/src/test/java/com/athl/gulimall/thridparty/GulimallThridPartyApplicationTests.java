package com.athl.gulimall.thridparty;

import com.athl.gulimall.thridparty.compoent.SmsComponent;
import com.athl.gulimall.thridparty.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GulimallThridPartyApplicationTests {

    @Autowired
    private SmsComponent smsComponent;
    @Test
    void contextLoads() {
    }

    @Test
    void testSms() {
        smsComponent.sendCode("18323667740","1235");
    }
}

