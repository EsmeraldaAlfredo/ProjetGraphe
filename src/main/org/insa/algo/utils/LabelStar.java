package org.insa.algo.utils;

import org.insa.graph.Node;
import org.insa.algo.shortestpath.ShortestPathData;
//import org.insa.graph.Arc;
import org.insa.algo.utils.*;
import org.insa.algo.AbstractInputData;
import org.insa.graph.Point;
import java.lang.Math;

public class LabelStar  extends Label implements Comparable<Label> {
	private float cout_estime;
	
	
	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);
		if(data.getMode()== AbstractInputData.Mode.LENGTH) {
		this.cout_estime=(float) Point.distance(noeud.getPoint(),data.getDestination().getPoint());
	   }
		else {
			int vitesse= Math.max(data.getMaximumSpeed(),data.getGraph().getGraphInformation().getMaximumSpeed());
			this.cout_estime= (float) Point.distance(noeud.getPoint(),data.getDestination().getPoint()) *( vitesse*1000.0f/3600.0f);
			
		}
	}

	public float getTotalCost() {
		return this.cout_estime + this.getTotalCost();
	}
}
