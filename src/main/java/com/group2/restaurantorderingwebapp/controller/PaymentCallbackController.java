package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.response.PaymentResponse;
import com.group2.restaurantorderingwebapp.entity.Payment;
import com.group2.restaurantorderingwebapp.repository.PaymentRepository;
import com.group2.restaurantorderingwebapp.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Callback", description = "Handle Payment Callback")
public class PaymentCallbackController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/bill")
    @Operation(summary = "Payment Callback", description = "Payment Callback API")
    public String payCallBackHandle(HttpServletRequest request, Model model) {
        Payment payment = paymentService.payCallBackHandle(request);
        System.out.println("hello");
        model.addAttribute("paymentId", payment.getPaymentId());
        model.addAttribute("paymentMethod", payment.getPaymentMethod());
        model.addAttribute("amount", payment.getAmount());
        model.addAttribute("user", payment.getUser());
        model.addAttribute("orders", payment.getOrder());


        // Trả về template "bill"
        return "bill";
    }
}
