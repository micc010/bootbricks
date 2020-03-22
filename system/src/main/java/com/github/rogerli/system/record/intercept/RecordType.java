package com.github.rogerli.system.record.intercept;

public enum RecordType {

    insert(0), update(1), del(2);

    private Integer code;

    private RecordType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
