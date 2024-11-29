package br.edu.ifpb.pdist.nfs;

public interface NFSService {

	String readdir(String dir);

	void rename(String currentName, String newName);

	void create(String fileNameWithExtension);

	void remove(String fileName);
}
