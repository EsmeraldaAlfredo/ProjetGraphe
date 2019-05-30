package org.insa.algo.utils;

import org.insa.graph.Node;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.AbstractInputData;
import org.insa.graph.Point;
import java.lang.Math;

public class LabelStar  extends Label implements Comparable<Label> {
	private float estimated_cost;
	
	public LabelStar(Node node, ShortestPathData data) {
		super(node);
		if(data.getMode()== AbstractInputData.Mode.LENGTH) {
		this.estimated_cost=(float) Point.distance(node.getPoint(),data.getDestination().getPoint());
	   }
		else {
			int vitesse= Math.max(data.getMaximumSpeed(),data.getGraph().getGraphInformation().getMaximumSpeed());
			this.estimated_cost = (float)Point.distance(node.getPoint(),data.getDestination().getPoint())/(vitesse*1000.0f/3600.0f);
			
		}
	}
	@Override
	public float getTotalCost() {
		return this.estimated_cost + this.cost;
	}
}
