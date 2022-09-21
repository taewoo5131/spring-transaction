package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
@Import(TestConfig.class)
public class TxLevelTest {

    @Autowired
    LevelService levelService;

    @Transactional(readOnly = true)
    static class LevelService {

        @Transactional(readOnly = false)
        public void write() {
            log.info("write");
            this.pringTxInfo();
        }

        public void read() {
            log.info("read");
            this.pringTxInfo();
        }

        private void pringTxInfo() {
            boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx Active >> {} ", actualTransactionActive);
            boolean currentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly >> {} ", currentTransactionReadOnly);
        }


    }
    @Test
    void test() {
        levelService.write();
        levelService.read();
    }
}

@TestConfiguration
class TestConfig {
    @Bean
    public TxLevelTest.LevelService levelService() {
        return new TxLevelTest.LevelService();
    }
}
