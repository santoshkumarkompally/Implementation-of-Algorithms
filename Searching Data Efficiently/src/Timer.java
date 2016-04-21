/*
  * 
  * @authors Santosh Kompally, Roshni Saraogi, Rohini Bandkodige
  * 	
  */

public class Timer {
    long startTime, endTime, elapsedTime, memAvailable, memUsed;

    Timer() {
	startTime = System.currentTimeMillis();
    }

    public void start() {
	startTime = System.currentTimeMillis();
    }

    public Timer end() {
	endTime = System.currentTimeMillis();
	elapsedTime = endTime-startTime;
	memAvailable = Runtime.getRuntime().totalMemory();
	memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	return this;
    }

    public String toString() {
        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1000000) + " MB / " + (memAvailable/1000000) + " MB.";
    }

}