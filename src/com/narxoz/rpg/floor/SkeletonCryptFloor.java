package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;

import java.util.List;

public class SkeletonCryptFloor extends TowerFloor {

    private Monster skeleton;

    @Override
    protected String getFloorName() {
        return "Skeleton Crypt";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] Bones move in the darkness...");
        skeleton = new Monster("Skeleton Warrior", 32, 8);
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] Combat begins against " + skeleton.getName() + ".");

        int damageTaken = 0;
        int round = 1;

        while (skeleton.isAlive() && hasAliveHeroes(party) && round <= 5) {
            System.out.println("Round " + round);

            for (Hero hero : party) {
                if (hero.isAlive() && skeleton.isAlive()) {
                    hero.attack(skeleton);
                }
            }

            if (skeleton.isAlive()) {
                Hero target = firstAliveHero(party);
                if (target != null) {
                    System.out.println(skeleton.getName() + " attacks " + target.getName() + ".");
                    int before = target.getHp();
                    target.receiveDamage(skeleton.getAttackPower());
                    damageTaken += Math.max(0, before - target.getHp());
                }
            }

            round++;
        }

        boolean cleared = !skeleton.isAlive();
        String summary = cleared
                ? "The party defeated the skeleton."
                : "The skeleton survived the fight.";

        return new FloorResult(cleared, damageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] The heroes find healing dust in the crypt.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(4);
            }
        }
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