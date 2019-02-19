package com.leyou.search.test;

import com.leyou.search.LySearchService;
import com.leyou.search.client.CategoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategories() {

        ResponseEntity<List<String>> datas = this.categoryClient.queryNameByIds(Arrays.asList(1l,2l,3l));

        List<String> list = datas.getBody();

        list.forEach(System.out::println);

    }
}
