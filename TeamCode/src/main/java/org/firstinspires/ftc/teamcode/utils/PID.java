package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Matrix34F;

public class PID {
    private double targetAngle;
    private double kP, kI, kD;
    private double accumulatedError = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();
    private double lastTime = 0;

    public PID(double target, double p, double i, double d){
        targetAngle = target;
        kP = p;
        kI = i;
        kD = d;
    }

    public double update(double currentAngle){
        // P
        double error = targetAngle - currentAngle;
        error %= 360;
        error += 360;
        error %= 360;

        if(error >180) error -= 360;
        // I

        accumulatedError += error;

        if(Math.abs(error) < 2){
            accumulatedError = 0;
        }

        //get the right sign
        accumulatedError = Math.abs(accumulatedError) * Math.signum(error);

        //D
        double slope = 0;
        if(lastTime > 0){
            slope = (error - lastError)/(timer.milliseconds() - lastTime);
        }

        lastTime = timer.milliseconds();
        lastError = error;

        //motor power calculations

        double motorPower = 0.1 * Math.signum(error) + 0.9 * Math.tanh(
                kP * error + kI * accumulatedError + kD * slope
        );

        return motorPower;

    }
}