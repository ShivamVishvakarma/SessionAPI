package com.shivam.session.service;

import com.shivam.session.Repository.SessionRepository;
import com.shivam.session.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (session == null) {
            return false;
        }

        LocalDateTime currentdatetime = LocalDateTime.now();
        LocalDateTime sessiondatetime = session.getSessionTime();

        long hoursUntilSession = ChronoUnit.HOURS.between(currentdatetime, sessiondatetime);

        if (hoursUntilSession > 12) {
            session.setCanceled(true);
            sessionRepository.save(session);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean scheduleSesion(Long sessionId, LocalDateTime newtime) {
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);

        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            LocalDateTime currentTime = LocalDateTime.now();

            Duration timeDiff = Duration.between(currentTime, newtime);

            if (timeDiff.toHours() > 4) {
                session.setSessionTime(newtime);
                sessionRepository.save(session);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public List<LocalDateTime> calculateSessionSchedule(LocalDateTime starttime, int frequency, int durationmonths) {
        List<LocalDateTime> sessionTimes = new ArrayList<>();

        LocalDateTime currenttime = starttime;

        for (int i = 0; i < durationmonths; i++) {
            for (int j = 0; j < frequency; j++) {
                sessionTimes.add(currenttime);
                currenttime = currenttime.plusWeeks(1);
            }
            currenttime = currenttime.plusMonths(1);
        }

        return sessionTimes;


    }
}
