/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;
import java.util.Comparator;

public class PairComparator implements Comparator<Pair>
{
    @Override
    public int compare(Pair x, Pair y)
    {
        if (x.distance < y.distance){
            return -1;
        }else if (x.distance > y.distance){
            return 1;
        }else{
        	return 0;
		}
    }
}
