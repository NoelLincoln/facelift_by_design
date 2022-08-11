package com.facelift.service;

import com.facelift.common.entity.Contact;
import com.facelift.common.entity.User;


import java.util.List;

public interface ContactService {

    void createEmail(Contact contact, String message, String subject, String email, String Name);

    List<User> findByEmail(User user);

    Contact save(Contact contact);

    List<Contact> findAll();

}
