package com.example.demo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.example.demo.model.State;

public class StateDaoImpl implements StateDao{
	Map<String, State> stateMap;
	
	public StateDaoImpl() {
		stateMap = new TreeMap<String, State>();
	}
	
	@Override
	public Map<String, State> loadFromFile() throws FileNotFoundException, IOException {

		try (BufferedReader br = new BufferedReader(new FileReader("./src/Data/Taxes.txt"))) {
			String stateWholeLine = br.readLine(); // The first line of the file is the header. Do not write it to Order.
			
			while ( (stateWholeLine = br.readLine()) != null ) {
				State state = new State();
				String[] stateElements = stateWholeLine.split(",");
				state.setStateAbbreviation(stateElements[0]);
				state.setStateName(stateElements[1]);
				state.setTaxRate(new BigDecimal(stateElements[2]));
				stateMap.put(state.getStateAbbreviation().toUpperCase(), state);
			}
//			
//			for (Entry<String,State>entry: stateMap.entrySet()) {
//				System.out.println(entry);
//			}
			
		} catch (FileNotFoundException fnf) {
			// This program will not handle this error because without the state file it is not possible to place orders.
			System.err.println("The Taxes.txt does not exist! The application cannot proceed.");
			throw fnf;
		} 
		
		return stateMap;	
	}

	@Override
	public Map<String, State> getStateMap() {
		// TODO Auto-generated method stub
		return stateMap;
	}
	
}

