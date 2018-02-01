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
        
        int minmax[] = min(p);
        System.out.print("\n");
        System.out.print(minmax[0]);
        System.out.print("\n");
        System.out.print(minmax[1]);
        int l[] = vhodnost(p,minmax);
        System.out.print("\n");
        System.out.print(l[0]);
        System.out.print("\n");
        System.out.print(l[1]);
      
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
     * odečíst - ? možnost 2
     */  
    public static int[] min(int p[][]){
        int minmax[] = new int [2];
//      větší minimum -> prvky dané posloupnosti budou hledány ve druhé
        if (p[0][0] > p[1][0]){
            minmax[0] = 0;
        }
        else if (p[0][0] < p[1][0]){
             minmax[0] = 1;
        }
        else{
            minmax[0] = 2;
        }
        
        if(p[0][p[0].length-1] < p[1][p[1].length-1]){
            minmax[1] = 0;
        }
        else if (p[0][p[0].length-1] > p[1][p[1].length-1]){
            minmax[1] = 1;
        }
        else{
            minmax[1] = 2;
        }
        return minmax;
    }
    public static int []vhodnost(int p[][], int minmax[]){
        int idaprvek[] = new int [2];    
        if(minmax[0]==2){
            if(minmax[1] == 0){
                int l = 0;
                int r = p[1].length-1;
                int m = p[0][p[0].length-1];
                idaprvek[1] = vyhledavanimax(p,l,r,m);
                idaprvek[0] = 1;
            }
            else if(minmax[1] == 1){
                int l = 0;
                int r = p[0].length-1;
                int m = p[1][p[1].length-1];
                idaprvek[1] = vyhledavanimax(p,l,r,m);
                idaprvek[0] = 0;
            }
        }
        else{
            for(int i =0;i<2;i++){
                if(minmax[0]==i){
                    if(minmax[1] == 0+i){
                        idaprvek[0] = 0+i;
                    }
                    else if(minmax[1] == 1-i){
                        int l = 0;
                        int r = p[i].length-1;
                        int m = p[1-i][p[1-i].length-1];
                        idaprvek[1] = vyhledavanimax(p,l,r,m);
                        idaprvek[0] = 0+i;
                    }
                    else {
                        idaprvek[0] = 0+i;
                    }
                    break;
                }
            }
        }
        return idaprvek;
//          menší maximum -> prvky dané posloupnosti budo hledány ve druhé
    }
    public static int vyhledavanimax(int p[][], int l, int r, int m){
        int k = (l+r)/2;
        if(r-l <= 1){
            if (p[0][r]==m){
                return r;
            }
            else if(p[0][l]==m){
                return l;
            }
            else {
                return r;
            }
        }
        if(m == p[0][k]){
            return k;
        }
        else if (m > p[0][k]){
            return vyhledavanimax(p,k,r,m);
        }
        else {
            return vyhledavanimax(p,l,k,m);
        }  
    }
}
