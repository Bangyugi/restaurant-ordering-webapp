package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.PaymentRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.PaymentResponse;
import com.group2.restaurantorderingwebapp.dto.response.VnPayResponse;
import com.group2.restaurantorderingwebapp.service.PaymentService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/vn-pay")
    public ResponseEntity<VnPayResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest, HttpServletRequest request) throws Exception {

        return new ResponseEntity<>(paymentService.createPayment(paymentRequest, request), HttpStatus.CREATED);

    }

    @PostMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallBackHandle(HttpServletRequest request) {

        return new ResponseEntity<>(paymentService.payCallBackHandle(request), HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long paymentId) {

        ApiResponse apiResponse = ApiResponse.success(paymentService.getPaymentById(paymentId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getPaymentByUserId(
            @PathVariable Long userId,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createAt", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse apiResponse = ApiResponse.success(paymentService.getPaymentByUser(userId, pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllPayment(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createAt", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse apiResponse = ApiResponse.success(paymentService.getAllPayment(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
