package br.edu.ifpb.pdist.nfs;

/**
 * Enumeration to name file commands and make it easier to modify those command labels
 */
public enum FileCommand {
	READDIR("readdir"),
	RENAME("rename"),
	CREATE("create"),
	REMOVE("remove"),
	STOP_CLIENT("x");
	
	public final String label;
	
	private FileCommand(String label) {
		this.label = label;
	}
	
	public static FileCommand valueOfLabel(String label) {
	    for (FileCommand command : values()) {
	        if (command.label.equals(label)) {
	            return command;
	        }
	    }
	    return null;
	}
}
