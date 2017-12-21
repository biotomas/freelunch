/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2011 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */
package freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph;

import java.util.LinkedHashSet;

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;

/**
 *
 * @author Filip Dvořák
 */
public class MutexAtoms {
    private LinkedHashSet<AtomPair> set;
    //boolean[][] map;
    //static int arSize = 1000;

    public MutexAtoms() {
        set = new LinkedHashSet<>();
        /*map = new boolean[arSize][];
        for (int i = 0; i < arSize; i++) {
            map[i] = new boolean[arSize];
        }*/
    }

    public boolean Contains(StandardAtom a, StandardAtom b) {
        //return map[a.mIndex][b.mIndex];
        return set.contains(new AtomPair(a, b));
    }

    public void Add(StandardAtom a, StandardAtom b) {
        set.add(new AtomPair(a, b));
        //map[a.mIndex][b.mIndex] = true;
        //map[b.mIndex][a.mIndex] = true;
    }
    
    public void RemoveSubset(LinkedHashSet<StandardAtom> atoms){
        LinkedHashSet<AtomPair> newSet = new LinkedHashSet<>();
        for(AtomPair p:set){
            if(!atoms.contains(p.a) && !atoms.contains(p.b)){
                newSet.add(p);
            }
        }
        set = newSet;
    }
}
