package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TestPipeline extends OpenCvPipeline {
    Mat workingMatrix = new Mat();


    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb); //converts to ycrcb

        Scalar low = new Scalar(0, 50, 150);
        Scalar high = new Scalar(255, 150, 255);

        Core.inRange(workingMatrix, low, high, workingMatrix);

        return workingMatrix;
    }
}
