public class ClawMechanism {
    private Claw claw;
    private Slide slide;

    private boolean flip;

    private int slideTarget;
    
    public ClawMechanism(HardwareMap hw) {
        claw = new Claw(hw);
        slide = new Slide(hw);
        
        flip = true; //starts in flipped position
    }
    
    public void closeClaw(double position) {
        claw.moveClaw(position);
    }

    public void setSlideLevel(int level) {
        if(level == 0) {
            //maybe make it run down until it hits limit switch and then reset
            slideTarget = 0;
            slide.setPosition(0);
        }
        else if(level == 1) {
            slideTarget = Slide.low;
            slide.setPosition(Slide.low);
        }
        else if(level == 2) {
            slideTarget = Slide.medium;
            slide.setPosition(Slide.medium);
        }
        else if(level == 3) {
            slideTarget = Slide.high;
            slide.setPosition(Slide.high);
        }
    }
    
    public void flipRoll() {
        if(flip) 
            claw.moveRoll(0);
        else
            claw.moveRoll(1);
    }
    //wacky shit if press flip twice cuz of threads, maybe synchronize or have a cancel button
    public void flipArm() {
        if(slide.getCurrentPosition >= Slide.slideMin) {
            if(flip)
                claw.moveArm(0);
            else
                claw.moveArm(1);
        }
        else {
            if (slideTarget >= Slide.slideMin) {
                //start thread to check if slide goes up and then flip the arm
                SlideCheck sc = new SlideCheck(slide, claw);
                sc.start();
            }
            else {
                int oldPos = slide.getPosition();
                slide.setPosition(Slide.slideMin);
                //start thread to check if slide goes up and then flip the arm, then go back to old position
                SlideCheck sc = new SlideCheck(slide, claw, oldPos);
                sc.start();
            }
        }
    }

    public void flip() {
        flipArm();
        flipRoll();
    }

    public void setPitch(double position) {
        claw.movePitch(position);
    }
}