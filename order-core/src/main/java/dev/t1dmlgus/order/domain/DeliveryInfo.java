package dev.t1dmlgus.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class DeliveryInfo {

    private String receiverName;
    private String receiverPhoneNum;
    private String zipCode;
    private String address1;
    private String address2;
    private String msg;

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
