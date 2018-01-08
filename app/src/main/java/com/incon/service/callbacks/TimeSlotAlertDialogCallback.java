package com.incon.service.callbacks;

public interface TimeSlotAlertDialogCallback extends AlertDialogCallback{
    byte BUTTON1 = 1;
    byte BUTTON2 = 2;
    byte BUTTON3 = 3;
    void selectedTimeSlot(String timeSlot);
}
