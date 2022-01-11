package com.gdx.game.world;

import java.util.ArrayList;

public class BuildingCost {

	private ArrayList<String> resourceList;
	private ArrayList<Integer> costList;
	
	public BuildingCost() {
		resourceList = new ArrayList<String>();
		costList = new ArrayList<Integer>();
	}
	
	public void addResourceCost(String name, int value) {
		resourceList.add(name);
		costList.add(value);
	}

	public ArrayList<String> getResourceList() {
		return resourceList;
	}

	public ArrayList<Integer> getCostList() {
		return costList;
	}
	
	
}
