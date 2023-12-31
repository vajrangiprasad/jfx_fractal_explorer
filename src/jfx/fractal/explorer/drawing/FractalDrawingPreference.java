package jfx.fractal.explorer.drawing;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class FractalDrawingPreference implements Observable{
	private List<InvalidationListener> invalidationListeners = new ArrayList<InvalidationListener>();
	private String eventId;
	private boolean jobCanceled = false;
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public boolean isJobCanceled() {
		return jobCanceled;
	}

	public void setJobCanceled(boolean jobCanceled) {
		this.jobCanceled = jobCanceled;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}
	
	public void invalidate(String eventId) {
		this.eventId = eventId;
		invalidationListeners.forEach(listner -> {
			listner.invalidated(this);
		});
	}

}
