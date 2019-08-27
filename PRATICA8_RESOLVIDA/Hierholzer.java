/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;
/*
@author Bárbara Schlottfeldt Maia e Arthur Diniz Torquato Flor Fernandes
*/
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.util.Random;

public class Hierholzer {
    static private Graph graph;
    private LinkedList<Integer> eulerianCycle;
    
    static void slow(){
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static Color getRandomColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }
    
    private void InsertCycle(LinkedList<Integer> newCycle, int index){
        this.eulerianCycle.addAll(index, newCycle);
    }
    
    private LinkedList<Integer> SearchSimpleCycle(int startVertice){
        Color colorInicial = Hierholzer.getRandomColor();
        LinkedList<Integer> Cycle = new LinkedList();
        int verticeAtual;
        Cycle.add(startVertice);
        slow();
         graph.markNode(startVertice, colorInicial);
        verticeAtual=Hierholzer.graph.getNeighbours(startVertice).getFirst().getV();
        slow();
        graph.markNode(verticeAtual, colorInicial);
        Cycle.add(verticeAtual);
        Edge arestainicial=graph.getNeighbours(startVertice).getFirst();
        graph.removeEdge(arestainicial);
        
        while(verticeAtual!=startVertice){
         slow();
        
         Edge e = graph.getNeighbours(verticeAtual).getFirst();
         verticeAtual = e.getV();
         graph.markNode(verticeAtual, colorInicial);
         Cycle.add(verticeAtual);
         graph.removeEdge(e);
        }
        
        
        return Cycle;
    }
    
    public LinkedList<Integer> SearchEulerianCycle(int startVertice){
        LinkedList <Integer> Cycle = new LinkedList();
        ArrayList<LinkedList<Integer>> novosCiclos = new ArrayList(20);
        if(graph.isEulerian()){
            eulerianCycle= SearchSimpleCycle(startVertice);
             System.out.println("Ciclo Simples Encontrado: comecando de " + startVertice);
             System.out.println(CycleToString(eulerianCycle));
            while(graph.nEdges!=0){
                for(Integer i=0; i<eulerianCycle.size();i++){ 
                    if(i!=startVertice  & !graph.getNeighbours(i).isEmpty()){
                    Cycle = SearchSimpleCycle(i);
                    System.out.println("Ciclo Simples Encontrado: comecando de " + i);
                    System.out.println(CycleToString(Cycle));
                    novosCiclos.add(Cycle);
                    }
                    else{
                        
                    }
        
            }
                for(Integer j=0; j<novosCiclos.size();j++){
                    
                    this.InsertCycle(novosCiclos.get(j), j);
                }
               
        }
            return eulerianCycle;
        }
        else{
            graph.turnEulerian();
            return SearchEulerianCycle(startVertice);
        }
    }
    
    /*******************************************************************************************/
    
    public static String CycleToString(LinkedList<Integer> cycle){
        String str = "";
        for (Integer integer : cycle) {
            str += integer +" -> ";
        }
        return str.substring(0, str.length()-4);
    }
    
    public void test1(){
        Graph graph = new Graph(5);
        graph.addEdge(0,1,5);
        graph.addEdge(0,2,5);
        graph.addEdge(0,3,5);
        graph.addEdge(0,4,5);
        graph.addEdge(4,1,5);
        graph.addEdge(4,2,5);
        graph.addEdge(4,3,5);
        
        System.out.println("Lista de adjacencia do grafo:");
        System.out.println(graph.toString());
    }
    
    public void test2(){
        Graph grafo = new Graph(5);
        grafo.addEdge(0,1,5);
        grafo.addEdge(0,2,5);
        grafo.addEdge(0,3,5);
        grafo.addEdge(0,4,5);
        grafo.addEdge(4,1,5);
        grafo.addEdge(4,2,5);
        grafo.addEdge(4,3,5);
        
        
        System.out.println("Lista de adjacencia do grafo ANTES da remocao:");
        System.out.println(grafo.toString());
        System.out.println();
        
        grafo.removeEdge(new Edge(1,4,5));
        grafo.removeEdge(new Edge(0,4,5));
        grafo.removeEdge(new Edge(0,2,5));
        
        System.out.println("Lista de adjacencia do grafo DEPOIS da remocao:");
        System.out.println(grafo.toString());
    }
    
