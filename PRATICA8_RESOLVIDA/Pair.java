/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;
public class Pair{
	public int label;
	public double distance;

	public Pair(int label, double distance){
		this.label = label;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "("+this.label+"|"+this.distance+")";
	}
}