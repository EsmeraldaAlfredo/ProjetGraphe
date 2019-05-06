package org.insa.algo.shortestpath;

import java.util.Iterator;

import org.insa.algo.utils.Label;
import org.insa.graph.*;
import org.insa.algo.utils.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        boolean fin = false;
        Graph graphe = data.getGraph();
        int TailleGraphe = graphe.size();
        /* table des labels*/
        Label tabLabel[] = new Label[TailleGraphe];
        /*tas de label*/
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        /* table des prédécesseurs*/
        //Arc[] predecessorArc = new Arc[TailleGraphe];
        // Initialiser sommet
        Label deb = new Label(data.getOrigin());
        tabLabel[(deb.GetNode().getId())] = deb;
        tas.insert(deb);
        deb.SetInTas();
        deb.SetCost(0);
        // Notifier les observeurs le premier élément
        notifyOriginProcessed(data.getOrigin());
        // tant qu'il existe des sommets non marqués
        while (!tas.isEmpty() && !fin) {
        	Label CurrentLabel = tas.deleteMin();
        	// Notifier les observeurs que le Node a été marqué
        	notifyNodeMarked(CurrentLabel.GetNode());
        	CurrentLabel.SetMarque();
        	if (CurrentLabel.GetNode() == data.getDestination()) {
        		// Notifier les observeurs qu'on atteint la fin
        		notifyDestinationReached(CurrentLabel.GetNode());
        		fin = true;
        	}
        	// parcours les successeurs de CurrentLabel
        	Iterator <Arc> arc = CurrentLabel.GetNode().iterator();
        	while (arc.hasNext()) {
        		Arc IteArc = arc.next();
        		Node successor = IteArc.getDestination();
        		//récupérer table de Label
        		Label SuccessorLabel = tabLabel[successor.getId()];
        		if (!SuccessorLabel.GetMarque()) {
        			if (SuccessorLabel.GetCost() > CurrentLabel.GetCost() + data.getCost(IteArc)) {
        				SuccessorLabel.SetCost(CurrentLabel.GetCost() + (float)data.getCost(IteArc));
        				if (SuccessorLabel.GetInTas()) {
        					tas.remove(SuccessorLabel);
        				}
        				else {
        					tas.insert(SuccessorLabel);
        				}
        			}	
        		}
        	}
        }  
        
        return solution;
    }

}
