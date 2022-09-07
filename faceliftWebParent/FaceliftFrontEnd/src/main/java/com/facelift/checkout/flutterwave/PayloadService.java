package com.facelift.checkout.flutterwave;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class PayloadService {
//    @Value("${flutterwave.public-key}")
//    private String ravePublicKey;
//    @Value("${flutterwave.secret-key}")
//    private String raveSecretKey;
//
//    public void initialize(Model model) throws Exception {
//        model.addAttribute("url", "https://checkout.flutterwave.com/v3.js");
//        Payload payload = (Payload) model.getAttribute("payload");
//        if (payload == null){
//            throw new Exception("Payload is empty");
//        }
//        addRaveDetailsToPayload(payload);
//        model.addAttribute("payload", payload);
//    }
//    private void addRaveDetailsToPayload(Payload payload) {
//        String txRef = UUID.randomUUID().toString();
//        payload.setPublic_key(ravePublicKey);
//        payload.setTx_ref(txRef);
//        payload.setRedirect_url("/callback");
//    }
//
//    public Map<String, Object> verifyTransaction(String transactionId) {
//        String url = "https://api.flutterwave.com/v3/transactions/" + transactionId + "/verify";
//        RestTemplate rest = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(raveSecretKey);
//        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<Map<String, Object>> response = rest.exchange(
//                url, HttpMethod.GET, httpEntity, typeRef);
//        return response.getBody();
//    }

}
