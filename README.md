# Operations-Research-Combinatorial-Optimization-problems
Maastricht University is in the process of forming a committee to handle students’ grievances.The administration wants the committee to include at least one female, one male, one student,one administrator, and one faculty member. Note that an individual is either a female or a male,and either a student, or an administrator, or a faculty member (the first two categories representa partition on the set of all the individuals, and the last three categories also represent a parti-tion).  There is one committee to be formed for each of the five faculties.  You have an instancefile for each faculty.  In the instance file, you can find the individuals (identified, for simplicity,by numbers) that have been nominated for the faculty committee. The mix of these individualsin the different categories is also provided in the instance file.





This folder contains instances.

An instance consists of the number of nominees n, and the list of nominees in each of the five categories.

The input format of the instances is as follows

instanceName
n
list of female nominees
list of male nominees
list of student nominees
list of administrator nominees
list of faculty nominees

Thus every instance file will consist of 7 lines.
Example of an instance called "instance0" with 3 nominees (nominee 1 is a female student, nominee 2 is a male administrator, and nominee 3 is female faculty member).

instance0
3
1,3
2
1
2
3
