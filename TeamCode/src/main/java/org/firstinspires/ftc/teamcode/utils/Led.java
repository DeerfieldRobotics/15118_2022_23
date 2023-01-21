package org.firstinspires.ftc.teamcode.utils;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Led {
    RevBlinkinLedDriver led;

    public Led (HardwareMap hw) {
        led = hw.get(RevBlinkinLedDriver.class, "led");
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
    }

    public void setPattern(RevBlinkinLedDriver.BlinkinPattern pattern) {
        led.setPattern(pattern);
    }
}
