package org.insa.algo.shortestpath;

import org.insa.algo.utils.LabelStar;

public class AStarAlgorithm extends DijkstraAlgorithm {

	private LabelStar label;
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
