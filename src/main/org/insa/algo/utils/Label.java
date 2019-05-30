package org.insa.algo.utils;

import org.insa.graph.Node;
//import org.insa.graph.Arc;
//import org.insa.algo.utils.*;


public class Label implements Comparable<Label> {
	private Node current_node; 
	private boolean marked; // true if node is marked
	protected float cost;
	private Node father;
	private boolean InTas; // true if node is in the tas

	
	public Label(Node node) {
		this.current_node = node;
		this.marked = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.father = null;
		this.InTas = false;
	}
	
	public float getCost() {
		return this.cost;
	}
	
	public Node getNode() {
		return this.current_node;
	}
	public boolean getMarked() {
		return this.marked;
	}
	
	public Node getFather() {
		return this.father;
	}
	
	public boolean getInTas() {
		return this.InTas;
	}
	
	public void setMarked(){
		this.marked = true;
	}
	
	public void setInTas() {
		this.InTas = true;
	}
	
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public void setFather(Node noeud) {
		this.father = noeud;
	}

	public float getTotalCost() {
		return this.cost;
	}

	@Override
	public int compareTo(Label o) {
		int resultat;
		if (this.getTotalCost() < o.getTotalCost() ) {
			resultat = -1;
		}
		else if (this.getTotalCost() == o.getTotalCost() ) {
			resultat = 0;
		}
		else {
			resultat = 1;
		}
		return resultat;
	}
	
	
	
	
	
}