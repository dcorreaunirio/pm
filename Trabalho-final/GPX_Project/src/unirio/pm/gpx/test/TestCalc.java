package unirio.pm.gpx.test;

import static org.junit.Assert.*;

import org.junit.Test;

import unirio.pm.gpx.calc.DistanceCalculator;

public class TestCalc {

	@Test
	public void testHaversine() {
		
		//Calculating distance between coordinates
		double y1 = 2;
		double x1 = 4;
		double y3 = 8;
		double x3 = 10;
		
		double h = DistanceCalculator.haversine(y1, x1, y3, x3);
		System.out.println("h: " + h);
		double hResult = 941;
		
		//Testing with the delta as 1
        assertEquals(h, hResult, 1); 
		
	}
	
	@Test
	public void TestDistance() {
		
		//x1 = longitude of previous point
		double x1 = -71.281; 
		//y1 = latitude of previous point
		double y1 = 42.052;
		//x2 = longitude of previous point
		double x2 = -71.279;
		//y1 = latitude of previous point
		double y2 = 42.055; 
		//x3 = longitude of next point
		double x3 = -71.266; 
		//y3 = latitude of next point
		double y3 = 42.049; 
		
		//Calculating distance between coordinates
		double h = DistanceCalculator.distanceBetweenPoints(x1, y1, x2, y2, x3, y3);
		System.out.println("h: " + h);
		double hResult = 7994;
		System.out.println("hResult: " + hResult);
		
		//Testing with the delta as 1
        assertEquals(h, hResult, 1); 
		
	}
	
	
	

}