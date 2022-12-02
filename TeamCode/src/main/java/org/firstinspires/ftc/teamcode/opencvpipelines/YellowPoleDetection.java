package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class YellowPoleDetection extends OpenCvPipeline {


    Scalar low = new Scalar(20, 150, 150);
    Scalar high = new Scalar(35, 255, 255);


    private int maxWidth, min, max, left, right;

    Mat workingMatrix = new Mat();

    @Override
    public Mat processFrame(Mat input) {

        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2HSV); //

        Core.inRange(workingMatrix, low, high, workingMatrix);

        maxWidth = 0;
        min = workingMatrix.width();
        max = 0;
        /*
        for(int y = 0; y < workingMatrix.height();y++) {
            int min = workingMatrix.width();
            int max = 0;
            for(int x = 0; x<workingMatrix.width() && (min == workingMatrix.width() || max == 0);x++){

                if(min == workingMatrix.width()&&workingMatrix.get(y,x)[0]==1) {
                    min = x;
                }
                if(max == 0&&workingMatrix.get(y,workingMatrix.width()-1-x)[0]==1) {
                    max = x;
                }
            }

            if(max-min>maxWidth) {
                maxWidth = max - min;
                Min = min;
                Max = max;
            }
        }
        */
        for(int x = 0; x<workingMatrix.width() &&(min == workingMatrix.width() || max == 0); x++){

            if(min == workingMatrix.width()&&workingMatrix.get(0,x)[0]!=0) {
                min = x;
                left = x;
            }
            if(max == 0&&workingMatrix.get(0,workingMatrix.width()-1-x)[0]!=0) {
                max = workingMatrix.width()-1-x;
                right = x;
            }
        }
        maxWidth = max -min;


        Imgproc.drawMarker(workingMatrix, new Point(min, 0), new Scalar(100,255,0));
        Imgproc.drawMarker(workingMatrix, new Point(max, 0), new Scalar(100,255,0));

        return workingMatrix;
    }


    public int height() {
        return workingMatrix.height();
    }

    public int width() {
        return workingMatrix.width();
    }

    public int getMaxWidth() { //use this to align with cone, when maxwidth reaches certain threshold after centered to reasonable threshold of accuracy based on the difference of the average of LDist1 and RDist 2 and the average of LDist2 and RDist 1
        return maxWidth;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

}