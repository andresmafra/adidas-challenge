import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.adidas.itinerarycalculator.domains.Itinerary
import com.adidas.itinerarycalculator.exceptions.ResourceNotFoundException
import com.adidas.itinerarycalculator.gateways.ItineraryManagerGatewayInMemoryImpl
import com.adidas.itinerarycalculator.http.json.CityInputJson
import com.adidas.itinerarycalculator.templates.ItineraryTemplates
import com.adidas.itinerarycalculator.usecases.CalculateItinerary
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from
import static com.adidas.itinerarycalculator.domains.CalculateMode.CONNECTIONS
import static com.adidas.itinerarycalculator.domains.CalculateMode.TIME

class CalculateItinerarySpec extends Specification {

    def itineraryGateway = new ItineraryManagerGatewayInMemoryImpl()

    def calculateItinerary = new CalculateItinerary(itineraryGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.adidas.itinerarycalculator.templates")
    }

    def setup() {
        itineraryGateway.clear()
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.SPCU))
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.SPFL))
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.CUFL))
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.FLPA))
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.SPRJ))
        itineraryGateway.add((Itinerary) from(Itinerary.class).gimme(ItineraryTemplates.MASA))
    }

    def "Calculate a itinerary by minimum connections between São Paulo and Porto Alegre"() {
        given: "A itinerary from SP to PA"
        def itineraryInput = new CityInputJson().builder().from("São Paulo").to("Porto Alegre").build()

        when: "Calculate the itinerary"
        def itineraryResult = calculateItinerary.calculate(itineraryInput.getFrom(), itineraryInput.getTo(), CONNECTIONS)

        then: "The best Itinerary with less connections is returned"
        itineraryResult != null
        itineraryResult.travelTime == 240l
        itineraryResult.itineraries.size() == 2
    }

    def "Calculate a itinerary by minimum travel time between São Paulo and Porto Alegre"() {
        given: "A itinerary from SP to PA"
        def itineraryInput = new CityInputJson().builder().from("São Paulo").to("Porto Alegre").build()

        when: "Calculate the itinerary"
        def itineraryResult = calculateItinerary.calculate(itineraryInput.getFrom(), itineraryInput.getTo(), TIME)

        then: "The best Itinerary with less travel time is returned"
        itineraryResult != null
        itineraryResult.travelTime == 180l
        itineraryResult.itineraries.size() == 3
    }

    def "Calculate a itinerary with only direct connection between São Paulo and Rio de Janeiro"() {
        given: "A itinerary from SP to RJ"
        def itineraryInput = new CityInputJson().builder().from("São Paulo").to("Rio de Janeiro").build()

        when: "Calculate the itinerary"
        def itineraryResult = calculateItinerary.calculate(itineraryInput.getFrom(), itineraryInput.getTo(), CONNECTIONS)

        then: "The best Itinerary with less travel time is returned and doest not have itineraries path"
        itineraryResult != null
        itineraryResult.travelTime == 360l
        itineraryResult.itineraries.size() == 0
    }

    def "Calculate a itinerary that does not exists"() {
        given: "A itinerary from SP to RJ"
        def itineraryInput = new CityInputJson().builder().from("São Paulo").to("Moscow").build()

        when: "Calculate the itinerary"
        calculateItinerary.calculate(itineraryInput.getFrom(), itineraryInput.getTo(), TIME)

        then: "The itinerary is not found"
        thrown ResourceNotFoundException
    }
}