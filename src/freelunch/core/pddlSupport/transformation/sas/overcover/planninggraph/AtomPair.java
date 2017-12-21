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

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;

/**
 *
 * @author Filip Dvořák
 */
class AtomPair {

    StandardAtom a, b;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.a.hashCode() + 1) + (this.b.hashCode() + 1);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        AtomPair an = (AtomPair) obj;
        return (a.equals(an.a) && b.equals(an.b)) || (a.equals(an.b) && b.equals(an.a));
    }

    AtomPair(StandardAtom _a, StandardAtom _b) {
        a = _a;
        b = _b;
    }
}
