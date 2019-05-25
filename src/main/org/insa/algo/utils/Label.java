package org.insa.algo.utils;

import org.insa.graph.Arc;


public class Label implements Comparable<Label>{
	protected int id;
	protected double cost;
	protected Arc father;
	protected boolean mark;
	
	public Label(int id, double cost, Arc father, boolean mark) {
		this.id = id;
		this.cost = cost;
		this.father = father;
		this.mark = mark;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Arc getFather() {
		return this.father;
	}
	
	public void setFather(Arc father) {
		this.father = father;
	}
	
	public boolean getMark() {
		return mark;
	}
	
	public void setMark(boolean mark) {
		this.mark=mark;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getCost () {
		return this.cost;
	}
	
	public double getCostEstimate() {
		return 0;
	}

	@Override
	public int compareTo(Label o) {
		int resultat;
		if (this.getCost() < o.getCost() ) {
			resultat = -1;
		}
		else if (this.getCost() == o.getCost() ) {
			resultat = 0;
		}
		else {
			resultat = 1;
		}
		return resultat;
	}
	
	public String toString() {
		return "id = " + this.id + ", cost = " + this.cost + ", father =  " + this.father + ", mark = "+ this.mark; 
	}
	
	
	
	
	
}