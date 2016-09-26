package com.parking.web.controller;

import com.parking.service.common.dto.model.Total;
import com.parking.service.persistence.model.Client;
import com.parking.service.persistence.model.ClientInvoice;
import com.parking.service.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/27/16
 * Time: 6:34 AM
 */

@Controller
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    ClientService clientService;

    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest req, Model model) {
        if (req.getSession() != null && req.getSession().getAttribute("user") != null) {
            return listInvoices(req, model);
        } else {
            return "login";
        }

    }

    @RequestMapping({"/generateinvoices"})
    public String generateInvoices(HttpServletRequest req, Model model) {
        final Client client = (Client) req.getSession().getAttribute("user");

        if (client != null && !client.isAdmin()) {
            new Thread() {
                public void run() {
                    clientService.issueClientDailyInvoices(client.getId());
                }
            }.start();

        } else if (client != null && client.isAdmin()) {
            new Thread() {
                public void run() {
                    for (Client c : clientService.findAllClients()) {
                        if (!c.isAdmin())
                            clientService.issueClientDailyInvoices(c.getId());
                    }
                }
            }.start();
        } else {
            return "login";
        }

        model.addAttribute("res", "Please wait a minute while your invoices are being generated then select List Invoices menu.");

        return "listinvoices";

    }

    @RequestMapping({"/listinvoices"})
    public String listInvoices(HttpServletRequest req, Model model) {

        Client client = null;
        if (req.getSession() != null) {
            client = (Client) req.getSession().getAttribute("user");
        } else {
            return "login";
        }
        List<Total> totals = new ArrayList<>();

        if (client != null && !client.isAdmin()) {
            List<ClientInvoice> clientInvoices = clientService.findInvoicesByClientId(client.getId());

            if (clientInvoices.size() == 0) {

                clientService.issueClientDailyInvoices(client.getId());
                model.addAttribute("res", "Please wait a minute while your invoices are being generated.");

            } else {
                Total total = clientService.getTotalForClient(client.getId(), clientInvoices.get(0).getParkingMonth());
                clientService.setTotalsForPremium(client, total);

                totals.add(total);
                model.addAttribute("invoices", clientInvoices);
                model.addAttribute("totals", totals);
            }
            return "listinvoices";
        } else if (client != null && client.isAdmin()) {

            List<Client> clientList = clientService.findAllClients();

            for (Client c : clientList) {
                if (!c.isAdmin()) {
                    List<Total> clientTotals = clientService.getTotalsForClient(c.getId());
                    for (Total t : clientTotals) {
                        clientService.setTotalsForPremium(c, t);
                        totals.add(t);
                    }
                }
            }

            model.addAttribute("totals", totals);
            return "listinvoices";
        } else {
            return "login";
        }
    }

}
