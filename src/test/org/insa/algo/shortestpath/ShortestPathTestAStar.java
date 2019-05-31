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
        
		if (mode == Mode.TIME) { //Evaluation with time
			//Fastest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    		ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		//DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);
    		AStarAlgorithm algoA = new AStarAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		if (algoA.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
	    		assertEquals((int)algoA.doRun().getPath().getMinimumTravelTime(), (int)algoB.doRun().getPath().getMinimumTravelTime());
    		}
		}
		
		if (mode == Mode.LENGTH) { //Evaluation with distance
			//Shortest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		//DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
			AStarAlgorithm algoA = new AStarAlgorithm (data);
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		if (algoA.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
	    		assertEquals((int)algoA.doRun().getPath().getLength(), (int)algoB.doRun().getPath().getLength());
    		}
		}
		
	}
	
	
	@Test
	public void testFastestPathToulouseWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/toulouse.mapgr";
		testScenarioWithOracle(mapName,Mode.TIME);
	}
	
	@Test
	public void testShortestPathToulouseWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr;
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/toulouse.mapgr";
		testScenarioWithOracle(mapName,Mode.LENGTH);
	}	
	
	
	@Test
	public void testCheminInexistantNewCaledonia() throws IOException {
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/new-caledonia.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[23200] and destination = node[24950]
		ShortestPathData data = new ShortestPathData(graph, graph.get(23200), graph.get(24950), arcInspector);	
		//DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		AStarAlgorithm algoA = new AStarAlgorithm(data);	
		assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
	}
	@Test
	public void testCheminInexistantHawaii() throws IOException {
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/hawaii.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[10] and destination = node[150]
		ShortestPathData data = new ShortestPathData(graph, graph.get(10), graph.get(150), arcInspector);	
		//DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		AStarAlgorithm algoA = new AStarAlgorithm(data);	
		assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testCheminDeLongueurNul() throws IOException {
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/bordeaux.mapgr";
		// Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
              new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data = new ShortestPathData(graph, graph.get(2302), graph.get(2302), arcInspector);	
      //DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
      AStarAlgorithm algoA = new AStarAlgorithm(data);	
      assertEquals(algoA.doRun().getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testSousCheminToulouse() throws IOException {
		//Test en distance
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/toulouse.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data1 = new ShortestPathData(graph, graph.get(18982), graph.get(21693), arcInspector);
		ShortestPathData data2 = new ShortestPathData(graph, graph.get(18982), graph.get(4372), arcInspector);
		ShortestPathData data3 = new ShortestPathData(graph, graph.get(4372), graph.get(21693), arcInspector);
		//DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
		//DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
		//DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
		AStarAlgorithm algo1 = new AStarAlgorithm(data1);	
		AStarAlgorithm algo2 = new AStarAlgorithm(data2);	
		AStarAlgorithm algo3 = new AStarAlgorithm(data3);	
		int AC = (int)algo1.doRun().getPath().getLength();
		int AB = (int)algo2.doRun().getPath().getLength();
		int BC = (int)algo3.doRun().getPath().getLength();
		assertEquals(AC, AB + BC);
	}

	
	@Test
	public void testInegaliteTriangulaire() throws IOException {
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/midi-pyrenees.mapgr";
		  // Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
    		  new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data1 = new ShortestPathData(graph, graph.get(309194), graph.get(174867), arcInspector);
      ShortestPathData data2 = new ShortestPathData(graph, graph.get(174867), graph.get(74060), arcInspector);
      ShortestPathData data3 = new ShortestPathData(graph, graph.get(74060), graph.get(309194), arcInspector);
      //DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
      //DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
      //DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
      AStarAlgorithm algo1 = new AStarAlgorithm(data1);	
      AStarAlgorithm algo2 = new AStarAlgorithm(data2);	
      AStarAlgorithm algo3 = new AStarAlgorithm(data3);	
      int AC = (int)algo1.doRun().getPath().getLength();
      int AB = (int)algo2.doRun().getPath().getLength();
      int BC = (int)algo3.doRun().getPath().getLength();
      assertTrue((AB + BC > AC) && (AB - BC < AC));
	}
}