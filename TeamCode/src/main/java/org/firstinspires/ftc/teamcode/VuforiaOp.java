package org.firstinspires.ftc.teamcode;
/*

ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf

*/
import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.vuforia.HINT;
import com.vuforia.Matrix34F;
import com.vuforia.Tool;
import com.vuforia.Vec2F;
import com.vuforia.Vec3F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Arrays;
@Autonomous(name = "VUF", group = "Autonomous")
public class VuforiaOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor driveR = hardwareMap.dcMotor.get("driveR");
        driveR.setDirection(DcMotorSimple.Direction.REVERSE);
        driveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DcMotor driveL = hardwareMap.dcMotor.get("driveL");
        driveL.setDirection(DcMotorSimple.Direction.FORWARD);
        driveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "AQvzNHX/////AAABmYmmaaVNKE0Fn7ZY4Zg4l7eJVdn+R1Wo+RCHqi3CHuwhgbvGMvnGGA9o5UftX9Do65NoScmnyN76f/+SnSuJVUR6ayzxLWL6EwUb8ProA5HyULl/ZKeHhSd4rVvylPP2reeQh8FtY6+RYYG4GWkoIExqmsp9qcng5HY02Wrvj6z46LVmON1I7tacnD4XnXbNMXR6eNdEoOEmyo6N0B09kW/SwFAU0m2btefibQpISi0AUT6TrN3+sg+Jc+qbPM84AC7+Dv0CvUVn4drAqRhCP9L9kUV9FDDM8Ch/Mv1xDVcsVykSVnj0UFziJD4k6mGvSld+6Ux2wY55IWcP5VzSuuTo6nwVxIdGbLXbtPkMjFua";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizerImplSubclass vuforia = new VuforiaLocalizerImplSubclass(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromFile("RoverImage");

        beacons.get(0).setName("RED_E6");
        beacons.get(1).setName("RED_E1");
        beacons.get(2).setName("BLUE_B6");
        beacons.get(3).setName("BLUE_B1");

        VuforiaTrackableDefaultListener wheels = (VuforiaTrackableDefaultListener) beacons.get(0).getListener();

        waitForStart();

        beacons.activate();

//        driveL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        driveR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        driveL.setPower(0.2);
//        driveR.setPower(0.2);
//
//
//        while(opModeIsActive() && wheels.getRawPose() ==null){
//            if(vuforia.rgb != null) {
//                Bitmap bm = Bitmap.createBitmap(vuforia.rgb.getWidth(), vuforia.rgb.getHeight(), Bitmap.Config.RGB_565);
//                bm.copyPixelsFromBuffer(vuforia.rgb.getPixels());
//            }
//
//            for(VuforiaTrackable beac : beacons){
//                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getRawPose();
//                if(pose !=null){
//                    Matrix34F rawPose = new Matrix34F();
//                    float[] poseData = Arrays.copyOfRange(pose.transposed().getData(),0,12);
//                    rawPose.setData(poseData);
//
//                    Vec2F upperLeft = Tool.projectPoint(com.vuforia.CameraDevice.getInstance().getCameraCalibration(),rawPose, new Vec3F(-0.1016f,0.078448f,0));
//                    Vec2F upperRight = Tool.projectPoint(com.vuforia.CameraDevice.getInstance().getCameraCalibration(),rawPose, new Vec3F(0.1016f,0.078448f,0));
//                    Vec2F lowerRight = Tool.projectPoint(com.vuforia.CameraDevice.getInstance().getCameraCalibration(),rawPose, new Vec3F(0.1016f,0.078448f,0));
//                    Vec2F lowerLeft = Tool.projectPoint(com.vuforia.CameraDevice.getInstance().getCameraCalibration(),rawPose, new Vec3F(-0.1016f,0.078448f,0));
//
//                    // upperLeft.getData()[0]: X COORDINATE
//
//                }
//
//
//
//            }
//            telemetry.update();
//        }
//
//        driveL.setPower(0);
//        driveR.setPower(0);
//
//        // analyze beacons
//
//        VectorF angles= anglesFromTarget(wheels);
//        VectorF trans = navOffWall(wheels.getPose().getTranslation(), Math.toDegrees(angles.get(0))-90,new VectorF(500,0,0));
//        // DIRECTLY FACING BEACON
//        if(trans.get(0)>0){
//            //if positive, turn to right
//            driveL.setPower(0.02);
//            driveR.setPower(-0.02);
//        } else{
//            driveL.setPower(-0.02);
//            driveR.setPower(0.02);
//        }
//
//        do{
//            if(wheels.getPose() != null){
//                trans = navOffWall(wheels.getPose().getTranslation(), Math.toDegrees(angles.get(0))-90,new VectorF(500,0,0));
//            }
//            idle();
//        } while(opModeIsActive() && Math.abs(trans.get(0))>30);
//
//        driveL.setPower(0);
//        driveR.setPower(0);
//
//        driveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        driveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        driveL.setTargetPosition((int)(driveL.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2))+150)/409.575 * 560)));
//        driveR.setTargetPosition((int)(driveL.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2))+150)/409.575 * 560)));
//        //150: camera is 15 cm off of center of turning, 409.575: WHEEL CIRCUMFERENCE, 560: TICKS PER ROTATION- andy mark 20's, 40's = 1120
//
//        driveL.setPower(0.3);
//        driveR.setPower(0.3);
//
//        while(opModeIsActive() && driveL.isBusy() && driveR.isBusy()) {
//            idle();
//        }
//
//        driveL.setPower(0);
//        driveR.setPower(0);
//
//        driveL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        driveR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        while(opModeIsActive() && (wheels.getPose() == null || Math.abs(wheels.getPose().getTranslation().get(0)) > 10)) {
//            if(wheels.getPose() != null){
//                if(wheels.getPose().getTranslation().get(0) > 0) {
//                    driveL.setPower(-0.3);
//                    driveR.setPower(0.3);
//                }
//                else {
//                    driveL.setPower(0.3);
//                    driveR.setPower(-0.3);
//                }
//            }
//            else {
//                driveL.setPower(-0.3);
//                driveR.setPower(0.3);
//            }
//        }

        driveL.setPower(0);
        driveR.setPower(0);


    }

    public VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall){
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1), (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image){
        float [] data = image.getRawPose().getData();
        float [] [] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};

        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }

}
