package freelunch.sat.bce.encoding.aig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freelunch.sat.bce.utilities.Logger;

public class AigerCircuit {
	
	public static final int TRUE = Integer.MAX_VALUE;
	public static final int FALSE = -TRUE;
	
	
	public static class AndGate {
		private int inLeft;
		private int inRight;
		private int out;
		
		public AndGate(int out, int inLeft, int inRight) {
			this.out = out;
			this.inLeft = inLeft;
			this.inRight = inRight;
		}

		@Override
		public String toString() {
			String left = inLeft == TRUE ? "TRUE" : inLeft == FALSE ? "FALSE" : Integer.toString(inLeft);
			String right = inRight == TRUE ? "TRUE" : inRight == FALSE ? "FALSE" : Integer.toString(inRight);
			return String.format("%d = (%s AND %s)", out, left, right);
		}
		
		public String toAiger() {
			int left = inLeft == TRUE ? 1 : inLeft == FALSE ? 0 : inLeft > 0 ? inLeft<<1 : ((-inLeft)<<1)+1; 
			int right = inRight == TRUE ? 1 : inRight == FALSE ? 0 : inRight > 0 ? inRight<<1 : ((-inRight)<<1)+1; 
			return String.format("%d %d %d", out<<1, left, right);
		}
	}
	
	private List<Integer> inputs;
	private List<Integer> outputs;
	private List<AndGate> gates;
	private int totalVariables;
	
	public AigerCircuit() {
		setInputs(new ArrayList<Integer>());
		setOutputs(new ArrayList<Integer>());
		setGates(new ArrayList<AndGate>());
	}
	
    public void printToAigerFile(String filename) throws IOException {
    	Logger.print(1, "c writing aiger file");
		FileWriter out = new FileWriter(filename);
		// header
        out.write(String.format("aag %d %d 0 %d %d\n", totalVariables, inputs.size(), outputs.size(), gates.size()));
        // inputs
        for (int input : inputs) {
        	out.write(String.format("%d\n", input << 1));
        }
        // outputs
        for (int output : outputs) {
        	out.write(String.format("%d\n", output > 0 ? output << 1 : (output << 1) + 1));
        }
        // gates
        for (AndGate gate : gates) {
        	out.write(String.format("%s\n", gate.toAiger()));
        }

        out.close();
    }

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("inputs: " + inputs.toString() + "\n");
		sb.append("outputs: " + outputs.toString() + "\n");
		for (AndGate gate : gates) {
			sb.append(gate.toString()+"\n");
		}
		return sb.toString();
	}

	public List<AndGate> getGates() {
		return gates;
	}

	public void setGates(List<AndGate> gates) {
		this.gates = gates;
	}

	public List<Integer> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Integer> outputs) {
		this.outputs = outputs;
	}

	public List<Integer> getInputs() {
		return inputs;
	}

	public void setInputs(List<Integer> inputs) {
		this.inputs = inputs;
	}

	public int getTotalVariables() {
		return totalVariables;
	}

	public void setTotalVariables(int totalVariables) {
		this.totalVariables = totalVariables;
	}
	
}
