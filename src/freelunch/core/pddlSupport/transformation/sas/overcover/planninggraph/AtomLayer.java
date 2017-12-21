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
public class AtomLayer {

    public LinkedHashSet<StandardAtom> facts;

    public AtomLayer() {
        facts = new LinkedHashSet<>();
    }
}
