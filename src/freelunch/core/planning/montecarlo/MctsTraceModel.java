/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.planning.montecarlo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import freelunch.core.planning.TraceModel;
import freelunch.core.planning.model.ActionInfo;


public class MctsTraceModel implements TraceModel {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Node root;
    private List<ActionInfo> selectedActions;

    public void updateModel(Node root) {
        Node oldRoot = this.root;
        this.root = root;
        this.propertyChangeSupport.firePropertyChange("root", oldRoot, root);
    }

    public Node getRoot() {
        return this.root;
    }

    public List<ActionInfo> getSelectedActions() {
        return selectedActions;
    }

    public void setSelectedActions(List<ActionInfo> selectedActions) {
        List<ActionInfo> oldActions = this.selectedActions;
        this.selectedActions = selectedActions;
        this.propertyChangeSupport.firePropertyChange("selectedActions", oldActions, root);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public static class Node {
        public String label;
        public boolean active;
        public ActionInfo actionInfo;
        public Node[] children;
    }

}
