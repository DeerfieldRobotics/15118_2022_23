package org.firstinspires.ftc.teamcode;
/*

ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf

*/
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class VuforiaOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "AQvzNHX/////AAABmYmmaaVNKE0Fn7ZY4Zg4l7eJVdn+R1Wo+RCHqi3CHuwhgbvGMvnGGA9o5UftX9Do65NoScmnyN76f/+SnSuJVUR6ayzxLWL6EwUb8ProA5HyULl/ZKeHhSd4rVvylPP2reeQh8FtY6+RYYG4GWkoIExqmsp9qcng5HY02Wrvj6z46LVmON1I7tacnD4XnXbNMXR6eNdEoOEmyo6N0B09kW/SwFAU0m2btefibQpISi0AUT6TrN3+sg+Jc+qbPM84AC7+Dv0CvUVn4drAqRhCP9L9kUV9FDDM8Ch/Mv1xDVcsVykSVnj0UFziJD4k6mGvSld+6Ux2wY55IWcP5VzSuuTo6nwVxIdGbLXbtPkMjFua";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromFile("RoverImage");
        beacons.get(0).setName("B1_BLUE");

        waitForStart();

        beacons.activate();

        while(opModeIsActive()){
            for(VuforiaTrackable beac : beacons){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose != null){
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + " - Translation",translation);

                    double degreeTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));//VERTICAL PHONE - Y AND Z AXIS, HORIZONTAL PHONE - get 0 , get 2

                    telemetry.addData(beac.getName() + " -Degrees", degreeTurn);

                }
            }
            telemetry.update();

        }


    }
}
