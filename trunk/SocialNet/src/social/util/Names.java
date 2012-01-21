package social.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Names {
	List<String> firstNames = new ArrayList<String>();
	private static volatile  Names names = null;

	public static Names getInstance() throws IOException {
		if (names == null) {
			synchronized (Names.class) {
				if (names == null) {
					names = new Names();
				}
			}
		}
		return names;
	}

	private Names createInstance() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("imiona.txt"));
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			firstNames.add(tmp);
		}
		return names;
	}

	private Names() throws IOException {
		createInstance();
	}

	public List<String> getFirstNames() {
		return firstNames;
	}

}
