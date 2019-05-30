package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.algo.utils.Label;
import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	int NbReachedNodes;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.NbReachedNodes = 0;
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        boolean fin = false;
        Graph graph = data.getGraph();
        int SizeGraph = graph.size();
        /* table of labels*/
        Label tabLabel[] = new Label[SizeGraph];
        /*tas of label*/
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        /* table of predecessors*/
        Arc[] PredecessorArc = new Arc[SizeGraph];
        // Initialize
        Label deb = newLabel(data.getOrigin(),data);
        tabLabel[(deb.getNode().getId())] = deb;
        tas.insert(deb);
        deb.setInTas();
        deb.setCost(0);
        //Notify observers about the first event (origin processed)
        notifyOriginProcessed(data.getOrigin());
        //While there are some unmarked nodes
        while (!tas.isEmpty() && !fin) {
        	Label CurrentLabel = tas.deleteMin();
        	// Notify observers that the Node is marked
        	notifyNodeMarked(CurrentLabel.getNode());
        	CurrentLabel.setMarked();
        	// Stop when it's the end
        	if (CurrentLabel.getNode() == data.getDestination()) {
        		fin = true;
        	}
        	// Run through the successors of CurrentLabel
        	Iterator <Arc> arc = CurrentLabel.getNode().iterator();
        	while (arc.hasNext()) {
        		Arc IteArc = arc.next();
        		
        		//  check allowed roads...
        		if (!data.isAllowed(IteArc)) {
					continue;
				}
        		Node successor = IteArc.getDestination();
        		//recorver the matching label from the table of Label
        		Label SuccessorLabel = tabLabel[successor.getId()];
        		// if label doesnt exist, we create
        		if (SuccessorLabel == null) {
        			//inform observers that the Node is reached for the first time 
        			notifyNodeReached(IteArc.getDestination());
        			SuccessorLabel = newLabel(successor,data);
        			tabLabel[SuccessorLabel.getNode().getId()] = SuccessorLabel;
        			this.NbReachedNodes ++;
        		}
        		// if it isnt' marked
        		if (!SuccessorLabel.getMarked()) {
        			if ((SuccessorLabel.getTotalCost() > (CurrentLabel.getCost() + data.getCost(IteArc)
        			+ (SuccessorLabel.getTotalCost() - SuccessorLabel.getCost())))
        			|| (SuccessorLabel.getCost()==Float.POSITIVE_INFINITY)){
        				SuccessorLabel.setCost(CurrentLabel.getCost() + (float)data.getCost(IteArc));
        				SuccessorLabel.setFather(CurrentLabel.getNode());
        				//if the label is in the tas -> remove
        				if (SuccessorLabel.getInTas()) {
        					tas.remove(SuccessorLabel);
        				}
        				//else -> insert
        				else 
        				{
        					SuccessorLabel.setInTas();
        				}
        				tas.insert(SuccessorLabel);
        				PredecessorArc[IteArc.getDestination().getId()] = IteArc;
        			}
        		}
        	}  
        }
        // Destination has no predecessor, the solution is infeasible...
        if (PredecessorArc[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = PredecessorArc[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = PredecessorArc[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }
    // Return the number of reached node
    public int getNbReachedNodes() {
    	return this.NbReachedNodes;
    }
    
    protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
}
