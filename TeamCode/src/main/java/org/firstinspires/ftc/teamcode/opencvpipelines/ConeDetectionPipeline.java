package org.firstinspires.ftc.teamcode.opencvpipelines;

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
    private double crThreshold = 50;


    Mat workingMatrix = new Mat();
    public ConeDetectionPipeline() {
    }

    public int height() {
        return workingMatrix.height();
    }

    public int width() {
        return workingMatrix.width();
    }

    @Override
    public Mat processFrame(Mat input) {


        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb); //

        //create rectangles, for loop
        double[][][] initial = new double[48][64][2] ;

        int heightMult = input.height()/initial.length;
        int widthMult = input.width()/initial[0].length;

        for(int i = 0; i<initial.length-1;i++) { //collects all cr cb values values
            for(int j = 0; j<initial[0].length-1;j++) {
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(new Range(i*heightMult,(i+1)*heightMult-1), new Range(j*widthMult,(j+1)*widthMult-1))).val[1]; //finds sum of submat and gets cb value

                initial[i][j][1]=Core.sumElems(workingMatrix.submat(new Range(i*heightMult,(i+1)*heightMult-1),new Range(j*widthMult,(j+1)*widthMult-1))).val[2]; //finds sum of submat and gets cr value
            }
        }

        //standard deviation:
        

