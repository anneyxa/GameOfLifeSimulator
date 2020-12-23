package agh.cs.gameoflife.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GenesManager { //Genes managing
    public static final int genesNumber = 32;

    public static List<Integer> initGenes(){
        List<Integer> genes = new ArrayList<>();
        for(int i=0; i<genesNumber; i++){
            genes.add(ThreadLocalRandom.current().nextInt(0, 8));
        }
        updateGenesToHaveAllOptions(genes);
        return genes;
    }

    public static Integer getRandGene(List<Integer> genes){
        Random r = new Random();
        return genes.get(r.nextInt(genesNumber));
    }

    public static void updateGenesToHaveAllOptions(List<Integer> genes){
        Collections.sort(genes);
        for(int i = 0; i<8; i++) {
            if(!genes.contains(i)) {
                genes.set(getMostCommonGeneIndex(genes), i);
            }
        }
        Collections.sort(genes);
    }

    public static Integer getMostCommonGeneIndex(List<Integer> genes){
        Map<Integer, Integer> map = new HashMap<>();
        for(Integer i : genes){
            Integer value = map.get(i);
            map.put(i, value == null ? 1 : value + 1);
        }
        Map.Entry<Integer, Integer> max = null;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            if(max == null || entry.getValue() > max.getValue()){
                max = entry;
            }
        }
        assert max != null;
        return genes.indexOf(max.getKey());
    }

    public static List<Integer> recombineDNA(List<Integer> genes1, List<Integer> genes2) {
        Collections.sort(genes1);
        Collections.sort(genes2);
        int divisor1 = ThreadLocalRandom.current().nextInt(0,genesNumber - 1);
        int divisor2 = ThreadLocalRandom.current().nextInt(divisor1,genesNumber + 1);
        List<Integer> dna = new ArrayList<>();
        for(int i = 0; i<divisor1; i++) {
            dna.add(genes1.get(i));
        }
        for(int i = divisor1; i<divisor2; i++) {
            dna.add(genes2.get(i));
        }
        for(int i = divisor2; i<genesNumber; i++) {
            dna.add(genes1.get(i));
        }
        updateGenesToHaveAllOptions(dna);
        return dna;
    }
}
