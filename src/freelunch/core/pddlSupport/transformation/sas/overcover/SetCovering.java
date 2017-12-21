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
package freelunch.core.pddlSupport.transformation.sas.overcover;

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;

/**
 *
 * @author Filip Dvořák
 */
public class SetCovering {

    static Comparator<List<StandardAtom>> listSizeComparator = new Comparator<List<StandardAtom>>() {
        @Override
        public int compare(List<StandardAtom> a, List<StandardAtom> b) {
            return b.size() - a.size();
        }
    };
    static Comparator<ListWrapper> listWrapperSizeComparator = new Comparator<ListWrapper>() {
        @Override
        public int compare(ListWrapper a, ListWrapper b) {
            return b.list.size() - a.list.size();
        }
    };

    public static class ListWrapper {

        LinkedList<StandardAtom> list, original;

        public ListWrapper(LinkedList<StandardAtom> listIn) {
            list = new LinkedList<>(listIn);
            original = new LinkedList<>(listIn);
        }
    }

    /**
     * finds standard greedy covering, but does not enforce empty intersections
     * of the sets
     *
     * @param mutSetsIn
     * @return
     */
    public static LinkedList<LinkedList<StandardAtom>> greedySetCoverWithLeftOvers(LinkedList<LinkedList<StandardAtom>> mutSetsIn) {
        LinkedList<ListWrapper> mutSets = new LinkedList<>();
        for (LinkedList<StandardAtom> l : mutSetsIn) {
            mutSets.add(new ListWrapper(l));
        }
        LinkedList<LinkedList<StandardAtom>> ret = new LinkedList<>();

        while (!mutSets.isEmpty()) {
            Collections.sort(mutSets, listWrapperSizeComparator);
            ListWrapper w = mutSets.pop();
            ret.add(w.original);
            for (StandardAtom a : w.list) {
                Iterator<ListWrapper> it = mutSets.iterator();
                while (it.hasNext()) {
                    ListWrapper ms = it.next();
                    Iterator<StandardAtom> innerIt = ms.list.iterator();
                    while (innerIt.hasNext()) {
                        StandardAtom b = innerIt.next();
                        if (a.equals(b)) {
                            innerIt.remove();
                        }
                    }
                    if (ms.list.isEmpty()) {
                        it.remove();
                    }
                }
            }
        }
        return ret;
    }

    private static class VectorizedMutexSet implements Comparable<VectorizedMutexSet> {

        double mValue;
        LinkedList<StandardAtom> mutexSet;
        BitSet vector;

        public VectorizedMutexSet(LinkedList<StandardAtom> in, HashMap<StandardAtom, Integer> map) {
            mutexSet = in;
            vector = new BitSet(map.size());
            for (StandardAtom a : in) {
                vector.set(map.get(a));
            }
        }

        private void Eval(LinkedList<VectorizedMutexSet> chosenVectors, double[] coverage) {
            //calculate intersection cardinality agains all chosen vectors
            int cards[] = new int[chosenVectors.size()];
            for (int i = 0; i < cards.length; i++) {
                BitSet bs = new BitSet(vector.length());
                bs.or(vector);
                bs.or(chosenVectors.get(i).vector);
                cards[i] = bs.cardinality();
            }
            //calculate the weight of an intersection for each atom
            double intersectionWeight[] = new double[vector.length()];
            for (int i = 0; i < vector.length(); i++) {
                if (vector.get(i)) {
                    double inter = 0;
                    for (int j = 0; j < chosenVectors.size(); j++) {
                        if (chosenVectors.get(j).vector.get(i)) {
                            inter += 1f / cards[j];
                        }
                    }
                    intersectionWeight[i] = inter;
                }
            }
            //calculate final weight
            mValue = 0;
            for (int i = 0; i < vector.length(); i++) {
                if (vector.get(i)) {
                    mValue += (intersectionWeight[i] + 1) * coverage[i];
                }
            }
        }

        @Override
        public int compareTo(VectorizedMutexSet o) {
            if (o == null) {
                throw new NullPointerException();
            }
            if (this.mValue < o.mValue) {
                return 1;
            } else if (this.mValue > o.mValue) {
                return -1;
            } else {
                return 0;
            }
            /*
             if (o == null) {
             throw new NullPointerException();
             } else {
             double diff = (this.mValue - o.mValue);
             if (diff < -prettySmallValue) {
             return 1;
             } else if (diff > prettySmallValue) {
             return -1;
             } else {
             return 0;
             }
             }*/
        }
    }
    public static HashMap<StandardAtom, Integer> mpAI;
    public static HashMap<Integer, StandardAtom> mpIA;

