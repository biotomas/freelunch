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
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import freelunch.core.planning.model.BasicSettings;


public class BasicSettingsPanel extends JPanel {

    private static final long serialVersionUID = -5438034355501642747L;
    public static final String PROPERTY_GENERATOR = "property_generator";
    final JLabel lblNewLabel = new JLabel("Time limit");
    final JTextField textField = new JTextField();
    final JCheckBox chckbxVerbose = new JCheckBox("Verbose");

    private int timeLimitValue = 0;

    /**
     * Create the panel.
     */
    public BasicSettingsPanel() {
        textField.setName("textField");
        textField.setColumns(10);
        setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Generator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 62, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 23, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);
        lblNewLabel.setName("lblNewLabel");

        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(5, 5, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        add(lblNewLabel, gbc_lblNewLabel);

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.anchor = GridBagConstraints.WEST;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        add(textField, gbc_textField);
        chckbxVerbose.setName("chckbxVerbose");

        GridBagConstraints gbc_chckbxVerbose = new GridBagConstraints();
        gbc_chckbxVerbose.insets = new Insets(5, 0, 5, 5);
        gbc_chckbxVerbose.anchor = GridBagConstraints.WEST;
        gbc_chckbxVerbose.gridx = 0;
        gbc_chckbxVerbose.gridy = 1;
        add(chckbxVerbose, gbc_chckbxVerbose);

        chckbxVerbose.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textField.setText("" + timeLimitValue);
        init();
    }

    private void init() {
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                int val = timeLimitValue;
                try {
                    val = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    textField.setText("" + timeLimitValue);
                    return false;
                }
                timeLimitValue = val;
                return true;
            }
        });
    }

    public BasicSettings getSettigs() {
        return new BasicSettings(timeLimitValue, chckbxVerbose.isSelected());
    }

}
