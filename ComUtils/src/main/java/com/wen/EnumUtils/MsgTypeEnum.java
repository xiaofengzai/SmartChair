package com.wen.EnumUtils;

public enum MsgTypeEnum implements EnumMessage<Integer> {
    PING(0x00,"PING指令"),
    LOGIN(0x01,"登录指令"),
    HEART(0x02,"心跳包"),
    PAY(0x03,"支付指令");
    private Integer value;
    private String displayName;

    MsgTypeEnum(Integer value, String displayName) {
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
 /*
    1.消息类型
    0x00:ping
    0x01:心跳
    0x02:支付

    2.消息结果
    0x00:成功
    0x01:失败

    金额 */