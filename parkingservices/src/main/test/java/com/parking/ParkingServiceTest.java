package com.parking;

import com.parking.service.common.dto.model.Total;
import com.parking.service.common.utils.DateUtils;
import com.parking.service.persistence.model.ClientInvoice;
import com.parking.service.services.ClientService;
import com.parking.service.services.ParkingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})

@ContextConfiguration(locations = {"classpath:applicationContext*.xml"})
public class ParkingServiceTest {

    @Autowired
    ParkingService parkingService;
    @Autowired
    ClientService clientService;

    @Test
    public void testIssueInvoices() {

        List<ClientInvoice> clientInvoices = clientService.findInvoicesByClientId(2);


        clientService.delete(clientInvoices);

        clientInvoices = clientService.findInvoicesByClientId(2);

        assertTrue("deleted all invoices for client ", clientInvoices.size() == 0);

        clientService.issueClientDailyInvoices(2);

        clientInvoices = clientService.findInvoicesByClientId(2);

        assertTrue("created daily invoices for client ", clientInvoices.size() > 0);

        Total object = clientService.getTotalForClient(2, DateUtils.formatMonthAsInt(DateUtils.formatDateAsInt(Calendar.getInstance())));

        assertNotNull("totals calculated for the client ", object);

    }

    //DO NOT RUN IT - we have similar test case in the ParkingWebServiceTest class

//    @Test
//    public void testParkingsImport() {
//        JSONParser parser = new JSONParser();
//        try {
//            InputStream is = getClass().getResourceAsStream("/parkingdata.json");
//            Object obj = parser.parse(new InputStreamReader(is));
//            JSONArray jsonArray = (JSONArray) obj;
//            assertNotNull(jsonArray);
//            List<Parking> parkingLst = new ArrayList<>();
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject object = (JSONObject) jsonArray.get(i);
//                Parking p = new Parking();
//                p.setClientId(Long.valueOf(object.get("clientId").toString()));
//                p.setEntryDate(object.get("entryDate").toString());
//                p.setDepartureDate(object.get("departureDate").toString());
//                parkingLst.add(p);
//            }
//
//            assertTrue(parkingService.importParkingInfo(parkingLst) > 0);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
