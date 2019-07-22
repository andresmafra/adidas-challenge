import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.adidas.itinerarymanager.domains.Itinerary
import com.adidas.itinerarymanager.exceptions.ItineraryAlreadyExistsException
import com.adidas.itinerarymanager.exceptions.ItineraryNotFoundException
import com.adidas.itinerarymanager.gateways.ItineraryGatewayInMemoryImpl
import com.adidas.itinerarymanager.templates.ItineraryTemplates
import com.adidas.itinerarymanager.usecases.CrudItinerary
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class CrudItinerarySpec extends Specification {

    def itineraryGateway = new ItineraryGatewayInMemoryImpl()

    def crudItinerary = new CrudItinerary(itineraryGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.adidas.itinerarymanager.templates")
    }

    def setup() {
        itineraryGateway.clear()
    }

    def "Create a itinerary with success"() {
        given: "A itinerary from SP to RJ"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPRJ)

        when: "Creating the itinerary"
        def itineraryResult = crudItinerary.create(itinerary)

        then: "Itinerary is created with a new id"
        itineraryResult.getId() != null

        and: "Itinerary is persisted"
        itineraryGateway.findById(itineraryResult.getId()).isPresent()
    }

    def "Create a Itinerary with existing city and destinyCity"() {
        given: "An existing itinerary from SP to RJ"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPRJ)
        crudItinerary.create(itinerary)

        and: "A new itinerary from SP to RJ are to be created"
        def newItinerary = from(Itinerary).gimme(ItineraryTemplates.SPRJ_2)

        when: "Creating the itinerary"
        crudItinerary.create(newItinerary)

        then: "An error occurs because a itinerary with city and destinyCity already exists"
        thrown ItineraryAlreadyExistsException
    }

    def "Update a itinerary with success"() {
        given: "An existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itinerary)

        when: "Updating the itinerary departureTime"
        itinerary.setDepartureTime("22:00")
        crudItinerary.update(itinerary.getId(), itinerary)

        then: "Itinerary is updated"
        itineraryGateway.findById(itinerary.getId()).get().getDepartureTime() == "22:00"
    }

    def "Update a itinerary with invalid id"() {
        given: "An existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)

        when: "Updating the itinerary with invalid id"
        crudItinerary.update("invalid", itinerary)

        then: "Itinerary is not found for update"
        thrown ItineraryNotFoundException
    }

    def "Update a itinerary with data from an existing itinerary"() {
        given: "An existing itinerary from SP to BH"
        def itineraryBh = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itineraryBh)

        and: "An existing itinerary from SP to RJ"
        def itineraryRj = from(Itinerary).gimme(ItineraryTemplates.SPRJ)
        crudItinerary.create(itineraryRj)

        when: "Updating the itinerary rj with BH destinyCity"
        crudItinerary.update(itineraryRj.getId(), itineraryBh)

        then: "Itinerary is not updated because other already exists with same city and destinyCity"
        thrown ItineraryAlreadyExistsException
    }

    def "Delete a itinerary with success"() {
        given: "An existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itinerary)

        when: "Deleting the itinerary"
        crudItinerary.delete(itinerary.getId())

        then: "Itinerary is deleted"
        !itineraryGateway.findById(itinerary.getId()).isPresent()
    }

    def "Delete a itinerary that does not exists"() {
        given: "An existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itinerary)

        when: "Deleting the itinerary with wrong id"
        crudItinerary.delete("invalid")

        then: "Itinerary is not found for delete"
        thrown ItineraryNotFoundException
    }

    def "List all itinerary"() {
        given: "Existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itinerary)

        and: "from SP to RJ"
        def itineraryRj = from(Itinerary).gimme(ItineraryTemplates.SPRJ)
        crudItinerary.create(itineraryRj)

        when: "Listing all the itinerary"
        def allItinerary = crudItinerary.list()

        then: "All itinerary is found"
        allItinerary.size() == 2
    }

    def "List all with no itinerary"() {
        given: "No existing itinerary"
        when: "Listing all the itinerary"
        def allItinerary = crudItinerary.list()

        then: "All itinerary is empty"
        allItinerary.size() == 0
    }

    def "Query itinerary bt city"() {
        given: "Existing itinerary from SP to BH"
        def itinerary = from(Itinerary).gimme(ItineraryTemplates.SPBH)
        crudItinerary.create(itinerary)

        when: "Query all the itinerary from SP city"
        def allItinerary = crudItinerary.findAllByCity("SP")

        then: "All itinerary is found"
        allItinerary.size() == 1
    }
}
