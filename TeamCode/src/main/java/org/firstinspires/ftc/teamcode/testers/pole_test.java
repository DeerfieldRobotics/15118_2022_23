package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.YellowPoleDetection;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "poletest")
public class pole_test extends LinearOpMode {
    private OpenCvInternalCamera phoneCam;
    private YellowPoleDetection detector = new YellowPoleDetection();


    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.setPipeline(detector);
        phoneCam.openCameraDevice();
        phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
        waitForStart();

    }
}
