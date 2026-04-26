package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;

import java.util.List;

public class PoisonTrapFloor extends TowerFloor {

    @Override
    protected String getFloorName() {
        return "Poison Trap Corridor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] Green fog fills the corridor.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] The party walks through a poisonous trap.");

        int damageTaken = 0;

        for (Hero hero : party) {
            if (hero.isAlive()) {
                int before = hero.getHp();
                hero.receiveDamage(6);
                damageTaken += Math.max(0, before - hero.getHp());

                if (hero.isAlive()) {
                    hero.setState(new PoisonedState(2));
                }
            }
        }

        return new FloorResult(true, damageTaken,
                "The party survived the poison corridor, but some heroes became poisoned.");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] The party finds an antidote bottle, but it is empty.");
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[CLEANUP] The poison fog disappears behind the party.");
    }
}