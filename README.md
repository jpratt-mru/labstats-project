# labstats-project

The results (in `output/results.xlsx`) was pulled together from LabStats raw data (graciously provided by Paul Morin). 
Utilization is calculated by examining every half hour (24 hours a day) and taking the total of number of computer minutes used during that half-hour and dividing that by the total number of minutes available in that lab during that half-hour.

Let's take B103 for example. It has 24 machines, so in any given half-hour, there are 30 x 24 = 720 "computer minutes" available as a resource that lab provides. Let's say over the course of a given half-hour, we find that someone was logged into those computers for a total of 200 minutes, then we could say that the utilization during that half-hour was 200/720 = 0.278 (call it 27.8%).

I used a Java program I put together to take the LabStats logon/logoff data it contained and partition it into half-hour chunks for each lab for each semester.

I used data I had available to figure out when each lab (B107, B162, and B173 - all used for booked labs) was in use for bookings.

I removed all instructor data - the results is only student uses. (I also didn't count the instructor station in B107, since it is solely for instructor use).

I removed all entries where a user was logged in for more than 8 hours - I assumed it was basically a bogus entry where a student forgot to log off.

