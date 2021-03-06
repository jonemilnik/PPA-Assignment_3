package animals;

import field.Field;
import field.Location;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Fox extends Predator {
    // Characteristics shared by all foxes (class variables).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.16;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    private Random random;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location, boolean isMale) {
        super(randomAge, field, location, isMale, BREEDING_PROBABILITY, MAX_LITTER_SIZE, MAX_AGE, BREEDING_AGE);
        random = new Random();
    }

    @Override
    protected boolean canEatAnimal(Object animal) {
        return animal instanceof Rabbit || animal instanceof Rat;
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newFoxes A list to return newly born foxes.
     */
    @Override
    protected void giveBirth(List<Animal> newFoxes, List<Location> adjacentLocations) {
        for (Location where : adjacentLocations) {
            Object animal = getField().getObjectAt(where);
            if (animal instanceof Fox && ((Fox) animal).isMale() != this.isMale())
                super.giveBirth(newFoxes, (field, location) -> new Fox(false, field, location, random.nextBoolean()));
        }
    }

    /**
     * @return default food level of fox.
     */
    @Override
    protected int getFoodLevel() {
        return 0;
    }
}
