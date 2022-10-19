package org.firstinspires.ftc.teamcode.opencvpipelines;

import com.vuforia.Rectangle;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Rect;
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

        int lowx = 0;
        int highx = 0;
        int x = 0;
        while (x<1920) {
            while (Core.sumElems(workingMatrix.submat(new Range(x, x + 1), new Range(0, 1079))).val[2] == 0) {
                x++;
                lowx = x;
            }
            while(Core.sumElems(workingMatrix.submat(new Range(x, x + 1), new Range(0, 1079))).val[2] > 0) {
                x++;
                highx = x;
            }
        }

        int lowy = 0;
        int highy = 0;
        int y = 0;
        while (y<1080) {
            while (Core.sumElems(workingMatrix.submat(new Range(y, y + 1), new Range(0, 1919))).val[2] == 0) {
                y++;
                lowy = y;
            }
            while(Core.sumElems(workingMatrix.submat(new Range(y, y + 1), new Range(0, 1919))).val[2] > 0) {
                y++;
                highx = y;
            }
        }



        Imgproc.rectangle(workingMatrix, new Rect(lowx, highx, lowy, highy), new Scalar(0,255,0));



        return workingMatrix;
    }
}
