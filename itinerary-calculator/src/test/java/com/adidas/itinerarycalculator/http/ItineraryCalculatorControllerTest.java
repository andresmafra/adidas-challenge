package com.adidas.itinerarycalculator.http;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.adidas.itinerarycalculator.domains.CalculateMode;
import com.adidas.itinerarycalculator.domains.ItineraryOutput;
import com.adidas.itinerarycalculator.exceptions.ResourceNotFoundException;
import com.adidas.itinerarycalculator.http.json.CityInputJson;
import com.adidas.itinerarycalculator.usecases.CalculateItinerary;
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
import static com.adidas.itinerarycalculator.domains.CalculateMode.TIME;
import static com.adidas.itinerarycalculator.templates.ItineraryOutputTemplates.SPPA;
import static com.adidas.itinerarycalculator.templates.ItineraryOutputTemplates.SPPA_2;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItineraryCalculatorControllerTest {

    private static final String API_PATH = "/v1/calculate";

    private ItineraryCalculatorController itineraryController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CalculateItinerary calculateItinerary;

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("com.adidas.itinerarycalculator.templates");
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        itineraryController = new ItineraryCalculatorController(calculateItinerary);
        mockMvc = MockMvcBuilders.standaloneSetup(itineraryController).build();
    }

    @Test
    public void calculate_itinerary_by_connections() throws Exception {
        when(calculateItinerary.calculate(any(), any(), any()))
                .thenReturn(from(ItineraryOutput.class).gimme(SPPA));

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(CityInputJson.builder()
                        .from("SP")
                        .to("PA")
                        .mode(CalculateMode.CONNECTIONS.toString())
                        .build())))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.city", equalTo("São Paulo")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.destinyCity", equalTo("Porto Alegre")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.travelTime", equalTo(200)));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.itineraries.length()", equalTo(3)));
    }

    @Test
    public void calculate_itinerary_by_time() throws Exception {
        when(calculateItinerary.calculate(any(), any(), any()))
                .thenReturn(from(ItineraryOutput.class).gimme(SPPA_2));

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .header("Method", TIME)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(CityInputJson.builder()
                        .from("SP")
                        .to("PA")
                        .mode(CalculateMode.TIME.toString())
                        .build())))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.city", equalTo("São Paulo")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.destinyCity", equalTo("Porto Alegre")));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.travelTime", equalTo(300)));
        assertThat(mvcResult.getResponse().getContentAsString(), hasJsonPath("$.itineraries.length()", equalTo(2)));
    }

    @Test
    public void calculate_itinerary_not_found() throws Exception {
        when(calculateItinerary.calculate(any(), any(), any()))
                .thenThrow(new ResourceNotFoundException("not found!"));

        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(CityInputJson.builder()
                        .from("SP")
                        .to("PA")
                        .mode(CalculateMode.TIME.toString())
                        .build())))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(NOT_FOUND.value()));
    }

    @Test
    public void calculate_itinerary_bad_request() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(CityInputJson.builder()
                        .from("SP")
                        .build())))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(BAD_REQUEST.value()));
    }

    @Test
    public void calculate_itinerary_mode_bad_request() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post(API_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(CityInputJson.builder()
                        .from("SP")
                        .to("PA")
                        .mode("KONECTIOM")
                        .build())))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(BAD_REQUEST.value()));
    }
}
