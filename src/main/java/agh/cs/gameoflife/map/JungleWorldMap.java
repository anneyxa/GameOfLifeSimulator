package agh.cs.gameoflife.map;

import agh.cs.gameoflife.map.interfaces.IAnimalPlantObserver;
import agh.cs.gameoflife.map.interfaces.IWorldMap;
import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.MoveDirection;
import agh.cs.gameoflife.model.Plant;
import agh.cs.gameoflife.model.Vector2d;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class JungleWorldMap extends AbstractWorldMap implements IWorldMap, IAnimalPlantObserver {
    protected int jungleWidth;
    protected int jungleHeight;
    protected Vector2d jungleLowerLeft;
    protected Vector2d jungleUpperRight;

    protected int animalInitEnergy;
    protected int animalMoveEnergy;
    protected int plantEnergy;
    protected int energyToReproduce;

    protected Map<Vector2d, Plant> plantsFields;
    protected List<IAnimalPlantObserver> observers;
    protected List<Animal> animalsDead;
    private int animalsNumber;
    private int plantsNumber;


    public JungleWorldMap(int plantsNumber, int animalsNumber, int width, int height, float jungleRatio, int animalInitEnergy, int animalMoveEnergy, int plantEnergy){
        super(width, height);
        this.observers = new ArrayList<>();
        this.jungleWidth = Math.round(jungleRatio * width);
        this.jungleHeight = Math.round(jungleRatio * height);
        this.setJungleLowerUpper();
        this.plantsFields = new ConcurrentHashMap<>();
        this.animalsDead = new ArrayList<>();
        this.animalInitEnergy = animalInitEnergy;
        this.animalMoveEnergy = animalMoveEnergy;
        this.energyToReproduce = animalInitEnergy/2;
        this.plantEnergy = plantEnergy;
        this.animalsNumber = animalsNumber;
        this.plantsNumber = plantsNumber;
    }

    public int getJungleWidth() {
        return jungleWidth;
    }

    public int getJungleHeight() {
        return jungleHeight;
    }

    private void setJungleLowerUpper(){
        int a = (this.width - this.jungleWidth);
        if(a%2 != 0){
            this.jungleWidth += 1;
        }
        int b = (this.height - this.jungleHeight);
        if(b%2 != 0){
            this.jungleHeight += 1;
        }
        a = a/2;
        b = b/2;
        int c = a + this.jungleWidth;
        int d = b + this.jungleHeight;
        this.jungleLowerLeft = new Vector2d(a, b);
        this.jungleUpperRight = new Vector2d(c, d);
    }

    public void initPlants(int grassNumber){
        for(int i=0; i<grassNumber; i++){
            placePlantRandom();
        }
    }

    public int initAnimals(int animalsNumber) {
        int i;
        for(i=1; i<=animalsNumber; i++){
            System.out.println("Init animal " + i);
            placeAnimalRandom(i);
        }
        return i;
    }

    public void placeAnimalRandom(int animalID){
        int iterator = 0;
        int max = width * height;
        while (iterator < max) { //proby umieszczenia zwierzęcia zostana podjete tyle razy, ile jest miejsca na mapie
            Vector2d vector = new Vector2d(ThreadLocalRandom.current().nextInt(0, this.width), ThreadLocalRandom.current().nextInt(0, this.height));
            if (!isOccupied(vector)) {
                Animal animal = new Animal(this, vector, animalID, this.animalInitEnergy, this.animalMoveEnergy);
                placeAnimal(animal);
                this.animalPlaced(animal);
                return;
            }
            iterator ++;
        }
    }

    public void placePlantRandom(){
        int iterator = 0;
        int max = width * height;
        while (iterator < max) { //proby umieszczenia rosliny zostana podjete tyle razy, ile jest miejsca na mapie
            Vector2d vector = new Vector2d(ThreadLocalRandom.current().nextInt(0, this.width),
                    ThreadLocalRandom.current().nextInt(0, this.height));
            if (this.objectsAt(vector) == null) {
                Plant plant = new Plant(this, vector);
                this.plantsFields.put(vector, plant);
                this.plantPlaced(plant);
                return;
            }
            iterator ++;
        }
    }

    public void placePlantIntoJungle(){
        int iterator = 0;
        int max = jungleWidth * jungleHeight;
        while (iterator < max) { //proby umieszczenia rosliny zostana podjete tyle razy, ile jest miejsca w dżungli
            Vector2d vector = new Vector2d(ThreadLocalRandom.current().nextInt(this.jungleLowerLeft.getX(), this.jungleUpperRight.getX()),
                    ThreadLocalRandom.current().nextInt(this.jungleLowerLeft.getY(), this.jungleUpperRight.getY()));
            if (this.objectsAt(vector) == null) {
                Plant plant = new Plant(this, vector);
                this.plantsFields.put(vector, plant);
                this.plantPlaced(plant);
                return;
            }
            iterator ++;
        }
    }

    public void animalsEatGrass(){
        for(Map.Entry<Vector2d, Plant> entry : this.plantsFields.entrySet()){
            if(this.animalsPositions.get(entry.getKey()) != null && !this.animalsPositions.get(entry.getKey()).isEmpty()){
                List<Animal> eatingAnimals = this.animalsPositions.get(entry.getKey());
                List<Animal> strongest = eatingAnimals.stream()
                        .collect(Collectors.groupingBy(
                                Animal::getEnergy,
                                TreeMap::new,
                                Collectors.toList()))
                        .lastEntry()
                        .getValue();
                int energyToAdd = this.plantEnergy/strongest.size();
                strongest.forEach(a -> a.increaseEnergy(energyToAdd));
                this.plantRemoved(this.plantsFields.remove(entry.getKey()));
            }
        }
    }

    public void moveAnimals(){
        if(!this.animals.isEmpty()){
            this.animals.forEach(a -> {
                a.move(MoveDirection.FORWARD);
                a.decreaseEnergy(this.animalMoveEnergy);
            });
        }
    }

    public void reproduce(int animalID){
        List<Animal> futureParents = new LinkedList<>();
        List<Animal> children = new LinkedList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : this.animalsPositions.entrySet()){
            futureParents = entry.getValue();
            if(futureParents != null && futureParents.size() >= 2){
                futureParents.sort(Comparator.comparing(Animal::getEnergy));
                Collections.reverse(futureParents);
                if((futureParents.get(0).getEnergy() >= this.energyToReproduce) && (futureParents.get(1).getEnergy() >= this.energyToReproduce)){
                    Animal animal = new Animal(futureParents.get(0), futureParents.get(1), animalID);
                    this.placeAnimal(animal);
                    children.add(animal);
                }
            }
        }
        for (Animal child : children){
            this.animalPlaced(child);
        }
    }


    @Override
    public void handleDeadAnimals() {
        List<Animal> killedAnimals = new ArrayList<>();
        for (Animal animal : this.animals){
            if(animal.isDead()){
                killedAnimals.add(animal);
                this.animalsDead.add(animal);
            }
        }
        if(killedAnimals.size() > 0){
            for(Animal animal : killedAnimals){
                this.animals.remove(animal);
                this.animalsPositions.get(animal.getPosition()).remove(animal);
                this.animalDied(animal);
            }
        }
    }

    @Override
    public Object objectsAt(Vector2d position) {
        if(this.animalsPositions.get(position) != null && !this.animalsPositions.get(position).isEmpty()){
            return this.animalsPositions.get(position);
        }
        return this.plantsFields.get(position);
    }

    public Map<Vector2d, Plant> getPlantsFields(){
        return this.plantsFields;
    }

    @Override
    public void plantRemoved(Plant plant) {
        this.observers.forEach(o -> o.plantRemoved(plant));
    }

    @Override
    public void animalDied(Animal animal) {
        this.observers.forEach(o -> o.animalDied(animal));
    }

    @Override
    public void plantPlaced(Plant plant) {
        this.observers.forEach(o -> o.plantPlaced(plant));
    }

    @Override
    public void animalPlaced(Animal animal) {
        this.observers.forEach(o -> o.animalPlaced(animal));
    }

    public void addObserver(IAnimalPlantObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IAnimalPlantObserver observer){
        observers.remove(observer);
    }

    public int getAnimalsNumber() {
        return animalsNumber;
    }

    public int getPlantsNumber() {
        return plantsNumber;
    }

    public List<Animal> getAnimalsDead() {
        return animalsDead;
    }
}