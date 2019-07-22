package com.adidas.itinerarymanager.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.adidas.itinerarymanager.domains.Itinerary;

public class ItineraryTemplates implements TemplateLoader {

    public static final String SPRJ = "SPxRJ";
    public static final String SPRJ_2 = "SPxRJ_2";
    public static final String SPBH = "SPxBH";

    @Override
    public void load() {
        Fixture.of(Itinerary.class).addTemplate(SPRJ, new Rule() {{
            add("id", "1234");
            add("city", "SP");
            add("destinyCity", "RJ");
            add("departureTime", "10:00");
            add("arrivalTime", "15:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(SPRJ_2, new Rule() {{
            add("id", "0000");
            add("city", "SP");
            add("destinyCity", "RJ");
            add("departureTime", "18:00");
            add("arrivalTime", "21:00");
        }});

        Fixture.of(Itinerary.class).addTemplate(SPBH, new Rule() {{
            add("id", "4321");
            add("city", "SP");
            add("destinyCity", "BH");
            add("departureTime", "21:00");
            add("arrivalTime", "23:50");
        }});
    }
}
