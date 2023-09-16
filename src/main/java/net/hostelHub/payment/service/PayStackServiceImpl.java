package net.hostelHub.payment.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.hostelHub.payment.dto.*;
import net.hostelHub.payment.entity.PaymentPayStack;
import net.hostelHub.payment.repository.PayStackPaymentRepository;
import net.hostelHub.payment.utils.PricingPlanType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import static net.hostelHub.payment.utils.ResponseUtils.*;


@Service
@RequiredArgsConstructor
public class PayStackServiceImpl implements PayStackService {

    private final PayStackPaymentRepository paystackPaymentRepository;
    private final WebClient webClient;

    @Value("${applyforme.paystack.public.key}")
    private String payStackSecretKey;


    @Override
    public CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception {
        CreatePlanResponse createPlanResponse = null;

        try {
            StringEntity postingString = new StringEntity("gson.toJson(createPlanDto)");
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INIT);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + payStackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_CREATED) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                return CreatePlanResponse.builder()
                        .message("Paystack is unable to process payment at the moment " +
                                "or something wrong with request")
                        .build();
            }

            ObjectMapper mapper = new ObjectMapper();
            createPlanResponse = mapper.readValue(result.toString(), CreatePlanResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return createPlanResponse;
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {

        InitializePaymentResponse response = webClient.post()
                .uri(PAYSTACK_INITIALIZE_PAY)
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + payStackSecretKey)
                .body(BodyInserters.fromValue(initializePaymentDto))
                .retrieve()
                .bodyToMono(InitializePaymentResponse.class)
                .block();

        if (response != null && response.getStatus().equals(true)) {
            PaymentPayStack user = PaymentPayStack.builder()
                    .amount(initializePaymentDto.getAmount())
                    .ipAddress(response.getData().getAuthorization_url())
                    .reference(response.getData().getReference())
                    .currency("NGN")
                    .build();
            paystackPaymentRepository.save(user);
            return response;
        } else {
            return InitializePaymentResponse.builder()
                    .message("Paystack is unable to initialize payment at the moment")
                    .build();
        }
    }

    @Override
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse;


        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + payStackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse.getData().getStatus().equals("success")) {

//                AppUser appUser = appUserRepository.getReferenceById(id);
//                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                CustomAttendeePaymentResponse user =getAttendeeDetails(paymentVerificationResponse.getData().getCustomer().getEmail());

                PaymentPayStack paymentPaystack = PaymentPayStack.builder()
                        .userId(user.getAttendeePaymentResponse().getId())
                        .name(user.getAttendeePaymentResponse().getName())
                        .email(user.getAttendeePaymentResponse().getEmail())
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGateway_response())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIp_address())
                        .pricingPlanType(PricingPlanType.BASIC)
                        .createdOn(new Date())
                        .build();
                if (paymentPaystack != null) {
                    paystackPaymentRepository.save(paymentPaystack);
                }
                return paymentVerificationResponse;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return paymentVerificationResponse;
    }

    public CustomAttendeePaymentResponse getAttendeeDetails(String email){
        CustomAttendeePaymentResponse response =  webClient.get()
                .uri(GETATTENDEEURL+ email)
                .retrieve()
                .bodyToMono(CustomAttendeePaymentResponse.class)
                .block();
        return response;
    }

}