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

public class BlueConeDetection extends OpenCvPipeline {


    List<int[]> CbXArray = new ArrayList<int[]>();

    private final double cbThreshold = 80;

    private double[][][] initial;

    Mat workingMatrix = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        CbXArray.clear();

        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb); //

        //create rectangles, for loop
        initial = new double[input.width()/5][input.height()/5][2] ;

        int widthMult = input.width()/initial.length;
        int heightMult = input.height()/initial[0].length;

        for(int i = 0; i<initial.length-1;i++) { //collects all cr cb values values
            for(int j = 0; j<initial[0].length-1;j++) {
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(new Range(j*widthMult,(j+1)*widthMult-1), new Range(i*heightMult,(i+1)*heightMult-1))).val[2]; //finds sum of submat and gets cb value
            }
        }

        int x1 = 0;
        int y1 = 0;
        for(x1 = 1; x1<initial.length;x1+=2) {
            for(y1 = 0;y1<initial[0].length;y1++) {
                int CbL = 0;
                int CbR = 0;

                double temp = 0;


                if (x1 > 0) {
                    temp = initial[x1][y1][0] - initial[x1 - 1][y1][0];
                    if (x1 != 0 && Math.abs(temp) > cbThreshold) //Cr Left, should set the right value for the square next to it as -CrL
                        CbL = (int) (temp);
                }


                if (x1 < initial.length - 1) {
                    temp = initial[x1][y1][0] - initial[x1 + 1][y1][0];
                    if (Math.abs(temp) > cbThreshold) //Cr right, should set right value for this square to CrR
                        CbR = (int) (temp);
                }

                if (CbL != 0) {
                    CbXArray.add(new int[]{x1 - 1, y1, -1 * CbL});
                }
                if (CbR != 0) {
                    CbXArray.add(new int[]{x1, y1, CbR});
                }
            }
        }

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

    public int outliersCrX() {return CbXArray.size();}

    public int[] getAlignment() {
        int LDist1 = Integer.MAX_VALUE;
        int LDist2 = Integer.MAX_VALUE;
        int RDist1 = 0;
        int RDist2 = 0;
        int Ly = 0;
        int Ry = 0;

        for(int[] p : CbXArray) {
            if(p[0]<LDist1){ //finds minimum x value
                LDist1=p[0];
                Ly=p[1];
            }
            if(p[0]>RDist1) { //finds maximum x value
                RDist1=p[0];
                Ry=p[1];
            }
        }

        for(int[] p : CbXArray) {
            if(p[1]==Ry&&p[0]<LDist2)  //finds corresponding left x value to the y value of max x
                LDist2=p[0];
            if(p[1]==Ly&&p[0]>RDist2)
                RDist2=p[0];
        }

        return new int[]{LDist1, LDist1, Ly, RDist1, RDist2, Ry};
    }

    public int[] getMaxWidth() { //use this to align with cone, when maxwidth reaches certain threshold after centered to reasonable threshold of accuracy based on the difference of the average of LDist1 and RDist 2 and the average of LDist2 and RDist 1
        List<Integer> checkedVals = new ArrayList<Integer>();
        int maxWidth = 0;
        int Min = 0;
        int Max = 0;
        int y = 0;
        for(int[] p : CbXArray) {
            if(!checkedVals.contains(p[1])) { //if not yet checked
                checkedVals.add(p[1]); //add to list of checked y values
                int min = Integer.MAX_VALUE;
                int max = 0;
                for(int[] q : CbXArray) {
                    if(q[1]==p[1]) { //if y values match
                        min = Math.min(min, q[0]); //get min x value
                        max = Math.max(max, q[0]); //get max x value
                    }
                }
                if(max-min>maxWidth) {
                    maxWidth = max - min;
                    y = p[1];
                    Min = min;
                    Max = max;
                }
            }
        }

        return new int[]{maxWidth, Min, Max, y};
    }

}