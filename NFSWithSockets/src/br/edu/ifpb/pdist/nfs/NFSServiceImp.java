package br.edu.ifpb.pdist.nfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NFSServiceImp implements NFSService {
	
	private static String NFS_HOME = "/tmp/my_tmp_nfs/";
	
	public NFSServiceImp() {
	}

	@Override
	public String readdir(String path) throws IOException {
		try (Stream<Path> stream = Files.list(Paths.get(NFS_HOME + path))) {
			return stream
					.map(Path::getFileName)
					.map(Path::toString)
					.collect(Collectors.toSet()).toString();
		}
	}

	@Override
	public void rename(String currentName, String newName) {
		File fileToRename = new File(NFS_HOME + currentName);
		boolean renamed = fileToRename.renameTo(new File(NFS_HOME + newName));
		
		if (!renamed) {
			System.out.println("No such file");
			return;
		}
		
		System.out.println("File renamed successfully!");
	}

	@Override
	public void create(String path) throws IOException {
		Files.createFile(Paths.get(NFS_HOME + path));
	}

	@Override
	public void remove(String path) {
		File fileToDelete = new File(NFS_HOME + path);
		boolean deleted = fileToDelete.delete();
		
		if (!deleted) {
			System.out.println("No such file.");
			return;
		}
		
		System.out.println("File deleted successfully!");
	}

}
