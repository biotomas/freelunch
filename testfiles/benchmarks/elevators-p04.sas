begin_version
3
end_version
begin_metric
1
end_metric
10
begin_variable
var0
-1
12
Atom boarded(p0, fast0)
Atom boarded(p0, slow0-0)
Atom boarded(p0, slow1-0)
Atom passenger-at(p0, n0)
Atom passenger-at(p0, n1)
Atom passenger-at(p0, n2)
Atom passenger-at(p0, n3)
Atom passenger-at(p0, n4)
Atom passenger-at(p0, n5)
Atom passenger-at(p0, n6)
Atom passenger-at(p0, n7)
Atom passenger-at(p0, n8)
end_variable
begin_variable
var1
-1
12
Atom boarded(p1, fast0)
Atom boarded(p1, slow0-0)
Atom boarded(p1, slow1-0)
Atom passenger-at(p1, n0)
Atom passenger-at(p1, n1)
Atom passenger-at(p1, n2)
Atom passenger-at(p1, n3)
Atom passenger-at(p1, n4)
Atom passenger-at(p1, n5)
Atom passenger-at(p1, n6)
Atom passenger-at(p1, n7)
Atom passenger-at(p1, n8)
end_variable
begin_variable
var2
-1
12
Atom boarded(p2, fast0)
Atom boarded(p2, slow0-0)
Atom boarded(p2, slow1-0)
Atom passenger-at(p2, n0)
Atom passenger-at(p2, n1)
Atom passenger-at(p2, n2)
Atom passenger-at(p2, n3)
Atom passenger-at(p2, n4)
Atom passenger-at(p2, n5)
Atom passenger-at(p2, n6)
Atom passenger-at(p2, n7)
Atom passenger-at(p2, n8)
end_variable
begin_variable
var3
-1
12
Atom boarded(p3, fast0)
Atom boarded(p3, slow0-0)
Atom boarded(p3, slow1-0)
Atom passenger-at(p3, n0)
Atom passenger-at(p3, n1)
Atom passenger-at(p3, n2)
Atom passenger-at(p3, n3)
Atom passenger-at(p3, n4)
Atom passenger-at(p3, n5)
Atom passenger-at(p3, n6)
Atom passenger-at(p3, n7)
Atom passenger-at(p3, n8)
end_variable
begin_variable
var4
-1
5
Atom lift-at(fast0, n0)
Atom lift-at(fast0, n2)
Atom lift-at(fast0, n4)
Atom lift-at(fast0, n6)
Atom lift-at(fast0, n8)
end_variable
begin_variable
var5
-1
5
Atom lift-at(slow0-0, n0)
Atom lift-at(slow0-0, n1)
Atom lift-at(slow0-0, n2)
Atom lift-at(slow0-0, n3)
Atom lift-at(slow0-0, n4)
end_variable
begin_variable
var6
-1
5
Atom lift-at(slow1-0, n4)
Atom lift-at(slow1-0, n5)
Atom lift-at(slow1-0, n6)
Atom lift-at(slow1-0, n7)
Atom lift-at(slow1-0, n8)
end_variable
begin_variable
var7
-1
4
Atom passengers(fast0, n0)
Atom passengers(fast0, n1)
Atom passengers(fast0, n2)
Atom passengers(fast0, n3)
end_variable
begin_variable
var8
-1
3
Atom passengers(slow0-0, n0)
Atom passengers(slow0-0, n1)
Atom passengers(slow0-0, n2)
end_variable
begin_variable
var9
-1
3
Atom passengers(slow1-0, n0)
Atom passengers(slow1-0, n1)
Atom passengers(slow1-0, n2)
end_variable
10
begin_mutex_group
12
0 0
0 1
0 2
0 3
0 4
0 5
0 6
0 7
0 8
0 9
0 10
0 11
end_mutex_group
begin_mutex_group
12
1 0
1 1
1 2
1 3
1 4
1 5
1 6
1 7
1 8
1 9
1 10
1 11
end_mutex_group
begin_mutex_group
12
2 0
2 1
2 2
2 3
2 4
2 5
2 6
2 7
2 8
2 9
2 10
2 11
end_mutex_group
begin_mutex_group
12
3 0
3 1
3 2
3 3
3 4
3 5
3 6
3 7
3 8
3 9
3 10
3 11
end_mutex_group
begin_mutex_group
5
4 0
4 1
4 2
4 3
4 4
end_mutex_group
begin_mutex_group
5
5 0
5 1
5 2
5 3
5 4
end_mutex_group
begin_mutex_group
5
6 0
6 1
6 2
6 3
6 4
end_mutex_group
begin_mutex_group
4
7 0
7 1
7 2
7 3
end_mutex_group
begin_mutex_group
3
8 0
8 1
8 2
end_mutex_group
begin_mutex_group
3
9 0
9 1
9 2
end_mutex_group
begin_state
4
11
9
7
0
1
1
0
0
0
end_state
begin_goal
4
0 3
1 10
2 4
3 8
end_goal
340
begin_operator
board p0 fast0 n0 n0 n1
1
4 0
2
0 0 3 0
0 7 0 1
0
end_operator
begin_operator
board p0 fast0 n0 n1 n2
1
4 0
2
0 0 3 0
0 7 1 2
0
end_operator
begin_operator
board p0 fast0 n0 n2 n3
1
4 0
2
0 0 3 0
0 7 2 3
0
end_operator
begin_operator
board p0 fast0 n2 n0 n1
1
4 1
2
0 0 5 0
0 7 0 1
0
end_operator
begin_operator
board p0 fast0 n2 n1 n2
1
4 1
2
0 0 5 0
0 7 1 2
0
end_operator
begin_operator
board p0 fast0 n2 n2 n3
1
4 1
2
0 0 5 0
0 7 2 3
0
end_operator
begin_operator
board p0 fast0 n4 n0 n1
1
4 2
2
0 0 7 0
0 7 0 1
0
end_operator
begin_operator
board p0 fast0 n4 n1 n2
1
4 2
2
0 0 7 0
0 7 1 2
0
end_operator
begin_operator
board p0 fast0 n4 n2 n3
1
4 2
2
0 0 7 0
0 7 2 3
0
end_operator
begin_operator
board p0 fast0 n6 n0 n1
1
4 3
2
0 0 9 0
0 7 0 1
0
end_operator
begin_operator
board p0 fast0 n6 n1 n2
1
4 3
2
0 0 9 0
0 7 1 2
0
end_operator
begin_operator
board p0 fast0 n6 n2 n3
1
4 3
2
0 0 9 0
0 7 2 3
0
end_operator
begin_operator
board p0 fast0 n8 n0 n1
1
4 4
2
0 0 11 0
0 7 0 1
0
end_operator
begin_operator
board p0 fast0 n8 n1 n2
1
4 4
2
0 0 11 0
0 7 1 2
0
end_operator
begin_operator
board p0 fast0 n8 n2 n3
1
4 4
2
0 0 11 0
0 7 2 3
0
end_operator
begin_operator
board p0 slow0-0 n0 n0 n1
1
5 0
2
0 0 3 1
0 8 0 1
0
end_operator
begin_operator
board p0 slow0-0 n0 n1 n2
1
5 0
2
0 0 3 1
0 8 1 2
0
end_operator
begin_operator
board p0 slow0-0 n1 n0 n1
1
5 1
2
0 0 4 1
0 8 0 1
0
end_operator
begin_operator
board p0 slow0-0 n1 n1 n2
1
5 1
2
0 0 4 1
0 8 1 2
0
end_operator
begin_operator
board p0 slow0-0 n2 n0 n1
1
5 2
2
0 0 5 1
0 8 0 1
0
end_operator
begin_operator
board p0 slow0-0 n2 n1 n2
1
5 2
2
0 0 5 1
0 8 1 2
0
end_operator
begin_operator
board p0 slow0-0 n3 n0 n1
1
5 3
2
0 0 6 1
0 8 0 1
0
end_operator
begin_operator
board p0 slow0-0 n3 n1 n2
1
5 3
2
0 0 6 1
0 8 1 2
0
end_operator
begin_operator
board p0 slow0-0 n4 n0 n1
1
5 4
2
0 0 7 1
0 8 0 1
0
end_operator
begin_operator
board p0 slow0-0 n4 n1 n2
1
5 4
2
0 0 7 1
0 8 1 2
0
end_operator
begin_operator
board p0 slow1-0 n4 n0 n1
1
6 0
2
0 0 7 2
0 9 0 1
0
end_operator
begin_operator
board p0 slow1-0 n4 n1 n2
1
6 0
2
0 0 7 2
0 9 1 2
0
end_operator
begin_operator
board p0 slow1-0 n5 n0 n1
1
6 1
2
0 0 8 2
0 9 0 1
0
end_operator
begin_operator
board p0 slow1-0 n5 n1 n2
1
6 1
2
0 0 8 2
0 9 1 2
0
end_operator
begin_operator
board p0 slow1-0 n6 n0 n1
1
6 2
2
0 0 9 2
0 9 0 1
0
end_operator
begin_operator
board p0 slow1-0 n6 n1 n2
1
6 2
2
0 0 9 2
0 9 1 2
0
end_operator
begin_operator
board p0 slow1-0 n7 n0 n1
1
6 3
2
0 0 10 2
0 9 0 1
0
end_operator
begin_operator
board p0 slow1-0 n7 n1 n2
1
6 3
2
0 0 10 2
0 9 1 2
0
end_operator
begin_operator
board p0 slow1-0 n8 n0 n1
1
6 4
2
0 0 11 2
0 9 0 1
0
end_operator
begin_operator
board p0 slow1-0 n8 n1 n2
1
6 4
2
0 0 11 2
0 9 1 2
0
end_operator
begin_operator
board p1 fast0 n0 n0 n1
1
4 0
2
0 1 3 0
0 7 0 1
0
end_operator
begin_operator
board p1 fast0 n0 n1 n2
1
4 0
2
0 1 3 0
0 7 1 2
0
end_operator
begin_operator
board p1 fast0 n0 n2 n3
1
4 0
2
0 1 3 0
0 7 2 3
0
end_operator
begin_operator
board p1 fast0 n2 n0 n1
1
4 1
2
0 1 5 0
0 7 0 1
0
end_operator
begin_operator
board p1 fast0 n2 n1 n2
1
4 1
2
0 1 5 0
0 7 1 2
0
end_operator
begin_operator
board p1 fast0 n2 n2 n3
1
4 1
2
0 1 5 0
0 7 2 3
0
end_operator
begin_operator
board p1 fast0 n4 n0 n1
1
4 2
2
0 1 7 0
0 7 0 1
0
end_operator
begin_operator
board p1 fast0 n4 n1 n2
1
4 2
2
0 1 7 0
0 7 1 2
0
end_operator
begin_operator
board p1 fast0 n4 n2 n3
1
4 2
2
0 1 7 0
0 7 2 3
0
end_operator
begin_operator
board p1 fast0 n6 n0 n1
1
4 3
2
0 1 9 0
0 7 0 1
0
end_operator
begin_operator
board p1 fast0 n6 n1 n2
1
4 3
2
0 1 9 0
0 7 1 2
0
end_operator
begin_operator
board p1 fast0 n6 n2 n3
1
4 3
2
0 1 9 0
0 7 2 3
0
end_operator
begin_operator
board p1 fast0 n8 n0 n1
1
4 4
2
0 1 11 0
0 7 0 1
0
end_operator
begin_operator
board p1 fast0 n8 n1 n2
1
4 4
2
0 1 11 0
0 7 1 2
0
end_operator
begin_operator
board p1 fast0 n8 n2 n3
1
4 4
2
0 1 11 0
0 7 2 3
0
end_operator
begin_operator
board p1 slow0-0 n0 n0 n1
1
5 0
2
0 1 3 1
0 8 0 1
0
end_operator
begin_operator
board p1 slow0-0 n0 n1 n2
1
5 0
2
0 1 3 1
0 8 1 2
0
end_operator
begin_operator
board p1 slow0-0 n1 n0 n1
1
5 1
2
0 1 4 1
0 8 0 1
0
end_operator
begin_operator
board p1 slow0-0 n1 n1 n2
1
5 1
2
0 1 4 1
0 8 1 2
0
end_operator
begin_operator
board p1 slow0-0 n2 n0 n1
1
5 2
2
0 1 5 1
0 8 0 1
0
end_operator
begin_operator
board p1 slow0-0 n2 n1 n2
1
5 2
2
0 1 5 1
0 8 1 2
0
end_operator
begin_operator
board p1 slow0-0 n3 n0 n1
1
5 3
2
0 1 6 1
0 8 0 1
0
end_operator
begin_operator
board p1 slow0-0 n3 n1 n2
1
5 3
2
0 1 6 1
0 8 1 2
0
end_operator
begin_operator
board p1 slow0-0 n4 n0 n1
1
5 4
2
0 1 7 1
0 8 0 1
0
end_operator
begin_operator
board p1 slow0-0 n4 n1 n2
1
5 4
2
0 1 7 1
0 8 1 2
0
end_operator
begin_operator
board p1 slow1-0 n4 n0 n1
1
6 0
2
0 1 7 2
0 9 0 1
0
end_operator
begin_operator
board p1 slow1-0 n4 n1 n2
1
6 0
2
0 1 7 2
0 9 1 2
0
end_operator
begin_operator
board p1 slow1-0 n5 n0 n1
1
6 1
2
0 1 8 2
0 9 0 1
0
end_operator
begin_operator
board p1 slow1-0 n5 n1 n2
1
6 1
2
0 1 8 2
0 9 1 2
0
end_operator
begin_operator
board p1 slow1-0 n6 n0 n1
1
6 2
2
0 1 9 2
0 9 0 1
0
end_operator
begin_operator
board p1 slow1-0 n6 n1 n2
1
6 2
2
0 1 9 2
0 9 1 2
0
end_operator
begin_operator
board p1 slow1-0 n7 n0 n1
1
6 3
2
0 1 10 2
0 9 0 1
0
end_operator
begin_operator
board p1 slow1-0 n7 n1 n2
1
6 3
2
0 1 10 2
0 9 1 2
0
end_operator
begin_operator
board p1 slow1-0 n8 n0 n1
1
6 4
2
0 1 11 2
0 9 0 1
0
end_operator
begin_operator
board p1 slow1-0 n8 n1 n2
1
6 4
2
0 1 11 2
0 9 1 2
0
end_operator
begin_operator
board p2 fast0 n0 n0 n1
1
4 0
2
0 2 3 0
0 7 0 1
0
end_operator
begin_operator
board p2 fast0 n0 n1 n2
1
4 0
2
0 2 3 0
0 7 1 2
0
end_operator
begin_operator
board p2 fast0 n0 n2 n3
1
4 0
2
0 2 3 0
0 7 2 3
0
end_operator
begin_operator
board p2 fast0 n2 n0 n1
1
4 1
2
0 2 5 0
0 7 0 1
0
end_operator
begin_operator
board p2 fast0 n2 n1 n2
1
4 1
2
0 2 5 0
0 7 1 2
0
end_operator
begin_operator
board p2 fast0 n2 n2 n3
1
4 1
2
0 2 5 0
0 7 2 3
0
end_operator
begin_operator
board p2 fast0 n4 n0 n1
1
4 2
2
0 2 7 0
0 7 0 1
0
end_operator
begin_operator
board p2 fast0 n4 n1 n2
1
4 2
2
0 2 7 0
0 7 1 2
0
end_operator
begin_operator
board p2 fast0 n4 n2 n3
1
4 2
2
0 2 7 0
0 7 2 3
0
end_operator
begin_operator
board p2 fast0 n6 n0 n1
1
4 3
2
0 2 9 0
0 7 0 1
0
end_operator
begin_operator
board p2 fast0 n6 n1 n2
1
4 3
2
0 2 9 0
0 7 1 2
0
end_operator
begin_operator
board p2 fast0 n6 n2 n3
1
4 3
2
0 2 9 0
0 7 2 3
0
end_operator
begin_operator
board p2 fast0 n8 n0 n1
1
4 4
2
0 2 11 0
0 7 0 1
0
end_operator
begin_operator
board p2 fast0 n8 n1 n2
1
4 4
2
0 2 11 0
0 7 1 2
0
end_operator
begin_operator
board p2 fast0 n8 n2 n3
1
4 4
2
0 2 11 0
0 7 2 3
0
end_operator
begin_operator
board p2 slow0-0 n0 n0 n1
1
5 0
2
0 2 3 1
0 8 0 1
0
end_operator
begin_operator
board p2 slow0-0 n0 n1 n2
1
5 0
2
0 2 3 1
0 8 1 2
0
end_operator
begin_operator
board p2 slow0-0 n1 n0 n1
1
5 1
2
0 2 4 1
0 8 0 1
0
end_operator
begin_operator
board p2 slow0-0 n1 n1 n2
1
5 1
2
0 2 4 1
0 8 1 2
0
end_operator
begin_operator
board p2 slow0-0 n2 n0 n1
1
5 2
2
0 2 5 1
0 8 0 1
0
end_operator
begin_operator
board p2 slow0-0 n2 n1 n2
1
5 2
2
0 2 5 1
0 8 1 2
0
end_operator
begin_operator
board p2 slow0-0 n3 n0 n1
1
5 3
2
0 2 6 1
0 8 0 1
0
end_operator
begin_operator
board p2 slow0-0 n3 n1 n2
1
5 3
2
0 2 6 1
0 8 1 2
0
end_operator
begin_operator
board p2 slow0-0 n4 n0 n1
1
5 4
2
0 2 7 1
0 8 0 1
0
end_operator
begin_operator
board p2 slow0-0 n4 n1 n2
1
5 4
2
0 2 7 1
0 8 1 2
0
end_operator
begin_operator
board p2 slow1-0 n4 n0 n1
1
6 0
2
0 2 7 2
0 9 0 1
0
end_operator
begin_operator
board p2 slow1-0 n4 n1 n2
1
6 0
2
0 2 7 2
0 9 1 2
0
end_operator
begin_operator
board p2 slow1-0 n5 n0 n1
1
6 1
2
0 2 8 2
0 9 0 1
0
end_operator
begin_operator
board p2 slow1-0 n5 n1 n2
1
6 1
2
0 2 8 2
0 9 1 2
0
end_operator
begin_operator
board p2 slow1-0 n6 n0 n1
1
6 2
2
0 2 9 2
0 9 0 1
0
end_operator
begin_operator
board p2 slow1-0 n6 n1 n2
1
6 2
2
0 2 9 2
0 9 1 2
0
end_operator
begin_operator
board p2 slow1-0 n7 n0 n1
1
6 3
2
0 2 10 2
0 9 0 1
0
end_operator
begin_operator
board p2 slow1-0 n7 n1 n2
1
6 3
2
0 2 10 2
0 9 1 2
0
end_operator
begin_operator
board p2 slow1-0 n8 n0 n1
1
6 4
2
0 2 11 2
0 9 0 1
0
end_operator
begin_operator
board p2 slow1-0 n8 n1 n2
1
6 4
2
0 2 11 2
0 9 1 2
0
end_operator
begin_operator
board p3 fast0 n0 n0 n1
1
4 0
2
0 3 3 0
0 7 0 1
0
end_operator
begin_operator
board p3 fast0 n0 n1 n2
1
4 0
2
0 3 3 0
0 7 1 2
0
end_operator
begin_operator
board p3 fast0 n0 n2 n3
1
4 0
2
0 3 3 0
0 7 2 3
0
end_operator
begin_operator
board p3 fast0 n2 n0 n1
1
4 1
2
0 3 5 0
0 7 0 1
0
end_operator
begin_operator
board p3 fast0 n2 n1 n2
1
4 1
2
0 3 5 0
0 7 1 2
0
end_operator
begin_operator
board p3 fast0 n2 n2 n3
1
4 1
2
0 3 5 0
0 7 2 3
0
end_operator
begin_operator
board p3 fast0 n4 n0 n1
1
4 2
2
0 3 7 0
0 7 0 1
0
end_operator
begin_operator
board p3 fast0 n4 n1 n2
1
4 2
2
0 3 7 0
0 7 1 2
0
end_operator
begin_operator
board p3 fast0 n4 n2 n3
1
4 2
2
0 3 7 0
0 7 2 3
0
end_operator
begin_operator
board p3 fast0 n6 n0 n1
1
4 3
2
0 3 9 0
0 7 0 1
0
end_operator
begin_operator
board p3 fast0 n6 n1 n2
1
4 3
2
0 3 9 0
0 7 1 2
0
end_operator
begin_operator
board p3 fast0 n6 n2 n3
1
4 3
2
0 3 9 0
0 7 2 3
0
end_operator
begin_operator
board p3 fast0 n8 n0 n1
1
4 4
2
0 3 11 0
0 7 0 1
0
end_operator
begin_operator
board p3 fast0 n8 n1 n2
1
4 4
2
0 3 11 0
0 7 1 2
0
end_operator
begin_operator
board p3 fast0 n8 n2 n3
1
4 4
2
0 3 11 0
0 7 2 3
0
end_operator
begin_operator
board p3 slow0-0 n0 n0 n1
1
5 0
2
0 3 3 1
0 8 0 1
0
end_operator
begin_operator
board p3 slow0-0 n0 n1 n2
1
5 0
2
0 3 3 1
0 8 1 2
0
end_operator
begin_operator
board p3 slow0-0 n1 n0 n1
1
5 1
2
0 3 4 1
0 8 0 1
0
end_operator
begin_operator
board p3 slow0-0 n1 n1 n2
1
5 1
2
0 3 4 1
0 8 1 2
0
end_operator
begin_operator
board p3 slow0-0 n2 n0 n1
1
5 2
2
0 3 5 1
0 8 0 1
0
end_operator
begin_operator
board p3 slow0-0 n2 n1 n2
1
5 2
2
0 3 5 1
0 8 1 2
0
end_operator
begin_operator
board p3 slow0-0 n3 n0 n1
1
5 3
2
0 3 6 1
0 8 0 1
0
end_operator
begin_operator
board p3 slow0-0 n3 n1 n2
1
5 3
2
0 3 6 1
0 8 1 2
0
end_operator
begin_operator
board p3 slow0-0 n4 n0 n1
1
5 4
2
0 3 7 1
0 8 0 1
0
end_operator
begin_operator
board p3 slow0-0 n4 n1 n2
1
5 4
2
0 3 7 1
0 8 1 2
0
end_operator
begin_operator
board p3 slow1-0 n4 n0 n1
1
6 0
2
0 3 7 2
0 9 0 1
0
end_operator
begin_operator
board p3 slow1-0 n4 n1 n2
1
6 0
2
0 3 7 2
0 9 1 2
0
end_operator
begin_operator
board p3 slow1-0 n5 n0 n1
1
6 1
2
0 3 8 2
0 9 0 1
0
end_operator
begin_operator
board p3 slow1-0 n5 n1 n2
1
6 1
2
0 3 8 2
0 9 1 2
0
end_operator
begin_operator
board p3 slow1-0 n6 n0 n1
1
6 2
2
0 3 9 2
0 9 0 1
0
end_operator
begin_operator
board p3 slow1-0 n6 n1 n2
1
6 2
2
0 3 9 2
0 9 1 2
0
end_operator
begin_operator
board p3 slow1-0 n7 n0 n1
1
6 3
2
0 3 10 2
0 9 0 1
0
end_operator
begin_operator
board p3 slow1-0 n7 n1 n2
1
6 3
2
0 3 10 2
0 9 1 2
0
end_operator
begin_operator
board p3 slow1-0 n8 n0 n1
1
6 4
2
0 3 11 2
0 9 0 1
0
end_operator
begin_operator
board p3 slow1-0 n8 n1 n2
1
6 4
2
0 3 11 2
0 9 1 2
0
end_operator
begin_operator
leave p0 fast0 n0 n1 n0
1
4 0
2
0 0 0 3
0 7 1 0
0
end_operator
begin_operator
leave p0 fast0 n0 n2 n1
1
4 0
2
0 0 0 3
0 7 2 1
0
end_operator
begin_operator
leave p0 fast0 n0 n3 n2
1
4 0
2
0 0 0 3
0 7 3 2
0
end_operator
begin_operator
leave p0 fast0 n2 n1 n0
1
4 1
2
0 0 0 5
0 7 1 0
0
end_operator
begin_operator
leave p0 fast0 n2 n2 n1
1
4 1
2
0 0 0 5
0 7 2 1
0
end_operator
begin_operator
leave p0 fast0 n2 n3 n2
1
4 1
2
0 0 0 5
0 7 3 2
0
end_operator
begin_operator
leave p0 fast0 n4 n1 n0
1
4 2
2
0 0 0 7
0 7 1 0
0
end_operator
begin_operator
leave p0 fast0 n4 n2 n1
1
4 2
2
0 0 0 7
0 7 2 1
0
end_operator
begin_operator
leave p0 fast0 n4 n3 n2
1
4 2
2
0 0 0 7
0 7 3 2
0
end_operator
begin_operator
leave p0 fast0 n6 n1 n0
1
4 3
2
0 0 0 9
0 7 1 0
0
end_operator
begin_operator
leave p0 fast0 n6 n2 n1
1
4 3
2
0 0 0 9
0 7 2 1
0
end_operator
begin_operator
leave p0 fast0 n6 n3 n2
1
4 3
2
0 0 0 9
0 7 3 2
0
end_operator
begin_operator
leave p0 fast0 n8 n1 n0
1
4 4
2
0 0 0 11
0 7 1 0
0
end_operator
begin_operator
leave p0 fast0 n8 n2 n1
1
4 4
2
0 0 0 11
0 7 2 1
0
end_operator
begin_operator
leave p0 fast0 n8 n3 n2
1
4 4
2
0 0 0 11
0 7 3 2
0
end_operator
begin_operator
leave p0 slow0-0 n0 n1 n0
1
5 0
2
0 0 1 3
0 8 1 0
0
end_operator
begin_operator
leave p0 slow0-0 n0 n2 n1
1
5 0
2
0 0 1 3
0 8 2 1
0
end_operator
begin_operator
leave p0 slow0-0 n1 n1 n0
1
5 1
2
0 0 1 4
0 8 1 0
0
end_operator
begin_operator
leave p0 slow0-0 n1 n2 n1
1
5 1
2
0 0 1 4
0 8 2 1
0
end_operator
begin_operator
leave p0 slow0-0 n2 n1 n0
1
5 2
2
0 0 1 5
0 8 1 0
0
end_operator
begin_operator
leave p0 slow0-0 n2 n2 n1
1
5 2
2
0 0 1 5
0 8 2 1
0
end_operator
begin_operator
leave p0 slow0-0 n3 n1 n0
1
5 3
2
0 0 1 6
0 8 1 0
0
end_operator
begin_operator
leave p0 slow0-0 n3 n2 n1
1
5 3
2
0 0 1 6
0 8 2 1
0
end_operator
begin_operator
leave p0 slow0-0 n4 n1 n0
1
5 4
2
0 0 1 7
0 8 1 0
0
end_operator
begin_operator
leave p0 slow0-0 n4 n2 n1
1
5 4
2
0 0 1 7
0 8 2 1
0
end_operator
begin_operator
leave p0 slow1-0 n4 n1 n0
1
6 0
2
0 0 2 7
0 9 1 0
0
end_operator
begin_operator
leave p0 slow1-0 n4 n2 n1
1
6 0
2
0 0 2 7
0 9 2 1
0
end_operator
begin_operator
leave p0 slow1-0 n5 n1 n0
1
6 1
2
0 0 2 8
0 9 1 0
0
end_operator
begin_operator
leave p0 slow1-0 n5 n2 n1
1
6 1
2
0 0 2 8
0 9 2 1
0
end_operator
begin_operator
leave p0 slow1-0 n6 n1 n0
1
6 2
2
0 0 2 9
0 9 1 0
0
end_operator
begin_operator
leave p0 slow1-0 n6 n2 n1
1
6 2
2
0 0 2 9
0 9 2 1
0
end_operator
begin_operator
leave p0 slow1-0 n7 n1 n0
1
6 3
2
0 0 2 10
0 9 1 0
0
end_operator
begin_operator
leave p0 slow1-0 n7 n2 n1
1
6 3
2
0 0 2 10
0 9 2 1
0
end_operator
begin_operator
leave p0 slow1-0 n8 n1 n0
1
6 4
2
0 0 2 11
0 9 1 0
0
end_operator
begin_operator
leave p0 slow1-0 n8 n2 n1
1
6 4
2
0 0 2 11
0 9 2 1
0
end_operator
begin_operator
leave p1 fast0 n0 n1 n0
1
4 0
2
0 1 0 3
0 7 1 0
0
end_operator
begin_operator
leave p1 fast0 n0 n2 n1
1
4 0
2
0 1 0 3
0 7 2 1
0
end_operator
begin_operator
leave p1 fast0 n0 n3 n2
1
4 0
2
0 1 0 3
0 7 3 2
0
end_operator
begin_operator
leave p1 fast0 n2 n1 n0
1
4 1
2
0 1 0 5
0 7 1 0
0
end_operator
begin_operator
leave p1 fast0 n2 n2 n1
1
4 1
2
0 1 0 5
0 7 2 1
0
end_operator
begin_operator
leave p1 fast0 n2 n3 n2
1
4 1
2
0 1 0 5
0 7 3 2
0
end_operator
begin_operator
leave p1 fast0 n4 n1 n0
1
4 2
2
0 1 0 7
0 7 1 0
0
end_operator
begin_operator
leave p1 fast0 n4 n2 n1
1
4 2
2
0 1 0 7
0 7 2 1
0
end_operator
begin_operator
leave p1 fast0 n4 n3 n2
1
4 2
2
0 1 0 7
0 7 3 2
0
end_operator
begin_operator
leave p1 fast0 n6 n1 n0
1
4 3
2
0 1 0 9
0 7 1 0
0
end_operator
begin_operator
leave p1 fast0 n6 n2 n1
1
4 3
2
0 1 0 9
0 7 2 1
0
end_operator
begin_operator
leave p1 fast0 n6 n3 n2
1
4 3
2
0 1 0 9
0 7 3 2
0
end_operator
begin_operator
leave p1 fast0 n8 n1 n0
1
4 4
2
0 1 0 11
0 7 1 0
0
end_operator
begin_operator
leave p1 fast0 n8 n2 n1
1
4 4
2
0 1 0 11
0 7 2 1
0
end_operator
begin_operator
leave p1 fast0 n8 n3 n2
1
4 4
2
0 1 0 11
0 7 3 2
0
end_operator
begin_operator
leave p1 slow0-0 n0 n1 n0
1
5 0
2
0 1 1 3
0 8 1 0
0
end_operator
begin_operator
leave p1 slow0-0 n0 n2 n1
1
5 0
2
0 1 1 3
0 8 2 1
0
end_operator
begin_operator
leave p1 slow0-0 n1 n1 n0
1
5 1
2
0 1 1 4
0 8 1 0
0
end_operator
begin_operator
leave p1 slow0-0 n1 n2 n1
1
5 1
2
0 1 1 4
0 8 2 1
0
end_operator
begin_operator
leave p1 slow0-0 n2 n1 n0
1
5 2
2
0 1 1 5
0 8 1 0
0
end_operator
begin_operator
leave p1 slow0-0 n2 n2 n1
1
5 2
2
0 1 1 5
0 8 2 1
0
end_operator
begin_operator
leave p1 slow0-0 n3 n1 n0
1
5 3
2
0 1 1 6
0 8 1 0
0
end_operator
begin_operator
leave p1 slow0-0 n3 n2 n1
1
5 3
2
0 1 1 6
0 8 2 1
0
end_operator
begin_operator
leave p1 slow0-0 n4 n1 n0
1
5 4
2
0 1 1 7
0 8 1 0
0
end_operator
begin_operator
leave p1 slow0-0 n4 n2 n1
1
5 4
2
0 1 1 7
0 8 2 1
0
end_operator
begin_operator
leave p1 slow1-0 n4 n1 n0
1
6 0
2
0 1 2 7
0 9 1 0
0
end_operator
begin_operator
leave p1 slow1-0 n4 n2 n1
1
6 0
2
0 1 2 7
0 9 2 1
0
end_operator
begin_operator
leave p1 slow1-0 n5 n1 n0
1
6 1
2
0 1 2 8
0 9 1 0
0
end_operator
begin_operator
leave p1 slow1-0 n5 n2 n1
1
6 1
2
0 1 2 8
0 9 2 1
0
end_operator
begin_operator
leave p1 slow1-0 n6 n1 n0
1
6 2
2
0 1 2 9
0 9 1 0
0
end_operator
begin_operator
leave p1 slow1-0 n6 n2 n1
1
6 2
2
0 1 2 9
0 9 2 1
0
end_operator
begin_operator
leave p1 slow1-0 n7 n1 n0
1
6 3
2
0 1 2 10
0 9 1 0
0
end_operator
begin_operator
leave p1 slow1-0 n7 n2 n1
1
6 3
2
0 1 2 10
0 9 2 1
0
end_operator
begin_operator
leave p1 slow1-0 n8 n1 n0
1
6 4
2
0 1 2 11
0 9 1 0
0
end_operator
begin_operator
leave p1 slow1-0 n8 n2 n1
1
6 4
2
0 1 2 11
0 9 2 1
0
end_operator
begin_operator
leave p2 fast0 n0 n1 n0
1
4 0
2
0 2 0 3
0 7 1 0
0
end_operator
begin_operator
leave p2 fast0 n0 n2 n1
1
4 0
2
0 2 0 3
0 7 2 1
0
end_operator
begin_operator
leave p2 fast0 n0 n3 n2
1
4 0
2
0 2 0 3
0 7 3 2
0
end_operator
begin_operator
leave p2 fast0 n2 n1 n0
1
4 1
2
0 2 0 5
0 7 1 0
0
end_operator
begin_operator
leave p2 fast0 n2 n2 n1
1
4 1
2
0 2 0 5
0 7 2 1
0
end_operator
begin_operator
leave p2 fast0 n2 n3 n2
1
4 1
2
0 2 0 5
0 7 3 2
0
end_operator
begin_operator
leave p2 fast0 n4 n1 n0
1
4 2
2
0 2 0 7
0 7 1 0
0
end_operator
begin_operator
leave p2 fast0 n4 n2 n1
1
4 2
2
0 2 0 7
0 7 2 1
0
end_operator
begin_operator
leave p2 fast0 n4 n3 n2
1
4 2
2
0 2 0 7
0 7 3 2
0
end_operator
begin_operator
leave p2 fast0 n6 n1 n0
1
4 3
2
0 2 0 9
0 7 1 0
0
end_operator
begin_operator
leave p2 fast0 n6 n2 n1
1
4 3
2
0 2 0 9
0 7 2 1
0
end_operator
begin_operator
leave p2 fast0 n6 n3 n2
1
4 3
2
0 2 0 9
0 7 3 2
0
end_operator
begin_operator
leave p2 fast0 n8 n1 n0
1
4 4
2
0 2 0 11
0 7 1 0
0
end_operator
begin_operator
leave p2 fast0 n8 n2 n1
1
4 4
2
0 2 0 11
0 7 2 1
0
end_operator
begin_operator
leave p2 fast0 n8 n3 n2
1
4 4
2
0 2 0 11
0 7 3 2
0
end_operator
begin_operator
leave p2 slow0-0 n0 n1 n0
1
5 0
2
0 2 1 3
0 8 1 0
0
end_operator
begin_operator
leave p2 slow0-0 n0 n2 n1
1
5 0
2
0 2 1 3
0 8 2 1
0
end_operator
begin_operator
leave p2 slow0-0 n1 n1 n0
1
5 1
2
0 2 1 4
0 8 1 0
0
end_operator
begin_operator
leave p2 slow0-0 n1 n2 n1
1
5 1
2
0 2 1 4
0 8 2 1
0
end_operator
begin_operator
leave p2 slow0-0 n2 n1 n0
1
5 2
2
0 2 1 5
0 8 1 0
0
end_operator
begin_operator
leave p2 slow0-0 n2 n2 n1
1
5 2
2
0 2 1 5
0 8 2 1
0
end_operator
begin_operator
leave p2 slow0-0 n3 n1 n0
1
5 3
2
0 2 1 6
0 8 1 0
0
end_operator
begin_operator
leave p2 slow0-0 n3 n2 n1
1
5 3
2
0 2 1 6
0 8 2 1
0
end_operator
begin_operator
leave p2 slow0-0 n4 n1 n0
1
5 4
2
0 2 1 7
0 8 1 0
0
end_operator
begin_operator
leave p2 slow0-0 n4 n2 n1
1
5 4
2
0 2 1 7
0 8 2 1
0
end_operator
begin_operator
leave p2 slow1-0 n4 n1 n0
1
6 0
2
0 2 2 7
0 9 1 0
0
end_operator
begin_operator
leave p2 slow1-0 n4 n2 n1
1
6 0
2
0 2 2 7
0 9 2 1
0
end_operator
begin_operator
leave p2 slow1-0 n5 n1 n0
1
6 1
2
0 2 2 8
0 9 1 0
0
end_operator
begin_operator
leave p2 slow1-0 n5 n2 n1
1
6 1
2
0 2 2 8
0 9 2 1
0
end_operator
begin_operator
leave p2 slow1-0 n6 n1 n0
1
6 2
2
0 2 2 9
0 9 1 0
0
end_operator
begin_operator
leave p2 slow1-0 n6 n2 n1
1
6 2
2
0 2 2 9
0 9 2 1
0
end_operator
begin_operator
leave p2 slow1-0 n7 n1 n0
1
6 3
2
0 2 2 10
0 9 1 0
0
end_operator
begin_operator
leave p2 slow1-0 n7 n2 n1
1
6 3
2
0 2 2 10
0 9 2 1
0
end_operator
begin_operator
leave p2 slow1-0 n8 n1 n0
1
6 4
2
0 2 2 11
0 9 1 0
0
end_operator
begin_operator
leave p2 slow1-0 n8 n2 n1
1
6 4
2
0 2 2 11
0 9 2 1
0
end_operator
begin_operator
leave p3 fast0 n0 n1 n0
1
4 0
2
0 3 0 3
0 7 1 0
0
end_operator
begin_operator
leave p3 fast0 n0 n2 n1
1
4 0
2
0 3 0 3
0 7 2 1
0
end_operator
begin_operator
leave p3 fast0 n0 n3 n2
1
4 0
2
0 3 0 3
0 7 3 2
0
end_operator
begin_operator
leave p3 fast0 n2 n1 n0
1
4 1
2
0 3 0 5
0 7 1 0
0
end_operator
begin_operator
leave p3 fast0 n2 n2 n1
1
4 1
2
0 3 0 5
0 7 2 1
0
end_operator
begin_operator
leave p3 fast0 n2 n3 n2
1
4 1
2
0 3 0 5
0 7 3 2
0
end_operator
begin_operator
leave p3 fast0 n4 n1 n0
1
4 2
2
0 3 0 7
0 7 1 0
0
end_operator
begin_operator
leave p3 fast0 n4 n2 n1
1
4 2
2
0 3 0 7
0 7 2 1
0
end_operator
begin_operator
leave p3 fast0 n4 n3 n2
1
4 2
2
0 3 0 7
0 7 3 2
0
end_operator
begin_operator
leave p3 fast0 n6 n1 n0
1
4 3
2
0 3 0 9
0 7 1 0
0
end_operator
begin_operator
leave p3 fast0 n6 n2 n1
1
4 3
2
0 3 0 9
0 7 2 1
0
end_operator
begin_operator
leave p3 fast0 n6 n3 n2
1
4 3
2
0 3 0 9
0 7 3 2
0
end_operator
begin_operator
leave p3 fast0 n8 n1 n0
1
4 4
2
0 3 0 11
0 7 1 0
0
end_operator
begin_operator
leave p3 fast0 n8 n2 n1
1
4 4
2
0 3 0 11
0 7 2 1
0
end_operator
begin_operator
leave p3 fast0 n8 n3 n2
1
4 4
2
0 3 0 11
0 7 3 2
0
end_operator
begin_operator
leave p3 slow0-0 n0 n1 n0
1
5 0
2
0 3 1 3
0 8 1 0
0
end_operator
begin_operator
leave p3 slow0-0 n0 n2 n1
1
5 0
2
0 3 1 3
0 8 2 1
0
end_operator
begin_operator
leave p3 slow0-0 n1 n1 n0
1
5 1
2
0 3 1 4
0 8 1 0
0
end_operator
begin_operator
leave p3 slow0-0 n1 n2 n1
1
5 1
2
0 3 1 4
0 8 2 1
0
end_operator
begin_operator
leave p3 slow0-0 n2 n1 n0
1
5 2
2
0 3 1 5
0 8 1 0
0
end_operator
begin_operator
leave p3 slow0-0 n2 n2 n1
1
5 2
2
0 3 1 5
0 8 2 1
0
end_operator
begin_operator
leave p3 slow0-0 n3 n1 n0
1
5 3
2
0 3 1 6
0 8 1 0
0
end_operator
begin_operator
leave p3 slow0-0 n3 n2 n1
1
5 3
2
0 3 1 6
0 8 2 1
0
end_operator
begin_operator
leave p3 slow0-0 n4 n1 n0
1
5 4
2
0 3 1 7
0 8 1 0
0
end_operator
begin_operator
leave p3 slow0-0 n4 n2 n1
1
5 4
2
0 3 1 7
0 8 2 1
0
end_operator
begin_operator
leave p3 slow1-0 n4 n1 n0
1
6 0
2
0 3 2 7
0 9 1 0
0
end_operator
begin_operator
leave p3 slow1-0 n4 n2 n1
1
6 0
2
0 3 2 7
0 9 2 1
0
end_operator
begin_operator
leave p3 slow1-0 n5 n1 n0
1
6 1
2
0 3 2 8
0 9 1 0
0
end_operator
begin_operator
leave p3 slow1-0 n5 n2 n1
1
6 1
2
0 3 2 8
0 9 2 1
0
end_operator
begin_operator
leave p3 slow1-0 n6 n1 n0
1
6 2
2
0 3 2 9
0 9 1 0
0
end_operator
begin_operator
leave p3 slow1-0 n6 n2 n1
1
6 2
2
0 3 2 9
0 9 2 1
0
end_operator
begin_operator
leave p3 slow1-0 n7 n1 n0
1
6 3
2
0 3 2 10
0 9 1 0
0
end_operator
begin_operator
leave p3 slow1-0 n7 n2 n1
1
6 3
2
0 3 2 10
0 9 2 1
0
end_operator
begin_operator
leave p3 slow1-0 n8 n1 n0
1
6 4
2
0 3 2 11
0 9 1 0
0
end_operator
begin_operator
leave p3 slow1-0 n8 n2 n1
1
6 4
2
0 3 2 11
0 9 2 1
0
end_operator
begin_operator
move-down-fast fast0 n2 n0
0
1
0 4 1 0
7
end_operator
begin_operator
move-down-fast fast0 n4 n0
0
1
0 4 2 0
13
end_operator
begin_operator
move-down-fast fast0 n4 n2
0
1
0 4 2 1
7
end_operator
begin_operator
move-down-fast fast0 n6 n0
0
1
0 4 3 0
19
end_operator
begin_operator
move-down-fast fast0 n6 n2
0
1
0 4 3 1
13
end_operator
begin_operator
move-down-fast fast0 n6 n4
0
1
0 4 3 2
7
end_operator
begin_operator
move-down-fast fast0 n8 n0
0
1
0 4 4 0
25
end_operator
begin_operator
move-down-fast fast0 n8 n2
0
1
0 4 4 1
19
end_operator
begin_operator
move-down-fast fast0 n8 n4
0
1
0 4 4 2
13
end_operator
begin_operator
move-down-fast fast0 n8 n6
0
1
0 4 4 3
7
end_operator
begin_operator
move-down-slow slow0-0 n1 n0
0
1
0 5 1 0
6
end_operator
begin_operator
move-down-slow slow0-0 n2 n0
0
1
0 5 2 0
7
end_operator
begin_operator
move-down-slow slow0-0 n2 n1
0
1
0 5 2 1
6
end_operator
begin_operator
move-down-slow slow0-0 n3 n0
0
1
0 5 3 0
8
end_operator
begin_operator
move-down-slow slow0-0 n3 n1
0
1
0 5 3 1
7
end_operator
begin_operator
move-down-slow slow0-0 n3 n2
0
1
0 5 3 2
6
end_operator
begin_operator
move-down-slow slow0-0 n4 n0
0
1
0 5 4 0
9
end_operator
begin_operator
move-down-slow slow0-0 n4 n1
0
1
0 5 4 1
8
end_operator
begin_operator
move-down-slow slow0-0 n4 n2
0
1
0 5 4 2
7
end_operator
begin_operator
move-down-slow slow0-0 n4 n3
0
1
0 5 4 3
6
end_operator
begin_operator
move-down-slow slow1-0 n5 n4
0
1
0 6 1 0
6
end_operator
begin_operator
move-down-slow slow1-0 n6 n4
0
1
0 6 2 0
7
end_operator
begin_operator
move-down-slow slow1-0 n6 n5
0
1
0 6 2 1
6
end_operator
begin_operator
move-down-slow slow1-0 n7 n4
0
1
0 6 3 0
8
end_operator
begin_operator
move-down-slow slow1-0 n7 n5
0
1
0 6 3 1
7
end_operator
begin_operator
move-down-slow slow1-0 n7 n6
0
1
0 6 3 2
6
end_operator
begin_operator
move-down-slow slow1-0 n8 n4
0
1
0 6 4 0
9
end_operator
begin_operator
move-down-slow slow1-0 n8 n5
0
1
0 6 4 1
8
end_operator
begin_operator
move-down-slow slow1-0 n8 n6
0
1
0 6 4 2
7
end_operator
begin_operator
move-down-slow slow1-0 n8 n7
0
1
0 6 4 3
6
end_operator
begin_operator
move-up-fast fast0 n0 n2
0
1
0 4 0 1
7
end_operator
begin_operator
move-up-fast fast0 n0 n4
0
1
0 4 0 2
13
end_operator
begin_operator
move-up-fast fast0 n0 n6
0
1
0 4 0 3
19
end_operator
begin_operator
move-up-fast fast0 n0 n8
0
1
0 4 0 4
25
end_operator
begin_operator
move-up-fast fast0 n2 n4
0
1
0 4 1 2
7
end_operator
begin_operator
move-up-fast fast0 n2 n6
0
1
0 4 1 3
13
end_operator
begin_operator
move-up-fast fast0 n2 n8
0
1
0 4 1 4
19
end_operator
begin_operator
move-up-fast fast0 n4 n6
0
1
0 4 2 3
7
end_operator
begin_operator
move-up-fast fast0 n4 n8
0
1
0 4 2 4
13
end_operator
begin_operator
move-up-fast fast0 n6 n8
0
1
0 4 3 4
7
end_operator
begin_operator
move-up-slow slow0-0 n0 n1
0
1
0 5 0 1
6
end_operator
begin_operator
move-up-slow slow0-0 n0 n2
0
1
0 5 0 2
7
end_operator
begin_operator
move-up-slow slow0-0 n0 n3
0
1
0 5 0 3
8
end_operator
begin_operator
move-up-slow slow0-0 n0 n4
0
1
0 5 0 4
9
end_operator
begin_operator
move-up-slow slow0-0 n1 n2
0
1
0 5 1 2
6
end_operator
begin_operator
move-up-slow slow0-0 n1 n3
0
1
0 5 1 3
7
end_operator
begin_operator
move-up-slow slow0-0 n1 n4
0
1
0 5 1 4
8
end_operator
begin_operator
move-up-slow slow0-0 n2 n3
0
1
0 5 2 3
6
end_operator
begin_operator
move-up-slow slow0-0 n2 n4
0
1
0 5 2 4
7
end_operator
begin_operator
move-up-slow slow0-0 n3 n4
0
1
0 5 3 4
6
end_operator
begin_operator
move-up-slow slow1-0 n4 n5
0
1
0 6 0 1
6
end_operator
begin_operator
move-up-slow slow1-0 n4 n6
0
1
0 6 0 2
7
end_operator
begin_operator
move-up-slow slow1-0 n4 n7
0
1
0 6 0 3
8
end_operator
begin_operator
move-up-slow slow1-0 n4 n8
0
1
0 6 0 4
9
end_operator
begin_operator
move-up-slow slow1-0 n5 n6
0
1
0 6 1 2
6
end_operator
begin_operator
move-up-slow slow1-0 n5 n7
0
1
0 6 1 3
7
end_operator
begin_operator
move-up-slow slow1-0 n5 n8
0
1
0 6 1 4
8
end_operator
begin_operator
move-up-slow slow1-0 n6 n7
0
1
0 6 2 3
6
end_operator
begin_operator
move-up-slow slow1-0 n6 n8
0
1
0 6 2 4
7
end_operator
begin_operator
move-up-slow slow1-0 n7 n8
0
1
0 6 3 4
6
end_operator
0
