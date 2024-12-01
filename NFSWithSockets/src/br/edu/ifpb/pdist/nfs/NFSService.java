package br.edu.ifpb.pdist.nfs;

import java.io.IOException;

public interface NFSService {

	String readdir(String dir) throws IOException;

	void rename(String currentName, String newName);

	void create(String path) throws IOException;

	void remove(String fileName);
}
