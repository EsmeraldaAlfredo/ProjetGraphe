package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.*;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.*;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShortestPathTestDijkstra {
	protected static ArcInspector arcInspector;
	
	private static Graph graph;
	
	private static Node[] nodes;
	
	@SuppressWarnings("unused")
	private static Arc x1x2, x1x3, x2x4, x2x5, x2x6, x3x1, x3x2, x3x6, x5x3, x5x4, x5x6, x6x5;
	
	@BeforeClass
	public static void initAll() throws IOException {
		// 10 meters per seconds
		RoadInformation speed = new RoadInformation (RoadType.MOTORWAY, null, true, 36,"");
		// Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
        
        //Add arcs
        x1x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed, null);
        x1x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed, null);
        x2x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed, null);
        x2x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed, null);
        x2x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed, null);
        x3x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed, null);
        x3x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed, null);
        x3x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed, null);
        x5x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed, null);
        x5x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed, null);
        x5x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed, null);
        x6x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed, null);
        
        graph = new Graph("ID","",Arrays.asList(nodes),null);
	}
	/*
	@Test
	public void testShortestPath() {
        	//Shortest path, all roads allowed
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		System.out.println("Shortest path, all roads allowed");
		for (int i =0; i<6;i++) {
			
			System.out.println("x"+(nodes[i].getId()+1) +":");
			
			for (int j=0;j<6;j++) {
				ShortestPathData data1 = new ShortestPathData(graph, nodes[i], nodes[j], arcInspector);	
	    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data1);	
	    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data1);
	    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {
	    			assertEquals((int)algoD.doRun().getPath().getLength(), (int)algoB.doRun().getPath().getLength());
	    			
	    			List<Arc> arc = algoD.doRun().getPath().getArcs();
					Node originOfLastArc = arc.get(arc.size()-1).getOrigin();
					
					System.out.print("("+ algoD.doRun().getPath().getLength()+ ", x" +(originOfLastArc.getId()+1) + ")");		
	    		}else {
	    			System.out.print("(infini) ");
	    		}
			}
			System.out.println();
        }
		System.out.println();
	}
	/*
	@Test
	public void testFastestPath() {
    	//Fastest path, all roads allowed
    	arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    	System.out.println("Fastest path, all roads allowed");
    	for (int i=0;i<6;i++) {
    		System.out.println("x"+(nodes[i].getId()+1) +":");
			
    		for (int j=0;j<6;j++) {
	    		ShortestPathData data1 = new ShortestPathData(graph, nodes[i], nodes[j], arcInspector);	
	    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data1);	
	    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data1);
	    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
		    		assertEquals((int)algoD.run().getPath().getMinimumTravelTime(), (int)algoB.run().getPath().getMinimumTravelTime());
		    		
	    		List<Arc> arc = algoD.doRun().getPath().getArcs();
				Node originOfLastArc = arc.get(arc.size()-1).getOrigin();
				
				System.out.print("("+ algoD.doRun().getPath().getLength()+ ", x" +(originOfLastArc.getId()+1) + ")");		
    		}else {
    			System.out.print("(infini) ");
    		}
		}
		System.out.println();
    		}	
    	System.out.println();
	}
	

	/*
	public void testScenarioWithOracle (String mapName, Mode mode) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        System.out.println("Test de validité de Dijkstra avec Oracle ");
        int origin = 35161;
        int destination =  38052;
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
    		
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		System.out.println("origin: "+ origin);
    		System.out.println("destination : " + destination);
    		//Tester si est la solution la plus optimal
    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
    			double  coutD = algoD.doRun().getPath().getMinimumTravelTime();
			    double coutB = algoB.doRun().getPath().getMinimumTravelTime();
	    		
    			assertEquals( (int)  coutD, (int) coutB);
	    		
			    System.out.println("cout en temps avec AStar "+ coutD);
			    System.out.println("cout en temps avec Belman " + coutB);
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
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		System.out.println("origin: "+ origin);
    		System.out.println("destination : " + destination);
    		
    		//Tester si est la solution la plus optimal
    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
    			double  coutD = algoD.doRun().getPath().getLength();
			    double coutB = algoB.doRun().getPath().getLength();
	    		
    			assertEquals( (int)  coutD, (int) coutB);
	    		
			    System.out.println("cout en distance avec Dijkstra "+ coutD);
			    System.out.println("cout en distance avec Belman " + coutB);
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
		ShortestPathTestDijkstra test = new ShortestPathTestDijkstra ();
		
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
		ShortestPathTestDijkstra test = new ShortestPathTestDijkstra ();
		
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
		ShortestPathTestDijkstra test = new ShortestPathTestDijkstra ();
		
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
		System.out.println(" Carte de Paris");
		ShortestPathTestDijkstra test = new ShortestPathTestDijkstra ();
		
		//FastestPath
		test.testScenarioWithOracle(mapName,Mode.TIME);
		System.out.println();
		
		//ShortestPath
		test.testScenarioWithOracle(mapName,Mode.LENGTH);
		System.out.println();
		
		
	}
	*/
  
	@Test
	public void testCheminInexistantNewCaledonia() throws IOException {
		System.out.println("Test de Validité  de Dijkstra sans Oracle ");
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
		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
		System.out.println("chemin inexistant");
	}
	
	@Test
	public void testCheminInexistantHawaii() throws IOException {
		System.out.println("Test de Validité  de Dijkstra sans Oracle ");
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
		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
		System.out.println("chemin inexistant");
		
	}
	
	
	@Test
	public void testCheminDeLongueurNul() throws IOException {
		System.out.println("Test de Validité  de Dijkstra sans Oracle ");
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
      DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
      assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
      System.out.println("chemin de cout 0");
	}
	/*
	@Test
	public void testSousCheminParis() throws IOException {
		System.out.println("Test de Validité  de Dijkstra  sans Oracle ");
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
		DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
		DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
		DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);	
		int AC = (int)algo1.doRun().getPath().getLength();
		int AB = (int)algo2.doRun().getPath().getLength();
		int BC = (int)algo3.doRun().getPath().getLength();
		assertEquals(AC, (AB + BC));
		System.out.println("il existe un souschemin ");
	}
	*/

	
	@Test
	public void testInegaliteTriangulaire() throws IOException {
		System.out.println("Test de Validité  de Dijkstra  sans Oracle ");
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
      DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
      DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
      DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
      int AC = (int)algo1.doRun().getPath().getLength();
      int AB = (int)algo2.doRun().getPath().getLength();
      int BC = (int)algo3.doRun().getPath().getLength();
      assertTrue((AB + BC > AC) && (AB - BC < AC));
  	  System.out.println("Chemin valide");
	}

}