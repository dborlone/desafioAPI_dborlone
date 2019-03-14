package br.com.pan.challenge.api.model;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.pan.challenge.api.model.anums.Region;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class State implements Comparable<State>{

	private Long id;
	private String shortName;
	private String name;
	private Region region;
	private Country country;

	/*
	 * Specifies non-natural comparation rules
	 */
	public static class StateComparator implements Comparator<State> {

		private static final String STATE_SAO_PAULO = "São Paulo";
		private static final String STATE_RIO = "Rio de Janeiro";

		@Override
		public int compare(State state1, State state2) {

			if (state1.getName().equals(STATE_SAO_PAULO)) {
				return -1;
			} else if (state2.getName().equals(STATE_SAO_PAULO)) {
				return 1;
			} else if (state1.getName().equals(STATE_RIO) && !state2.getName().equals(STATE_SAO_PAULO)) {
				return -1;
			}

			return state1.compareTo(state2);
		}

	}

	@Override
	public int compareTo(State state2) {
		return this.getName().compareTo(state2.getName());
	}

}
