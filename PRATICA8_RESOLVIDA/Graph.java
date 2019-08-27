/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;
/*
@author Bárbara Schlottfeldt Maia e Arthur Diniz Torquato Flor Fernandes
*/
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {

    public int nVertices;
    public int nEdges;
    private ArrayList<LinkedList<Edge>> adjacentList;
    private int[][] cordinades;
    private GraphicsUtils graphics;

    public void addEdge(int u, int v, double weight) {
        nEdges++;
        LinkedList<Edge> edges = null;
        Edge aresta1, aresta2;
        aresta1 = new Edge(u, v, weight);
        aresta2 = new Edge(v, u, weight);

        adjacentList.get(u).add(aresta1);
        adjacentList.get(v).add(aresta2);
    }

    public void removeEdge(Edge edge) {
        nEdges--;
        int u, v;
        u=edge.getU();
        v=edge.getV();
        Edge aresta1, aresta2;
        aresta1 = edge;
        aresta2 = new Edge(v,u,edge.getWeight());
        adjacentList.get(u).remove(aresta1);
        adjacentList.get(v).remove(aresta2);
        
    }

    public LinkedList<Edge> getNeighbours(int index) {
        
        return adjacentList.get(index);
        
        
    }

    public boolean isEulerian() {
        boolean flag;
        flag=true;
        for(int i=0; i<adjacentList.size();i++){
            if(adjacentList.get(i).size()%2!=0){
                flag=false;
            }
        }
        return flag;
    }

    /**
     * ************************************************************************************
     */
    public boolean existEdges() {
        if (nEdges == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Graph(GraphicsUtils otherGraphics, final String mapFileName, String title) {
        this.graphics = new GraphicsUtils(otherGraphics, mapFileName, title);
    }

    public Graph(final String filePath, final String mapFileName, String title) {
        this.graphics = new GraphicsUtils(mapFileName, title);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {
            br = new BufferedReader(new FileReader(filePath));
            this.nVertices = Integer.parseInt(br.readLine());
            cordinades = new int[nVertices][2];

            adjacentList = new ArrayList<LinkedList<Edge>>();
            for (int i = 0; i < nVertices; i++) {
                adjacentList.add(new LinkedList<Edge>());
            }

            while ((line = br.readLine()) != null) {

                String[] edges;
                String[] e;
                String[] read = line.split(cvsSplitBy);

                cordinades[Integer.parseInt(read[0])][0] = Integer.parseInt(read[1]);
                cordinades[Integer.parseInt(read[0])][1] = Integer.parseInt(read[2]);

                if (read.length == 3) {
                    break;
                }

                edges = read[3].split("%");
                for (String edge : edges) {
                    e = edge.split(":");
                    addEdge(Integer.parseInt(read[0]), Integer.parseInt(e[0]),
                            Double.parseDouble(e[1]));
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        for (int i = 0; i < nVertices; i++) {
            graphics.addNode(cordinades[i][0], cordinades[i][1]);
        }

        for (LinkedList<Edge> verticeNeightbors : adjacentList) {
            for (Edge edge : verticeNeightbors) {
                int u = edge.getU();
                int v = edge.getV();
                graphics.addSegment(u, v, cordinades[u][0], cordinades[u][1],
                        cordinades[v][0], cordinades[v][1]);
            }
        }
    }

    public Graph(int V) {
        this.nVertices = V;
        nEdges = 0;

        adjacentList = new ArrayList<LinkedList<Edge>>();
        for (int i = 0; i < V; i++) {
            adjacentList.add(new LinkedList<Edge>());
        }
    }

    public void markNode(int vertice, Color color) {
        this.graphics.setNodeColor(vertice, color);
    }

    public ArrayList<Integer> getOddVertices() {
        ArrayList<Integer> vertices = new ArrayList<Integer>();
        if (!isEulerian()) {
            int i = 0;
            for (LinkedList<Edge> edges : adjacentList) {
                if (edges.size() % 2 != 0) {
                    vertices.add(i);
                }
                i++;
            }
        }
        return vertices;
    }

    public void turnEulerian() {
        ArrayList<Integer> oddVertices = this.getOddVertices();
        Path path = new Path();
        path.setGraph(this);

        ArrayList<Edge> createdEdges = new ArrayList<Edge>();
        int nOddVertices = oddVertices.size();
        int nAdded = 0;
        int[] added = new int[nOddVertices];
        Arrays.fill(added, 0);

        for (int i = 0; i < nOddVertices; i++) {
            if (added[i] == 1) {
                continue;
            }
            double minDistance = Double.MAX_VALUE;
            int minV = -1;

            for (int j = 0; j < nOddVertices; j++) {
                if (added[j] == 1 || i == j) {
                    continue;
                }
                double aux = path.findDistance(oddVertices.get(i), oddVertices.get(j));
                if (aux < minDistance) {
                    minDistance = aux;
                    minV = j;
                }
            }

            if (minV != -1) {
                added[i] = 1;
                added[minV] = 1;
                int u = oddVertices.get(i);
                int v = oddVertices.get(minV);
                addEdge(u, v, minDistance);
                graphics.addSegment(u, v, cordinades[u][0], cordinades[u][1],
                        cordinades[v][0], cordinades[v][1]);
            }
        }
    }

    public GraphicsUtils getGraphics() {
        return this.graphics;
    }

    @Override
    public String toString() {
        String str = "";
        for (LinkedList<Edge> edges : adjacentList) {
            for (Edge e : edges) {
                str += e.toString();
            }
            str += "\n";
        }
        return str;
    }
}

