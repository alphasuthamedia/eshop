package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment();

        payment.setId(UUID.randomUUID().toString());
        payment.setMethod(method);
        payment.setPaymentData(paymentData);

        paymentRepository.save(payment.getId(), payment);

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (status.equals("SUCCESS")) {
            paymentRepository.setStatus(payment.getId(), status);
        } else if (status.equals("REJECTED")) {
            paymentRepository.setStatus(payment.getId(), "FAILED");
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentID) {
        return paymentRepository.getPayment(paymentID);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}
