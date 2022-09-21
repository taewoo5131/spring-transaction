package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Autowired
    BasicService basicService;

    @TestConfiguration
    static class TxApplyBasicConfig {
        @Bean
        BasicService basicService() {
            return new BasicService();
        }
    }

    @Slf4j
    static class BasicService{
        @Transactional
        public void tx() {
            log.info("call tx");
            boolean isActiveTransaction = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}  ", isActiveTransaction);
        }

        public void nonTx() {
            log.info("call nonTx");
            boolean isActiveTransaction = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("nonTx active = {}  ", isActiveTransaction);
        }
    }

    @Test
    void test() {
        basicService.tx();
        basicService.nonTx();
    }
}
