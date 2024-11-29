package br.edu.ifpb.pdist.nfs;

import java.util.ArrayList;
import java.util.List;

public class NFSServer implements NFSService {
	
	private List<String> files;
	
	public NFSServer() {
		this.files = new ArrayList<String>();
		
		// mimicking a file server with an array list
		files.add("README.md");
		files.add("script.py");
		files.add("view.html");
		files.add("utils.ts");
		files.add("styles.scss");
	}

	@Override
	public String readdir(String dir) {
		String output = "===== Files =====\n";
		
		for (String file : files) {
			output += file + "\n";
		}
		
		output += "=================\n";
		
		return output;
	}

	@Override
	public void rename(String currentName, String newName) {
		for (int i = 0; i < files.size(); i++) {
			// find current file name and replace it with a new name
			if (currentName.equals(files.get(i))) {
				files.set(i, newName);
				return;
			}
		}
		
	}

	@Override
	public void create(String fileNameWithExtension) {
		files.add(fileNameWithExtension);
	}

	@Override
	public void remove(String fileName) {
		files.remove(fileName);
	}

}
