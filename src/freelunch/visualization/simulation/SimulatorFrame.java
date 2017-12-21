/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.visualization.simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import freelunch.benchmarks.PlanningProblem;



public class SimulatorFrame<T extends PlanningProblem> extends JFrame {

    private static final long serialVersionUID = -4348477417133852657L;
    private final JPanel contentPane;
    final JSplitPane splitPane = new JSplitPane();
    final JScrollPane rightScrollPane = new JScrollPane();
    final JScrollPane leftScrollPane = new JScrollPane();
    final JPanel panel = new JPanel();
    final JPanel rightPanel = new JPanel();
    final JPanel panel_1 = new JPanel();
    final JPanel panel_5 = new JPanel();
    final JButton btnMakePlan = new JButton("Make plan");
    final JButton btnStart = new JButton("Start");
    final JButton btnStop = new JButton("Stop");
    final JComboBox<Object> comboBox = new JComboBox<Object>();
    final JPanel panel_2 = new JPanel();
    final JComboBox<Object> speedComboBox = new JComboBox<Object>();
    final ProblemSelector<T> problemSelector = new ProblemSelector<T>();
    final BasicSettingsPanel basicSettingsPanel = new BasicSettingsPanel();
    final JScrollPane consoleScrollPane = new JScrollPane();
    final JTextArea textArea = new JTextArea();

    /**
     * Create the frame.
     */
    public SimulatorFrame() {
        setTitle("FreeLunch simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 768, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        splitPane.setContinuousLayout(true);
        splitPane.setName("splitPane");

        contentPane.add(splitPane, BorderLayout.CENTER);
        rightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        rightScrollPane.setName("rightScrollPane");

        splitPane.setRightComponent(rightScrollPane);
        rightPanel.setName("rightPanel");

        rightScrollPane.setViewportView(rightPanel);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(panel_2, BorderLayout.CENTER);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        panel_2.setLayout(gbl_panel_2);
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_5.weightx = 1.0;
        gbc_panel_5.weighty = 1.0;
        gbc_panel_5.anchor = GridBagConstraints.WEST;
        gbc_panel_5.insets = new Insets(5, 5, 5, 5);
        gbc_panel_5.gridx = 0;
        gbc_panel_5.gridy = 0;
        panel_2.add(panel_5, gbc_panel_5);
        panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Controls", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_5.setName("panel_2");
        GridBagLayout gbl_panel_5 = new GridBagLayout();
        gbl_panel_5.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
        gbl_panel_5.columnWeights = new double[] { 1.0 };
        panel_5.setLayout(gbl_panel_5);
        btnMakePlan.setName("btnMakePlan");

        GridBagConstraints gbc_btnMakePlan = new GridBagConstraints();
        gbc_btnMakePlan.weighty = 1.0;
        gbc_btnMakePlan.weightx = 1.0;
        gbc_btnMakePlan.anchor = GridBagConstraints.WEST;
        gbc_btnMakePlan.insets = new Insets(5, 5, 5, 0);
        gbc_btnMakePlan.gridx = 0;
        gbc_btnMakePlan.gridy = 0;
        panel_5.add(btnMakePlan, gbc_btnMakePlan);
        btnStart.setName("btnStart");

        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.weighty = 1.0;
        gbc_btnStart.weightx = 1.0;
        gbc_btnStart.anchor = GridBagConstraints.WEST;
        gbc_btnStart.insets = new Insets(5, 5, 5, 0);
        gbc_btnStart.gridx = 0;
        gbc_btnStart.gridy = 1;
        panel_5.add(btnStart, gbc_btnStart);
        btnStop.setName("btnStop");

        GridBagConstraints gbc_btnStop = new GridBagConstraints();
        gbc_btnStop.anchor = GridBagConstraints.WEST;
        gbc_btnStop.weightx = 1.0;
        gbc_btnStop.weighty = 1.0;
        gbc_btnStop.insets = new Insets(5, 5, 5, 0);
        gbc_btnStop.gridx = 0;
        gbc_btnStop.gridy = 2;
        panel_5.add(btnStop, gbc_btnStop);
        speedComboBox.setName("speedComboBox");

        GridBagConstraints gbc_speedComboBox = new GridBagConstraints();
        gbc_speedComboBox.weightx = 1.0;
        gbc_speedComboBox.weighty = 1.0;
        gbc_speedComboBox.insets = new Insets(5, 5, 5, 0);
        gbc_speedComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_speedComboBox.gridx = 0;
        gbc_speedComboBox.gridy = 3;
        panel_5.add(speedComboBox, gbc_speedComboBox);
        comboBox.setName("comboBox");

        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.weighty = 1.0;
        gbc_comboBox.weightx = 1.0;
        gbc_comboBox.insets = new Insets(5, 5, 5, 0);
        gbc_comboBox.gridx = 0;
        gbc_comboBox.gridy = 4;
        panel_5.add(comboBox, gbc_comboBox);
        problemSelector.setName("problemSelector");

        GridBagConstraints gbc_problemSelector = new GridBagConstraints();
        gbc_problemSelector.insets = new Insets(0, 0, 5, 0);
        gbc_problemSelector.weighty = 1.0;
        gbc_problemSelector.weightx = 1.0;
        gbc_problemSelector.fill = GridBagConstraints.BOTH;
        gbc_problemSelector.gridx = 0;
        gbc_problemSelector.gridy = 5;
        panel_5.add(problemSelector, gbc_problemSelector);

        GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
        gbc_settingsPanel.fill = GridBagConstraints.BOTH;
        gbc_settingsPanel.gridx = 0;
        gbc_settingsPanel.gridy = 6;
        basicSettingsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        basicSettingsPanel.setName("basicSettingsPanel");

        GridBagConstraints gbc_basicSettingsPanel = new GridBagConstraints();
        gbc_basicSettingsPanel.fill = GridBagConstraints.BOTH;
        gbc_basicSettingsPanel.gridx = 0;
        gbc_basicSettingsPanel.gridy = 6;
        panel_5.add(basicSettingsPanel, gbc_basicSettingsPanel);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(5, 5, 5, 5);
        gbc_panel_1.weightx = 1.0;
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.weighty = 1.0;
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        panel_2.add(panel_1, gbc_panel_1);
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Output", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setName("panel_1");
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.rowWeights = new double[] { 1.0 };
        gbl_panel_1.columnWeights = new double[] { 1.0 };
        panel_1.setLayout(gbl_panel_1);
        consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        consoleScrollPane.setName("consoleScrollPane");

        GridBagConstraints gbc_consoleScrollPane = new GridBagConstraints();
        gbc_consoleScrollPane.insets = new Insets(2, 2, 2, 2);
        gbc_consoleScrollPane.fill = GridBagConstraints.BOTH;
        gbc_consoleScrollPane.gridx = 0;
        gbc_consoleScrollPane.gridy = 0;
        panel_1.add(consoleScrollPane, gbc_consoleScrollPane);
        textArea.setRows(10);
        textArea.setEditable(false);
        textArea.setColumns(15);
        textArea.setBackground(Color.WHITE);
        textArea.setName("consoleTextArea");

        consoleScrollPane.setViewportView(textArea);
        leftScrollPane.setName("scrollPane_1");

        splitPane.setLeftComponent(leftScrollPane);
        panel.setName("panel");

        leftScrollPane.setViewportView(panel);
        splitPane.setDividerLocation(500);
    }

}
