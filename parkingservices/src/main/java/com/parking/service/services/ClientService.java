package com.parking.service.services;

import com.parking.service.common.dto.model.Total;
import com.parking.service.common.utils.DateUtils;
import com.parking.service.persistence.model.Client;
import com.parking.service.persistence.model.ClientInvoice;
import com.parking.service.persistence.model.Parking;
import com.parking.service.persistence.repository.ClientInvoiceRepository;
import com.parking.service.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientInvoiceRepository invoiceRepository;
    @Autowired
    ParkingService parkingService;

    public void saveInvoice(ClientInvoice invoice) {

        invoiceRepository.save(invoice);

    }

    public List<ClientInvoice> findInvoiceByClientIdAndDate(long clientId, int month) {
        return invoiceRepository.getClientInvoiceByClientIdAndParkingMonth(clientId, month);

    }

    public List<ClientInvoice> findInvoicesByClientId(long clientId) {
        return invoiceRepository.getClientInvoiceByClientId(clientId);
    }

    public Total getTotalForClient(long clientId, int month) {
        return invoiceRepository.getTotalForClient(clientId, month);
    }

    public List<Total> getTotalsForClient(long clientId) {
        return invoiceRepository.getTotalsForClient(clientId);
    }

    public List<ClientInvoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void setTotalsForPremium(Client c, Total t) {
        if (c.isPremium()) {
            t.setTotal(t.getTotal().add(c.getMonthlyFee()));
            if (t.getTotal().compareTo(new BigDecimal(300)) == 1) {
                t.setTotal(new BigDecimal(300));
            }
        }
        t.setClient(c);
    }

    public void delete(List<ClientInvoice> clientInvoices) {
        invoiceRepository.delete(clientInvoices);
    }

    public void issueClientDailyInvoices(long clientId) {
        Calendar calendar = Calendar.getInstance();

        List<Parking> parkingList = parkingService.findParkingsForDate(clientId, calendar);

        Calendar entryCal = Calendar.getInstance();
        Calendar departureCal = Calendar.getInstance();
        Calendar amTime = Calendar.getInstance();
        Calendar pmTime = Calendar.getInstance();

        if (parkingList != null && parkingList.size() > 0) {
            //cleanup before we do new invoice calc
            invoiceRepository.delete(invoiceRepository.getClientInvoiceByClientIdAndParkingMonth(clientId, DateUtils.formatMonthAsInt(DateUtils.formatDateAsInt(entryCal))));
        }


        for (Parking parking : parkingList) {
            ClientInvoice invoice = new ClientInvoice();

            entryCal.setTimeInMillis(parking.getEntryDate().getTime());
            departureCal.setTimeInMillis(parking.getDepartureDate().getTime());

            amTime.setTimeInMillis(entryCal.getTimeInMillis());
            amTime.set(Calendar.HOUR_OF_DAY, 7);
            amTime.set(Calendar.MINUTE, 0);
            amTime.set(Calendar.SECOND, 0);

            pmTime.setTimeInMillis(entryCal.getTimeInMillis());

            pmTime.set(Calendar.HOUR_OF_DAY, 19);
            pmTime.set(Calendar.MINUTE, 0);
            pmTime.set(Calendar.SECOND, 0);

            invoice.setClient(parking.getClient());
            invoice.setParkingDate(DateUtils.formatDateAsInt(entryCal));//setting the max date for the month to the invoice date
            invoice.setParkingMonth(DateUtils.formatMonthAsInt(invoice.getParkingDate()));

            if (entryCal.getTimeInMillis() > amTime.getTimeInMillis()
                    &&
                    departureCal.getTimeInMillis() < pmTime.getTimeInMillis()) {
                int time = (int) Math.ceil((departureCal.getTimeInMillis() - entryCal.getTimeInMillis()) / 1000 / 60 / 30);

                BigDecimal calculatedTotal = parking.getClient().getGroup().getDayTimeFee().multiply(new BigDecimal(time));
                invoice.setTotal(calculatedTotal);
                invoice.setParking(parking);
                invoice.setCreatedDate(new Date());
                saveInvoice(invoice);

            } else if (entryCal.getTimeInMillis() > amTime.getTimeInMillis()
                    &&
                    entryCal.getTimeInMillis() < pmTime.getTimeInMillis()
                    && departureCal.getTimeInMillis() > pmTime.getTimeInMillis()) {

                int timeBeforePM = (int) Math.ceil((pmTime.getTimeInMillis() - entryCal.getTimeInMillis()) / 1000 / 60 / 30);

                int timeAfterPM = (int) Math.ceil((departureCal.getTimeInMillis() - pmTime.getTimeInMillis()) / 1000 / 60 / 30);

                BigDecimal feeBeforePM = parking.getClient().getGroup().getDayTimeFee().multiply(new BigDecimal(timeBeforePM));
                BigDecimal feeAfterPM = parking.getClient().getGroup().getNightTimeFee().multiply(new BigDecimal(timeAfterPM));

                invoice.setTotal(feeAfterPM.add(feeBeforePM));

                invoice.setParking(parking);
                invoice.setCreatedDate(new Date());
                saveInvoice(invoice);

            } else if (entryCal.getTimeInMillis() > pmTime.getTimeInMillis()
                    &&
                    departureCal.getTimeInMillis() > pmTime.getTimeInMillis()) {

                int time = (int) Math.ceil((departureCal.getTimeInMillis() - entryCal.getTimeInMillis()) / 1000 / 60 / 30);

                BigDecimal feeAfterPM = parking.getClient().getGroup().getNightTimeFee().multiply(new BigDecimal(time));

                invoice.setTotal(feeAfterPM);

                invoice.setParking(parking);
                invoice.setCreatedDate(new Date());
                saveInvoice(invoice);

            }
        }
    }

    public Client findByUserNameAndPassword(String username, String password) {
        return clientRepository.findByUserNameAndPassword(username, password);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }


}
