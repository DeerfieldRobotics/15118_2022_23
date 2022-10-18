package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ConeDetectionPipeline extends OpenCvPipeline {


    Mat workingMatrix = new Mat();
    public ConeDetectionPipeline() {
    }

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb);

        Mat matLeft = workingMatrix.submat(120, 150, 10, 50);
        Mat matCenter = workingMatrix.submat(120, 150, 80, 120);
        Mat matRight = workingMatrix.submat(120, 150, 150, 190);

        Imgproc.rectangle(workingMatrix, new Rect(10,120,40,30), new Scalar(255,255,255));

        double leftTotal = Core.sumElems(matLeft).val[2];
        double centerTotal = Core.sumElems(matCenter).val[2];
        double rightTotal = Core.sumElems(matRight).val[2];

        if(leftTotal > centerTotal) {
            if(leftTotal > rightTotal) {
                //left is skystone
            }
            else {
                //right is skystone
            }
        }
        else if(centerTotal>rightTotal) {
            //center is skystone
        }
        else {
            //right is skystone
        }


        return workingMatrix;
    }
}
