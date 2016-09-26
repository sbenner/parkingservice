package com.parking.web.controller;


import com.parking.service.persistence.model.Client;
import com.parking.service.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    ClientService clientService;

    @RequestMapping({"/login"})
    public String login(Model model, Locale locale) {

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "login";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String auth(HttpServletRequest req,
                       @RequestParam("username") String username,
                       @RequestParam("password") String password) {

        Client user = clientService.findByUserNameAndPassword(username, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            return "redirect:/";
        } else
            return "login";
    }

    @RequestMapping({"/logout"})
    public String logout(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "login";
    }


}
