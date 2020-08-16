package com.github.roman1306.lead.channel;

import com.github.roman1306.lead.model.LeadNotification;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface NotificationSender {

  Mono<Void> send(LeadNotification leadNotification);
}
