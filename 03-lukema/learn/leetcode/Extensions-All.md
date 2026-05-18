# Extensions for VS Code

## Extenstion Icon

    Extensions Icon (The square icon on top left side of "Activity Bar")
    Or
    Ctrl + Shift + X 

    /c/Users/lma/.vscode/extensions> ls -1 | xargs -L 1 echo code --install-extension > vscode-extensions.txt

## Installed Extensions

    C:\Users\lma\.vscode\extensions
    bs
    ls -1
    #
    # This deletes all installed extesions:
    rm -rf /c/Users/lma/.vscode

## Clean up vscode plugin caches

    # use case: "Spring Boot Dashboard" extension plugin misplaced "Beans" and "Endpoint Mappings" into "Explorer" view,
    #           instead of correctly placed these views in "Spring Boot Dashboard" view.
    # to fix "Spring Boot Dashboard" misplacement error, in linux under `/home/lma/.config/Code/User`:
    cd /home/lma/.config/Code/User
    rm -rf workspaceStorage/
    rm -rf globalStorage/
    rm -rf workspaceStorage/
    rm -rf History/

    # in laptop/windows, this is under `(gitbash) /c/Users/lma/AppData/Roaming/Code/User`, or `C:\Users\lma\AppData\Roaming\Code\User`:
    (gitbash) cd /c/Users/lma/AppData/Roaming/Code/User
    rm -rf workspaceStorage/
    rm -rf globalStorage/
    rm -rf workspaceStorage/
    rm -rf History/

## Change Color Theme to `Dark+`

   Visual Studio Code --> (menu bar) File --> Preferences --> Color Theme --> Dark+

## 1. Eclipse kepmap

## 2. lombok

## 3. npm Intellisense

## 4 (1/4). Extension Pack for Java (by Microsoft)

Note: **Extension Pack for Java (by Microsoft)** is an extension pack for Java, with six extensions.

1. Language Support for Java™ by Red Hat
      * Code Navigation
      * Auto Completion
      * Refactoring
      * Code Snippets
  
      ////////////////////////////////////////////////
      // [Language Support for Java(TM) by Red Hat]<https://marketplace.visualstudio.com/items?itemName=redhat.java>
      // `java.home` : Deprecated, please use `java.jdt.ls.java.home` instead. Absolute path to JDK home folder used
      // to launch the Java Language Server. Requires VS Code restart.
      // (1) Add to .vscode/settions.json: "java.jdt.ls.java.home": "C:/02-LukeTools/jdk-19.0.2". "java.home" had been deprectaed.
      // (2) Set JAVA_HOME to "C:/02-LukeTools/jdk-19.0.2"
      ////////////////////////////////////////////////

2. Dependency Analytics by Red Hat
3. Debugger for Java
      * Debugging
4. Test Runner for Java
      * Run & Debug JUnit/TestNG Test Cases
5. Maven for Java
      * Project Scaffolding
      * Custom Goals
6. Project Manager for Java
      * Manage Java projects, referenced libraries, resource files, packages, classes, and class members
7. Visual Studio IntelliCode
      * AI-assisted development
      * Completion list ranked by AI

### Debugger for Java

* `F5` to debug java
* `Ctrl+F5` to run java
* `Ctrl+Shift+D` for Run and Debug view
* `Ctrl+Shift+E` for File Explorer view
* `.vscode\launch.json` <--- Remove `launch.json`? "Yes". `launch.json` will bind `F5` to the first item in `launch.json`, instead of run **Currently Selected/Opened File**
  * Add Configuration
  * Select Configuration

    // Sample `.vscode\launch.json`
    {
      // Use IntelliSense to learn about possible attributes.
      // Hover to view descriptions of existing attributes.
      // For more information, visit: <https://go.microsoft.com/fwlink/?linkid=830387>
      "version": "0.2.0",
      "configurations": [
        {
          "type": "java",
          "name": "Launch RecursionToInteration",
          "request": "launch",
          "mainClass": "com.learn.other.RecursionToInteration",
          "projectName": "leetcode",
          "args": "Test1 Test1 Test3"
        },
      ]
    }

## 4 (2/4). Other Java Extensions

Search "Java" extensions

