package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    Map<String, String> validVoucherData = new HashMap<String,String>();
    Map<String, String> invalidVoucherData = new HashMap<String,String>();
    Map<String, String> validCodData = new HashMap<String,String>();


    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.payments = new ArrayList<>();

        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");
        invalidVoucherData.put("voucherCode", "akusayangkamujugasayang");
        validCodData.put("address", "Ponorogo");
        validCodData.put("deliveryFee","10000000");

        Payment payment1 = new Payment();
        payment1.setId(UUID.randomUUID().toString());
        payment1.setMethod(PaymentMethod.BY_COD.getValue());
        payment1.setStatus(PaymentStatus.SUCCESS.getValue());
        payment1.setPaymentData(validCodData);

        Payment payment2 = new Payment();
        payment2.setId(UUID.randomUUID().toString());
        payment2.setMethod(PaymentMethod.BY_COD.getValue());
        payment2.setStatus(PaymentStatus.SUCCESS.getValue());
        payment2.setPaymentData(validCodData);

        Payment payment3 = new Payment();
        payment3.setId(UUID.randomUUID().toString());
        payment3.setMethod(PaymentMethod.BY_COD.getValue());
        payment3.setStatus(PaymentStatus.SUCCESS.getValue());
        payment3.setPaymentData(validCodData);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.getFirst();
        paymentRepository.save(payment.getId(), payment);

        Payment paymentResult = paymentRepository.getPayment(payments.getFirst().getId());

        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(payment.getStatus(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindValidId() {
        for (Payment payment : payments){
            paymentRepository.save(payment.getId(), payment);
        }

        Payment paymentResult = paymentRepository.getPayment(payments.get(2).getId());
        assertEquals(payments.get(2).getId(), paymentResult.getId());
        assertEquals(payments.get(2).getMethod(), paymentResult.getMethod());
        assertEquals(payments.get(2).getStatus(), paymentResult.getStatus());
        assertEquals(payments.get(2).getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindNotValidId() {
        for (Payment payment : payments){
            paymentRepository.save(payment.getId(), payment);
        }

        Payment paymentResult = paymentRepository.getPayment("maswisnugantenxx");
        assertNull(paymentResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments){
            paymentRepository.save(payment.getId(), payment);
        }

        List<Payment> paymentList = paymentRepository.getAllPayments();
        assertEquals(paymentList.size(), payments.size());
    }
}