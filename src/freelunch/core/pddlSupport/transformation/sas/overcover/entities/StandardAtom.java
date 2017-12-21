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
package freelunch.core.pddlSupport.transformation.sas.overcover.entities;

import java.util.LinkedList;
import java.util.HashMap;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;

/**
 *
 * @author Filip Dvořák
 */
public class StandardAtom {

    private static HashMap<String, Integer> indexMap = new HashMap<>();
    public int mIndex; //unique ID based on its unique name
    public boolean functionalOrigin;
    public String originName;
    public LinkedList<IOVariable> mOrigins;
    public String name;

    /**
     *
     * @param origins
     * @param fOrigin
     * @param _originName
     */
    public StandardAtom(LinkedList<IOVariable> origins, boolean fOrigin, String _originName) {
        mOrigins = origins;
        functionalOrigin = fOrigin;
        originName = _originName;
        name = _originName + "(";
        for (int i = 0; i < mOrigins.size(); i++) {
            name += mOrigins.get(i).mName + ((i < mOrigins.size() - 1) ? "," : "");
        }
        name += ")";

        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }

    public static void ResetPooling() {
        indexMap = new HashMap<>();
    }

    /**
     *
     * @param a
     * @return
     */
    public boolean FunctionMatched(StandardAtom a) {
        if (!a.originName.equals(originName)) {
            return false;
        }
        for (int i = 0; i < a.mOrigins.size() - 1; i++) {
            if (!mOrigins.get(i).mName.equals(a.mOrigins.get(i).mName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        return ((StandardAtom) obj).mIndex == mIndex;
    }

    @Override
    public int hashCode() {
        return mIndex;
    }

    public boolean precedes(StandardAtom b) {
        return mIndex < b.mIndex;
    }

    /*public void ReIndex() {
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }*/
    
    public StandardAtom(String nm){
        name = nm;
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }
}
