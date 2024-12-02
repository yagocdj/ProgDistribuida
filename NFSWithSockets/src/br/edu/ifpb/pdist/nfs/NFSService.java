package br.edu.ifpb.pdist.nfs;

public interface NFSService {

	String readdir(String path);

	void rename(String currentName, String newName);

	void create(String path);

	void remove(String path);
}
