package dev.t1dmlgus.order.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class DeliveryInfo {

    private final String receiverName;
    private final String receiverPhoneNum;
    private final String zipCode;
    private final String address1;
    private final String address2;
    private final String msg;

    @Builder
    public DeliveryInfo(String receiverName, String receiverPhoneNum, String zipCode, String address1, String address2, String msg) {
        this.receiverName = receiverName;
        this.receiverPhoneNum = receiverPhoneNum;
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
        this.msg = msg;
    }




}
