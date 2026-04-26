package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.DragonLairFloor;
import com.narxoz.rpg.floor.PoisonTrapFloor;
import com.narxoz.rpg.floor.RestFloor;
import com.narxoz.rpg.floor.SkeletonCryptFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Hero> party = new ArrayList<>();

        Hero arman = new Hero("Arman", 45, 12, 3, new NormalState());
        Hero aisha = new Hero("Aisha", 40, 10, 2, new PoisonedState(2));

        party.add(arman);
        party.add(aisha);

        List<TowerFloor> floors = new ArrayList<>();
        floors.add(new SkeletonCryptFloor());
        floors.add(new PoisonTrapFloor());
        floors.add(new RestFloor());
        floors.add(new DragonLairFloor());

        TowerRunner runner = new TowerRunner(floors);
        TowerRunResult result = runner.run(party);

        System.out.println("\nFINAL TOWER RUN RESULT");
        System.out.println("Floors cleared: " + result.getFloorsCleared());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Reached top: " + result.isReachedTop());
    }
}