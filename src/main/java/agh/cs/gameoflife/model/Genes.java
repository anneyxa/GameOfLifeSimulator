package agh.cs.gameoflife.model;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Genes { //Genes managing
    public static final int genesNumber = 32;
    private int[] genes;

    public Genes(){
        this.genes = this.initGenes();
    }

    public int[] initGenes(){
        int[] genes = new int[genesNumber];
        Arrays.setAll(genes, g -> ThreadLocalRandom.current().nextInt(0, 8));
        return genes;
    }

    public int getRandGene(){
        Random r = new Random();
        return this.genes[r.nextInt(genesNumber)];
    }
}
