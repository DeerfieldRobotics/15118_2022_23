public class SlideCheck extends Thread {
    private Slide slide;
    private boolean targetLow = false;
    private int oldPos;

    public SlideCheck(Slide s) {
        slide = s;
    }
    public SlideCheck(Slide s, int o) {
        slide = s;
        oldPos = o;
        targetLow = true;
    }

    @Override
    public void run() {
        while(slide.getPosition() < slideMin)
            Thread.sleep(100);
        if(flip)
            claw.moveArm(0);
        else
            claw.moveArm(1);
        if(targetLow) {
            slide.toPosition(oldPos);
        }
    }
}
