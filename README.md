# Nalezení společných prvků dvou posloupností

Program je neinteraktivní, protože jde jen o ukázku algoritmu. Nikoliv o využití v praxi. Délky posloupností se dají měnit přímo v kódu, změnou exponentu k. Interval pro generování čísel je možné rovněž upravit jednoduše v kódu.

## Popis programu

Po spuštění programu dojde k náhodnému vygenerování dvou posloupností z nastaveného intervalu čísel. Následně dojde k jejich seřazení. Program porovná vztahy mezi minimy a maximy posloupností, vyhodnotí z jaké posloupnosti a v jakém intervalu bude brát čísla, které bude vyhledávat v posloupnosti druhé, tak aby se ušetřilo co nejvíce zbytečných vyhledávání. Následně dojde k vyhledávání a naleznuté společné prvky jsou vypsány.

## Funkcionalita

Posloupnosti jsou v programu uloženy v dvourozměrném poli a jsou generovány pomocí funkce numbers.nextInt(), která je v samostatné metodě. Následně dojde k seřazení posloupností pomocí funkce Arrays.sort(). Potom je volána metoda, která pomocí matematických symbolů (<,>) zjistí vztahy mezi minimy a maximy posloupností. Následně jsou v mainu vytvořeny 3 pole, které budou sloužit jako pomocné indexy při vyhledávání. Poté program zavolá metodu vhodnost, která na základě vztahů mezi minimy a maximy určí vhodnější posloupnost, ze které se budou čísla hledat. Pokud se stane, že posloupnost s větším minimem nemá zároveň menší maximum, tak je zavolána metoda vyhledavani, která najde pozici pro menší maximum z druhé posloupnosti v první posloupnosti. Následně je volána z mainu poslední metoda intersect, která ze zjištěných údajů stanoví interval, ve kterém se bude vyhledávat a interval, ze kterého se bude vyhledávat. Následně zavolá metodu vyhledavani, která zjišťuje, jestli se hledané číslo nachází v druhé posloupnosti. Duplicitní čísla jsou řešena pomocí metody podminka, která je případně volána metodou vyhledavani. Po vyřešení případné duplicity je vrácena hodnota nalezené pozice zpět do metody intersect, kde se zapíše do pole.

## Algoritmus

Zvolil jsem binární vyhledávání, jako jádro mého algoritmu. Binární vyhledávání funguje na principu „hádání“ čísla v polovině intervalu, který se po každém neúspěšném „hádání“ zmenší o polovinu, dokud číslo „neuhádne“ nebo nezjistí, že se v seznamu nevyskytuje. Jako další možnost by šla jistě použít hrubá síla, ale potřebný čas pro řešení by s narůstající množinou čísel rostl lineárně. 
Můj kód zvládá velikosti posloupností až 108 v intervalu čísel od minimální hodnoty integeru do maximální hodnoty integeru za cca 30 sekund. Pokud je interval upraven například od 0 do 100, pak se čas dostane pod 10 sekund. V celém algoritmu jsem používal datový typ integer pro lepší přehlednost, pro větší možnost společných prvků a pro binární vyhledávání. 

## Problematická místa

Největším problémem v celém kódu bylo řešení duplicitních čísel. Například jedna posloupnost měla 40 stejných čísel a druhá 35 těchto čísel. V kódu na to existuje samostatná metoda, ale bylo potřeba dalších tří indexů, které nejsou moc intuitivní, abych problém vyřešil. 


 Dominik Mazur
