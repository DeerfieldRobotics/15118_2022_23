package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "conetest")
public class ConeTest extends LinearOpMode {
    private OpenCvInternalCamera phoneCam;
    private TestPipeline detector = new TestPipeline();


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
            telemetry.addData("height",detector.height());
            telemetry.addData("width",detector.width());
            telemetry.update();
        }
    }
}
