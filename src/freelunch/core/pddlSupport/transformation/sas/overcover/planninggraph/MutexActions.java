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

/**
 *
 * @author Filip Dvořák
 */
class MutexActions {

    LinkedHashSet<ActionPair> set;

    public MutexActions() {
        set = new LinkedHashSet<>();
    }
}
