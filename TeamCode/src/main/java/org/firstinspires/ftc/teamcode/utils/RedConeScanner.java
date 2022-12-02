package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opencvpipelines.RedConeDetection;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class RedConeScanner {

    private OpenCvCamera camera;
    private RedConeDetection redConeDetection;

    private Drivetrain drivetrain;

    public RedConeScanner(OpenCvCamera c, Drivetrain d) {
        camera = c;
        drivetrain = d;
        redConeDetection = new RedConeDetection();

        camera.setPipeline(redConeDetection);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
    }

    public void getRedCone() {
        alignStripe();
        alignCone();
    }

    public void alignStripe() {
        while(!coneInView()) {
            int[] alignment = redConeDetection.getAlignment();

            int loD1, loD2, hiD1, hiD2;

            if(alignment[2]<alignment[5]) {
                loD1 = alignment[0];
                loD2 = alignment[4];
                hiD1 = alignment[1];
                hiD2 = alignment[3];
            }
            else {
                loD1 = alignment[1];
                loD2 = alignment[3];
                hiD1 = alignment[0];
                hiD2 = alignment[4];
            }

            double strafe = 0;
            double forward = 0;
            double rotate = 0;

            int loError = Math.abs(loD2-loD1);
            int hiError = Math.abs(hiD2-hiD1);

            //TODO
            int threshold1 = 5;
            int threshold2 = 10;
            int threshold3 = 15;

            double speed1 = 0.4;
            double speed2 = 0.6;
            double speed3 = 0.8;

            //guard clauses for threshold
            if(loError<threshold1&&hiError<threshold1) {
                //go forward
                forward=speed3;
                strafe = 0;
                rotate = 0;
            }
            else if(loError<threshold2&&hiError<threshold2) {
                forward = speed2;
                if(loD1<loD2) {
                    //strafe right added to mvoement
                    strafe = speed1;
                }
                else{
                    //strafe left
                    strafe = -speed1;
                }

                if(hiD1<hiD2) {
                    //rotate right
                    rotate = speed1;
                }
                else{
                    //rotate left
                    rotate = -speed1;
                }
            }
            else if(loError<threshold3&&hiError<threshold3) {
                forward = speed1;
                if(loD1<loD2) {
                    //strafe right added to mvoement
                    strafe = speed2;
                }
                else{
                    //strafe left
                    strafe = -speed2;
                }

                if(hiD1<hiD2) {
                    //rotate right
                    rotate = speed2;
                }
                else{
                    //rotate left
                    rotate = -speed2;
                }
            }
            else {
                forward = 0;
                if(loD1<loD2) {
                    //strafe right added to mvoement
                    strafe = speed3;
                }
                else{
                    //strafe left
                    strafe = -speed3;
                }

                if(hiD1<hiD2) {
                    //rotate right
                    rotate = speed3;
                }
                else{
                    //rotate left
                    rotate = -speed3;
                }
            }
            
            drivetrain.run_using_encoder();
            drivetrain.setDrivetrainSpeedMod(1);
            drivetrain.move(strafe, rotate, forward);
        }

    }

    public boolean coneInView() {
        int viewThreshold = 40; //TODO width for it to be in view
        return redConeDetection.getMaxWidth()[0]>viewThreshold;
    }

    public void alignCone() {
        int grabThreshold = 80; //TODO width to grab
        while(redConeDetection.getMaxWidth()[0]<grabThreshold) {
            int [] coneAlign = redConeDetection.getMaxWidth();

            int leftWidth = coneAlign[1];
            int rightWidth = redConeDetection.arrayWidth()-coneAlign[2];

            double forward = 0;
            double rotate = 0;

            //TODO
            int threshold1 = 5;
            int threshold2 = 10;
            int threshold3 = 15;

            double speed1 = 0.4;
            double speed2 = 0.6;
            double speed3 = 0.8;

            int error = Math.abs(leftWidth-rightWidth);

            if(error<threshold1) {
                forward = speed3;
                rotate = 0;
            }
            else if(error<threshold2) {
                if(leftWidth>rightWidth) {
                    rotate = speed1;
                }
                else {
                    rotate = -speed1;
                }
            }
            else if(error<threshold3) {
                if(leftWidth>rightWidth) {
                    rotate = speed2;
                }
                else {
                    rotate = -speed2;
                }
            }
            else {
                if(leftWidth>rightWidth) {
                    rotate = speed3;
                }
                else {
                    rotate = -speed3;
                }

            }
            
            drivetrain.run_using_encoder();
            drivetrain.setDrivetrainSpeedMod(1);
            drivetrain.move(0, rotate, forward);
        }
    }
}