# GameOfLifeSimulator

## Using: Java 15 and JavaFX with Gradle

To run the project, go to class Main and run main.


### Project for object oriented programming

This is a simulator of animals' day by day cycle in 2D world. World consists of steppe and jungle, where plants grows faster.
Animals moves & directions depend on their genes. Animal gain energy when it stepps on a plant and eat it. Day by day animal's energy is lower. Animals can reproduce - make other animals and die.
Next to the world board are shown short statistics updated every day about the world and it's content.
There is a possibility to pause & start the simulation by pressing space.
Every x days (once per defined epoch) current game status is saved to status.txt file.


Data can be pulled from data.json file (example):

{
  "plantsNumber": 70,
  "animalsNumber": 25,
  "width": 20,
  "height": 20,
  "jungleRatio": 0.5,
  "animalInitEnergy": 30,
  "animalMoveEnergy": 1,
  "plantEnergy": 3,
  "epochFrequency": 10
}


### Detailed description:
https://github.com/apohllo/obiektowe-lab/tree/master/lab8
