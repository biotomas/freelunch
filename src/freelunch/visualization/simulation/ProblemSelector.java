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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import freelunch.benchmarks.PlanningProblem;
import freelunch.benchmarks.PlanningProblemGenerator;



public class ProblemSelector<T extends PlanningProblem> extends JPanel {
    private static final long serialVersionUID = 7245433273319318987L;
    public static final String PROPERTY_GENERATOR = "property_generator";

    private PlanningProblemGenerator<T> generator;
    private T[] problems;
    private T randomProblem;

    final JComboBox<Object> comboBox = new JComboBox<Object>();
    final JButton btnGenerateNew = new JButton("Generate new");

    /**
     * Create the panel.
     */
    public ProblemSelector() {
        setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Generator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 62, 0 };
        gridBagLayout.rowHeights = new int[] { 23, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);
        comboBox.setName("comboBox");

        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.gridx = 0;
        gbc_comboBox.gridy = 0;
        add(comboBox, gbc_comboBox);
        btnGenerateNew.setName("btnGenerateNew");

        GridBagConstraints gbc_btnGenerateNew = new GridBagConstraints();
        gbc_btnGenerateNew.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnGenerateNew.anchor = GridBagConstraints.NORTH;
        gbc_btnGenerateNew.gridx = 0;
        gbc_btnGenerateNew.gridy = 1;
        add(btnGenerateNew, gbc_btnGenerateNew);
    }

    public void setGenerator(final PlanningProblemGenerator<T> generator) {
        this.generator = generator;

        if (generator.isStatic()) {
            btnGenerateNew.setEnabled(false);
            this.problems = generator.getProblems();
            comboBox.setModel(new DefaultComboBoxModel<Object>(generator.getNames()));
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProblemSelector.this.firePropertyChange(PROPERTY_GENERATOR, null, getPlanningProblem());
                }
            });
        } else {
            comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "RANDOMLY GENERATED" }));
            comboBox.setEnabled(false);
            btnGenerateNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    randomProblem = generator.getNext();
                    ProblemSelector.this.firePropertyChange(PROPERTY_GENERATOR, null, getPlanningProblem());
                }
            });
            randomProblem = generator.getNext();
        }
        this.validate();
    }

    @SuppressWarnings("unchecked")
    public T getPlanningProblem() {
        return generator.isStatic() ? (T) problems[comboBox.getSelectedIndex()].copy() : randomProblem;
    }

}
