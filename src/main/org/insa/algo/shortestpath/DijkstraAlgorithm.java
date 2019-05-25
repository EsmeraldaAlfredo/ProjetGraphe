package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.algo.utils.Label;
import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	int NbrSommetsVisites;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.NbrSommetsVisites = 0;
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
        Arc[] PredecessorArc = new Arc[TailleGraphe];
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
        	// on s'arrête quand on atteint la fin
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
        		//récupérer le label correspondant dans table de Label
        		Label SuccessorLabel = tabLabel[successor.getId()];
        		// si label n'existe pas, on le crée
        		if (SuccessorLabel == null) {
        			//informer qu'on atteint ce Node la 1ère fois
        			notifyNodeReached(IteArc.getDestination());
        			SuccessorLabel = new Label(successor);
        			tabLabel[SuccessorLabel.GetNode().getId()] = SuccessorLabel;
        			this.NbrSommetsVisites ++;
        		}
        		// s'il n'est pas marqué
        		if (!SuccessorLabel.GetMarque()) {
        			if (SuccessorLabel.GetCost() > CurrentLabel.GetCost() + data.getCost(IteArc)) {
        				SuccessorLabel.SetCost(CurrentLabel.GetCost() + (float)data.getCost(IteArc));
        				//si le label est déjà dans le tas -> remove
        				if (SuccessorLabel.GetInTas()) {
        					tas.remove(SuccessorLabel);
        				}
        				//sinon on y ajoute label dans 
        				else 
        				{
        					SuccessorLabel.SetInTas();
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
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graphe, arcs));
        }
        return solution;
    }
    // Retourner le nbr de sommets visités
    public int getNbSommetsVisites() {
    	return this.NbrSommetsVisites;
    }
    
    protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
}