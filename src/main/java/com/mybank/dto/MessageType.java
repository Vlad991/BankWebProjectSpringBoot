package com.mybank.dto;

public enum MessageType {
    PRIVATE("PRIVATE"),
    COMMENT("COMMENT"),
    LOGOUT("LOGOUT"),
    ACTIVE_CLIENTS_LIST("ACTIVE_CLIENTS_LIST"); // TODO (MORE MESSAGE TYPES)

    private String type;

    private MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
