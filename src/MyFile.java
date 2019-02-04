import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFile {
	private String name;
	private final List<MyFile> subDirectories;
	private final MyFile parentDirectory;
	
	//file attributes
	private boolean isDirectory;
	private boolean isRoot;
	private Date createDate;
	private int size;
	private final int DEFAULT_SIZE = 0;
	
	private static MyFile currentDirectory;
	private static MyFile rootDirectory;
	
	
	public MyFile(MyFile parent, String name, boolean isDirectory) {
		this.parentDirectory = parent;
		this.name = name;
		this.isDirectory = isDirectory;
		this.isRoot = (parent==null) ? true : false;
		createDate = new Date();
		subDirectories = new ArrayList<>();
		size = DEFAULT_SIZE;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getIsDirectory() {
		return this.isDirectory;	
	}

	public List<MyFile> getSubDirectories() {
		return this.subDirectories;
	}

	public MyFile getParent() {
		return this.parentDirectory;
	}
	
	public static void setCurrentDirectory(MyFile directory) {
		currentDirectory = directory;
	}
	
	public static MyFile getCurrentDirectory() {
		return currentDirectory;
	}
	
	public static void setRootDirectory(MyFile root) {
		if(rootDirectory == null) {
			rootDirectory = root;
		}
		else {
			System.out.println("Root Directory already set. Cannot be set again");
		}
	}
	
	public static MyFile getRootDirectory() {
		return rootDirectory;
	}
	
}
