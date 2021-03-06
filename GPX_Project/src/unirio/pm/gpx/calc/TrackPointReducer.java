package unirio.pm.gpx.calc;

import java.util.ArrayList;

import unirio.pm.gpx.calc.DistanceCalculator;
import unirio.pm.gpx.model.TrackPoint;

public class TrackPointReducer {

	static public ArrayList<TrackPoint> reducePointsByDistance(
			ArrayList<TrackPoint> trackPointList, Double distance) {

		//Creating objects
		TrackPoint previous = null;
		TrackPoint next = null;
		ArrayList<TrackPoint> newTrackPointList = new ArrayList<TrackPoint>();
		double d = 0;
		int i = 0;
		int trackPointListSize = trackPointList.size();

		//Changing the distance from KM to Meters
		distance = distance * 1000;

		//Verifying if the list is empty
		if (emptyList(trackPointList)) {
			System.out.println("There was an error while reading the file. Please verify it and try again later.");
			System.exit(0);
		}

		//Verifying if there's more than two TrackPoints
		if (onlyTwoTrackPoints(trackPointList)) {
			System.out.println("Your list has only two values, the file will not be changed.");
			System.exit(0);
		}

		//Removing TrackPoints in the same place
		trackPointList = removeTrackPointsAtTheSamePlace(trackPointList);

		//Creating Loop for TrackPoint List
		for (TrackPoint trackPoint : trackPointList) {

			//For the calc, the TrackPoint can't be the first or the last on the list
			if ((i != 0) && (i != trackPointListSize - 1)) {

				//Get the previous and the next TrackPoint
				previous = trackPointList.get(i - 1);
				next = trackPointList.get(i + 1);

				//Calculating the distance from the TrackPoints
				d = DistanceCalculator.getDistance(trackPoint, previous, next);

				//Verifying if the calculated distance is bigger than the desired one
				if (d > distance) {
					// Adding to the new TrackPoint list
					newTrackPointList.add(trackPoint);
				}
			//If it's the first or the last
			} else {
				newTrackPointList.add(trackPoint);
			}
			i++;
		}
		return newTrackPointList;
	}

	static public ArrayList<TrackPoint> reducePointsByPercentage(
			ArrayList<TrackPoint> trackPointList, int percentage) {

		//Counter to know how many TrackPoints were already removed
		int trackPointCount = 0;

		//Removing TrackPoints in the same place
		trackPointList = removeTrackPointsAtTheSamePlace(trackPointList);

		//Quantity of TrackPoints from percentage to remove
		int trackPointsToRemove = ((trackPointList.size() * percentage) / 100);
		System.out.println("TrackPoints to remove: " + trackPointsToRemove);

		//Index of the TrackPoint to remove from the list
		int trackPointToRemove;

		//Loop to last until the percentage is achieved
		while (trackPointsToRemove > trackPointCount) {

			//Get the smallest TrackPoint
			trackPointToRemove = smallestTrackPoint(trackPointList);

			//Remove it from the list
			trackPointList.remove(trackPointToRemove);

			trackPointCount++;
		}

		return trackPointList;

	}

	//Verify the TrackPoint with the smallest distance
	static private int smallestTrackPoint(ArrayList<TrackPoint> trackPointList) {
		
		// Object to identify smallest distance
		double smallestDistance = 1000000;

		// Index to return
		int indexSmallest = 0;

		// Creating objects
		TrackPoint previous = null;
		TrackPoint next = null;
		double d = 0;
		int i = 0;
		int trackPointListSize = trackPointList.size();

		// Verifying if the list is empty
		if (emptyList(trackPointList)) {
			System.out.println("There was an error while reading the file. Please verify it and try again later.");
			System.exit(0);
		}

		// Creating Loop for TrackPoint List
		for (TrackPoint trackPoint : trackPointList) {

			// For the calc, the TrackPoint can't be the first or the last on the list
			if ((i != 0) && (i != trackPointListSize - 1)) {

				// Get the previous and the next TrackPoint
				previous = trackPointList.get(i - 1);
				next = trackPointList.get(i + 1);

				// Calculating the distance from the TrackPoints
				d = DistanceCalculator.getDistance(trackPoint, previous, next);

				// Verifying if the calculated distance is bigger than the desired one
				// or if the point is at the line
				if ((d < smallestDistance) || (pointsAtTheSameLine(d))) {
					indexSmallest = i;
					smallestDistance = d;
				}
			}
			i++;
		}
		return indexSmallest;
	}

	// Remove Trackpoints in the same place
	static private ArrayList<TrackPoint> removeTrackPointsAtTheSamePlace(
			ArrayList<TrackPoint> trackPointList) {
		// Creating objects
		TrackPoint previous = null;
		TrackPoint next = null;
		int i = 0;
		int trackPointListSize = trackPointList.size();

		// Verifying if the list is empty
		if (emptyList(trackPointList)) {
			System.out.println("There was an error while reading the file. Please verify it and try again later.");
			System.exit(0);
		}

		// Creating Loop for TrackPoint List
		for (TrackPoint trackPoint : trackPointList) {

			// For the calc, the TrackPoint can't be the first or the last on
			// the list
			if ((i != 0) && (i != trackPointListSize - 1)) {

				// Get the previous and the next TrackPoint
				previous = trackPointList.get(i - 1);
				next = trackPointList.get(i + 1);

				// Verify if the current TrackPoint is at the same place as
				// the previous or the next one
				if (pointsAtTheSamePlace(trackPoint, previous)
						|| pointsAtTheSamePlace(trackPoint, previous)) {
					trackPointList.remove(trackPoint);
				}
			}
			i++;
		}
		return trackPointList;
	}

	// Verify if there's at least one TrackPoint in the list.
	static public boolean emptyList(ArrayList<TrackPoint> trackPointList) {
		if (trackPointList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	// Verify if the list has only two TrackPoints. As it's not possible to take
	// the first and the last TrackPoints, the output will be the same as the input.
	static private boolean onlyTwoTrackPoints(
			ArrayList<TrackPoint> trackPointList) {
		if (trackPointList.size() > 2) {
			return false;
		} else {
			return true;
		}
	}

	// Verify if the TrackPoints are in the same place
	static public boolean pointsAtTheSamePlace(TrackPoint x1, TrackPoint x2) {
		if ((x1.getLatitude() == x2.getLatitude())
				&& (x1.getLongitude() == x2.getLongitude())) {
			return true;
		} else {
			return false;
		}
	}
	
	// Verify if the TrackPoints are in the same line
	static public boolean pointsAtTheSameLine(double d) {
		if (d == 0) {
			return true;
		} else {
			return false;
		}
	}	
}
