package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
@Disabled
@Autonomous(name = "conetestjank")
public class ConeTest2 extends LinearOpMode {
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
        }
    }
}
