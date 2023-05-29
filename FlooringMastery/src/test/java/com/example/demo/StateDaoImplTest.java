package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.example.demo.dao.StateDao;
import com.example.demo.dao.StateDaoImpl;

class StateDaoImplTest {

	StateDao stateDao = new StateDaoImpl();

	@Test
	public void loadFromFileTest() throws FileNotFoundException, IOException {
		assertNotNull(stateDao.loadFromFile());
		
	}

		
	public void getProductMapTest() throws IOException {
		assertNotNull(stateDao.getStateMap());
		
	}
}
