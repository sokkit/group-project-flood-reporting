# Flood Wrack Data

A crowdsourced project to collate reports of flood wracking and damage.

The GitLab repository is: https://git.cardiff.ac.uk/c2061455/wrack-report-2061455-personal-fork

1.	Go to the repository, select clone, and copy either the SSH or HTTPS url (Use HTTPS if you have not set up SSH).
2.	Open Git Bash in an empty directory on your machine.
3.	In Git Bash type in “git clone ” and paste your URL. Press enter.
4.	Open the project folder created in your directory.
5.	You now need to start the database. Connect to MySQL Server. You can do this in a program such as MySQL Workbench. 
6.	Run two SQL scripts located in your project folder. They are in /src/main/resources/. Run schema-wrack-report then data-wrack-report. The database is now ready
7.	Open command prompt (cmd) in the location of the folder from step 4
8.	Type “gradlew” and press enter
9.	Type “gradlew -PJASYPT_ENCRYPTOR_PASSWORD=T34M-3 build” and press enter. 
10.	Type “gradlew -PJASYPT_ENCRYPTOR_PASSWORD=T34M-3 bootrun” and press enter. The application is now running (if the previous step failed you should still be able to do this step)

Now the project has been built it can also be run from the JAR file. Go to /build/libs/ and open cmd in that location. Type in “java -DJASYPT_ENCRYPTOR_PASSWORD=T34M-3 -jar WrackReport-0.0.1-SNAPSHOT.jar” and press enter

Running with bootrun is preferred as there appears to be problems uploading media to reports when running from a jar.


