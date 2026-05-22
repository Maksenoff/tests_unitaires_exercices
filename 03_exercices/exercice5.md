## Exercice 5

- Le but de l'exercice est de réaliser des tests unitaire de la classe suivante en mockant l'interface Ide

```java

public interface Ide {
    public int getRoll();
}

public class DiceScore {

    private Ide de;

    public int getScore (){
        int scoreFirst = de.getRoll();
        int scoreSecond = de.getRoll();

        if(scoreFirst == scoreSecond){
            if(scoreFirst == 6){
                return 30;
            }
            return scoreFirst*2 + 10;
        }else{
            return scoreFirst < scoreSecond ? scoreSecond : scoreFirst;
        }
    }
}

```

- lorsque les 2 dés sont identiques on recupere bien valeur du dé * 2 + 10
- dans le cas ou les 2 dés sont identiques et egaux a 6 on recupere 30
- dans le cas ou les dé sont quelconque on recupere la valeure du plus haut des 2