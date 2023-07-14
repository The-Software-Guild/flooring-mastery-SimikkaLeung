package com.example.demo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.example.demo.model.State;

public interface StateDao {
	public Map<String, State> loadFromFile() throws FileNotFoundException, IOException;
	public Map<String, State> getStateMap();
}
