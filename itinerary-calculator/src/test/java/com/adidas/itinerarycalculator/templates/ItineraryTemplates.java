package com.adidas.itinerarycalculator.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.adidas.itinerarycalculator.domains.Itinerary;

public class ItineraryTemplates implements TemplateLoader {

    public static final String SPCU = "SPCU";
    public static final String SPFL = "SPFL";
    public static final String CUFL = "CUFL";
    public static final String FLPA = "FLPA";
    public static final String SPRJ = "SPRJ";
    public static final String MASA = "MASA";

    @Override
    public void load() {
        Fixture.of(Itinerary.class).addTemplate(SPCU, new Rule() {{
            add("id", "123");
            add("city", "São Paulo");
            add("destinyCity", "Curitiba");
            add("departureTime", "08:00");
            add("arrivalTime", "09:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(SPFL, new Rule() {{
            add("id", "1234");
            add("city", "São Paulo");
            add("destinyCity", "Floripa");
            add("departureTime", "12:00");
            add("arrivalTime", "15:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(CUFL, new Rule() {{
            add("id", "12345");
            add("city", "Curitiba");
            add("destinyCity", "Floripa");
            add("departureTime", "13:00");
            add("arrivalTime", "14:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(FLPA, new Rule() {{
            add("id", "123456");
            add("city", "Floripa");
            add("destinyCity", "Porto Alegre");
            add("departureTime", "15:00");
            add("arrivalTime", "16:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(SPRJ, new Rule() {{
            add("id", "1234567");
            add("city", "São Paulo");
            add("destinyCity", "Rio de Janeiro");
            add("departureTime", "12:00");
            add("arrivalTime", "18:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(MASA, new Rule() {{
            add("id", "1234567");
            add("city", "Manaus");
            add("destinyCity", "Salvador");
            add("departureTime", "08:00");
            add("arrivalTime", "21:00");
        }});
    }
}
