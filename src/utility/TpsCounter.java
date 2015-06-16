package utility;

import java.io.Serializable;

/**
 * Testing method for counting raw ticks per second (TPS = Ticks Per Second).
 * <p>
 * Benchmarking on my machine:<br>
 * ---------------------------
 * <p>
 * without synchronization:<br>
 * ------------------------<br>
 * 18,053,438 ticks per second (18 MHz)<br>
 * <p>
 * with synchronization:<br>
 * ---------------------<br>
 * 12,581,002 ticks per second (12 MHz)<br>
 */
public class TpsCounter implements Serializable
{	
	private static final long	serialVersionUID	= 1L;
	private long prevTime = System.currentTimeMillis();
	private long currentTime = prevTime;
	private long elapsed = 0;
	private long oneSecond = 1000;
	private long counter = 0;
	
	private String reference;
	/**
	 * Create a default TpsCounter.
	 */
	public TpsCounter(){}
	
	/**
	 * Create a TpsCounter with the given reference object.
	 * @param refObj An object used to reference where the update is reported 
	 *               from (prints the class name when reporting).
	 */
	public TpsCounter(Object refObj){
		this.reference = refObj == null ? "none" : refObj.getClass().getSimpleName();	
	}
	
	/**
	 * Update the benchmark device, and report ticks per second if at least 
	 * one second has passed since last report.
	 */
	public void tick(){
		currentTime = System.currentTimeMillis();
		elapsed += currentTime - prevTime;
		prevTime = currentTime;
		counter ++;
		if(elapsed >= oneSecond) {
			System.out.printf("%-20s %,5d ticks per second (elapsed time: %.3f s)\n", reference, counter, elapsed/1000.0);
			counter = 0;
			elapsed = 0;
		}
	}
}
