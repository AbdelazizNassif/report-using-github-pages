package utils;

public class WaitUtility {

    public synchronized void waitForInterval (long timeInMillis) {
        long initialTime = System.currentTimeMillis();
        while(true)
        {
            long finalTime = System.currentTimeMillis();
            if (finalTime >= timeInMillis+initialTime)
            {
                break;
            }
        }
    }
}
