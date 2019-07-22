package com.adidas.itinerarycalculator.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.adidas.itinerarycalculator.domains.ItineraryOutput;
import com.google.common.collect.Lists;

public class ItineraryOutputTemplates implements TemplateLoader {

    public static final String SPPA = "SPxPA";
    public static final String SPPA_2 = "SPxPA_2";

    @Override
    public void load() {
        Fixture.of(ItineraryOutput.class).addTemplate(SPPA, new Rule() {{
            add("city", "São Paulo");
            add("destinyCity", "Porto Alegre");
            add("travelTime", 200l);
            add("itineraries", Lists.newArrayList("Curitiba", "Floripa", "Porto Alegre"));
        }});

        Fixture.of(ItineraryOutput.class).addTemplate(SPPA_2, new Rule() {{
            add("city", "São Paulo");
            add("destinyCity", "Porto Alegre");
            add("travelTime", 300l);
            add("itineraries", Lists.newArrayList("Floripa", "Porto Alegre"));
        }});
    }
}
