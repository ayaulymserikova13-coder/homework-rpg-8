package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        System.out.println("THE HAUNTED TOWER RUN BEGINS");
        printPartyStatus(party);

        for (TowerFloor floor : floors) {
            if (countAliveHeroes(party) == 0) {
                System.out.println("All heroes have fallen. The tower run ends.");
                break;
            }

            FloorResult result = floor.explore(party);

            System.out.println("[RESULT] " + result.getSummary());
            System.out.println("[RESULT] Damage taken on this floor: " + result.getDamageTaken());

            if (result.isCleared()) {
                floorsCleared++;
            } else {
                System.out.println("The party failed to clear the floor.");
                break;
            }

            printPartyStatus(party);
        }

        int heroesSurviving = countAliveHeroes(party);
        boolean reachedTop = floorsCleared == floors.size() && heroesSurviving > 0;

        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }

    private int countAliveHeroes(List<Hero> party) {
        int count = 0;

        for (Hero hero : party) {
            if (hero.isAlive()) {
                count++;
            }
        }

        return count;
    }

    private void printPartyStatus(List<Hero> party) {
        System.out.println("\nParty status:");

        for (Hero hero : party) {
            System.out.println(hero.getName()
                    + " | HP: " + hero.getHp() + "/" + hero.getMaxHp()
                    + " | State: " + hero.getStateName());
        }
    }
}