package com.zhengqing.demo;

import com.zhengqing.demo.modules.system.service.impl.TableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {



    @Autowired
    public TableService tableService;



    @Test
    public void contextLoads() {
        String tableInfo = tableService.getTableInfo();
    }


}
