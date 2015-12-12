import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

import structures.graph.Neighbor;
import structures.graph.UndirGraph;


public class FriendshipGraph<T> extends UndirGraph<T>{
	
	UndirGraph<Friend> graph;

	public FriendshipGraph(UndirGraph<Friend> graph) {
		this.graph = graph;
	}

	public FriendshipGraph (String graphFile) throws FileNotFoundException {
		graph = new UndirGraph<Friend>();
		ArrayList<Friend> friends = new ArrayList<Friend>();
		Scanner scfile = null;

		try {
			scfile = new Scanner(new File(graphFile));
		} 
		
		catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}

		int NumOfVert = Integer.parseInt(scfile.nextLine());

		for (int i = 0; i < NumOfVert; i++) {
			StringTokenizer txt = new StringTokenizer(scfile.nextLine(),"|");
			String name = txt.nextToken(), college;
			
			if (txt.nextToken().equals("y"))
				college = txt.nextToken();
			else 
			    college = "";
				
			graph.addVertex(new Friend(name, college));
			friends.add(new Friend(name, college));
		}

		while (scfile.hasNext()) {
			String line = scfile.next(), nameOne = line.substring(0, line.indexOf('|')), nameTwo = line.substring(line.indexOf('|') + 1);

			int friendOne = 0, friendTwo = 0;

			for (int i = 0; i < friends.size(); i++) {
				if (friends.get(i).name.equals(nameOne)) 
					friendOne = i;
				else if (friends.get(i).name.equals(nameTwo)) 
					friendTwo = i;
			}

			graph.addEdge(friendOne, new Neighbor(friendTwo));
		}

		scfile.close();
	}
	
	public FriendshipGraph<Friend> getStudentsAtTheCollege(String college) {

		UndirGraph<Friend> collegeSubgraph = new UndirGraph<Friend>();
		int vCount = this.graph.numberOfVertices();

		for (int i = 0; i < vCount; i++) {
			if (this.graph.vertexInfoOf(i).school.compareTo(college) == 0) 
				collegeSubgraph.addVertex(this.graph.vertexInfoOf(i));
		}

		vCount = collegeSubgraph.numberOfVertices();

		for (int i = 0; i < vCount; i++) {
			int firstRootRef = this.graph.vertexNumberOf(collegeSubgraph.vertexInfoOf(i));

			for (int j = 0; j < vCount; j++) {
				int secondRootRef = this.graph.vertexNumberOf(collegeSubgraph.vertexInfoOf(j));

				if (this.graph.containsEdge(firstRootRef, new Neighbor(secondRootRef)))
					collegeSubgraph.addEdge(i, new Neighbor(j));
			}
		}
		return new FriendshipGraph<Friend>(collegeSubgraph);
	}

	public String getIntroductionChain(String firstPerson, String firstCollege, String secondPerson, String secondSchool) {
		Friend firstFriend = new Friend(firstPerson, firstCollege);
		Friend secondFriend = new Friend(secondPerson, secondSchool);
		String introductionChain = "";

		if (!this.graph.containsVertex(firstFriend) || !this.graph.containsVertex(secondFriend)) {
			introductionChain = "Either one or both of these students do not seem to exist!";
		} else {
			ArrayList<Friend> path = this.graph.getShortestPath(
					this.graph.vertexNumberOf(firstFriend), 
					this.graph.vertexNumberOf(secondFriend));

			if (path != null) {
				if (path.size() == 0)
					introductionChain += "These people cannot meet at the moment.";
				 else for (int i = 0; i < path.size(); i++) 
					introductionChain += path.get(i).name + (i < path.size() - 1 ? "<-->": "");	
			}
		}

		return introductionChain;
	}
	
	public String getCliques(String college) {

		UndirGraph<Friend> CollegeRestricted = this.getStudentsAtTheCollege(college).graph;
		ArrayList<ArrayList<Friend>> cliques = CollegeRestricted.getCliques();
		String retstr = "";

		for (int i = 0; i < cliques.size(); i++) {
			retstr += "clique " + (i + 1) + ": \n\n" + cliques.get(i).size() + "\n";
			for (int j = 0; j < cliques.get(i).size(); j++) 
				retstr += cliques.get(i).get(j).toString() + "\n";
		
			HashSet<String> s = new HashSet<String>();

			for (int j = 0; j < cliques.get(i).size(); j++) {
				for (int k = 0; k < cliques.get(i).size(); k++) {

					if (this.graph.containsEdge(this.graph.vertexNumberOf(cliques.get(i).get(j)), new Neighbor(this.graph.vertexNumberOf(cliques.get(i).get(k))))) {

						String nameOne = cliques.get(i).get(j).name, nameTwo = cliques.get(i).get(k).name, nameCom = "";

						if (nameOne.compareTo(nameTwo) < 0) 
							nameCom = nameOne + "|" + nameTwo;
						 else if (nameOne.compareTo(nameTwo) > 0) 
							nameCom = nameTwo + "|" + nameOne;
						
						s.add(nameCom);
					}
				}
			}

			for (String adj : s) 
				retstr += adj + "\n";
		}
		return retstr;
	}

	public String getConnectors() {
		ArrayList<String> r= new ArrayList<String>();

		for(int i=0; i < graph.adjlists.size(); i++){
			String result= graph.findConnectors(i);
			if (result != "None") r.add(result);
		}

		String result= "";
		for (int l=0; l< r.size(); l++)
			result= result+ r.get(l) + "\n";
		
		return result;
	}
}
