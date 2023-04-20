# WAP Project
This project is a forum-like platform which functionalities relate to the popular website stackoverflow.com. The application is based on an SQL database.

![image](https://user-images.githubusercontent.com/62851535/233479734-2ac6f466-e192-4802-b680-48f338316091.png)

The basis of the system are users asking questions, writing answers and comments. Creating a user is done by entering an e-mail, username and password. Users are divided into admins and regulars. The questions asked are assigned to different categories. The ranking table stores data for generating a ranking and assigning ranks to users who are rewarded with the option to change their avatar if they have a sufficiently high rank. Avatars are stored on the server and there is a link to the current graphic in the users table. The unwantedvocab table contains words that are undesirable or vulgar. The automatic user lock mechanism is based on this table. In the actualuser table we store the currently logged in users, and in the rateanswer table there are the ratings of the response data.


##	Application features
- account creation
- login and logout
- asking questions in various fields of the automotive industry
- answers
- rating answers
- positioning of responses by rating
- menu with threads
- comments for questions and answers
- separate panel for admins and standard users
- Auto-admin - when adding questions, answers and comments, system verifies the content in terms of inappropriate vocabulary and automatically block the possibility of posting such statements. This functionality is based on a new table containing a dictionary of unwanted word.
- Temporary user block - admin can set a temporary lock time for a specific user, which prevents him from using the platform.
- User panel - additional panel for the user, in which user can change basic data such as: login, password and avatar.
- Topics filtering menu - the ability to filter the forum by attributes such as the number of responses, date, categories.
- Admin’s dashboard - the admin panel contains information about the most visited topics, the most commented ones, etc. It represents the statistic data using charts.
- Ranking of the most active users - ranking of the most active users based on the number of questions asked, answers given, comments written and the quality of answers based on the ratings of other users. The ranking indicator determines the rank of a user, such as a novice. Introducing a system of rewarding the user for the quality of published content. Users with a rank higher than greenhorn have the option to change their own avatar.
- User avatar - the ability to set your own user avatar by sending an image file.
-	password reminder by e-mail
- password hashing
- expanding the registration process with anti-robotic mechanisms (reCAPTCHA)
