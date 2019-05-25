package org.insa.algo.shortestpath;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.ArcInspector;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class PerformanceTest{
	
	protected static ArcInspector arcInspector;
	
	public void Test (String map, String type) throws IOException {
		
		ArcInspector arcInspector;
		BufferedWriter out;
		if(type=="temps") {
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			out = new BufferedWriter(new FileWriter(map+"_temps.txt"));
			
		}
		else {
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			out = new BufferedWriter(new FileWriter(map+"_distance.txt"));
		}
		
		
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/"+map+".mapgr";
		// Create a graph reader
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new FileInputStream(mapName))
				);
		// Read the graph
		Graph graph = reader.read();
		//type Evaluation
		out.write("Map  Origin  Destination  CPUtimeDijkstra  NodesReachedD  CPUtimeAStar  NodesReachedA\n");

		for (int i =0;i<100;i++) {
			int ind = i+1;
			int a = (int)(Math.random() * graph.size()); 
        	int b = (int)(Math.random() * graph.size()); 
            ShortestPathData data = new ShortestPathData(graph, graph.get(a), graph.get(b), arcInspector);	
            DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);
            AStarAlgorithm algoA = new AStarAlgorithm(data);
        	long beginD = System.currentTimeMillis();
            algoD.doRun();
            long durationD = System.currentTimeMillis() - beginD;
            long beginA = System.currentTimeMillis();
            algoA.doRun();
            long durationA = System.currentTimeMillis() - beginA;
            out.write(ind+". "+map+"  "+a+"  "+b+"  "
            +durationD+"ms  "+algoD.getNbSommetsVisites()+"  "
            +durationA+"ms  "+algoA.getNbSommetsVisites()+"\n");
		}
		out.close();
	}
	@Test
	public void Tester() throws IOException {
		//Test("vietnam","temps");
		//Test("japan","temps");
		//Test("belgium","temps");
		Test("carre-dense","temps");
		//Test("vietnam","distance");
		//Test("japan","distance");
		//Test("belgium","distance");
		Test("carre-dense","distance");
		
	}
	
	
}