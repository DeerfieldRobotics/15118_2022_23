package org.firstinspires.ftc.teamcode.utils;


import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public Robot(HardwareMap hwMap) {
        Drivetrain drivetrain = new Drivetrain(hwMap);
        Slide slide = new Slide(hwMap);
        // -> INSERT INTAKE CLASS
    }
}
