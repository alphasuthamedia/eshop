package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PaymentTest{

    private Map<String, String> voucherData;
    @BeforeEach
    void setUp() {
        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreateVoucherPayment() {
        Payment payment = new Payment();
        String uuid = UUID.randomUUID().toString();
        payment.setId(uuid);
        payment.setMethod("voucherCode");
        payment.setPaymentData(voucherData);
        payment.setStatus("SUCCESS");

        assertEquals(uuid, payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals(voucherData, payment.getPaymentData());
    }

    @Test
    void testCreateSuccessPayment() {
        Map<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment();
        String uuid = UUID.randomUUID().toString();
        payment.setId(uuid);
        payment.setMethod("voucherCode");
        payment.setPaymentData(voucherData);
        payment.setStatus("SUCCESS");

        assertEquals(uuid, payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals(voucherData, payment.getPaymentData());
        assertEquals("ESHOP1234ABC5678", voucherData.get("voucherCode"));
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateFailedPayment() {
        Map<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ALPHAGANTENXX");

        Payment payment = new Payment();
        String uuid = UUID.randomUUID().toString();
        payment.setId(uuid);
        payment.setMethod("voucherCode");
        payment.setPaymentData(voucherData);
        payment.setStatus("SUCCESS");

        assertEquals(uuid, payment.getId());
        assertNotEquals("ESHOP1234ABC5678", newData.get("voucherCode"));
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
    }
}
