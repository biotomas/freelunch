/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2011 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using of this program is prohibited
 * without previous written permission of author. Publishing or providing further
 * of the contents of this file is prohibited without previous written permission
 * of the author.
 */
package freelunch.core.pddlSupport.fLib.utils.random;

import java.util.Random;

/**
 *
 * @author Filip Dvořák
 */
public class ArrayShuffler {
    /**
     * Standard algorithm for shuffling an array.
     * @param ar
     * @param rg
     */
    public static void ShuffleArray(int[] ar, Random rg){
        for (int i = 0; i < ar.length; i++) {
            int j = rg.nextInt(i + 1);
            int o = ar[i];
            ar[i] = ar[j];
            ar[j] = o;
        }
    }
}
