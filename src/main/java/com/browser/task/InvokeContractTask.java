package com.browser.task;

import com.browser.service.impl.RequestWalletService;
import com.browser.service.impl.SyncSenatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 锁仓资产查询
 */
@Component
public class InvokeContractTask {

    private static Logger logger = LoggerFactory.getLogger(InvokeContractTask.class);

    @Value("${scheduled.task}")
    private boolean task;

    @Autowired
    private RequestWalletService requestWalletService;

    @Autowired
    private SyncSenatorService syncSenatorService;

    @Scheduled(cron = "0/60 * * * * ? ")
    public void syncData() {
        if (task) {
            logger.info("不开启定时任务");
            return;
        }
        logger.info("同步锁仓数据开始");
        try {
            syncSenatorService.lockBalance();
        } catch (Exception e) {
            logger.error("同步锁仓数据异常：{}", e);
        }
        logger.info("同步锁仓数据结束");
    }
}
