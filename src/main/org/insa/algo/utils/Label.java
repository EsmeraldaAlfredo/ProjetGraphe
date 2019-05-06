package org.insa.algo.utils;

import org.insa.graph.Node;
//import org.insa.graph.Arc;
import org.insa.algo.utils.*;


public class Label implements Comparable<Label> {
	private Node sommet_courant; 
	private boolean marque; // vrai si le noeud est marqué
	private float cout;
	private Node pere;
	//private Arc arc;
	private boolean InTas; // vrai si 
	//private BinaryHeap<Node> tas;
	
	public Label(Node noeud) {
		this.sommet_courant = noeud;
		this.marque = false;
		this.cout = Float.POSITIVE_INFINITY;
		this.pere = null;
		//this.arc = null;
		this.InTas = false;
	}
	
	public float GetCost() {
		return this.cout;
	}
	
	public Node GetNode() {
		return this.sommet_courant;
	}
	public boolean GetMarque() {
		return this.marque;
	}
	
	public Node GetPere() {
		return this.pere;
	}
	
	/*public Arc GetArc() {
		return this.arc;
	}
	*/
	
	public boolean GetInTas() {
		return this.InTas;
	}
	
	public void SetMarque(){
		this.marque = true;
	}
	
	public void SetInTas() {
		this.InTas = true;
	}
	
	public void SetCost(float cost) {
		this.cout = cost;
	}
	
	public void SetPere(Node noeud) {
		this.pere = noeud;
	}
	
	public int Compare (Label autre) {
		int res;
		if (this.cout > autre.cout) {
			res = 1;
		}
		else if (this.cout == autre.cout) {
			res = 0;
		}
		else {
			res = -1;
		}
		return res;
	}
	

	@Override
	public int compareTo(Label o) {
		return Double.compare(this.cout, o.cout);
	}
	
	
	
	
	
}