famcash
=======

Android app to track family allowances

App is dev is just getting started. I have a long list of features I want to build, but this is my first android app so it's going to take a while to build it all. 

1.0 Intended feature list
=========================
* Main Activity shows kids and their current allowance
-- Button for inserting tasks
-- Button for clearing current allowance value for all kids
-- Current allowance pulled from database
* TaskAward activity
-- select kid and task from spinners
-- write to database

2.0 Intended feature list
=====================
* I think I need a main menu, and then a couple activities off that:
** TaskAward awards tasks and writes to the database
** AwardReport reads the database and shows the list of events a kid has done, 
           shows their current money at the top,
           and allows user to delete events from the kid's record and subtracts money
** AddKid allows users to add/remove kids to the database
** AddTask allows users to add/remove tasks to the database
* track loaned money
* award arbitrary money for tasks
-- display current money in DB
-- subtract requested money
* monthly report
* share events
-- via email
-- via twitter
-- via google+
-- via facebook
* echo settings to the cloud
* echo value/status to the cloud

Back on the wagon
=================
20131227 - realizing that getting all features is moe of a 2.0 version. See above for 1.0 vs 2.0 design ideas.