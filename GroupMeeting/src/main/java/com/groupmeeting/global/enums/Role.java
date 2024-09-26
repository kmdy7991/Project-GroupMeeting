package com.groupmeeting.global.enums;

public enum Role {
    ADMIN, USER;

    public String securityRole(){
        return "ROLE_" + this.name();
    }
}
