package com.shivam.session.controller;

import com.shivam.session.Repository.SessionRepository;
import com.shivam.session.dto.SessionRequest;
import com.shivam.session.dto.SessionResponse;
import com.shivam.session.entity.Session;
import com.shivam.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/session")
public class sessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponse> saveClientDetails(@RequestBody SessionRequest sessionRequest) {
        Session session = mapToEntity(sessionRequest);
        Session savedSession = sessionService.SaveClientdetails(session);
        if (savedSession != null) {
            return new ResponseEntity<>(mapToResponse(savedSession), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel/{Sessionid}")
    public ResponseEntity<String> cancelSession(@PathVariable Long Sessionid) {

        boolean canceled = sessionService.cancelSession(Sessionid);

        if (canceled) {
            return ResponseEntity.ok("Session cancelled Successfully");
        } else {
            return ResponseEntity.badRequest().body("unable to cancel Session");
        }
    }

    @PutMapping("/reschedule/{SessionId}")
    public ResponseEntity<String> RescheduleSession(@PathVariable Long SessionId, @RequestParam LocalDateTime newtime) {

        boolean reschedules = sessionService.scheduleSesion(SessionId, newtime);
        if (reschedules) {
            return ResponseEntity.ok("Session is Rescheduled");
        } else {
            return ResponseEntity.badRequest().body("Session cannot be rescheduled");
        }
    }


    @PostMapping("/book_recurring")
    public ResponseEntity<String> bookRecurringSession(@RequestParam Long mentorId,
                                                       @RequestParam LocalDateTime Starttime,
                                                       @RequestParam int frequency,
                                                       @RequestParam int durationmonths) {

        List<LocalDateTime> sessionTimes = sessionService.calculateSessionSchedule(Starttime, frequency, durationmonths);
        return ResponseEntity.ok("Recurring sessions have been booked.");
    }

    private Session mapToEntity(SessionRequest sessionRequest) {
        Session session = new Session();
        session.setName(sessionRequest.getName());
        session.setAge(sessionRequest.getAge());
        session.setTypeofsession(sessionRequest.getTypeofsession());
        session.setMentor(sessionRequest.getMentor());
        session.setSessionTime(sessionRequest.getSessionTime());
        return session;
    }

    private SessionResponse mapToResponse(Session session) {
        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setId(session.getId());
        sessionResponse.setName(session.getName());
        sessionResponse.setTypeofsession(session.getTypeofsession());
        sessionResponse.setMentor(session.getMentor());
        sessionResponse.setSessionTime(session.getSessionTime());
        return sessionResponse;
    }

}



