package com.shivam.session.controller;

import com.shivam.session.Repository.SessionRepository;
import com.shivam.session.entity.Session;
import com.shivam.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class sessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

   @PostMapping("/session")
    public Session saveClientDetails(@RequestBody Session session){
         return sessionService.SaveClientdetails(session);
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
    public ResponseEntity<String> RescheduleSession(@PathVariable Long SessionId , @RequestParam LocalDateTime newtime){
        Optional<Session> optionalSession = sessionRepository.findById(SessionId);

        if(optionalSession.isPresent()){
            Session session = optionalSession.get();
            LocalDateTime currentTime = LocalDateTime.now();

            Duration timeDiff = Duration.between(currentTime , newtime);

            if(timeDiff.toHours()>4){
                session.setSessionTime(newtime);
                sessionRepository.save(session);

                return ResponseEntity.ok("Session is Rescheduled");
            }else{
                return ResponseEntity.badRequest().body("Session cannot be rescheduled");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/book_recurring")
    public ResponseEntity<String> bookRecurringSession(@RequestParam Long mentorId ,
                                                       @RequestParam LocalDateTime Starttime,
                                                       @RequestParam int frequency ,
                                                       @RequestParam int durationmonths) {

        List<LocalDateTime> sessionTime = calculateSessionSchedule(Starttime, frequency, durationmonths);

        for (LocalDateTime Time : sessionTime) {
            Session session = new Session();
            session.setSessionTime(Time);
            sessionRepository.save(session);
        }
        return ResponseEntity.ok("Recurring sessions have been booked.");
    }

    private List<LocalDateTime> calculateSessionSchedule(LocalDateTime starttime, int frequency, int durationmonths) {
        List<LocalDateTime>sessionTimes = new ArrayList<>();

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
