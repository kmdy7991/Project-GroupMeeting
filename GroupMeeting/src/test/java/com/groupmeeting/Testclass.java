package com.groupmeeting;

import com.groupmeeting.global.enums.Role;
import org.junit.jupiter.api.Test;

public class Testclass {
    @Test
    void test3() throws Exception {
        Role user = Role.USER;
        System.out.println(user);
        System.out.println(user.securityRole());
    }
}