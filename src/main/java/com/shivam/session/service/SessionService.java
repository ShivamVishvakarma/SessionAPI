package com.shivam.session.service;

import com.shivam.session.entity.Session;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public interface SessionService {

   public Session SaveClientdetails(Session session);

   boolean cancelSession(Long sessionId);

   boolean scheduleSesion(Long sessionId, LocalDateTime newtime);

   List<LocalDateTime> calculateSessionSchedule(LocalDateTime starttime, int frequency, int durationmonths);
}
