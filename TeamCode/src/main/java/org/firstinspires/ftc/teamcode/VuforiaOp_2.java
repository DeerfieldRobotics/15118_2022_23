package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/*
 * This OpMode was written for the VuforiaDemo Basics video. This demonstrates basic principles of
 * using VuforiaDemo in FTC.
 */

@Autonomous(name = "Vuforia2", group = "Auto")
public class VuforiaOp_2 extends LinearOpMode
{
    // Variables to be used for later
    private VuforiaLocalizer vuforiaLocalizer;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackables visionTargets;
    // private VuforiaTrackable target0_RE6;
    // private VuforiaTrackable target1_RE1;
    // private VuforiaTrackable target2_BB6;
    private VuforiaTrackable target3_BB1;
    // private VuforiaTrackableDefaultListener listener0;
//    private VuforiaTrackableDefaultListener listener1;
//    private VuforiaTrackableDefaultListener listener2;
    private VuforiaTrackableDefaultListener listener3;

    // private OpenGLMatrix lastKnownLocation0;
//    private OpenGLMatrix lastKnownLocation1;
//    private OpenGLMatrix lastKnownLocation2;
    private OpenGLMatrix lastKnownLocation3;
    private OpenGLMatrix phoneLocation;

    private static final String VUFORIA_KEY = "AQvzNHX/////AAABmYmmaaVNKE0Fn7ZY4Zg4l7eJVdn+R1Wo+RCHqi3CHuwhgbvGMvnGGA9o5UftX9Do65NoScmnyN76f/+SnSuJVUR6ayzxLWL6EwUb8ProA5HyULl/ZKeHhSd4rVvylPP2reeQh8FtY6+RYYG4GWkoIExqmsp9qcng5HY02Wrvj6z46LVmON1I7tacnD4XnXbNMXR6eNdEoOEmyo6N0B09kW/SwFAU0m2btefibQpISi0AUT6TrN3+sg+Jc+qbPM84AC7+Dv0CvUVn4drAqRhCP9L9kUV9FDDM8Ch/Mv1xDVcsVykSVnj0UFziJD4k6mGvSld+6Ux2wY55IWcP5VzSuuTo6nwVxIdGbLXbtPkMjFua";

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

    public void runOpMode() throws InterruptedException
    {
        setupVuforia();

        // We don't know where the robot is, so set it to the origin
        // If we don't include this, it would be null, which would cause errors later on
        lastKnownLocation3 = createMatrix(0, 0, 0, 0, 0, 0);

        waitForStart();

        // Start tracking the targets
        visionTargets.activate();

        while(opModeIsActive())
        {
            // Ask the listener for the latest information on where the robot is
            // OpenGLMatrix latestLocation0 = listener0.getUpdatedRobotLocation();
//            OpenGLMatrix latestLocation1 = listener1.getUpdatedRobotLocation();
//            OpenGLMatrix latestLocation2 = listener2.getUpdatedRobotLocation();
            OpenGLMatrix latestLocation3 = listener3.getUpdatedRobotLocation();

            // The listener will sometimes return null, so we check for that to prevent errors
            if(latestLocation3 != null) {
                lastKnownLocation3 = latestLocation3;
            }
            float[] coordinates = lastKnownLocation3.getTranslation().getData();

            robotX = coordinates[0];
            robotY = coordinates[1];
            robotAngle = Orientation.getOrientation(lastKnownLocation3, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            // Send information about whether the target is visible, and where the robot is
            // telemetry.addData("Tracking " + target0_RE6.getName(), listener0.isVisible());
//            telemetry.addData("Tracking " + target1_RE1.getName(), listener.isVisible());
//            telemetry.addData("Tracking " + target2_BB6.getName(), listener.isVisible());
            telemetry.addData("Tracking " + target3_BB1.getName(), listener3.isVisible());
            telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation3));

            // Send telemetry and idle to let hardware catch up
            telemetry.update();
            idle();
        }
    }

    private void setupVuforia()
    {
        // Setup parameters to create localizer
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId); // To remove the camera view from the screen, remove the R.id.cameraMonitorViewId
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RoverImage");
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        // Setup the target to be tracked
        // target0_RE6 = visionTargets.get(0); // 0 corresponds to the wheels target
        //    target0_RE6.setName("RED_E6");
//        target1_RE1 = visionTargets.get(1);
//        target1_RE1.setName("RED_E1");
//        target2_BB6 = visionTargets.get(2);
//        target2_BB6.setName("BLUE_B6");
        target3_BB1 = visionTargets.get(3);
        target3_BB1.setName("BLUE_B1");
        // target0_RE6.setLocation(createMatrix(0, 500, 0, 90, 0, 90));
//        target1_RE1.setLocation(createMatrix(0, 500, 0, 90, 0, 90));
//        target2_BB6.setLocation(createMatrix(0, 500, 0, 90, 0, 90));
        target3_BB1.setLocation(createMatrix(0, 500, 0, 90, 0, 90));

        // Set phone location on robot
        phoneLocation = createMatrix(0, 225, 0, 90, 0, 0);

        // Setup listener and inform it of phone information
        listener3 = (VuforiaTrackableDefaultListener) target3_BB1.getListener();
        listener3.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    private OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w)
    {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    private String formatMatrix(OpenGLMatrix matrix)
    {
        return matrix.formatAsTransform();
    }
}