package com.wen.EnumUtils;

public enum ResultTypeEnum implements EnumMessage<Integer> {
    SUCCESS(0x00,"成功"),
    FAIL(0x01,"失败");
    private Integer value;
    private String displayName;

    ResultTypeEnum(Integer value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public Integer getValue() {
        return value;
    }


    public String getDisplayName() {
        return displayName;
    }
}