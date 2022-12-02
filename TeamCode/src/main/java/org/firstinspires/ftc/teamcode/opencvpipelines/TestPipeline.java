package org.firstinspires.ftc.teamcode.opencvpipelines;

import com.vuforia.Rectangle;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

//import javafx.scene.shape.Rectangle;

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

        int lowx = 319;
        int highx = 0;
        int lowy = 239;
        int highy = 0;

        for(int i = 0; i < workingMatrix.height();i++) {
            for(int j = 0; j<workingMatrix.width();j++) {
                if(workingMatrix.get(i,j)[0]==255) {
                    if(i>highy)
                        highy=i;
                    if(i<lowy)
                        lowy=i;
                    if(j>highx)
                        highx=j;
                    if(j<lowx)
                        lowx=j;
                };
            }
        }
        /*
        int x = 1;
        while (x<240) {
            while (Core.sumElems(workingMatrix.submat(new Range(x, x + 1), new Range(0, 319))).val[2] == 0) {
                x++;
                lowx = x;
            }
            /*
            while(Core.sumElems(workingMatrix.submat(new Range(x, x + 1), new Range(0, 319))).val[2] > 0) {
                x++;
                highx = x;
            }

             */
/*
        int lowy = 0;
        int highy = 0;
        int y = 1;
        while (y<1919) {
            while (Core.sumElems(workingMatrix.submat(new Range(0,1079), new Range(y, y + 1))).val[2] == 0) {
                y++;
                lowy = y;
            }
            while(Core.sumElems(workingMatrix.submat(new Range(0,1079), new Range(y, y + 1))).val[2] > 0) {
                y++;
                highx = y;
            }
        }

*/
        Imgproc.rectangle(input, new Rect(lowx, lowy, highx-lowx, highy-lowy), new Scalar(0,255,0));



        return input;
    }

    public int height() {
        return workingMatrix.height();
    }
    public int width() {
        return workingMatrix.width();
    }
}
