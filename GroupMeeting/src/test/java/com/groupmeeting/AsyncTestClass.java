package com.groupmeeting;

import com.groupmeeting.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AsyncTestClass {
    @Autowired
    private UseTest useTest;


    @Test
    public void test() throws Exception {
        long l = System.nanoTime();
        System.out.println((System.nanoTime() - l) / 1_000_000);

        List<String> a = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            a.add(i + "");
        }

        var k = a.stream().map(i -> {
                    try {
                        return useTest.call(i);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();

        System.out.println(111);


        for (var kk : k) {
            System.out.println(kk.get());
        }
//        useTest.useAsync();
//
        System.out.println((System.nanoTime() - l) / 1_000_000);

    }

    @Test
    public void test2() throws Exception {
        List<String> a = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            a.add(i + "");
        }

        var k = a.stream().map(i -> {
                    try {
                        return useTest.call2(i);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();

        System.out.println(111);
    }
}
