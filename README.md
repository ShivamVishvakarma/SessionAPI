# SessionAPI
Created a session API which helps users to Add , create ,update and delete the sessions for the Clients.

methods:
1. Add Details of user : @PostMapping("/sessions") :  This method is POST which is used to get the data/details from the user.

2.CancelSession : @DeleteMapping("/cancel/{Sessionid}")- This method is used to cancel a session of user , but within the limitations that  Users should be able to cancel a session that they 
                                                         have booked with a mentor but the time difference between current time and session time should be greater than 12 hours then only they should 
                                                         be allowed to cancel the session/meeting.

3.RescheduleSession : @PutMapping("/reschedule/{SessionId}")- This method is used to reschedule the session ,bt the condition is , User should be able to reschedule a meeting booked with a 
                                                              mentor. Reschedule only allowed if the time difference between current time and session time is greater than 4 hours.

4. BookRecurringSession : @PostMapping("/book_recurring") - This method is used to book recurring session which means , User should be able to book a session with a mentor for recurring
                                                            sessions in regular interval given by the user.(like once per week for 1 month or twice per week for 2 months).
