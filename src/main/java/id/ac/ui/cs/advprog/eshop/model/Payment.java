package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Setter @Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public void setPaymentData(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode == null) {
            if (this.method.equals(PaymentMethod.BY_COD.getValue())) {
                if (paymentData.get("address") != null && paymentData.get("deliveryFee") != null) {
                    this.paymentData = paymentData;
                    return;
                }
            }
            this.status = PaymentStatus.REJECTED.getValue();
            return;
        }

        if (voucherCode.length() != 16) {
            this.status = PaymentStatus.REJECTED.getValue();
            return;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            this.status = PaymentStatus.REJECTED.getValue();;
            return;
        }

        int numericCharCount = 0;
        for (char character : voucherCode.toCharArray()) {
            if (Character.isDigit(character)) {
                numericCharCount++;
            }
        }

        if (numericCharCount != 8) {
            this.status = PaymentStatus.REJECTED.getValue();;
            return;
        }

        this.status = PaymentStatus.SUCCESS.getValue();;
        this.paymentData = paymentData;
        return;
    }


    public Map getPaymentData() {
        return paymentData;
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Status must be contained on PaymentStatus Enum");
        }
    }
}

