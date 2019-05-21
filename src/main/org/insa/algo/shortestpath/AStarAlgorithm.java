package org.insa.algo.shortestpath;

import org.insa.algo.utils.LabelStar;
import org.insa.algo.utils.Label;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    protected Label label(Node node, ShortestPathData data) {
    	return new LabelStar(node,data);
		
	};

}
