package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.itaps.portal.domain.dto.OrsrDto;

@Service
public class OrsrServiceImpl {
    @Value("${orsr.url}")
    private String BASE_URL;
    @Value("${orsr.token}")
    private String ACCESS_TOKEN;

    private final RestTemplate restTemplate;

    public OrsrServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public OrsrDto[] getCompany(String ico) {
        return this.restTemplate.getForObject(this.BASE_URL + "?q=cin:" + ico + "&private_access_token=" + ACCESS_TOKEN + "&filter=active", OrsrDto[].class);
    }
}
