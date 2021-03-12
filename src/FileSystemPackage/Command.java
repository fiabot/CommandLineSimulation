/**
 * An interface for a command that can be run on a command line
 */
package FileSystemPackage;

public interface Command {
	/**
	 * Execute the command given the information and command line
	 * @param command additional information given by user
	 * @param cl command line to run command on
	 */
	public void execute (String command, CommandLine cl); 
	
	/**
	 * provides an explanation of function of command
	 * @return function and usage of command
	 */
	public String info(); 
}
