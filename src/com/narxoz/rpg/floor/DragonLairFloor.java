package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.StunnedState;

import java.util.List;

public class DragonLairFloor extends TowerFloor {

    private Monster dragon;

    @Override
    protected String getFloorName() {
        return "Dragon's Lair";
    }

    @Override
    protected void announce() {
        System.out.println("\n=== FINAL FLOOR: " + getFloorName() + " ===");
        System.out.println("A dragon roars from the top of the haunted tower!");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] The dragon spreads its wings.");
        dragon = new Monster("Ancient Dragon", 45, 12);

        Hero target = firstAliveHero(party);
        if (target != null) {
            System.out.println("The dragon's roar stuns " + target.getName() + ".");
            target.setState(new StunnedState(1));
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] Boss fight begins.");

        int damageTaken = 0;
        int round = 1;

        while (dragon.isAlive() && hasAliveHeroes(party) && round <= 6) {
            System.out.println("Boss Round " + round);

            for (Hero hero : party) {
                if (hero.isAlive() && dragon.isAlive()) {
                    hero.attack(dragon);
                }
            }

            if (dragon.isAlive()) {
                Hero target = firstAliveHero(party);
                if (target != null) {
                    System.out.println(dragon.getName() + " breathes fire at " + target.getName() + ".");
                    int before = target.getHp();
                    target.receiveDamage(dragon.getAttackPower());
                    damageTaken += Math.max(0, before - target.getHp());
                }
            }

            round++;
        }

        boolean cleared = !dragon.isAlive();
        String summary = cleared
                ? "The dragon was defeated. The tower is conquered."
                : "The dragon still guards the tower.";

        return new FloorResult(cleared, damageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] The heroes claim the Dragon Crown.");
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[CLEANUP] Smoke fades from the dragon chamber.");
    }

    private boolean hasAliveHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Hero firstAliveHero(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return hero;
            }
        }
        return null;
    }
}