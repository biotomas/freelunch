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

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Filip Dvořák
 */
public class RandomSubSequence {
    /**
     * Assuming a sequence [0,...,sequenceSize], the method chooses randomly distributed number (hitsCount) of the sequence indexes and returns them in an array 
     * @param rg
     * @param hitsCount
     * @param sequenceSize
     * @return
     */
    public static int[] GetRandomSubSequence(Random rg, int hitsCount, int sequenceSize){
        int[] ret;
        if(hitsCount >= sequenceSize){
            //return all
            ret = new int[sequenceSize];
            for(int i = 0; i < ret.length; i++){
                ret[i] = i;
            }
        }else if(sequenceSize == 0 || hitsCount == 0){
            //return empty list
            ret = new int[0];
        }else{
            //make random selection
            LinkedList<Integer> queue = new LinkedList<>();
            for(int i = 0; i < sequenceSize; i++){
                queue.add(i);
            }
            ret = new int[hitsCount];
            for(int i = 0 ; i < hitsCount; i++){
                ret[i] = -1;
            }
            while(queue.size() > sequenceSize - hitsCount){
                Integer i = queue.get(rg.nextInt(queue.size()));
                ret[sequenceSize - queue.size()] = i;
                queue.remove(i);
            }
        }
        ArrayShuffler.ShuffleArray(ret, rg);
        return ret;
    }
}
