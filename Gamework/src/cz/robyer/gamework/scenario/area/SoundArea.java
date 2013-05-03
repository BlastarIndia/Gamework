package cz.robyer.gamework.scenario.area;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.SoundPool;
import android.util.Log;
import cz.robyer.gamework.scenario.Scenario;
import cz.robyer.gamework.util.GPoint;

/**
 * This represents point area in which space is played some sound.
 * @author Robert P�sel
 */
public class SoundArea extends PointArea {
	protected String value;
	protected int soundId = -1;
	protected float volume = 1.0f;
	protected float pitch = 1.0f;
	protected int loop = -1; // loop forever
	protected int soundRadius;
	
	/**
	 * Class constructor
	 * @param id - identificator of area
	 * @param point - center point of area
	 * @param radius - radius in meters
	 * @param value - sound to be played
	 * @param soundRadius - radius in which sound will be played, in meters
	 */
	public SoundArea(String id, GPoint point, int radius, String value, int soundRadius) {
		super(id, point, radius);
		this.value = value;
		this.soundRadius = soundRadius;
	}
	
	/* (non-Javadoc)
	 * @see cz.robyer.gamework.scenario.BaseObject#setScenario(cz.robyer.gamework.scenario.Scenario)
	 */
	@Override
	public void setScenario(Scenario scenario) {
		super.setScenario(scenario);
		
		if (soundId == -1) {
			AssetFileDescriptor descriptor = null;
			try {
				// TODO: maybe not use soundpool but classic audiomanager for this, as this could be music
				descriptor = getContext().getAssets().openFd(value);
				soundId = getScenario().getSoundPool().load(descriptor, 1);
			} catch (IOException e) {
				Log.e(TAG, String.format("Can't load sound '%s'", value));
			} finally {
				try {
		        	if (descriptor != null)
		        		descriptor.close();
		        } catch (IOException ioe) {
		        	Log.e(TAG, ioe.getMessage(), ioe);
		        }
			}
		}
	}

	/**
	 * Calculate sound volume depending on distance
	 * @param distance in meters
	 * @return volume from 0.0 to 1.0
	 */
	private float calcVolume(double distance) {
		return 1.0f - (float)((soundRadius - distance) / soundRadius);  
	}
	
	/* (non-Javadoc)
	 * @see cz.robyer.gamework.scenario.area.Area#updateLocation(double, double)
	 */
	@Override
	public void updateLocation(double lat, double lon) {
		// TODO: fix when soundRadius > radius
		double distance = GPoint.distanceBetween(point.latitude, point.longitude, lat, lon);
		boolean r = distance < (radius + (inArea ? LEAVE_RADIUS : 0));
		float actualVolume = calcVolume(distance);

		SoundPool soundPool = getScenario().getSoundPool();
		
		if (inArea != r) {
			// entering or leaving area
			Log.i(TAG, String.format("We %s location", (r ? "entered" : "leaved")));
			inArea = r;
			callHooks(inArea);
			
			if (soundId != -1) {
				if (inArea) {
					Log.d(TAG, String.format("Started playing sound '%s' at volume '%.2f'", value, actualVolume));
					soundPool.play(soundId, actualVolume, actualVolume, 1, loop, pitch);
				} else {
					Log.d(TAG, String.format("Stopped playing sound '%s'", value));
					soundPool.stop(soundId);
				}
			}
		} else if (inArea) {
			// update sound volume 
			Log.d(TAG, String.format("Changed volume of sound '%s' to '%.2f'", value, actualVolume));
			soundPool.setVolume(soundId, actualVolume, actualVolume);
		}
	}
	
}
