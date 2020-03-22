package com.github.rogerli.system;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDelay {

    private Map<String, DeferredResult<String>> deferreds = new ConcurrentHashMap<>();


    public DeferredResult<String> testDeferred(String msgId) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferreds.put(msgId, deferredResult);
        return deferredResult;
    }

    /**
     * 定时任务
     */
    @Scheduled(fixedDelay = 5000)
    public void taskResp() {
        if (deferreds != null && deferreds.size() > 0) {
            deferreds.forEach((k, deferredResult) -> {
                try {
                    deferredResult.setResult(k + "1234");
                    deferreds.remove(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {
        TestDelay delay = new TestDelay();
        delay.testDeferred("111");
    }

}
