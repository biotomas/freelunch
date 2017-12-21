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

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.ActionInstance;

/**
 *
 * @author Filip Dvořák
 */
class ActionPair {

        ActionInstance a, b;

        public ActionPair(ActionInstance get, ActionInstance get0) {
            a = get;
            b = get0;
        }

        @Override
        public boolean equals(Object obj) {
            ActionPair an = (ActionPair) obj;
            return (a.equals(an.a) && b.equals(an.b)) || (a.equals(an.b) && b.equals(an.a));
        }

        @Override
        public int hashCode() {
            int hash = 413;
            hash += this.a.hashCode() + this.b.hashCode();
            return hash;
        }
    }
