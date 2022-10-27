package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class YellowPoleDetection extends OpenCvPipeline {


    List<int[]> yXArray = new ArrayList<int[]>();

    Scalar low = new Scalar(61, 50, 100);
    Scalar high = new Scalar(120, 50, 100);
    private double[][][] initial;

    Mat workingMatrix = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        yXArray.clear();

        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2HSV); //

        //create rectangles, for loop
        initial = new double[input.width()/5][input.height()/5][2] ;

        int widthMult = input.width()/initial.length;
        int heightMult = input.height()/initial[0].length;

        Core.inRange(workingMatrix, low, high, workingMatrix);



        return input;
    }


    public int height() {
        return workingMatrix.height();
    }

    public int width() {
        return workingMatrix.width();
    }

    public int arrayWidth() {
        return initial.length;
    }
    public int arrayHeight() {
        return initial[0].length;
    }

    public int[] getAlignment() {
        int LDist1 = Integer.MAX_VALUE;
        int LDist2 = Integer.MAX_VALUE;
        int RDist1 = 0;
        int RDist2 = 0;
        int Ly = 0;
        int Ry = 0;
        for(int i = 0; i < workingMatrix.height();i++) {
            for(int j = 0; j<workingMatrix.width();j++) {
                if(workingMatrix.get(i,j)[0]==255) {
                    if(j>RDist1)
                        RDist1=j;
                        Ry = i;
                    if(j<LDist1)
                        LDist1=j;
                        Ly = i;


                }
            }
        }


        for(int i = 0; i < workingMatrix.height();i++) {
            for(int j = 0; j<workingMatrix.width();j++){
                if (j == Ry && i < LDist2)  //finds corresponding left x value to the y value of max x
                    LDist2 = i;
                if (j == Ly && i > RDist2)
                    RDist2 = i;
            }
        }

        return new int[]{LDist1, LDist1, Ly, RDist1, RDist2, Ry};
    }

    public int[] getMaxWidth() { //use this to align with cone, when maxwidth reaches certain threshold after centered to reasonable threshold of accuracy based on the difference of the average of LDist1 and RDist 2 and the average of LDist2 and RDist 1
        List<Integer> checkedVals = new ArrayList<Integer>();
        int maxWidth = 0;
        int Min = 0;
        int Max = 0;
        int y = 0;
        for(int i = 0; i < workingMatrix.height();i++) {
            for(int j = 0; j<workingMatrix.width();j++)
                if(!checkedVals.contains(j)) { //if not yet checked
                    checkedVals.add(j); //add to list of checked y values
                    int min = Integer.MAX_VALUE;
                    int max = 0;
                    for(int p = 0; i < workingMatrix.height();i++) {
                        for(int q = 0; j<workingMatrix.width();j++) {
                            if (j == q) { //if y values match
                                min = Math.min(min, i); //get min x value
                                max = Math.max(max, i); //get max x value
                            }
                        }
                    }
                    if(max-min>maxWidth) {
                        maxWidth = max - min;
                        y = j;
                        Min = min;
                        Max = max;
                    }
                }
        }

        return new int[]{maxWidth, Min, Max, y};
    }

}