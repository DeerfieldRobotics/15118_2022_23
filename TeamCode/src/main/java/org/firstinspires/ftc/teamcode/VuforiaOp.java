package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class VuforiaOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "AQvzNHX/////AAABmYmmaaVNKE0Fn7ZY4Zg4l7eJVdn+R1Wo+RCHqi3CHuwhgbvGMvnGGA9o5UftX9Do65NoScmnyN76f/+SnSuJVUR6ayzxLWL6EwUb8ProA5HyULl/ZKeHhSd4rVvylPP2reeQh8FtY6+RYYG4GWkoIExqmsp9qcng5HY02Wrvj6z46LVmON1I7tacnD4XnXbNMXR6eNdEoOEmyo6N0B09kW/SwFAU0m2btefibQpISi0AUT6TrN3+sg+Jc+qbPM84AC7+Dv0CvUVn4drAqRhCP9L9kUV9FDDM8Ch/Mv1xDVcsVykSVnj0UFziJD4k6mGvSld+6Ux2wY55IWcP5VzSuuTo6nwVxIdGbLXbtPkMjFua";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromFile()
    }
}
