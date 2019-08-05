package com.mybank.dto;

public enum MessageType {
    PRIVATE("PRIVATE"),
    COMMENT("COMMENT"),
    LOGOUT("LOGOUT"),
    ACTIVE_CLIENT_LIST("ACTIVE_CLIENT_LIST"), // TODO (MORE MESSAGE TYPES)
    ACTIVE_MANAGER_LIST("ACTIVE_MANAGER_LIST");

    private String type;

    private MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
