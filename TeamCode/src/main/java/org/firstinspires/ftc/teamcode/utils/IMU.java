package org.firstinspires.ftc.teamcode.utils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU{
    private Orientation lastAngles;
    //this is the temporary orientation used for comparison aganinst intended/previous orientation
    private Orientation temp;
    private double currentAngle;

    private double distanceTraveled;

    BNO055IMU imu;
    BNO055IMU.Parameters parameters;

    private PID pid;

    public IMU(){
        currentAngle = 0.0;
        distanceTraveled = 0.0;

        parameters = new BNO055IMU.Parameters();

        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(parameters);
    }

    public double getAngle(){
        //get the current orientation
        getTemp();

        //compare current orientation with orientation last checked
        double deltaAngle = temp.firstAngle - lastAngles.firstAngle;

        // Normalize the angle, if the angle is too big (like 350 degrees) we can subtract 360 to bring it down
        deltaAngle = normalize(deltaAngle);

        //update the current angle;
        currentAngle += deltaAngle;

        //update the angle last measured to the current angle
        lastAngles = temp;

        return currentAngle;
    }

    public Orientation getTemp(){
        //get the CURRENT orientation
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public double getAbsoluteAngle(){
        getTemp();
        return temp().firstAngle;
    }

    public Orientation temp(){
        return temp;
    }

    public double normalize(double angle){
        if(angle > 180){
            angle-=360;
        } else if(angle < -180){
            angle += 360;
        }
        return angle;
    }

    public void resetAngle(){
        //update orientation and reset current angle
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currentAngle = 0.0;
    }

    public float getHeading(){
        return lastAngles.firstAngle;
    }

    public float getRoll(){
        return lastAngles.secondAngle;
    }

    public float getPitch(){
        return lastAngles.thirdAngle;
    }


}
