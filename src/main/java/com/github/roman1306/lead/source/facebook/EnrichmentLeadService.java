package com.github.roman1306.lead.source.facebook;

import com.github.roman1306.lead.model.Lead;
import com.github.roman1306.lead.source.facebook.model.FacebookEnrichedLead;
import com.github.roman1306.lead.source.facebook.model.FacebookLead;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnrichmentLeadService {

 Flux<FacebookEnrichedLead> enrich(Iterable<FacebookLead> facebookLeads);
}
