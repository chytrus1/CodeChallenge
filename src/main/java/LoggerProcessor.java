import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseOperator;
import model.EventPair;

public class LoggerProcessor {
	//String filePath = "C:/Users/luk/Desktop/code_challenge/LoggerCodingChallenge/res/logs_example.txt";
	
	private Map<String, EventPair> pairs;

	public LoggerProcessor() {
		pairs = new HashMap<>();
	}
	
	public void processLogFile(String filePath) {
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(line -> mapStringToJson(line));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void mapStringToJson(String str) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(str);

			String id = jsonObject.getString("id");
			EventPair pair = pairs.get(id);
			
			if (pair == null) {
				pair = new EventPair();
				pairs.put(id, pair);
			}
			if (isFinished(jsonObject)) {
				pair.setFinishedObject(jsonObject);
			} else {
				pair.setStartedObject(jsonObject);
			}
			
			if (pair.computeTimestamp()) {
				
				DatabaseOperator.insertLogRow(id,
						pair.getStartedObject().getString("state"), 
						Optional.ofNullable(pair.getStartedObject().getString("type")).orElse(""), 
						Optional.ofNullable(pair.getStartedObject().getString("host")).orElse(""), 
						pair.getDuration(), 
						pair.isAlertFlag());
				
				this.pairs.remove(id);
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private boolean isFinished(JSONObject jsonObject) throws JSONException {
		return jsonObject.getString("state").equalsIgnoreCase("FINISHED");
	}

}