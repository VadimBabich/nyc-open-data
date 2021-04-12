package org.babich.street.configuration;

import org.babich.street.service.SodaStreetTreeService;
import org.babich.street.service.StreetTreeService;
import com.socrata.api.Soda2Consumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of third party service interoperability
 * @author Vadim Babich
 */
@Configuration
public class StreetTreeServiceConfiguration {

    @Value("${street.tree.base-url:https://data.cityofnewyork.us}")
    String baseUrl;

    @Value("${street.tree.resource-id:nwxe-4ae8}")
    private String resourceId;

    @Value("${street.tree.batch-limit:5}")
    private Integer batchLimit;

    @Bean
    public Soda2Consumer soda2Consumer() {
        return Soda2Consumer.newConsumer(baseUrl);
    }

    @Bean
    @ConditionalOnMissingClass
    public StreetTreeService streetTreeService(Soda2Consumer soda2Consumer){
        SodaStreetTreeService streetTreeService = new SodaStreetTreeService(soda2Consumer);
        streetTreeService.setResourceId(resourceId);
        streetTreeService.setBatchLimit(batchLimit);
        return streetTreeService;
    }
}
