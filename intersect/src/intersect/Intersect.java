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
//      exponent k, pro lehčí manipulaci s velikostí posloupností 
        int k = 2;
        int p [][] = new int [2][(int) Math.pow(10,k)];
        rnd(k,p);
//      seřazení posloupností pro lepší hledání
        Arrays.sort(p[0]);
        Arrays.sort(p[1]);
//          int p[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};

        
        for(int i = 0;i<p[0].length;i++){
            System.out.format("%d ",p[0][i]);
        }
        System.out.print("\n");
        for(int i = 0;i<p[1].length;i++){
            System.out.format("%d ",p[1][i]);
        }

//      volání metody min
        int minmax[] = min(p);
//      vytvoření promněnných pro podmínky při hledání
        int exist[] = {0};
        int t[] = {-1};
        int a[] = {0};
//      volání metody vhodnost
        int idapozice[] = new int [2];
        vhodnost(p,minmax,idapozice,exist,t,a);
        intersect(p,exist,idapozice,t,a);
      
    }
    /** Metoda pro generování náhodných čísel
     * @param k - exponent
     * @param p - dvourozměrné pole reprezentující posloupnosti
     */    
    public static void rnd(int k, int p[][]){
        Random numbers = new Random();
        for(int i =0;i<Math.pow(10,k);i++){
//          interval čísel pro generování 
            p[0][i] = numbers.nextInt(10);
            p[1][i] = numbers.nextInt(10);
        }
    }
    /** Metoda, která charakterizuje vztahy mezi minimy a maximy posloupností
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @return pole s čísly od 0 do 2, které reprezentují vztah mezi
     * minimy a maximy
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
//      první pozice reprezentuje vztah minim a druhá maxim
        return minmax;
    }
    /** Metoda pro výběr vhodnější posloupnosti pro hledání stejných čísel
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param minmax - pole, které obsahuje vztahy mezi minimy a maximy
     * posloupností
     * @param idapozice - pole, které na první pozici získá číslo vhodnější
     * posloupnosti a na druhé pozici získá číslo pozice menšího maxima,
     * pokud se nachází v odlišné posloupnosti než větší minimum
     * 
     */  
    public static void vhodnost(int p[][], int minmax[],int idapozice[],
            int exist[], int t[], int a[]){
        idapozice[1] = -1;
//      index j - mění posloupnost z té, ve které se hledá číslo na posloupnost
//      z které se hledá číslo
        int j=0;
//      Pokud jsou minima stejná, využiji vztah maxim a zavolám metodu
//      vyhledavani -> naleznu pozici menšího maxima v druhé posloupnosti
        if(minmax[0]==2){
            if(minmax[1] == 0){
                int l = 0;
                int r = p[1].length-1;
                int m = p[0][p[0].length-1];
                j++;
                idapozice[1] = vyhledavani(p,l,r,m,j,exist,t,a);
                idapozice[0] = 1;
            }
            else if(minmax[1] == 1){
                int l = 0;
                int r = p[0].length-1;
                int m = p[1][p[1].length-1];
                idapozice[1] = vyhledavani(p,l,r,m,j,exist,t,a);
                idapozice[0] = 0;
            }
        }
//      pokud minima nejsou stejná, pokračuji porovnáním všech možností, abych
//      nalezl nejvhodnější interval pro vyhledávání
        else{
            for(int i =0;i<2;i++){
//              ptám se na vztah minim
                if(minmax[0]==i){
//                  vztah maxim
                    if(minmax[1] == 0+i){
                        idapozice[0] = 0+i;
                    }
//                  pokud je větší minimum v odlišné posloupnosti než
//                  menší maximum -> naleznu pozici menšího maxima
//                  v druhé posloupnosti
                    else if(minmax[1] == 1-i){
                        int l = 0;
                        int r = p[i].length-1;                      
                        int m = p[1-i][p[1-i].length-1];
                        idapozice[1] = vyhledavani(p,l,r,m,j+i,exist,t,a);
                        idapozice[0] = 0+i;
                    }
                    else {
                        idapozice[0] = 0+i;
                    }
                    break;
                }
            }
        }
    }
    /** Metoda pro vyhledávání pozice čísla
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param l - levá strana intervalu.
     * @param r - pravá strana intervalu.
     * @param m - hledané číslo.
     * @param j - index pro změnu posloupnosti, v které hledáme
     * @param exist - parametr, který určuje, jestli číslo v posloupnosti
     * existuje nebo ne
     * Metoda funguje na principu binárního vyhledávání
     * @return -  pozice hledaného čísla.
     */  
    public static int vyhledavani(int p[][], int l, int r, int m,int j,
            int [] exist, int [] t, int [] a){
        int s =0;
        int k = (l+r)/2;
        if(r-l <= 1){
            if (p[0+j][r]==m){
                if (t[0] == r){
                    k =r;
                    s =1;
                    return podminka(t,a,exist,p,j,k,s);
//                    a[0]++;
//                    if(0 <= r-a[0]){
//                        if(p[0+j][r]==p[0+j][r-a[0]]){
//                            return r-a[0];
//                        }
//                    }    
//                    exist[0] = -1;
                }
                return r;
            }
            else if(p[0+j][l]==m){
                if (t[0] == l){
                    k = l;
                    return podminka(t,a,exist,p,j,k,s);
//                    a[0]++;
//                    if(0 <= l-a[0]){
//                        if(p[0+j][l]==p[0+j][l-a[0]]){
//                            return l-a[0];
//                        }
//                    }    
//                    if(p[0+j][l]==p[0+j][l+1]){
//                    p[j][l] = p[j][0] -1;
//                        exist[0] = -3;
//                        return l+1;
//                    }
//                    exist[0] = -1;
                }
                return l;
            }
            else {
//              -1 -> číslo se v posloupnosti nevyskytuje
                exist[0] = -1;
//              pokud zde číslo není, je mu přiřazena pozice na které by se
//              vyskytovalo při jeho doplnění do posloupnosti
                return r;
            }
        }
        if(m == p[0+j][k]){
            if (t[0] == k){
                return podminka(t,a,exist,p,j,k,s);
//                a[0]++;
//                if(0 <= k-a[0]){
//                    if(p[0+j][k]==p[0+j][k-a[0]]){
//                    return k-a[0];
//                    }
//                }
//                    if(p[0+j][k]==p[0+j][k+1]){
//                    p[j][k] = p[j][0] -1;
//                    exist[0] = -3;
//                        return k+1;
//                    }
//                exist[0] = -1;
//                p[j][k] = p[j][0] -1;
            }
            return k;
        }
        else if (m > p[0+j][k]){
            return vyhledavani(p,k,r,m,j,exist,t,a);
        }
        else {
            return vyhledavani(p,l,k,m,j,exist,t,a);
        }  
    }
    public static void intersect(int p[][],int exist [], int idapozice [],
            int t[], int []a){
        int l = 0;int r;int j =0;int mmax;int g =0;
        if (idapozice[0] == 0){
            j++;
            r = p[1].length-1;
            if(idapozice[1] == -1){
               mmax = p[0].length;
            }
            else {
                mmax = idapozice[1]+1;
            }
        }
        else {
            r = p[0].length-1;
            if(idapozice[1] == -1){
               mmax = p[1].length;
            }
            else {
                mmax = idapozice[1]+1;
            }
        }
        int intersect [] = new int [mmax];
        for(int i =0;i<mmax;i++){
            exist[0] = -2;
            int m = p[1-j][i];
            int h = vyhledavani(p,l,r,m,j,exist,t,a);
            if (exist[0] == -1){
//                a[0] =1;
                g++;
            }
            else {
                intersect [i-g] = p[j][h];
                if(t[0] != h+a[0]){
                    a[0] =0;
                }
                t[0] = h+a[0];
                if (exist[0] == -3){
                    p[j][h] = p[j][0] -1;
                }
            }
        }        
//        int in [] = new int [mmax-g];
//        for(int i=0;i<in.length;i++){
//            in[i] = intersect[i]; 
//        }
        System.out.print("\n");
        for(int i = 0;i<mmax-g;i++){
            System.out.format("%d ",intersect[i]);
        }
        
    }
    public static int podminka(int t[],int a[], int exist[], int p[][], int j,
    int k, int s){
        a[0]++;
        if(0 <= k-a[0]){
            if(p[0+j][k]==p[0+j][k-a[0]]){
            return k-a[0];
            }
        }
        if(s == 0){
            if(p[0+j][k]==p[0+j][k+1]){
            p[j][k] = p[j][0] -1;
            exist[0] = -3;
                return k+1;
            }
        }
        exist[0] = -1;
        p[j][k] = p[j][0] -1;
        return k;
    }
}
