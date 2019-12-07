/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads01.state.immutable;

import examples.chap06.threads01.state.Characters;
import examples.chap06.threads01.state.State;
import examples.chap06.threads01.state.StateObserver;
import examples.chap06.threads01.state.World;
import examples.chap06.threads01.state.mutable.MutableCharacters;
import examples.chap06.threads01.state.mutable.MutableState;
import examples.chap06.threads01.state.mutable.MutableWorld;

public final class ImmutableState implements State {

    private final MutableState state;

    public ImmutableState(MutableState state) {
        this.state = state;
    }
    
    @Override
    public int getEpoch() {
        return state.getEpoch();
    }

    @Override
    public void incEpoch() {
        throw new IllegalAccessError();
    }

    @Override
    public void decEpoch() {
        throw new IllegalAccessError();
    }

    @Override
    public void setEpoch(int epoch) {
        throw new IllegalAccessError();
    }

    @Override
    public int getEpochRate() {
        return state.getEpochRate();
    }

    @Override
    public long getEpochDuration() {
        return state.getEpochDuration();
    }

    @Override
    public void setEpochRate(int epochRate) {
        throw new IllegalAccessError();
    }

    @Override
    public int getSuperDuration() {
        return state.getSuperDuration();
    }

    @Override
    public void setSuperDuration(int superDuration) {
        throw new IllegalAccessError();
    }

    @Override
    public int getGumCount() {
        return state.getGumCount();
    }

    @Override
    public void setGumCount(int gumCount) {
        throw new IllegalAccessError();
    }

    @Override
    public Characters getChars() {
        return new ImmutableCharacters((MutableCharacters)state.getChars());
    }

    @Override
    public void setChars(Characters chars) {
        throw new IllegalAccessError();
    }

    @Override
    public World getWorld() {
        return new ImmutableWorld((MutableWorld)state.getWorld());
    }

    @Override
    public void setWorld(World world) {
        throw new IllegalAccessError();
    }

    @Override
    public void init(int[][] level, int width, int height, int pacmanSpeed, int ghostSpeed) {
        throw new IllegalAccessError();
    }

    @Override
    public State clone() {
        return state.clone();
    }

    @Override
    public void registerObserver(StateObserver observer) {
        state.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(StateObserver observer) {
        state.unregisterObserver(observer);
    }

    @Override
    public void notityStateChanged() {
        throw new IllegalAccessError();
    }

    @Override
    public void notifyWorldElementChanged(int x, int y) {
        throw new IllegalAccessError();
    }

    @Override
    public void notifyCharacterChanged(int charIndex) {
        throw new IllegalAccessError();
    }

    @Override
    public void save(String fileName) {
        state.save(fileName);
    }
    
    
}
