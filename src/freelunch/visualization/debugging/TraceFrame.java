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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import freelunch.core.planning.TraceModel;
import freelunch.core.planning.montecarlo.MctsTraceModel;
import freelunch.core.planning.montecarlo.MctsTraceModel.Node;


public class TraceFrame extends JFrame {
    private static final long serialVersionUID = -7487222496805293452L;
    private final JScrollPane scrollPane;
    final JMenuBar menuBar = new JMenuBar();
    final JMenu mnOptions = new JMenu("Options");
    final JCheckBoxMenuItem chckbxmntmUpdateEnabled = new JCheckBoxMenuItem("Update enabled");

    public TraceFrame(TraceModel traceModel, boolean updateDefault) {
        super("Trace frame");
        getContentPane().setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.scrollPane = new JScrollPane();
        getContentPane().add(scrollPane);
        menuBar.setName("menuBar");

        scrollPane.setColumnHeaderView(menuBar);
        mnOptions.setName("mnOptions");

        menuBar.add(mnOptions);
        chckbxmntmUpdateEnabled.setSelected(updateDefault);
        chckbxmntmUpdateEnabled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        chckbxmntmUpdateEnabled.setName("chckbxmntmUpdateEnabled");

        mnOptions.add(chckbxmntmUpdateEnabled);
    }

    public void update(TraceModel traceModel) {
        if (chckbxmntmUpdateEnabled.isSelected()) {
            setInnerPanel(buildInnerPanel(traceModel));
        }
    }

    private void setInnerPanel(JComponent component) {
        scrollPane.setViewportView(component);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        validate();
    }

    public static JComponent buildInnerPanel(TraceModel traceModel) {
        if (traceModel instanceof MctsTraceModel) {
            MctsTraceModel mtd = (MctsTraceModel) traceModel;
            TreeVisualizer<Node> treeVisualizer = new TreeVisualizer<MctsTraceModel.Node>(mtd.getRoot()) {
                private static final long serialVersionUID = 4611171699699919259L;

                @Override
                protected String getLabel(Node tNode) {
                    return tNode.label;
                }

                @Override
                protected Color getColor(Node tNode) {
                    return tNode.active ? new Color(200, 0, 0) : Color.LIGHT_GRAY;
                }

                @Override
                protected Node[] getChildren(Node tNode) {
                    return tNode.children;
                }
            };
            treeVisualizer.setSize(treeVisualizer.getDimensions());
            treeVisualizer.setPreferredSize(treeVisualizer.getDimensions());
            return new JScrollPane(treeVisualizer);
        } else {
            throw new RuntimeException("Tracable type not supported");
        }
    }
}
