package com.adidas.itinerarymanager.http;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.adidas.itinerarymanager.domains.Itinerary;
import com.adidas.itinerarymanager.exceptions.ItineraryAlreadyExistsException;
import com.adidas.itinerarymanager.exceptions.ItineraryNotFoundException;
import com.adidas.itinerarymanager.http.json.QueryJson;
import com.adidas.itinerarymanager.usecases.CrudItinerary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.adidas.itinerarymanager.templates.ItineraryTemplates.SPBH;
import static com.adidas.itinerarymanager.templates.ItineraryTemplates.SPRJ;
import static com.google.common.collect.Lists.newArrayList;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItineraryControllerTest {

    private static final String API_PATH = "/v1/itinerary";

    private ItineraryController itineraryController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CrudItinerary crudItinerary;

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("com.adidas.itinerarymanager.templates");
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        itineraryController = new ItineraryController(crudItinerary);
        mockMvc = MockMvcBuilders.standaloneSetup(itineraryController).build();
    }

    @Test
    public void create_itinerary_success() throws Exception {

        when(crudItinerary.create(any(Itinerary.class)))
                .thenReturn(from(Itinerary.class).gimme(SPRJ));

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(from(Itinerary.class).gimme(SPRJ))))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(CREATED.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.id", equalTo("1234")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.destinyCity", equalTo("RJ")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.departureTime", equalTo("10:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.arrivalTime", equalTo("15:00")));
    }

    @Test
    public void create_itinerary_already_exists() throws Exception {

        when(crudItinerary.create(any(Itinerary.class))).thenThrow(new ItineraryAlreadyExistsException("exists!"));

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(from(Itinerary.class).gimme(SPBH))))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(CONFLICT.value()));
    }

    @Test
    public void create_itinerary_bad_request() throws Exception {

        final Itinerary badRequest = new Itinerary();
        badRequest.setCity("Lima");

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(badRequest)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(BAD_REQUEST.value()));
    }

    @Test
    public void update_itinerary_success() throws Exception {

        when(crudItinerary.update(any(), any(Itinerary.class)))
                .thenReturn(from(Itinerary.class).gimme(SPBH));

        final MvcResult mvcResult = mockMvc.perform(put(API_PATH + "/ID")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(from(Itinerary.class).gimme(SPBH))))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.id", equalTo("4321")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.destinyCity", equalTo("BH")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.departureTime", equalTo("21:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.arrivalTime", equalTo("23:50")));
    }

    @Test
    public void update_itinerary_not_found() throws Exception {

        when(crudItinerary.update(any(), any(Itinerary.class))).thenThrow(new ItineraryNotFoundException("not found!"));

        final MvcResult mvcResult = mockMvc.perform(put(API_PATH + "/ID")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(from(Itinerary.class).gimme(SPBH))))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(NOT_FOUND.value()));
    }

    @Test
    public void update_itinerary_already_exists() throws Exception {

        when(crudItinerary.update(any(), any(Itinerary.class))).thenThrow(new ItineraryAlreadyExistsException("axists!"));

        final MvcResult mvcResult = mockMvc.perform(put(API_PATH + "/ID")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(from(Itinerary.class).gimme(SPBH))))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(CONFLICT.value()));
    }

    @Test
    public void delete_itinerary_success() throws Exception {

        doNothing().when(crudItinerary).delete(any());

        final MvcResult mvcResult = mockMvc.perform(delete(API_PATH + "/ID")).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(NO_CONTENT.value()));
    }

    @Test
    public void delete_itinerary_not_found() throws Exception {

        doThrow(new ItineraryNotFoundException("not found!")).when(crudItinerary).delete(any());

        final MvcResult mvcResult = mockMvc.perform(delete(API_PATH + "/ID")).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(NOT_FOUND.value()));
    }

    @Test
    public void list_itinerary_success() throws Exception {

        when(crudItinerary.list())
                .thenReturn(newArrayList(from(Itinerary.class).gimme(SPBH), from(Itinerary.class).gimme(SPRJ)));

        final MvcResult mvcResult = mockMvc.perform(get(API_PATH)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$", hasSize(2)));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].id", equalTo("4321")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].destinyCity", equalTo("BH")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].departureTime", equalTo("21:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].arrivalTime", equalTo("23:50")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].id", equalTo("1234")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].destinyCity", equalTo("RJ")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].departureTime", equalTo("10:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].arrivalTime", equalTo("15:00")));
    }

    @Test
    public void list_itinerary_no_content() throws Exception {

        when(crudItinerary.list()).thenReturn(newArrayList());

        final MvcResult mvcResult = mockMvc.perform(get(API_PATH)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$", hasSize(0)));
    }

    @Test
    public void query_by_city_success() throws Exception {

        when(crudItinerary.findAllByCity(any()))
                .thenReturn(newArrayList(from(Itinerary.class).gimme(SPBH), from(Itinerary.class).gimme(SPRJ)));

        final QueryJson queryJson = new QueryJson();
        queryJson.setCity("São Paulo");

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH + "/query/city")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(queryJson)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$", hasSize(2)));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].id", equalTo("4321")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].destinyCity", equalTo("BH")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].departureTime", equalTo("21:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[0].arrivalTime", equalTo("23:50")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].id", equalTo("1234")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].city", equalTo("SP")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].destinyCity", equalTo("RJ")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].departureTime", equalTo("10:00")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$[1].arrivalTime", equalTo("15:00")));
    }
}
