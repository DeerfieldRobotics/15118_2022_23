package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opencvpipelines.BetterRedConeDetection;
import org.firstinspires.ftc.teamcode.opencvpipelines.RedConeDetection;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "redconetest")
public class redconetest extends LinearOpMode {

    private OpenCvInternalCamera phoneCam;
    private BetterRedConeDetection detector = new BetterRedConeDetection();

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

        while(opModeIsActive()) {
            telemetry.addData("width",detector.getMaxWidth());
            telemetry.addData("left",detector.getLeft());
            telemetry.addData("right",detector.getRight());
            telemetry.update();
        }
    }
}
