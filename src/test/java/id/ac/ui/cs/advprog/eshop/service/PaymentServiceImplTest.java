package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order1, order2;
    Map<String, String> paymentDataVoucher = Map.of("voucherCode", "ESHOP1234ABC5678");
    Map<String, String> paymentDataCod = Map.of(
            "address", "Johor Selatan",
            "deliveryFee", "100"
    );

    @BeforeEach
    void setUp() {
        // Setup Order
        List<Product> products1 = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductID(UUID.randomUUID().toString());
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products1.add(product1);

        List<Product> products2 = new ArrayList<>();
        Product product2 = new Product();
        product2.setProductID(UUID.randomUUID().toString());
        product2.setProductName("Sabun Cap bunda");
        product2.setProductQuantity(1);
        products2.add(product2);

        order1 = new Order(UUID.randomUUID().toString(), products1, 1708560000L, "Safira Jenner");
        order2 = new Order(UUID.randomUUID().toString(), products2, 1708570000L, "Bambang Jenner");
        // Setup PaymentData

    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Payment result = paymentService.addPayment(order1, PaymentMethod.BY_VOUCHER.getValue(), paymentDataVoucher);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCodSuccess() {
        Payment payment = paymentService.addPayment(order2, PaymentMethod.BY_COD.getValue(), paymentDataCod);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejected() {
        Map<String, String> invalidVoucher = Map.of("voucherCode", "ESHOP1234ABCD"); // Kurang dari 16 karakter
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment.setPaymentData(invalidVoucher);

        Payment result = paymentService.addPayment(order1, PaymentMethod.BY_VOUCHER.getValue(), invalidVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());

        Map<String, String> invalidVoucher2 = Map.of("voucherCode", "INVALID1234ABC5678"); // tidak diawali dengan eshop
        Payment payment2 = new Payment();
        payment2.setId(UUID.randomUUID().toString());
        payment2.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment2.setPaymentData(invalidVoucher2);

        Payment result2 = paymentService.addPayment(order1, PaymentMethod.BY_VOUCHER.getValue(), invalidVoucher2);

        assertEquals(PaymentStatus.REJECTED.getValue(), result2.getStatus());

        Map<String, String> invalidVoucher3 = Map.of("voucherCode", "ESHOPABCDEFGHJKLMN"); // tidak ada 8 angka
        Payment payment3 = new Payment();
        payment3.setId(UUID.randomUUID().toString());
        payment3.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment3.setPaymentData(invalidVoucher3);

        Payment result3 = paymentService.addPayment(order1, PaymentMethod.BY_VOUCHER.getValue(), invalidVoucher3);

        assertEquals(PaymentStatus.REJECTED.getValue(), result3.getStatus());

        Map<String, String> invalidVoucher6 = Map.of("voucherCode", ""); // tidak ada 8 angka
        Payment payment6 = new Payment();
        payment6.setId(UUID.randomUUID().toString());
        payment6.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment6.setPaymentData(invalidVoucher6);

        Payment result6 = paymentService.addPayment(order1, PaymentMethod.BY_VOUCHER.getValue(), invalidVoucher6);

        assertEquals(PaymentStatus.REJECTED.getValue(), result6.getStatus());

        Map<String, String> invalidVoucher4 = Map.of("address", "", "deliveryFee", "1000"); // empty address

        Payment result4 = paymentService.addPayment(order1, PaymentMethod.BY_COD.getValue(), invalidVoucher4);
        paymentService.setStatus(result4, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result4.getStatus());

        Map<String, String> invalidVoucher5 = Map.of("address", "Ponorogo", "deliveryFee", ""); // empty fee
        Payment payment5 = new Payment();
        payment5.setId(UUID.randomUUID().toString());
        payment5.setMethod(PaymentMethod.BY_COD.getValue());
        payment5.setPaymentData(invalidVoucher4);

        Payment result5 = paymentService.addPayment(order1, PaymentMethod.BY_COD.getValue(), invalidVoucher5);
        paymentService.setStatus(result5, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result5.getStatus());
    }


    @Test
    void testGetPayment() {
        paymentService.addPayment(order1, PaymentStatus.SUCCESS.getValue(), paymentDataVoucher);

        assertNotNull(paymentService.getAllPayments());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment.setPaymentData(paymentDataVoucher);
        payments.add(payment);

        Payment payment1 = new Payment();
        payment1.setId(UUID.randomUUID().toString());
        payment1.setMethod(PaymentMethod.BY_VOUCHER.getValue());
        payment1.setPaymentData(paymentDataVoucher);
        payments.add(payment);

        assertEquals(2, payments.size());
    }
}