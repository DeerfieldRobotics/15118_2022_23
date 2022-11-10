package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.YellowPoleDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "poletest")
public class pole_test extends LinearOpMode {
    //OpenCvCamera frontCamera;
    private OpenCvInternalCamera frontCamera;

    private YellowPoleDetection detector = new YellowPoleDetection();


    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //frontCamera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "frontWeb"), cameraMonitorViewId);
        frontCamera = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        frontCamera.setPipeline(detector);
        frontCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                frontCamera.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("maxwidth",detector.getMaxWidth());
            telemetry.addData("left",detector.getLeft());
            telemetry.addData("right",detector.getRight());
            telemetry.update();
            Thread.sleep(100);
        }
    }
}
