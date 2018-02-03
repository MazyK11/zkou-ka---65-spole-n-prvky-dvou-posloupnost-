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
public class stop {

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
//      výpis polí pro vizuální kontrolu u malých posloupností
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
        int[] repeat = {-1};
        int[] duplicity = {0};
//      volání metody vhodnost
        int idapozice[] = new int [2];
        vhodnost(p,minmax,idapozice,exist,repeat,duplicity);
        int intersect [] = intersect(p,exist,idapozice,repeat,duplicity);
//      výpis pole pro vizuální kontrolu u malých posloupností
        System.out.print("\n");
        for(int i = 0;i<intersect.length;i++){
            System.out.format("%d ",intersect[i]);
        }
      
    }
    /** Metoda pro generování náhodných čísel
     * @param k - exponent
     * @param p - dvourozměrné pole reprezentující posloupnosti
     */    
    public static void rnd(int k, int p[][]){
        Random numbers = new Random();
        for(int i =0;i<Math.pow(10,k);i++){
//          upravitelný interval čísel pro generování 
            p[0][i] = numbers.nextInt(10);
            p[1][i] = numbers.nextInt(10);
        }
    }
    /** Metoda, která charakterizuje vztahy mezi minimy a maximy posloupností
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @return pole s čísly od 0 do 2, které reprezentují vztah mezi
     *  minimy a maximy
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
     * @param exist - parametr pro metodu vyhledavani
     * @param repeat - parametr pro metodu vyhledavani
     * @param duplicity - parametr pro metodu vyhledavani
     */  
    public static void vhodnost(int p[][], int minmax[],int idapozice[],
            int exist[], int[] repeat, int[] duplicity){
        idapozice[1] = -1;
//      index j - mění posloupnost z té, ve které se hledá číslo na posloupnost,
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
                idapozice[1] = vyhledavani(p,l,r,m,j,exist,repeat,duplicity);
                idapozice[0] = 1;
            }
            else if(minmax[1] == 1){
                int l = 0;
                int r = p[0].length-1;
                int m = p[1][p[1].length-1];
                idapozice[1] = vyhledavani(p,l,r,m,j,exist,repeat,duplicity);
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
                        idapozice[1] = vyhledavani(p,l,r,m,j+i,exist,repeat,
                                duplicity);
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
     * @param repeat - index, který kontroluje zda byla danná pozice již využita
     * @param duplicity - index, který pomáhá najít duplicitní čísla
     * @return -  pozice hledaného čísla.
     * Metoda funguje na principu binárního vyhledávání
     */  
    public static int vyhledavani(int p[][], int l, int r, int m,int j,
            int [] exist, int [] repeat, int [] duplicity){
//      parametr stop, pro metodu podminka
        int stop =0;
//      k -> pivot (půlka intervalu)
        int k = (l+r)/2;
//      Ukončovací podmínka - hledané číslo je buď, hranice pravého nebo levého
//      intervalu. Nebo se v dané posloupnosti nevyskytuje
        if(r-l <= 1){
            if (p[0+j][r]==m){
//              podmínka pro duplicitu
                if (repeat[0] == r){
                    k =r;
                    stop =1;
                    return podminka(duplicity,exist,p,j,k,stop);
                }
                return r;
            }
            else if(p[0+j][l]==m){
//              podmínka pro duplicitu 
                if (repeat[0] == l){
                    k = l;
                    return podminka(duplicity,exist,p,j,k,stop);
                }
                return l;
            }
            else {
//              -1 -> číslo se v posloupnosti nevyskytuje
                exist[0] = -1;
//              pokud zde číslo není, je mu přiřazena pozice na které by se
//              vyskytovalo při jeho doplnění do posloupnosti -> využito pro
//              maxima
                return r;
            }
        }
        if(m == p[0+j][k]){
//          podmínka pro duplicitu
            if (repeat[0] == k){
                return podminka(duplicity,exist,p,j,k,stop);
            }
            return k;
        }
//      rekurze -> zmenšuji intervaly pro hledání dokud rozdíl neni menší než 1
        else if (m > p[0+j][k]){
            return vyhledavani(p,k,r,m,j,exist,repeat,duplicity);
        }
        else {
            return vyhledavani(p,l,k,m,j,exist,repeat,duplicity);
        }  
    }
    /** Metoda, pro určění velikosti intervalu, ze kterého se bude hledat a
     *  pro následné volání vyhledávací funkce.
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param exist - parametr, který určuje, jestli číslo v posloupnosti
     * existuje nebo ne
     * @param idapozice -pole, které nese informaci o vhodnější posloupnosti
     * pro hledání a potencionální horní hranici, do které se budou čísla hledat
     * @param repeat - index, který kontroluje zda byla danná pozice již využita
     * @param duplicity - index, který pomáhá najít duplicitní čísla
     * @return -  pole stop výslednými stejnými prvky v obou posloupnostech.
     */  
    public static int [] intersect(int p[][],int exist [], int idapozice [],
            int[] repeat, int []duplicity){
//      vytvoření proměnných, které budou následně použity
        int l = 0;int r;int j =0;int mmax;int hole =0;
//      identifikace vhodnější posloupnosti, ze které se budou vyhledávat čísla
//      a určení horní hranice, do které se budou čísla hledat
        if (idapozice[0] == 0){
            j++;
            r = p[1].length-1;
//          pokud bylo maximum dané posloupnosti menší, horní hranice je maximum
            if(idapozice[1] == -1){
               mmax = p[0].length;
            }
//          pokud ne, horní hranice je vyhledaná pozice druhého maxima
//          v této posloupnosti
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
//      vyhledávám postupně prvky z vybrané posloupnosti až do horní hranice
        for(int i =0;i<mmax;i++){
            exist[0] = -2;
            int m = p[1-j][i];
            int pozice = vyhledavani(p,l,r,m,j,exist,repeat,duplicity);
//          pokud číslo neni v druhé posloupnosti, neproběhne zápis do pole
            if (exist[0] == -1){
//              hole - zamezuje "dírám v poli"
                hole++;
            }
            else {
                intersect [i-hole] = p[j][pozice];
//              restart indexu pro duplicitní čísla pokud neexistuje
//              další duplicitní číslo směrem zpět
                if(repeat[0] != pozice+duplicity[0]){
                    duplicity[0] =0;
                }
//              nastavení vyhledané pozice v tomto cyklu
                repeat[0] = pozice+duplicity[0];
//              -3 -> bylo zapsáno duplicitní číslo směrem dopředu od 
//              vyhledané hodnoty - změna hodnoty, aby nemohlo být započítáno
//              dvakrát
                if (exist[0] == -3){
                    p[j][pozice] = p[j][0] -1;
                }
            }
        }
//      kopírování do nového pole, aby zmizela prázdná místa na konci        
        int in [] = new int [mmax-hole];
        for(int i=0;i<in.length;i++){
            in[i] = intersect[i]; 
        }
        return in;        
    }
     /** Metoda pro řešení duplicitních čísel
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param stop - index, který zaručuje, aby program nesáhl za hranici pole
     * @param k - pozice čísla v posloupnosti
     * @param duplicity - index, který pomáhá najít duplicitní čísla
     * @param j - index pro změnu posloupnosti, v které hledáme
     * @param exist - parametr, který určuje, jestli číslo v posloupnosti
     * existuje nebo ne
     * @return -  pozice duplicitního čísla
     */ 
    public static int podminka(int[] duplicity, int exist[], int p[][], int j,
    int k, int stop){
        duplicity[0]++;
//      podmínka pro hledání duplicitního čísla směrem zpět
        if(0 <= k-duplicity[0]){
            if(p[0+j][k]==p[0+j][k-duplicity[0]]){
            return k-duplicity[0];
            }
        }
//      podmínka pro hledání duplicitního čísla směrem dopředu
        if(stop == 0){
            if(p[0+j][k]==p[0+j][k+1]){
//          změna hodnoty vyhledané pozice -> binární hledání se tedy
//          již k této hodnotě nevrátí
            p[j][k] = p[j][0] -1;
            exist[0] = -3;
                return k+1;
            }
        }
//      Číslo nemá duplicitu -> změna hodnoty vyhledané pozice
        exist[0] = -1;
        p[j][k] = p[j][0] -1;
        return k;
    }
}
