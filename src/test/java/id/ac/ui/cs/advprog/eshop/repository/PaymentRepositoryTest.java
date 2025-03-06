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

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.payments = new ArrayList<>();

        Map<String, String> validVoucherData = new HashMap<String,String>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> invalidVoucherData = new HashMap<String,String>();
        invalidVoucherData.put("voucherCode", "akusayangkamujugasayang");

        Map<String, String> validCodData = new HashMap<String,String>();
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
        payment1.setPaymentData(validCodData);

        Payment payment3 = new Payment();
        payment3.setId(UUID.randomUUID().toString());
        payment3.setMethod(PaymentMethod.BY_COD.getValue());
        payment3.setStatus(PaymentStatus.SUCCESS.getValue());
        payment1.setPaymentData(validCodData);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.getFirst();
        Payment result = paymentRepository.save(payment);

        Payment paymentResult = paymentRepository.findById(payments.getFirst().getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(payment.getStatus(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testSaveEdit() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment modifiedPayment = new Payment();
        modifiedPayment.setId(UUID.randomUUID().toString());
        modifiedPayment.setMethod(PaymentMethod.BY_COD.getValue());
        modifiedPayment.setStatus(PaymentStatus.SUCCESS.getValue());
        modifiedPayment.setPaymentData(validCodData);
        Payment result = paymentRepository.save(modifiedPayment);

        Payment paymentResult = paymentRepository.findById(payments.getFirst().getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindValidId() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payments.get(2).getId(), paymentResult.getId());
        assertEquals(payments.get(2).getMethod(), paymentResult.getMethod());
        assertEquals(payments.get(2).getStatus(), paymentResult.getStatus());
        assertEquals(payments.get(2).getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindNotValidId() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById("maswisnugantenxx");
        assertNull(paymentResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(paymentList.size(), payments.size());
    }
}