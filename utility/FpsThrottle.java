package utility;

/**
 * Convenience Class to throttle updates to the given frameRate (or FPS).
 * @author Aaron Carson
 * @version Jun 16, 2015
 */
public class FpsThrottle
{	
	private long				prevUpdate;
	
	private int					frameRate;
	private int                 maximumWait;

	
	/**
	 * Creat a new FpsThrottle.
	 * @param frameRate The frameRate to throttle the updates to.
	 */
	public FpsThrottle(int frameRate){
		this.frameRate = frameRate;
		this.maximumWait = 1000 / frameRate;
		this.prevUpdate = System.nanoTime();
		//this.prevUpdate = System.currentTimeMillis();
	}
	
	/**
	 * Check the time since the last update, calculate an appropriate wait
	 * time, and sleep the current thread.
	 */
	public void throttle(){
		
		try {
			Thread.sleep(Math.max(0, maximumWait - (System.nanoTime() - prevUpdate)/1000000));
			prevUpdate = System.nanoTime();
			//Thread.sleep(Math.max(0, maximumWait - (System.currentTimeMillis() - prevUpdate)));
			//prevUpdate = System.currentTimeMillis();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getMaximumWait(){
		return maximumWait;
	}
	
	public int getFrameRate(){
		return frameRate;
	}
}
