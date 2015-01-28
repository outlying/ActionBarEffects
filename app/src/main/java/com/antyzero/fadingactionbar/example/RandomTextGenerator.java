package com.antyzero.fadingactionbar.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iwopolanski on 28.01.15.
 */
public class RandomTextGenerator {

    private static final String ALPHABET = "abcdefghijklmnoprstquwxzABCDEFGHIJKLMNOPRSTQUWXZ";

    private List<String> wordsList = new ArrayList<>();

    private Random random = new Random();

    public RandomTextGenerator() {
        this( 793L );
    }

    public RandomTextGenerator( long randomSeed ) {
        random.setSeed( randomSeed );
    }

    public String generate() {

        StringBuilder stringBuilder = new StringBuilder();

        for ( int i = 0; i < random.nextInt( 290 ) + 10; i++ ) {

            StringBuilder workBuilder = new StringBuilder();

            for ( int j = 0; j < random.nextInt( 15 ) + 1; j++ ) {

                workBuilder.append( ALPHABET.charAt( random.nextInt( ALPHABET.length() ) ) );
            }

            stringBuilder.append( workBuilder.toString() ).append( " " );
        }

        return stringBuilder.toString();
    }
}
