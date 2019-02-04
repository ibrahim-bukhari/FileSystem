import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RunFileSystem {

	public static void main(String[] args) {
		MyFile treeRootNode = new MyFile(null, "root", true);
		MyFile.setCurrentDirectory(treeRootNode);
		MyFile.setRootDirectory(treeRootNode);

		Scanner scan = new Scanner(System.in);
		System.out.println("--Command List--");
		System.out.println("Make Directory: mkdir [name]");
		System.out.println("Make File: mkfile [name]");
		System.out.println("Remove Directory: rmdir [name]");
		System.out.println("Change Directory: cd [name]");
		System.out.println("Go To Root: cd_slash");
		System.out.println("Go To Parent: cd..");
		System.out.println("List Directory Contents: ls");
		System.out.println("Current Directory Path: pwd");
		System.out.println("Stop Program: exit");
		
		while(true) {
			System.out.print("Enter Command:");
			String input = scan.nextLine();
			String[] arr = input.split(" ");

			switch (arr[0]) {
			case "mkdir":  
				mkdir(MyFile.getCurrentDirectory(), arr[1], true);
				break;
			case "mkfile":  
				mkdir(MyFile.getCurrentDirectory(), arr[1], false);
				break;
			case "rmdir":  
				rmdir(arr[1]);
				break;
			case "cd":  
				cd(arr[1]);
				break;
			case "cd_slash":
				cdSlash();
				break;
			case "cd..":
				cdDotDot();
				break;
			case "ls":
				ls();
				break;
			case "pwd":
				pwd();
				break;
			case "exit":
				return;
			default: 
				System.out.println("Invalid Command");
				showDirectoryContent(MyFile.getCurrentDirectory(), " ");
				break;
			}
		}
	}

	//create directory
	private static MyFile mkdir(MyFile parent, String name, boolean isDirectory) {
		MyFile node = new MyFile(parent, name, isDirectory);
		node.setName(name);
		parent.getSubDirectories().add(node);
		System.out.println("Directory created");
		return node;
	}

	//remove directory
	private static void rmdir(String name) {
		MyFile dir = MyFile.getCurrentDirectory();
		List<MyFile> fileList = dir.getSubDirectories();
		if(fileList == null || fileList.isEmpty()) {
			System.out.println("Empty Folder");
		}
		else {
			boolean fileDeleted = false;
			for(MyFile file : fileList) {
				if(file.getName().equals(name)) {
					fileDeleted = true;
					fileList.remove(file);
					break;
				}
			}
			if(fileDeleted) {
				System.out.println("File Successfully Deleted");
			}
			else {
				System.out.println("No file with that name");
			}
		}

	}

	//go to parent directory
	private static void cdSlash() {
		MyFile.setCurrentDirectory(MyFile.getRootDirectory());
		System.out.println("Current directory set: " + MyFile.getCurrentDirectory().getName());
	}


	//go to parent directory
	private static void cdDotDot() {
		MyFile.setCurrentDirectory(MyFile.getCurrentDirectory().getParent());
		System.out.println("Current directory set: " + MyFile.getCurrentDirectory().getName());
	}

	//list contents of directory sorted by name
	private static void ls() {
		//showDirectoryContent(MyFile.getCurrentDirectory());
		MyFile dir = MyFile.getCurrentDirectory();
		List<MyFile> fileList = dir.getSubDirectories();
		if(fileList == null || fileList.isEmpty()) {
			System.out.println("Empty Folder");
		}
		else {
			// Sorting
			Collections.sort(fileList, new Comparator<MyFile>() {
			        @Override
			        public int compare(MyFile file2, MyFile file1)
			        {
			            return  file2.getName().compareTo(file1.getName());
			        }
			    });
			
			for(MyFile file : fileList) {
				System.out.println(file.getName());
			}
		}
	}

	//show current folder path
	private static void pwd() {
		getFullPath(MyFile.getCurrentDirectory());
	}
	private static void getFullPath(MyFile directory) {
		if(directory==null) {
			System.out.println();
			return;
		}
		System.out.print("\\" + directory.getName());
		getFullPath(directory.getParent());
		//for (MyFile each : directory.getSubDirectories()) {
		//	showDirectoryContent(each, appender + appender);
		//}
	}


	//change directory
	private static void cd(String name) {
		MyFile dir = MyFile.getCurrentDirectory();
		List<MyFile> fileList = dir.getSubDirectories();
		if(fileList == null || fileList.isEmpty()) {
			System.out.println("Empty Folder");
		}
		else {
			boolean folderFound = false;
			for(MyFile file : fileList) {
				if(!file.getIsDirectory()) {
					if(file.getName().equals(name)) {
						folderFound = true;
						MyFile.setCurrentDirectory(file);
						break;
					}
				}
				else {
					System.out.println("Selected file is not a directory");
					return;
				}
				
			}
			if(folderFound) {
				System.out.println("Current folder changed");
			}
			else {
				System.out.println("No file with that name");
			}
		}
	}

	private static void showDirectoryContent(MyFile node, String appender) {
		System.out.println(appender + node.getName());
		for (MyFile each : node.getSubDirectories()) {
			showDirectoryContent(each, appender + appender);
		}
	}

}
