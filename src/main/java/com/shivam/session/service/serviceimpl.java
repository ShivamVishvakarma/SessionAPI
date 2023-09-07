package com.shivam.session.service;

import com.shivam.session.Repository.SessionRepository;
import com.shivam.session.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class serviceimpl implements SessionService {


    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public Session SaveClientdetails(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public boolean cancelSession(Long sessionId) {

        Session session = sessionRepository.findById(sessionId).orElse(null);

        if(session== null){
            return false;
        }

        LocalDateTime currentdatetime = LocalDateTime.now();
        LocalDateTime sessiondatetime= session.getSessionTime();

        long hoursUntilSession = ChronoUnit.HOURS.between(currentdatetime,sessiondatetime);

        if(hoursUntilSession>12){
            session.setCanceled(true);
            sessionRepository.save(session);
            return true;
        }else{
            return false;
        }
    }
}
