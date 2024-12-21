import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.Robot;

public class ClearOutput {
    public static void main(String[] args) throws AWTException {
        
        System.out.println("Hello");

        Robot rob = new Robot();
        try{
            Thread.sleep(1000);
            rob.keyPress(KeyEvent.VK_CONTROL);
            rob.keyPress(KeyEvent.VK_L);
            rob.keyRelease(KeyEvent.VK_L);
            rob.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(500);
        }catch(InterruptedException e) {
            System.out.println("Error!");
        }

        // new output
        System.out.println("happy");
        
        
        
        
    }
}