/*
        double cr_sum = 0;
        double cb_sum = 0;
        for(int i = 0; i<initial.length-1;i++) {
            for(int j = 0;j<initial[0].length-1;j++) {
                cr_sum +=Math.abs(initial[i][j][1]-initial[i+1][j][1]); //adds lefts up
                cr_sum+=Math.abs(initial[i][j][1]-initial[i][j+1][1]); //adds ups up or smth
                cb_sum +=Math.abs(initial[i][j][0]-initial[i+1][j][0]);
                cb_sum+=Math.abs(initial[i][j][0]-initial[i][j+1][0]);
            }
        }

        double cbStandardDev = 0;
        double crStandardDev = 0;
        final double total_value = ;
        final double cr_mean = cr_sum/total_value;
        final double cb_mean = cb_sum/total_value;
        for(int i = 0; i<initial.length-1;i++) {
            for(int j = 0;j<initial[0].length-1;j++) {
                cbStandardDev+=Math.pow((initial[i][j][0]-cb_mean),2);
                crStandardDev+=Math.pow((initial[i][j][1]-cr_mean),2);
            }
        }

        cbStandardDev=Math.sqrt(cbStandardDev/(initial.length*initial[0].length));
        crStandardDev=Math.sqrt(crStandardDev/(initial.length*initial[0].length));
*/
        //counts outliers from each side
        //left outliers are from low saturation -> high saturation
        //down otliers are low saturation
        //                  high saturation

        //stores coords, values
        //index 0 is for row coord, 1 is for col coord, 2 for val, x is rightward, y is downward
        List<int[]> CrXArray = new ArrayList<int[]>();
        List<int[]> CrYArray = new ArrayList<int[]>();
        List<int[]> CbXArray = new ArrayList<int[]>();
        List<int[]> CbYArray = new ArrayList<int[]>();



        for(int i = 0; i<initial.length;i+=2) {
            for(int j = 1;j<initial[0].length;j+=2) {
                int CrL = 0;
                int CrR = 0;
                int CrU = 0;
                int CrD = 0;

                double temp = 0;

                if(i!=0) {
                    temp = initial[i][j][0]-initial[i-1][j][0];
                    if(i!=0&&Math.abs(temp)>crThreshold*CrSens) //Cr Left, should set the right value for the square next to it as -CrL
                        CrL = (int)(temp);
                }

                if(i!= initial.length-1) {
                    temp = initial[i][j][0]-initial[i+1][j][0];
                    if(i!=initial.length-1&&Math.abs(temp)>crThreshold*CrSens) //Cr right, should set right value for this square to CrR
                        CrR = (int)(temp);
                }

                if(j!=0) {
                    temp = initial[i][j][0]-initial[i][j-1][0];
                    if(j!=0&&Math.abs(temp)>crThreshold*CrSens)  //Cr up
                        CrU = (int)(temp);
                }

                if(j!=initial[0].length) {
                    temp = initial[i][j][0]-initial[j+1][j][0];
                    if(j!=initial[0].length-1&&Math.abs(temp)>crThreshold*CrSens)  //Cr down
                        CrD = (int)(temp);
                }

                //maybe filter things with all outliers here , probably arent significant

                if(CrL!=0) {
                    CrXArray.add(new int[]{i-1,j,-1*CrL});
                }
                if(CrR!=0) {
                    CrXArray.add(new int[]{i,j,CrR});
                }
                if(CrU!=0) {
                    CrXArray.add(new int[]{i,j-1,-1*CrU});
                }
                if(CrD!=0) {
                    CrXArray.add(new int[]{i,j,CrD});
                }
            }
        }
        for(int i = 1; i<initial.length;i+=2) {
            for(int j = 0;j<initial[0].length;j+=2) {
                int CrL = 0;
                int CrR = 0;
                int CrU = 0;
                int CrD = 0;

                double temp = 0;


                if(i!=0) {
                    temp = initial[i][j][0]-initial[i-1][j][0];
                    if(i!=0&&Math.abs(temp)>crThreshold*CrSens) //Cr Left, should set the right value for the square next to it as -CrL
                        CrL = (int)(temp);
                }

                if(i!= initial.length-1) {
                    temp = initial[i][j][0]-initial[i+1][j][0];
                    if(i!=initial.length-1&&Math.abs(temp)>crThreshold*CrSens) //Cr right, should set right value for this square to CrR
                        CrR = (int)(temp);
                }

                if(j!=0) {
                    temp = initial[i][j][0]-initial[i][j-1][0];
                    if(j!=0&&Math.abs(temp)>crThreshold*CrSens)  //Cr up
                        CrU = (int)(temp);
                }

                if(j!=initial[0].length) {
                    temp = initial[i][j][0]-initial[j+1][j][0];
                    if(j!=initial[0].length-1&&Math.abs(temp)>crThreshold*CrSens)  //Cr down
                        CrD = (int)(temp);
                }
                //maybe filter things with all outliers here , probably arent significant

                if(CrL!=0) {
                    CrXArray.add(new int[]{i-1,j,-1*CrL});
                }
                if(CrR!=0) {
                    CrXArray.add(new int[]{i,j,CrR});
                }
                if(CrU!=0) {
                    CrXArray.add(new int[]{i,j-1,-1*CrU});
                }
                if(CrD!=0) {
                    CrXArray.add(new int[]{i,j,CrD});
                }
            }
        }


/*
        //identify outliers with mean and threshold for standard deviation thing
        //store outlier location somehow, maybe boolean array, or just array of points
        //iterate using squares found with above


        Imgproc.rectangle(input, new Rect(lowx, lowy, highx-lowx, highy-lowy), new Scalar(0,255,0));
*/

        for(int[] p: CrXArray) {
            if(p[2]<0) {
                int y = p[0]*heightMult;
                int x = p[1]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.drawMarker(input, new Point(x, y), new Scalar(0, 255, 0));
            }
            else {
                int y = p[0] * heightMult;
                int x = p[1] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.drawMarker(input, new Point(x, y), new Scalar(255, 0, 0));
            }
        }

        for(int[] p: CrYArray) {
            if(p[2]<0) {
                int y = p[0]*heightMult;
                int x = p[1]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.drawMarker(input, new Point(x, y), new Scalar(0, 0, 255));
            }
            else {
                int y = p[0] * heightMult;
                int x = p[1] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.drawMarker(input, new Point(x, y), new Scalar(255, 255, 0));
            }
        }

        return input;
    }
}