    public void test3(){
        Graph grafo = new Graph(5);
        grafo.addEdge(0,1,5);
        grafo.addEdge(0,2,5);
        grafo.addEdge(0,3,5);
        grafo.addEdge(0,4,5);
        grafo.addEdge(4,1,5);
        grafo.addEdge(4,2,5);
        grafo.addEdge(4,3,5);
        
        System.out.println("Vertices vizinhos do vertice 0:");
        for(Edge e : grafo.getNeighbours(0)){
            System.out.print(e.getV()+" ");
        }
        System.out.println();
        
        System.out.println("Vertices vizinhos do vertice 2:");
        for(Edge e : grafo.getNeighbours(2)){
            System.out.print(e.getV()+" ");
        }
        System.out.println();;
    }
    
    public void test4(){
        Graph grafo = new Graph(5);
        grafo.addEdge(0,1,5);
        grafo.addEdge(0,2,5);
        grafo.addEdge(0,3,5);
        grafo.addEdge(0,4,5);
        grafo.addEdge(4,1,5);
        grafo.addEdge(4,2,5);
        grafo.addEdge(4,3,5);
        
        System.out.println("Lista de adjacencia do grafo 1:");
        System.out.print(grafo.toString());
        System.out.println("Euleriano:" + grafo.isEulerian() + "\n");
        
        grafo = new Graph(6);
        grafo.addEdge(0,1,5);
        grafo.addEdge(0,2,5);
        grafo.addEdge(0,3,5);
        grafo.addEdge(1,4,5);
        grafo.addEdge(1,2,5);
        grafo.addEdge(2,3,5);
        grafo.addEdge(2,5,5);
        grafo.addEdge(3,5,5);
        grafo.addEdge(4,5,5);
        
        System.out.println("Lista de adjacencia do grafo 2:");
        System.out.print(grafo.toString());
        System.out.println("Euleriano: " + grafo.isEulerian());
    }
    
    public void test5(int startVertice){
        graph = new Graph(5);
        graph.addEdge(0,1,5);
        graph.addEdge(0,2,5);
        graph.addEdge(0,3,5);
        graph.addEdge(0,4,5);
        graph.addEdge(4,1,5);
        graph.addEdge(4,2,5);
        graph.addEdge(4,3,5);
        
        LinkedList<Integer> cycle =  SearchSimpleCycle(startVertice);
        System.out.println("Ciclo Simples Encontrado: comecando de " + startVertice);
        System.out.println(CycleToString(cycle));
    }
    
    public void test6(){
        Hierholzer hierholzer = new Hierholzer ();
        //graph = new Graph("grafo1.csv", "white.png", "Roteamento"); // Grafo nao Euleriano
        //graph = new Graph("grafo2.csv", "white.png", "Roteamento"); // Grafo Euleriano
        graph = new Graph("grafo2.csv", "white.png", "Roteamento");
        System.out. println ();
        eulerianCycle = hierholzer.SearchEulerianCycle(0);
        System.out.println("Ciclo␣Final: ");
        System.out.println(Hierholzer.CycleToString(eulerianCycle));
        Graph graph2 = new Graph(graph.getGraphics(), "white.png", "Rota␣Final");
        Color color = Hierholzer.getRandomColor();
        for(Integer i : eulerianCycle){ slow ();
            slow ();
            graph2.markNode(i, color);
        }
    }
    
    public void test7(){
        Hierholzer hierholzer = new Hierholzer();
        // hierholzer.test5(0);
        
        // graph = new Graph("grafo.csv", "white.png", "Roteamento");
        // graph = new Graph("grafo1.csv", "white.png", "Roteamento");
        // graph = new Graph("grafoMapa1.csv", "mapa1.png", "Roteamento");
        graph = new Graph("grafoMapa2.csv", "mapa2.png", "Roteamento");
        
        
        System.out.println();
        LinkedList<Integer> eulerianCycle = hierholzer.SearchEulerianCycle(0);
        System.out.println("Ciclo Final:");
        System.out.println(Hierholzer.CycleToString(eulerianCycle));
        
        // Graph graph2 = new Graph(graph.getGraphics(), "white.png", "Rota Final");
        // Graph graph2 = new Graph(graph.getGraphics(), "mapa1.png", "Rota Final");
        Graph graph2 = new Graph(graph.getGraphics(), "mapa2.png", "Rota Final");
        for(Integer i: eulerianCycle){
            slow();
            Color color = Hierholzer.getRandomColor();
            graph2.markNode(i, color);
        }
    }
    
    public static void main(String[] args) {
    Hierholzer instance = new Hierholzer();
     /*instance.test1();
     instance.test2();
     instance.test3();
     instance.test4();
     instance.test5();
     */
     instance.test6();
     //instance.test7();
    }
}
