package com.github.roman1306.lead.source.facebook;

import com.github.roman1306.lead.channel.NotificationSender;
import com.github.roman1306.lead.model.Lead;
import com.github.roman1306.lead.model.LeadNotification;
import com.github.roman1306.lead.source.facebook.model.Body;
import com.github.roman1306.lead.source.facebook.model.FacebookEnrichedLead;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/webhook/ads/facebook")
public class WebhookRestController {

  @NonNull
  private final EnrichmentLeadService enrichService;

  @NonNull
  private final Converter<FacebookEnrichedLead, Lead> converter;

  @NonNull
  private final Converter<Lead, LeadNotification> notificationConverter;

  @NonNull
  private final NotificationSender notificationSender;

  public WebhookRestController(
      @NonNull EnrichmentLeadService enrichService,
      @NonNull Converter<FacebookEnrichedLead, Lead> converter,
      @NonNull Converter<Lead, LeadNotification> notificationConverter,
      @NonNull NotificationSender notificationSender) {
    this.enrichService = enrichService;
    this.converter = converter;
    this.notificationConverter = notificationConverter;
    this.notificationSender = notificationSender;
  }

  @PostMapping
  Mono<Void> receiveLead(@RequestBody Body body) {
    return this.enrichService
        .enrich(body)
        .map(this.converter::convert)
        .map(this.notificationConverter::convert)
        .flatMap(this.notificationSender::send)
        .collectList()
        .then();

  }

}
