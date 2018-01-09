/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intersect;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author MazyK
 */
public class Intersect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k = 1;
        int p [][] = new int [2][(int) Math.pow(10,k)];
        rnd(k,p);
        
        Arrays.sort(p[0]);
        Arrays.sort(p[1]);
        
        for(int i = 0;i<p[0].length;i++){
            System.out.format("%d ",p[0][i]);
        }
        System.out.print("\n");
        for(int i = 0;i<p[1].length;i++){
            System.out.format("%d ",p[1][i]);
        }
        
        int min = min(p);
        System.out.print("\n");
        System.out.print(min);
      
    }
    /** Metoda pro generování náhodných čísel
     * @param k - exponent
     * @param p - dvourozměrné pole reprezentující posloupnosti
     */    
    public static void rnd(int k, int p[][]){
        Random numbers = new Random();
        for(int i =0;i<Math.pow(10,k);i++){
//          interval čísel pro generování 
            p[0][i] = numbers.nextInt(100);
            p[1][i] = numbers.nextInt(100);
        }
    }
    /** Metoda pro výběr vhodnější posloupnosti pro hledání
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @return číslo sudé nebo liché, které označuje vhodnější posloupnost
     */  
    public static int min(int p[][]){
        int i;
//      větší minimum -> prvky dané posloupnosti budo hledány ve druhé
        if (p[0][0] > p[1][0]){
            i = 0;
        }
        else if (p[0][0] < p[1][0]){
            i=1;
        }
        else {
//          menší maximum -> prvky dané posloupnosti budo hledány ve druhé
            if(p[0][p[0].length-1] < p[1][p[1].length-1]){
                i = 2;
            }
            else {
                i = 3;
            }
        }
        return i;
    }
}
