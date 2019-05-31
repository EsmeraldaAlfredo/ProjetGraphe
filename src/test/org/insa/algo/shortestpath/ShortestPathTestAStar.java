package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.*;
import org.insa.graph.*;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class ShortestPathTestAStar{
	
	protected static ArcInspector arcInspector;
    
    // Small graph use for tests
    private static Graph graph;
	
	public void testScenarioWithOracle (String mapName, Mode mode) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        System.out.println("Test de Validité  de A* avec Oracle ");
        int origin =  35161;
        int destination = 38052;
        if(origin == destination) {
        	System.out.println("origin: "+ origin);
    		System.out.println("destination : " + destination);
        	System.out.println("cout = 0");
        	
        }
        else {
        	
        	if (mode == Mode.TIME) { //Evaluation with time
			//Fastest path, all roads allowed
			  System.out.println("Mode: Temps");
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    		ShortestPathData data = new ShortestPathData(graph, graph.get(origin), graph.get(destination), arcInspector);
    		
    	
    		AStarAlgorithm algoA = new AStarAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		System.out.println("origin: "+ origin);
    		System.out.println("destination : " + destination);
    		//Tester si est la solution la plus optimal
    		if (algoA.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
    			double  coutA = algoB.doRun().getPath().getMinimumTravelTime();
			    double coutB = algoA.doRun().getPath().getMinimumTravelTime();
	    		
    			assertEquals( (int)  coutA, (int) coutB);
	    		
			    System.out.println("cout avec AStar "+ coutA);
			    System.out.println("cout avec Belman " + coutB);
    		}
    		else {
    			System.out.println("pas de chemin trouvé");
    			}
    		
        	}
		
		if (mode == Mode.LENGTH) { //Evaluation with distance
			//Shortest path, all roads allowed
			System.out.println("Mode: Distance");
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		//DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
			AStarAlgorithm algoA = new AStarAlgorithm (data);
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		System.out.println("origin: "+ origin);
    		System.out.println("destination : " + destination);
    		
    		//Tester si est la solution la plus optimal
    		if (algoA.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
    			double  coutA = algoB.doRun().getPath().getLength();
			    double coutB = algoA.doRun().getPath().getLength();
	    		
    			assertEquals( (int)  coutA, (int) coutB);
	    		
			    System.out.println("cout avec AStar "+ coutA);
			    System.out.println("cout avec Belman " + coutB);
    		}
    		else {
    			System.out.println("pas de chemin trouvé");
    		}
		  }
       }
	}
        
	@Test
	public void testAstarNewCeledoniaWithOracle() throws Exception {
		// test de validité de AStar avec oracle sur une carte 
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/new-caledonia.mapgr";
		System.out.println("Carte de New-caledonia");
		ShortestPathTestAStar test = new ShortestPathTestAStar();
		
		//FastestPath
		test.testScenarioWithOracle(mapName,Mode.TIME);
		System.out.println();
		
		//ShortestPath
		System.out.println("Teste de validité avec oracle sur une carte");
		test.testScenarioWithOracle(mapName,Mode.LENGTH);
		System.out.println();
		
		
	}
	/*
	@Test
	public void testAstarMidiPyreneesWithOracle() throws Exception {
		// test de validité de AStar avec oracle sur une carte 
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/midi-pyrenees.mapgr";
		System.out.println("Carte de Midi-Pyrenees");
		ShortestPathTestAStar test = new ShortestPathTestAStar();
		
		//FastestPath
		test.testScenarioWithOracle(mapName,Mode.TIME);
		System.out.println();
		
		//ShortestPath
		System.out.println("Teste de validité avec oracle sur une carte");
		test.testScenarioWithOracle(mapName,Mode.LENGTH);
		System.out.println();
		
		
	}
	@Test
	public void testAstarBelgiqueWithOracle() throws Exception {
		// test de validité de AStar avec oracle sur une carte 
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/belgium.mapgr";
		System.out.println("Carte de Belgique");
		ShortestPathTestAStar test = new ShortestPathTestAStar();
		
		//FastestPath
		test.testScenarioWithOracle(mapName,Mode.TIME);
		System.out.println();
		
		//ShortestPath
		test.testScenarioWithOracle(mapName,Mode.LENGTH);
		System.out.println();
		
		
	}
 
	@Test
	public void testAstarParisWithOracle() throws Exception {
		// test de validité de AStar avec oracle sur une carte 
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/paris.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/paris.mapgr";
		System.out.println("Carte de Paris");
		ShortestPathTestAStar test = new ShortestPathTestAStar();
		
		//FastestPath
		test.testScenarioWithOracle(mapName,Mode.TIME);
		System.out.println();
		
		//ShortestPath
		System.out.println("Teste de validité avec oracle sur une carte");
		test.testScenarioWithOracle(mapName,Mode.LENGTH);
		System.out.println();
		
		
	}
	*/

	@Test
	public void testCheminInexistantNewCaledonia() throws IOException {
		System.out.println("Test de Validité  de A* sans Oracle ");
		System.out.println("CAS DE CHEMIN INEXISTANT NEW-CALEDONIA");
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/new-caledonia.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/new-caledonia.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[23200] and destination = node[24950]
		ShortestPathData data = new ShortestPathData(graph, graph.get(23200), graph.get(24950), arcInspector);		
		AStarAlgorithm algoA = new AStarAlgorithm(data);	
		assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
		System.out.println("chemin inexistant");
	}
	
	@Test
	public void testCheminInexistantHawaii() throws IOException {
		System.out.println("Test de Validité  de A* sans Oracle ");
		System.out.println("CAS DE CHEMIN INEXISTANT HAWAII");
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/hawaii.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/hawaii.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[10] and destination = node[150]
		ShortestPathData data = new ShortestPathData(graph, graph.get(10), graph.get(150), arcInspector);	
		AStarAlgorithm algoA = new AStarAlgorithm(data);	
		assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
		System.out.println("chemin inexistant");
		
	}
	
	
	@Test
	public void testCheminDeLongueurNul() throws IOException {
		System.out.println("Test de Validité  de A* sans Oracle ");
		System.out.println("CAS DE CHEMIN DE LONGUER NUL CUBA ");
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/cuba.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/cuba.mapgr";
		// Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
              new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data = new ShortestPathData(graph, graph.get(2302), graph.get(2302), arcInspector);		
      AStarAlgorithm algoA = new AStarAlgorithm(data);	
      assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
      System.out.println("chemin de cout 0");
	}
	/*
	@Test
	public void testSousCheminParis() throws IOException {
		System.out.println("Test de Validité  de A* sans Oracle ");
		System.out.println("CAS DE SOUS CHEMIN PARIS");
		//Test en distance
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/paris.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/paris.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data1 = new ShortestPathData(graph, graph.get(18982), graph.get(21693), arcInspector);
		ShortestPathData data2 = new ShortestPathData(graph, graph.get(18982), graph.get(4372), arcInspector);
		ShortestPathData data3 = new ShortestPathData(graph, graph.get(4372), graph.get(21693), arcInspector);
		AStarAlgorithm algo1 = new AStarAlgorithm(data1);	
		AStarAlgorithm algo2 = new AStarAlgorithm(data2);	
		AStarAlgorithm algo3 = new AStarAlgorithm(data3);	
		int AC = (int)algo1.doRun().getPath().getLength();
		int AB = (int)algo2.doRun().getPath().getLength();
		int BC = (int)algo3.doRun().getPath().getLength();
		assertEquals(AC, AB + BC);
		System.out.println("il existe un souschemin ");
	}
	
*/

	
	@Test
	public void testInegaliteTriangulaire() throws IOException {
		System.out.println("Test de Validité  de A* sans Oracle ");
		System.out.println("CAS DE INEGALITÉ TRIANGULAIRE MIDI-PYRENEES");
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr;
		String mapName = "/home/esmeralda/ProjetGraphe/extras/midi-pyrenees.mapgr";
		  // Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
    		  new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data1 = new ShortestPathData(graph, graph.get(309194), graph.get(174867), arcInspector);
      ShortestPathData data2 = new ShortestPathData(graph, graph.get(174867), graph.get(74060), arcInspector);
      ShortestPathData data3 = new ShortestPathData(graph, graph.get(74060), graph.get(309194), arcInspector);
      AStarAlgorithm algo1 = new AStarAlgorithm(data1);	
      AStarAlgorithm algo2 = new AStarAlgorithm(data2);	
      AStarAlgorithm algo3 = new AStarAlgorithm(data3);	
      int AC = (int)algo1.doRun().getPath().getLength();
      int AB = (int)algo2.doRun().getPath().getLength();
      int BC = (int)algo3.doRun().getPath().getLength();
      assertTrue((AB + BC > AC) && (AB - BC < AC));
  	  System.out.println("chemin existant");
	}
}