package com.shivam.session.service;

import com.shivam.session.entity.Session;

@org.springframework.stereotype.Service
public interface SessionService {

   public Session SaveClientdetails(Session session);

   boolean cancelSession(Long sessionId);
}
