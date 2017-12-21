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

package freelunch.core.pddlSupport.representations.SAS;

/**
 *
 * @author FD
 */
public class STransition {
    public SVariable var;
    public SValue from, to;

    @Override
    public String toString() {
        return var.mName + ":"+from.toString() + " -> "+to.toString();
    }
    
    
}
