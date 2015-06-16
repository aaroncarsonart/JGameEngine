package main;

import java.io.Serializable;

import engine.Game;

/**
 * Entry point for the application.
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class Main implements Serializable
{	
	private static final long serialVersionUID = 1L;

	/**
	 * Start the application.
	 * @param args Does nothing.
	 */
	public static void main(String[] args){
		Game game = new Game();
		game.start();
		
		/*
		TpsCounter tps = new TpsCounter();
		while(true){
			tps.tick();
		}
		*/
	}
}
