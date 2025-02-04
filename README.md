# Simple CLI
A simple implementation of Unix-like shell, supports various commands and operators. Developed in Java.

## Featured Commands and Operators
### 1. Commands
- `pwd` Print the current working directory.  
- `cd` Change working directory to a specified path.  
- `ls` List files in the current directory, can take extra options as arguments:
  - `-a` Include hidden files.  
  - `-r` Reverse order listing.
- `touch` Create a file with each given name or update its last modification time.  
- `rm` Delete each given file.
- `echo` Write text to the shell.  
- `cat` Concatenates the content of each given file and prints it. if cat got no arguments, it will take input from user and print it to the shell.
- `mkdir` Create a directory for each given name.
- `rmdir` Deletes each given directory, only if it is empty.
- `mv`: Move/rename files or directories. 
   
### 2. Operators
- `>`: Redirect the output of the first command to be written to a file. (creates file if it doesn't exist)
- `>>`: Redirect the output of the first command to be appended to a file. (works only if file exists)
- `|`: Redirect the output of the left command as in input to the right command.

### 3. Internal Commands
- `exit`: Terminate the shell.  
- `help`: List all supported commands and how to use them.  
