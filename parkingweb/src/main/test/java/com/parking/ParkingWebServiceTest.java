package com.parking;

import com.parking.service.persistence.repository.ClientInvoiceRepository;
import com.parking.service.persistence.repository.ParkingRepository;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
@ContextConfiguration(locations = {"classpath:applicationContext*.xml"})
@WebAppConfiguration("classpath:applicationContext-webmvc.xml")
public class ParkingWebServiceTest {

    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    ClientInvoiceRepository clientInvoiceRepository;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void importParkings() throws Exception {

        clientInvoiceRepository.deleteAll();
        parkingRepository.deleteAll();

        this.mockMvc.perform(post("/importparkings")
                .contentType(contentType)
                .content(json()))
                .andExpect(status().isCreated());
    }

    private String json() throws Exception {
        JSONParser parser = new JSONParser();
        InputStream is = getClass().getResourceAsStream("/parkingdata.json");
        Object obj = parser.parse(new InputStreamReader(is));

        return obj.toString();
    }


}
