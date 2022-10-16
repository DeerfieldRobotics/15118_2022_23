public class SlideCheck extends Thread {
    private Slide slide;
    private Claw claw;

    private boolean targetLow = false;
    private int oldPos;

    public SlideCheck(Slide s, Claw c) {
        slide = s;
        claw = c;
    }
    public SlideCheck(Slide s, Claw c, int o) {
        slide = s;
        claw = c;

        oldPos = o;
        targetLow = true;
    }

    @Override
    public void run() {
        while(slide.getPosition() < Slide.slideMin) //checks every .1 seconds if it is at least minimum
            try{Thread.sleep(100);}catch(Exception e){}
            if(slide.getTargetPosition() < Slide.slideMin)
                return; //If slide target changes it will return
            if(targetLow && slide.getTargetPosition() != slideMin) //if target has changed, make sure it goes to new target
                oldPos = slide.getTargetPosition();
        if(flip)
            claw.moveArm(0);
        else
            claw.moveArm(1);
        if(targetLow) {
            slide.toPosition(oldPos);
        }
    }
}
