package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this(name, hp, attackPower, defense, new NormalState());
    }

    public Hero(String name, int hp, int attackPower, int defense, HeroState state) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = state;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public HeroState getState() {
        return state;
    }

    public String getStateName() {
        return state.getName();
    }

    public void setState(HeroState newState) {
        String oldState = state == null ? "None" : state.getName();
        this.state = newState;
        System.out.println(name + " state changed: " + oldState + " -> " + newState.getName());
    }

    public void onTurnStart() {
        state.onTurnStart(this);
    }

    public void onTurnEnd() {
        state.onTurnEnd(this);
    }

    public boolean canAct() {
        return state.canAct();
    }

    public void attack(Monster monster) {
        onTurnStart();

        if (!isAlive()) {
            System.out.println(name + " cannot attack because they have fallen.");
            return;
        }

        if (!canAct()) {
            System.out.println(name + " skips the turn because of " + state.getName() + ".");
            onTurnEnd();
            return;
        }

        int damage = state.modifyOutgoingDamage(attackPower);
        damage = Math.max(1, damage);

        monster.takeDamage(damage);
        System.out.println(name + " attacks " + monster.getName()
                + " for " + damage + " damage. Monster HP: " + monster.getHp());

        onTurnEnd();
    }

    public void receiveDamage(int rawDamage) {
        int afterDefense = Math.max(1, rawDamage - defense);
        int finalDamage = Math.max(1, state.modifyIncomingDamage(afterDefense));
        takeDamage(finalDamage);

        System.out.println(name + " receives " + finalDamage
                + " damage. HP: " + hp + "/" + maxHp);
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " heals " + amount + " HP. HP: " + hp + "/" + maxHp);
    }
}