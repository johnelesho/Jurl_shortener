package com.elsoft.assessment.jurlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/schemas.sql",})
class JUrlShortenerApplicationTests {

    @Test
    void contextLoads() {
    }

}
