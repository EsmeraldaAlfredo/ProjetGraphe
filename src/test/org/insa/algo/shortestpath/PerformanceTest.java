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
		FileWriter outCsv;
		
		if(type=="temps") {
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			out = new BufferedWriter(new FileWriter(map+"_temps.txt"));
			outCsv = new FileWriter(map+"_temps.csv");
		}
		else {
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			out = new BufferedWriter(new FileWriter(map+"_distance.txt"));
			outCsv = new FileWriter(map+"_distance.csv");
		}
		
		
		String mapName = "/Users/HaHa/Documents/3è année/Graphes/Maps/"+map+".mapgr";
		// Create a graph reader
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new FileInputStream(mapName))
				);
		// Read the graph
		Graph graph = reader.read();
		//type Evaluation
		out.write("Map  Origin  Destination  Cout  CPUtimeDijkstra  NodesReachedD  CPUtimeAStar  NodesReachedA\n");
		outCsv.append("Map,Origin,Destination,Cout,CPUtimeDijkstra,NodesReachedD,CPUtimeAStar,NodesReachedA");
		outCsv.append("\n");
	
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
            ShortestPathSolution solution = algoA.run();
            double costSolution = 0.0;
            if (solution.getPath() == null) {
            	costSolution = 0.0;
            }
            else {
                	costSolution = solution.getPath().getLength();
            }
            
            
            out.write(ind+". "+map+"  "+a+"  "+b+"  "
            +costSolution + "  "
            +durationD+"ms  "+algoD.getNbReachedNodes()+"  "
            +durationA+"ms  "+algoA.getNbReachedNodes()+"\n");
            //Write to .csv file
            outCsv.append(map);
            outCsv.append(",");
            outCsv.append(String.valueOf(a));
            outCsv.append(",");
            outCsv.append(String.valueOf(b));
            outCsv.append(",");
            outCsv.append(String.valueOf(costSolution));
            outCsv.append(",");
            outCsv.append(String.valueOf(durationD));
            outCsv.append(",");
            outCsv.append(String.valueOf(algoD.getNbReachedNodes()));
            outCsv.append(",");
            outCsv.append(String.valueOf(durationA));
            outCsv.append(",");
            outCsv.append(String.valueOf(algoA.getNbReachedNodes()));
            outCsv.append("\n");
		}
		out.close();
		outCsv.flush();
		outCsv.close();
	}
	@Test
	public void Tester() throws IOException {
		Test("vietnam","temps");
		Test("belgium","temps");
		Test("carre-dense","temps");
		//Test("vietnam","distance");
		//Test("belgium","distance");
		//Test("carre-dense","distance");
		
	}
	
	
}