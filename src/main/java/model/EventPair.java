package model;

import org.json.JSONException;
import org.json.JSONObject;

public class EventPair {

	private JSONObject startedObject;
	private JSONObject finishedObject;
	private long duration;
	private boolean alertFlag;

	public boolean hasBoth() {
		return startedObject != null && finishedObject != null; 
	}

	public boolean computeTimestamp() throws NumberFormatException, JSONException {
		if (!hasBoth()) {
			return false;
		}

		Long startedTimestamp = Long.parseLong(this.startedObject.getString("timestamp"));
		Long finishedTimestamp = Long.parseLong(this.finishedObject.getString("timestamp"));

		this.duration = finishedTimestamp - startedTimestamp;
		if(this.duration > 4)
			this.alertFlag = true;

		return true;
	}

	public JSONObject getStartedObject() {
		return startedObject;
	}

	public JSONObject getFinishedObject() {
		return finishedObject;
	}

	public long getDuration() {
		return duration;
	}

	public boolean isAlertFlag() {
		return alertFlag;
	}

	public void setStartedObject(JSONObject startedObject) {
		this.startedObject = startedObject;
	}

	public void setFinishedObject(JSONObject finishedObject) {
		this.finishedObject = finishedObject;
	}
	
	

}