    /**
     * finds a over-covering
     *
     * @param mutSets
     * @param atomsToCover
     * @return
     */
    public static LinkedList<LinkedList<StandardAtom>> smartSetCoverN1(LinkedList<LinkedList<StandardAtom>> mutSets, LinkedHashSet<StandardAtom> atomsToCover) {
        //create maps
        System.out.println("Starting SmartN1 set overcovering.");
        mpAI = new HashMap<>();
        mpIA = new HashMap<>();
        int ct = 0;
        for (StandardAtom a : atomsToCover) {
            mpAI.put(a, ct);
            mpIA.put(ct, a);
            ct++;
        }
        //vectorize
        LinkedList<VectorizedMutexSet> vectors = new LinkedList<>();
        for (LinkedList<StandardAtom> s : mutSets) {
            vectors.add(new VectorizedMutexSet(s, mpAI));
        }
        double coverage[] = new double[atomsToCover.size()];
        for (int i = 0; i < coverage.length; i++) {
            coverage[i] = 1;
        }

        LinkedList<VectorizedMutexSet> chosenVectors = new LinkedList<>();

        boolean end = false;

        double alpha = 2f;
        //double coverageThreshold = 1 / (2 * alpha);


        /*Comparator doubleComparator = new Comparator() {
         @Override
         public int compare(Object a, Object b) {
         VectorizedMutexSet aa = (VectorizedMutexSet) a,
         bb = (VectorizedMutexSet) b;
         return Math.round(bb.mValue - aa.mValue);
         }
         };*/

        while (!end) {
            for (VectorizedMutexSet v : vectors) {
                v.Eval(chosenVectors, coverage);
            }
            Collections.sort(vectors);
            VectorizedMutexSet ms = vectors.pop();
            if (ms.mValue < 1f) {
                System.out.println("Set covering ended: quality of the sets dropped below 2.");
                break;
            }
            chosenVectors.add(ms);
            System.out.println("Best set quality: " + ms.mValue);
            for (int i = 0; i < ms.vector.length(); i++) {
                if (ms.vector.get(i)) {
                    coverage[i] /= alpha;
                }
            }
            double average = 0;
            for (int i = 0; i < coverage.length; i++) {
                average += coverage[i];
            }
            average /= coverage.length;
            System.out.println("Coverage quality: " + average);
            /*if(average < coverageThreshold){
             System.out.println("Set covering ended: coverage quality reached desired level.");
             break;
             }*/
        }



        LinkedList<LinkedList<StandardAtom>> ret = new LinkedList<>();
        for (VectorizedMutexSet ms : chosenVectors) {
            ret.add(ms.mutexSet);
        }

        HashSet<StandardAtom> kb = new HashSet<>();
        //check if everything is really covered
        for (LinkedList<StandardAtom> l : ret) {
            kb.addAll(l);
        }

        return ret;
    }

    /**
     * standard greedy set cover
     * @param mutSetsIn
     * @return
     */
    public static LinkedList<LinkedList<StandardAtom>> greedySetCover(LinkedList<LinkedList<StandardAtom>> mutSetsIn) {
        LinkedList<LinkedList<StandardAtom>> mutSets = new LinkedList<>();
        for (LinkedList<StandardAtom> l : mutSetsIn) {
            mutSets.add(new LinkedList<>(l));
        }
        LinkedList<LinkedList<StandardAtom>> ret = new LinkedList<>();

        while (!mutSets.isEmpty()) {
            Collections.sort(mutSets, listSizeComparator);
            LinkedList<StandardAtom> l = mutSets.pop();
            ret.add(l);
            for (StandardAtom a : l) {
                Iterator<LinkedList<StandardAtom>> it = mutSets.iterator();
                while (it.hasNext()) {
                    LinkedList<StandardAtom> ms = it.next();
                    Iterator<StandardAtom> innerIt = ms.iterator();
                    while (innerIt.hasNext()) {
                        StandardAtom b = innerIt.next();
                        if (a.equals(b)) {
                            innerIt.remove();
                        }
                    }
                    if (ms.isEmpty()) {
                        it.remove();
                    }
                }
            }
        }
        return ret;
    }
}
