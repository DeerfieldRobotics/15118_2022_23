package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "conetest")
public class cone_test extends LinearOpMode {
    private OpenCvInternalCamera phoneCam;
    private ConeDetectionPipeline detector = new ConeDetectionPipeline(telemetry);


    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.setPipeline(detector);
        phoneCam.openCameraDevice();
        phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
        waitForStart();
        while(opModeIsActive())
        {
            telemetry.addData("pipeline","conedetector");
            telemetry.addData("height",detector.height());
            telemetry.addData("width",detector.width());
            telemetry.addData("Cr threshold",detector.crThreshold());
            telemetry.addData("Cr outliers",detector.outliersCr());
            telemetry.addData("test",detector.tester());
            telemetry.update();
        }
    }
}
