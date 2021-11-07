package com.gdx.game.world;

import java.util.HashMap;

public class ResourceManager {
	
	private int startingValue = 100;
	
	private HashMap<String, Integer> resourceValues;
	
	public ResourceManager(Empire empire) {
		resourceValues = new HashMap<String, Integer>();
		initValues();
	}
	
	private void initValues() {
		resourceValues.put("currency", startingValue);
		resourceValues.put("energy", startingValue);
		resourceValues.put("metal", startingValue);
		resourceValues.put("food", startingValue);
	}
	
	public void changeResourceValue(String resourceName, int changeAmount) {
		if(resourceValues.containsKey(resourceName)) {
			int currVal = getResourceValue(resourceName);
			int newVal = currVal + changeAmount;
			setResourceValue(resourceName, newVal);
		}
	}
	
	public void setResourceValue(String resourceName, int value) {
		if(resourceValues.containsKey(resourceName)) {
			resourceValues.replace(resourceName, value);
		}
	}
	
	public int getResourceValue(String resourceName) {
		if(resourceValues.containsKey(resourceName)) {
			return resourceValues.get(resourceName);
		}
		
		return 0;
	}
	
	public HashMap<String, Integer> getResourceMap(){
		return resourceValues;
	}
	
	public int getNumResources() {
		return resourceValues.size();
	}
	
	
}
