package org.insa.performance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.*;

import org.insa.algo.*;
import org.insa.graph.*;

public class PerformanceTest{
	
	private ArrayList <Resultat> ResPerformanceList;
	
	public PerformanceTest(){
		this.ResPerformanceList = new ArrayList <Resultat>();
	}
	
	public void Tester(String NameRead, String NameWrite, int type) {
		Read read = new Read(NameRead);
		
		String map = read.getMapName();
		
		Iterator<Integer> IteOrigine = read.getListOrigins().iterator();
		Iterator<Integer> IteDest = read.getListDestinations().iterator();
		
		while(IteOrigine.hasNext()) {
			Resultat Res = new Resultat(".../Maps/haute-garonne.mapgr", type, IteOrigine.next(),IteDest.next() );
			this.ResPerformanceList.add(Res);
		}
		
		Write write = new Write(NameWrite, map, this.ResPerformanceList);
		
		
		
		
		
	}
	
}