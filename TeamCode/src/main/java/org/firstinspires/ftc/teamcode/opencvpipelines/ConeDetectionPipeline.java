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

public class ConeDetectionPipeline extends OpenCvPipeline {

    private int CrSens = 1;
    private int CbSens = 1;


    List<int[]> CrXArray = new ArrayList<int[]>();
    List<int[]> CrYArray = new ArrayList<int[]>();
    List<int[]> CbXArray = new ArrayList<int[]>();
    List<int[]> CbYArray = new ArrayList<int[]>();

    private final double crThreshold = 80;

    private double[][][] initial;

    private Telemetry t;


    Mat workingMatrix = new Mat();
    public ConeDetectionPipeline(Telemetry telemetry) {
        t = telemetry;
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

    public String tester() {return "1";};

    public double crThreshold() {return crThreshold;}

    public int outliersCrX() {return CrXArray.size();}
    
    public int outliersCrY() {return CrYArray.size();}

    public int[] getAlignment() {
        int LDist1 = Integer.MAX_VALUE;
        int LDist2 = Integer.MAX_VALUE;
        int RDist1 = 0;
        int RDist2 = 0;
        int Ly = 0;
        int Ry = 0;

        for(int[] p : CrXArray) {
            if(p[0]<LDist1){ //finds minimum x value
                LDist1=p[0];
                Ly=p[1];
            }
            if(p[0]>RDist1) { //finds maximum x value
                RDist1=p[0];
                Ry=p[1];
            }
        }

        for(int[] p : CrXArray) {
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
        for(int[] p : CrXArray) {
            if(!checkedVals.contains(p[1])) { //if not yet checked
                checkedVals.add(p[1]); //add to list of checked y values
                int min = Integer.MAX_VALUE;
                int max = 0;
                for(int[] q : CrXArray) {
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

    @Override
    public Mat processFrame(Mat input) {
        CrXArray.clear();
        CrYArray.clear();
        CbXArray.clear();
        CbYArray.clear();

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
                //Imgproc.rectangle(input, new Point(i*widthMult, j*heightMult), new Point((i+1)*widthMult-1, (j+1)*heightMult-1), new Scalar(255,255,255), 1); //draws grid for reference
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(new Range(j*widthMult,(j+1)*widthMult-1), new Range(i*heightMult,(i+1)*heightMult-1))).val[1]; //finds sum of submat and gets cb value

                initial[i][j][1]=Core.sumElems(workingMatrix.submat(new Range(j*widthMult,(j+1)*widthMult-1), new Range(i*heightMult,(i+1)*heightMult-1))).val[2]; //finds sum of submat and gets cr value
            }
        }

        int x1 = 0;
        int y1 = 0;
        for(x1 = 0; x1<initial.length;x1++) {
            for(y1 = 0;y1<initial[0].length;y1++) {
                if((x1%2==1&&y1%2==0)||(x1%2==0&&y1%2==1)) {
                    int CrL = 0;
                    int CrR = 0;
                    int CrU = 0;
                    int CrD = 0;

                    double temp = 0;


                    if (x1 > 0) {
                        temp = initial[x1][y1][0] - initial[x1 - 1][y1][0];
                        if (x1 != 0 && Math.abs(temp) > crThreshold * CrSens) //Cr Left, should set the right value for the square next to it as -CrL
                            CrL = (int) (temp);
                    }


                    if (x1 < initial.length - 1) {
                        temp = initial[x1][y1][0] - initial[x1 + 1][y1][0];
                        if (Math.abs(temp) > crThreshold * CrSens) //Cr right, should set right value for this square to CrR
                            CrR = (int) (temp);
                    }

                    if (y1 > 0) {
                        temp = initial[x1][y1][0] - initial[x1][y1 - 1][0];
                        if (Math.abs(temp) > crThreshold * CrSens)  //Cr up
                            CrU = (int) (temp);
                    }


                    if (y1 < initial[0].length - 1) {
                        temp = initial[x1][y1][0] - initial[x1][y1 + 1][0];
                        if (Math.abs(temp) > crThreshold * CrSens)  //Cr down
                            CrD = (int) (temp);
                    }


                    if (CrL != 0) {
                        CrXArray.add(new int[]{x1 - 1, y1, -1 * CrL});
                    }
                    if (CrR != 0) {
                        CrXArray.add(new int[]{x1, y1, CrR});
                    }
                    if (CrU != 0) {
                        CrYArray.add(new int[]{x1, y1 - 1, -1 * CrU});
                    }
                    if (CrD != 0) {
                        CrYArray.add(new int[]{x1, y1, CrD});
                    }

                }
            }
        }


/*
        Visualization
*/
        for(int[] p: CrXArray) {
            if(p[2]<0) {
                int y = p[1]*heightMult;
                int x = p[0]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.rectangle(input, new Point(x, y), new Point(x+1,y+heightMult), new Scalar(0, 255, 0), -1);
            }
            else {
                int y = p[1] * heightMult;
                int x = p[0] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.rectangle(input, new Point(x+widthMult-1, y), new Point(x+widthMult,y+heightMult), new Scalar(255, 0, 0), -1);
            }
        }
        
        for(int[] p: CrYArray) {
            if(p[2]<0) {
                int y = p[1]*heightMult;
                int x = p[0]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.rectangle(input, new Point(x, y), new Point(x+widthMult,y+1), new Scalar(0, 0, 255), -1);
            }
            else {
                int y = p[1] * heightMult;
                int x = p[0] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.rectangle(input, new Point(x, y+heightMult-1), new Point(x+widthMult,y+heightMult), new Scalar(255, 255, 0), -1);
            }
        }
        return input;
    }
}
