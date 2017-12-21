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
package freelunch.visualization.debugging;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;

public abstract class TreeVisualizer<T> extends JPanel {

    private static final long serialVersionUID = 678763625269634988L;
    private static final int NODE_WIDTH = 150;
    private static final int NODE_HEIGHT = 50;
    private static final int HORIZONTAL_GAP = 20;
    private static final int VERTICAL_GAP = 10;

    private final Node root;
    private int depth;
    private int breadth;
    private TreeIntCounter<Integer> counter = new TreeIntCounter<Integer>();

    public TreeVisualizer(T tRoot) {
        this.root = recursiveBuild(tRoot, 1);
    }

    private Node recursiveBuild(T tNode, int level) {
        T[] tChildren = getChildren(tNode);
        Node[] children = null;
        if (tChildren != null) {
            children = new Node[tChildren.length];
            for (int i = 0; i < children.length; i++) {
                children[i] = recursiveBuild(tChildren[i], level + 1);
            }
            counter.increment(depth, children.length);
        }
        depth = Math.max(depth, level);
        breadth = Math.max(breadth, counter.count(depth));
        return new Node(getLabel(tNode), getColor(tNode), children);
    }

    public Dimension getDimensions() {
        return new Dimension(depth * (NODE_WIDTH + HORIZONTAL_GAP), breadth * (NODE_HEIGHT + VERTICAL_GAP));
    }

    protected abstract String getLabel(T tNode);

    protected abstract Color getColor(T tNode);

    protected abstract T[] getChildren(T tNode);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        counter = new TreeIntCounter<Integer>();
        recursivePaint(g, root, 0);
    }

    private void recursivePaint(Graphics g, Node node, int depth) {
        int breadth = counter.count(depth);

        g.setColor(node.color);
        g.fillRect(depth * (NODE_WIDTH + HORIZONTAL_GAP), breadth * (NODE_HEIGHT + VERTICAL_GAP), NODE_WIDTH, NODE_HEIGHT);

        g.setColor(Color.BLACK);
        g.drawRect(depth * (NODE_WIDTH + HORIZONTAL_GAP), breadth * (NODE_HEIGHT + VERTICAL_GAP), NODE_WIDTH, NODE_HEIGHT);

        g.setColor(Color.BLACK);
        int j = 1;
        for (String line : node.label.split("\n")) {
            g.drawString(line, depth * (NODE_WIDTH + HORIZONTAL_GAP) + 10, breadth * (NODE_HEIGHT + VERTICAL_GAP) + g.getFontMetrics().getHeight() * j);
            j++;
        }

        if (node.children != null) {
            for (int i = 0; i < node.children.length; i++) {
                recursivePaint(g, node.children[i], depth + 1);
                int childBreadth = counter.count(depth + 1);
                g.drawLine(depth * (NODE_WIDTH + HORIZONTAL_GAP) + NODE_WIDTH, breadth * (NODE_HEIGHT + VERTICAL_GAP) + NODE_HEIGHT / 2,
                        (depth + 1) * (NODE_WIDTH + HORIZONTAL_GAP), childBreadth * (NODE_HEIGHT + VERTICAL_GAP) + NODE_HEIGHT / 2
                        );
                counter.increment(depth + 1, 1);
            }
        }
    }

    private static class Node {
        public final String label;
        public final Color color;
        public final Node[] children;

        public Node(String label, Color color, Node[] children) {
            this.label = label;
            this.color = color;
            this.children = children;
        }
    }

    private static class TreeIntCounter<K> {
        private final TreeMap<K, int[]> map = new TreeMap<K, int[]>();

        public void increment(K element, int by) {
            int[] el = map.get(element);
            if (el == null) {
                map.put(element, new int[] { by });
            } else {
                el[0] += by;
            }
        }

        public int count(K element) {
            int[] el = map.get(element);
            return el == null ? 0 : map.get(element)[0];
        }

        @SuppressWarnings("unused")
        public Set<K> getKeySet() {
            return map.keySet();
        }

    }
}
