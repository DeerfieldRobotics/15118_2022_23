package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.HashMap;

public class YellowPoleDetection extends OpenCvPipeline {


    Scalar high = new Scalar(255, 255, 60);
    Scalar low = new Scalar(150, 120, 0);


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



        int x = workingMatrix.width()/2, y = 0;

        int currentMax = 0;
        int[] pole;

        while(y < workingMatrix.height()){
            while(x > workingMatrix.width()/4){
                if(workingMatrix.get(x,y)[0] >= low.val[0]
                    && workingMatrix.get(x,y)[1] >= low.val[1]
                    && workingMatrix.get(x,y)[2] >= low.val[2]
                    && workingMatrix.get(x,y)[2] <= high.val[2]
                        ){
                    min = x;
                }

                x -= 50;
            }

            while(x < 3 * workingMatrix.width()/4){
                if(workingMatrix.get(x,y)[0] >= low.val[0]
                        && workingMatrix.get(x,y)[1] >= low.val[1]
                        && workingMatrix.get(x,y)[2] >= low.val[2]
                        && workingMatrix.get(x,y)[2] <= high.val[2]
                            ){
                    max = x;
                }
                x += 50;
            }

            currentMax = max-min;

            if(currentMax > max){

            }

            y+= 75;
        }



        maxWidth = max -min;


        Imgproc.drawMarker(workingMatrix, new Point(min, 0), new Scalar(0,255,0));
        Imgproc.drawMarker(workingMatrix, new Point(max, 0), new Scalar(0,255,0));

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