package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class RestFloor extends TowerFloor {

    @Override
    protected String getFloorName() {
        return "Silent Rest Chamber";
    }

    @Override
    protected void announce() {
        System.out.println("\n--- A calm room appears: " + getFloorName() + " ---");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] The room is safe and quiet.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] There is no enemy here. The heroes recover.");

        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(8);
            }
        }

        return new FloorResult(true, 0,
                "The party rested and recovered before climbing higher.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("[HOOK] Rest floor does not award loot.");
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] This line should not appear because shouldAwardLoot() returns false.");
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[CLEANUP] The heroes leave the rest chamber.");
    }
}