1. "Java Language Support" by George Fraser (aka `georgewfraser.vscode-javac`)  <=== It has "Format/Lint on Save" feature

    Bad 1: `georgewfraser.vscode-javac` causes **vscode on aws linux workspace** unable to resolve symbol `log`
           [Symbols not getting resolved for lombok on vs code]<https://github.com/microsoft/vscode-lombok/issues/72>
           **Resolution**: Remove `georgewfraser.vscode-javac` from **vscode on aws linux workspace**. But on windows there is no such error.

    Bad 2: This Extension causes "Couldn't start client Java Language Server" error.
           **Resolution**: Do nothing because there is side effect observed.

    Good 1: It has "Format/Lint on Save" feature.

    This Extension causes "Couldn't start client Java Language Server" error. Based on
    <https://github.com/redhat-developer/vscode-java/issues/785> comments, Leomedina Leo suggests remove this extension.
    And the solution works to several users, including me.

    ////////////////////////////////////////////////
    // [Language Support for Java(TM) by Red Hat]<https://marketplace.visualstudio.com/items?itemName=redhat.java>
    // `java.home` : Deprecated, please use `java.jdt.ls.java.home` instead. Absolute path to JDK home folder used
    // to launch the Java Language Server. Requires VS Code restart.
    ////////////////////////////////////////////////
    // All of the following are invalid. All have been resolved with above solution.
    //
    // This extension has much wanted "Format/Lint on Save" features, and even perhaps autocomplete feature.
    // However, it generates the following error:
    // Couldn't start client Java Language Server
    // Source: Java Language Support (Extension)
    // Google search indicates WLS2's JAVA_HOME should be set correctly. After setting this thing, the error still persists.
    ////////////////////////////////////////////////
    This extension requires **Install same version of java on WSL2**. Steps:
    // Unable to resolve host name in `WSL`
    sudo vi /etc/resolv.conf
    // # nameserver 172.25.224.1
    nameserver 8.8.8.8
    nameserver 1.1.1.1
    // Java. **Open JDK** vs **Oracle Jav**: The former is open-source, whereas the latter is license-based.
    // **Oracle Java** is much better when it comes to performance and stability.
    cd /home/lma/02-LukeTools
    // `curl` does not work for *.gz file
    wget "https://download.oracle.com/java/19/latest/jdk-19_linux-x64_bin.tar.gz"
    tar zxvf jdk-19_linux-x64_bin.tar.gz

    // maven <https://maven.apache.org/download.cgi>
    // `curl` does not work for *.gz file
    wget "https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.tar.gz"
    tar zxvf apache-maven-3.8.7-bin.tar.gz

    // add `JAVA_HOME` to `/etc/environment`
    cat /etc/environment
    sudo vi /etc/environment
    PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin"
    export JAVA_HOME=/home/lma/jdk-19.0.2
    export PATH=${JAVA_HOME}/bin:$PATH

    // add `/etc/environment` to `/etc/profile`
    sudo vi /etc/rpofile
    // append to bottom
    if [ -f /etc/environment ]
    then
        source /etc/environment
    fi

2. "Spring Initializer for Java"
3. "Gradle for Java"
4. (double check this has been installed) "Dependency Analytics" by Red Hat - Part of "Language Support for Java™ by Red Hat"

## 4 (3/4) "EditorConfig for VS Code" by EditorConfig. File `.editorconfig`

    # Editor configuration, see https://editorconfig.org
    root = true
    
    [*]
    charset = utf-8
    indent_style = space
    indent_size = 2
    insert_final_newline = true
    trim_trailing_whitespace = true
    
    [*.java]
    indent_size = 4
    
    [*.ts]
    quote_type = single
    
    [*.md]
    max_line_length = off
    trim_trailing_whitespace = false

## 4 (4/4) Search `Spring` and `Spring Boot` extensions

## Angular

## 7. `beautify` is deprecated. Use `prettier`

To format HTML files, use built-in "HTML Language Features". Do NOT use Beautify.

## 8. TypeScript Importer

## 9. TypeScript Hero

## 10. ESLint

## 11. "REST Client" by Huachao Mao

## 12. Do NOT install all of "Angular Essentials" to avoid "Prettier". Pick the following from "Angular Essentials"

* Angular Snippets
* Angular Language Service
* EditorConfig for VS Code
* ESLint
* (Deprecated) npm (npm support for VS Code by Microsoft)

## 13. "IntelliCode" by Microsoft

## 14. Copy Extensions

## 15. Markdown Extensions

1. Markdown All In One -- by Yu Zhang
2. markdownlint -- by David Anson (Uses `.markdownlint.json` to customize lint.)

#################################
#################################
#################################

* In case extensions can not be installed from a network due to fire wall settings, one can copy (zip it first) a working `extensions` folder to destination computer.
* Location of the `extensions` folder: `C:/Users/lma/.vscode/extensions`

## **Optionals** Extensions

## A. (1/2) jshint. (2/2) Run 'npm install -g jshint'

## B. Tab size: 2 (spaces)

     VS-Code --> Files --> Preferences --> Settings --> (Search) tab size --> 2 (for both User and Workspace) 

## C. SVN
