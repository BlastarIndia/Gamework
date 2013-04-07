package cz.robyer.gamework.game;

/**
 * Interface for receiving {@link GameEvent}s.
 * @author Robert P�sel
 */
public interface GameEventListener {
	public void receiveEvent(GameEvent event);
}
