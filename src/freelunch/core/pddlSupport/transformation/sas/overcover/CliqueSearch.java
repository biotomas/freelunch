/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2012 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */
package freelunch.core.pddlSupport.transformation.sas.overcover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;
import freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph.MutexAtoms;

/**
 *
 * @author FD
 */
public class CliqueSearch {

    public static LinkedList<LinkedList<StandardAtom>> GetMutexSetsExhaustive(MutexAtoms mx, LinkedHashSet<StandardAtom> atoms) {
        LinkedList<LinkedList<StandardAtom>> ret = new LinkedList<>();
        NaiveMutexSearchComplete(mx, new LinkedList<StandardAtom>(), new LinkedHashSet<>(atoms), ret);
        return ret;
    }

    public static LinkedList<LinkedList<StandardAtom>> GetMutexSetsLimited(MutexAtoms mx, LinkedHashSet<StandardAtom> atoms, int limit) {
        LinkedList<LinkedList<StandardAtom>> ret = new LinkedList<>();
        LinkedList<StandardAtom> ats = new LinkedList<>(atoms);
        while (limit-- > 0) {
            Collections.shuffle(ats);
            LinkedList<StandardAtom> ms = NaiveMutexSearchOneShot(mx, new LinkedList<StandardAtom>(), new LinkedList<>(ats));
            if (ms != null) {
                ret.add(ms);
            }
        }

        return ret;
    }

    private static boolean fits(MutexAtoms mAtoms, LinkedList<StandardAtom> currentSet, StandardAtom one) {
        boolean ret = true;
        for (StandardAtom a : currentSet) {
            if (!mAtoms.Contains(a, one)) {
                ret = false;
            }
        }
        return ret;
    }

    private static void NaiveMutexSearchComplete(MutexAtoms mAtoms, LinkedList<StandardAtom> currentSet, LinkedHashSet<StandardAtom> candidates, LinkedList<LinkedList<StandardAtom>> mutexSets) {
        ArrayList<StandardAtom> newCandidates = new ArrayList<>();
        for (StandardAtom at : candidates) {
            if (fits(mAtoms, currentSet, at)) {
                newCandidates.add(at);
            }
        }
        if (newCandidates.isEmpty()) {
            if (currentSet.size() > 0) {
                mutexSets.add(currentSet);
            }
        } else {
            for (StandardAtom at : newCandidates) {
                LinkedList<StandardAtom> newCurrent = new LinkedList<>(currentSet);
                LinkedHashSet<StandardAtom> newNewCandidates = new LinkedHashSet<>(newCandidates);
                Iterator<StandardAtom> it = newNewCandidates.iterator();
                boolean done = false;
                while (!done) {
                    StandardAtom atom = it.next();
                    it.remove();
                    if (atom == at) {
                        done = true;
                    }
                }
                newCurrent.add(at);
                NaiveMutexSearchComplete(mAtoms, newCurrent, newNewCandidates, mutexSets);
            }
        }
    }

    /**
     * finds one clique by adding elements from candidates one by one, while each addition is followed by filtering the remaining candidates
     *
     * @param mAtoms
     * @param currentSet
     * @param candidates
     * @return
     */
    private static LinkedList<StandardAtom> NaiveMutexSearchOneShot(MutexAtoms mAtoms, LinkedList<StandardAtom> currentSet, LinkedList<StandardAtom> candidates) {
        // if none was found, we have a maximal clique
        if (candidates.isEmpty()) {
            if (currentSet.size() > 0) {
                return currentSet;
            } else {
                return null;
            }
        } else {
            // else choose a candidate and filter out everything else
            StandardAtom i = candidates.poll();
            currentSet.add(i);
            //exclude the candidates that are not connected with the chosen extension
            //(they cannot be possibly used for further extensions)
            Iterator<StandardAtom> it = candidates.iterator();
            while (it.hasNext()) {
                if (!mAtoms.Contains(i, it.next())) { //inversion here !!
                    it.remove();
                }
            }
            return NaiveMutexSearchOneShot(mAtoms, currentSet, candidates);
        }
    }
}
