package com.mybank.entity;

public enum BlockingReason {
    ADVERTISING("ADVERTISING"),
    INAPPROPRIATE_COMMENTS("INAPPROPRIATE_COMMENTS"),
    HACKING_ATTEMPT("HACKING_ATTEMPT"); // todo

    private String reason;

    private BlockingReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

}
