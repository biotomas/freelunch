package freelunch.core.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParametersProcessor {
	
	private List<String> parameters;
	private Set<String> binaryOptions;
	private Map<String, String> options;
	
	public ParametersProcessor(String[] params) {
		parameters = new ArrayList<String>();
		binaryOptions = new HashSet<String>();
		options = new HashMap<String, String>();

		for (int i = 0; i < params.length; i++) {
			String param  = params[i];
			if (param.startsWith("-")) {
				if (param.contains("=")) {
					int eqIndex = param.indexOf("=");
					String option = param.substring(1, eqIndex);
					String value = param.substring(eqIndex+1);
					options.put(option, value);
				} else {
					String option = param.substring(1);
					binaryOptions.add(option);
				}
			} else {
				parameters.add(param);
			}
		}
	}
	
	public String getParameter(int index) {
		if (index < parameters.size()) {
			return parameters.get(index);
		} else {
			return null;
		}
	}
	
	public boolean isSet(String optionName) {
		return binaryOptions.contains(optionName);
	}
	
	public String getOptionValue(String optionName) {
		return options.get(optionName);
	}

}
