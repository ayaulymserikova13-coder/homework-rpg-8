package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {

    private int turnsLeft;

    public StunnedState(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is stunned and cannot act.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsLeft--;

        if (turnsLeft <= 0 && hero.isAlive()) {
            System.out.println(hero.getName() + " recovers from stun.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return false;
    }
}