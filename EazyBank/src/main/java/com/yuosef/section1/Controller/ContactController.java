package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.ContactDao;
import com.yuosef.section1.Models.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ContactController {
    private final ContactDao contactRepository;

    @PostMapping("/contact")
    //@PreFilter("filterObject.contactName != 'test'")
    public Contact saveContactInquiryDetails(@RequestBody  Contact contact) {

           contact.setContactId(getServiceReqNumber());
           contact.setCreateDt(new Date(System.currentTimeMillis()));
           return contactRepository.save(contact);

    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}
