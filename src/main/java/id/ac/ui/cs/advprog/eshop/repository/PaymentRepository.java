package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    Map<String, Payment> lumbungPayment = new HashMap<>();

    public Payment save(String ID, Payment payment) {
        lumbungPayment.put(ID, payment);
        return payment;
    }

    public Payment getPayment(String ID) {
        return lumbungPayment.get(ID);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(lumbungPayment.values());
    }

}
