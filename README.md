# 01418211 Software Construction 
---
### Mailbox For Residence Project KU CS 2020 
> **My Project** [Bitbucket](https://bitbucket.org/6210402453/6210402453/src/master/) 
> 6210402453 - Trus Arjarsiri // Computer-Science - Kasetsart University.
#### My Features! (Update 2020‑10-17)
  - **Admin Users**
    - Host adding / blocking.
    - List of host with latest access.
    - Admin changing password.
  - **Host Users**
    - Resident adding / removing.
    - List of residents with their room.
    - Room adding.
    - List of room.
    - Stock any letter, document or package. (with upload picture if you want.)
    - Host changing password.
  - **Resident**
    - View all in stock list / received list.
    - Resident changing password. 
  - **About ME**
  - **Info** - Manual Guide for all users.

### Commit History (Update 2020‑10-17)
  - **2020‑09‑21**
    - [6d40d92](https://bitbucket.org/6210402453/6210402453/commits/6d40d92db9719185472c8194e93cc8d8bcf3c1a7) - Add maven to project.
    - [3d62af8](https://bitbucket.org/6210402453/6210402453/commits/6d40d92db9719185472c8194e93cc8d8bcf3c1a7) - Change name Main.java -> MailboxMain.java
  - **2020‑09‑22**
    - [67a3ab2](https://bitbucket.org/6210402453/6210402453/commits/67a3ab2ee0d29b9b0cba2bb32ed65e8e1d6d18cf) - Add aboutMe to project.
  - **2020‑09‑30**
    - [433ed7f](https://bitbucket.org/6210402453/6210402453/commits/433ed7fc4921c00f6464883ccab003f01bb183fa) - Add admin changing password, admin host adding / blocking and OOP coding.
  - **2020‑10‑01**
    - [9088463](https://bitbucket.org/6210402453/6210402453/commits/90884637213dd7a9501fffd7824e4d3322203191) - Add reading and writing external files to project.
  - **2020‑10‑04**
    - [147eb51](https://bitbucket.org/6210402453/6210402453/commits/147eb51748efe17352f4d2986e04548704a2b473) - Adjust reading and writing external files in project.
  - **2020‑10‑07**
    - [788ddd5](https://bitbucket.org/6210402453/6210402453/commits/788ddd597689c8595cd7629f1362da6dd9d4245d) - Modify code, add host changing password.
    - [52985ae](https://bitbucket.org/6210402453/6210402453/commits/52985ae122c6bce4bd8d67469f0c5eec99ca9dbf) - Add admin host lists and sort admin host list by latest access date and time.
  - **2020‑10‑13**
    - [168c90b](https://bitbucket.org/6210402453/6210402453/commits/168c90beaed24419032e540c81f519cfdab7f95a) - Add host resident adding / removing and host room adding.
  - **2020‑10‑17**
    - [252fb9f](https://bitbucket.org/6210402453/6210402453/commits/252fb9f21da43a01b0d8ab374237c30064355c70) - Add host room list and host residents list to project.
  - **2020-10-20**
    - [ccda742](https://bitbucket.org/6210402453/6210402453/commits/ccda742d6cea0cd75a8a3e2faa1461328563cca3) - Add stock manage, list of items, received list, with upload picture.
  - **2020-10-21**
    - [47f3fcc](https://bitbucket.org/6210402453/6210402453/commits/47f3fcccb3e4c0c8b1be244573789b222aa7d6ca) - Add README.md and PDF.
  - **2020-10-24**
    - [105b4c7](https://bitbucket.org/6210402453/6210402453/commits/105b4c798b943af8e2386547cdfbae614ba01e36) - Add residents user and search bar.
  - **2020-10-27**
    - [590da92](https://bitbucket.org/6210402453/6210402453/commits/590da9251de853781b127e35e564c961946f65dc) - Check Project : 1
  - **2020-10-28**
    - [753c6c7](https://bitbucket.org/6210402453/6210402453/commits/753c6c74a98ebfeec5cacfaac1d4530bbec290aa) - Resident can only view their own item in Resident Stock List and Resident Receive List
  - **2020-11-1**
    - [e74835d](https://bitbucket.org/6210402453/6210402453/commits/e74835d232223c4bc694d11790d990242202a283) - Fix table sort by time from pattarn dd/MM/yy to yyyy/MM/dd.
    - [5117bd6](https://bitbucket.org/6210402453/6210402453/commits/5117bd69876c5e005142bc401c4048772869cc3e) - Add Jarfile to atMyMailForResidence directory.
    - [b5c6c3c](https://bitbucket.org/6210402453/6210402453/commits/b5c6c3c41ffc13d7cab2761caf018a448109a3b1) - Add Model UML Class Diagram.

### Project Structure (Update 2020‑10-17)
##### Directory
  - **data** - CSV files.
  - **src** - Source code.
    - **main** - Main work directory. 
        - **java** - All java files.
            - **mailbox** Java files of mailbox application.
                - **controller** - Accepts input from user. 
                - **model** - Manages the data, logic of the application.
                - **service** - Manages reading and writing external files.
        - **resource** - Displayed on the user interface.
  - **stockImage** - Image that you upload in application will be store here.
  - **ModelUML** - UML Class Diagram of java file in Model directory.
    
### Installation
##### Execute Application
> Window


  - Double-click on jar files to execute
    - if can not execute with double-click .
        - open git bash (right click if you have git) or 
          Powershell window. (hold shift and right click in jar file directory.)
        - use "**java -jar <filename.jar>" to execute**".
        
        
> MacOS


  - Right Click on jar file then **Open**

### Starter Guide (Update 2020‑10-17)
  - **Admin**
    - username : admin
    - password : admin (*default*)
    - **Select Admin on main page to admin login page**
      - After login you will in the host adding page (**plus sign**) input host data to add host and    the host you added will show in host lists page. (**list sign**)
      - You can block or unblock any host by typing host username in textfield. (**ban sign**)
      - You can also change admin password by enter changing password page. (**key sign**)
      - Logout by click on logout button (**logout sign**)
  - **Host** (*Can be accessed when admin has added host*)
    - username : host
    - password : host (*defualt*)
    - **Select Host on main page to host login page**
      - you have to add some room (**building sign**) to add resident in to the room and the room you added will show in room lists page. (**room page (building sign) - Room List**)
      - After add some room you're allow to add resident (**people sign - Adding Resident**) input your resident data to add resident and the resident you added will show in residents lists page. (**add page (people sign) - Residents List**)
      - You can remove any resident by select resident in resident lists. (**people sign - Residents List**)
      - You can also change your password by enter changing password page. (**key sign**)
      - Logout by click on logout button (**logout sign**)
  - **Resident** (Can be access after host adding resident)
    - username : resident
    - password : resident (*defualt*)
    - **Select Resident on main page to resident login page**
      - After you got added by host, use username that host gave you to register.
      - You can view all in stock list and received list (**boxes sign and check sign**) but can't manage any data.
      - You can also change your password by enter changing password page. (**key sign**)
      - Logout by click on logout button (**logout sign**)
---
### Patch Notes 1.01 (Editing 2020-11-26 commit - [08fc25](https://bitbucket.org/6210402453/6210402453/commits/08fc2553fbeb5ef2ccae419d415383a8addcfe03))
   - Remove the Class that unnecessary. (One list for one Stock -> One list for all Stock)
   - Remove the Package that unnecessary. (One package for one interface/implementations -> One package for all interface class and one package for all implementation class.)
   - Change name of package for easier to understand.
   - All of the writing and reading classes implementations from only one interface class.
   - When input entered incorrectly, the textfield of that input cleared.
---