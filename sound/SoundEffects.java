package sound;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Plays Sound effects.
 * <p>
 * TODO Resources aren't loading properly for jar distribution.
 * @see {@link Class#getResource(String)}
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class SoundEffects extends Application
{
	public static final String	OK		= "/res/sounds/treasure.wav";
	public static final String	CANCEL	= "/res/sounds/magic.wav";
	public static final String	MUSIC	= "/res/sounds/Mysterious.mp3";
	
	private List<MediaPlayer> playerList;
	
	/**
	 * Create a new SoundEffects object.
	 */
	public SoundEffects() {
		playerList = new LinkedList<>();
	}
	
	/**
	 * Play the given sound effect.
	 * @param path The path to the sound effect.
	 * @returns the MediaPlayer playing the given sound. (use if needed to stop 
	 * prematurely).
	 */
	public MediaPlayer playSound(String path) {
		Media media = new Media(getResourceString(path));
		MediaPlayer player = new MediaPlayer(media);
		FinishedListener l = new FinishedListener(player);
		player.setOnEndOfMedia(l);
		player.setOnStopped(l);
		player.play();
		return player;		
	}
		
	/**
	 * Convenience method to get absolute path to a resource.
	 * @param path The string path of the resource.
	 * @return A properly formatted resource string.
	 */
	private String getResourceString(String path){
		//return  new File(path).toURI().toString();
		//String tmp =  getClass().getResource(path).toString();
		//System.out.printf("formatted path: %s\n", tmp);
		return getClass().getResource(path).toString();
	}
	
	/**
	 * Creating a new listener adds the player to a list, and removes it from
	 * the list when it is run.
	 * @author Aaron Carson
	 * @version Jun 17, 2015
	 */
	class FinishedListener implements Runnable{
		private MediaPlayer player;
		public FinishedListener(MediaPlayer player){
			playerList.add(player);
		}
		@Override
		public void run() {
			playerList.remove(player);
		}		
	}
	
	/**
	 * Creates a repeating 
	 * @param path
	 * @return
	 */
	public MediaPlayer playRepeatingSound(String path) {
		Media media = new Media(getResourceString(path));
		MediaPlayer player = new MediaPlayer(media);
		FinishedListener l = new FinishedListener(player);
		player.setOnStopped(l);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.play();
		return player;		
	}
	
	/**
	 * Stop and remove all sounds.
	 */
	public void clear(){
		Iterator<MediaPlayer> it = playerList.iterator();
		while(it.hasNext()){
			MediaPlayer mp = it.next();
			mp.setOnStopped(null);
			mp.setOnEndOfMedia(null);
			mp.stop();
			it.remove();
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		SoundEffects sfx = new SoundEffects();
		sfx.playRepeatingSound(MUSIC);
		Thread.sleep(1000);
		sfx.playSound(OK);
		Thread.sleep(300);
		sfx.playSound(CANCEL);
		Thread.sleep(1000);
		sfx.clear();		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {}
}
