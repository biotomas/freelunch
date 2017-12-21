begin_version
3
end_version
begin_metric
1
end_metric
32
begin_variable
var0
-1
2
Atom available(fe1-rsrc)
<none of those>
end_variable
begin_variable
var1
-1
2
Atom available(hw1-rsrc)
<none of those>
end_variable
begin_variable
var2
-1
2
Atom available(lc1-rsrc)
<none of those>
end_variable
begin_variable
var3
-1
2
Atom available(lime-rsrc)
<none of those>
end_variable
begin_variable
var4
-1
2
Atom available(sys-rsrc)
<none of those>
end_variable
begin_variable
var5
-1
2
Atom available(uc1-rsrc)
<none of those>
end_variable
begin_variable
var6
-1
2
Atom available(uime-rsrc)
<none of those>
end_variable
begin_variable
var7
-1
2
Atom hasimage(sheet1, back, image-1)
<none of those>
end_variable
begin_variable
var8
-1
2
Atom hasimage(sheet1, back, image-2)
<none of those>
end_variable
begin_variable
var9
-1
2
Atom hasimage(sheet1, front, image-1)
<none of those>
end_variable
begin_variable
var10
-1
2
Atom hasimage(sheet1, front, image-2)
<none of those>
end_variable
begin_variable
var11
-1
2
Atom hasimage(sheet2, back, image-1)
<none of those>
end_variable
begin_variable
var12
-1
2
Atom hasimage(sheet2, back, image-2)
<none of those>
end_variable
begin_variable
var13
-1
2
Atom hasimage(sheet2, front, image-1)
<none of those>
end_variable
begin_variable
var14
-1
2
Atom hasimage(sheet2, front, image-2)
<none of those>
end_variable
begin_variable
var15
-1
12
Atom location(sheet1, fe1_exit-hw1_leftentry)
Atom location(sheet1, hw1_rightexit-sys_entry)
Atom location(sheet1, hw1_toprightentry-uc1_exit)
Atom location(sheet1, lc1_entry-hw1_bottomleftexit)
Atom location(sheet1, lc1_entryfromime-lime_exit)
Atom location(sheet1, lc1_exit-hw1_bottomrightentry)
Atom location(sheet1, lime_entry-lc1_exittoime)
Atom location(sheet1, some_feeder_tray)
Atom location(sheet1, some_finisher_tray)
Atom location(sheet1, uc1_entry-hw1_topleftexit)
Atom location(sheet1, uc1_exittoime-uime_entry)
Atom location(sheet1, uime_exit-uc1_entryfromime)
end_variable
begin_variable
var16
-1
12
Atom location(sheet2, fe1_exit-hw1_leftentry)
Atom location(sheet2, hw1_rightexit-sys_entry)
Atom location(sheet2, hw1_toprightentry-uc1_exit)
Atom location(sheet2, lc1_entry-hw1_bottomleftexit)
Atom location(sheet2, lc1_entryfromime-lime_exit)
Atom location(sheet2, lc1_exit-hw1_bottomrightentry)
Atom location(sheet2, lime_entry-lc1_exittoime)
Atom location(sheet2, some_feeder_tray)
Atom location(sheet2, some_finisher_tray)
Atom location(sheet2, uc1_entry-hw1_topleftexit)
Atom location(sheet2, uc1_exittoime-uime_entry)
Atom location(sheet2, uime_exit-uc1_entryfromime)
end_variable
begin_variable
var17
-1
2
Atom notprintedwith(sheet1, back, black)
<none of those>
end_variable
begin_variable
var18
-1
2
Atom notprintedwith(sheet1, back, color)
<none of those>
end_variable
begin_variable
var19
-1
2
Atom notprintedwith(sheet1, front, black)
<none of those>
end_variable
begin_variable
var20
-1
2
Atom notprintedwith(sheet1, front, color)
<none of those>
end_variable
begin_variable
var21
-1
2
Atom notprintedwith(sheet2, back, black)
<none of those>
end_variable
begin_variable
var22
-1
2
Atom notprintedwith(sheet2, back, color)
<none of those>
end_variable
begin_variable
var23
-1
2
Atom notprintedwith(sheet2, front, black)
<none of those>
end_variable
begin_variable
var24
-1
2
Atom notprintedwith(sheet2, front, color)
<none of those>
end_variable
begin_variable
var25
-1
2
Atom sideup(sheet1, back)
<none of those>
end_variable
begin_variable
var26
-1
2
Atom sideup(sheet1, front)
<none of those>
end_variable
begin_variable
var27
-1
2
Atom sideup(sheet2, back)
<none of those>
end_variable
begin_variable
var28
-1
2
Atom sideup(sheet2, front)
<none of those>
end_variable
begin_variable
var29
-1
2
Atom stackedin(sheet1, sys_outputtray)
<none of those>
end_variable
begin_variable
var30
-1
2
Atom stackedin(sheet2, sys_outputtray)
<none of those>
end_variable
begin_variable
var31
-1
2
Atom uninitialized()
<none of those>
end_variable
2
begin_mutex_group
12
15 0
15 1
15 2
15 3
15 4
15 5
15 6
15 7
15 8
15 9
15 10
15 11
end_mutex_group
begin_mutex_group
12
16 0
16 1
16 2
16 3
16 4
16 5
16 6
16 7
16 8
16 9
16 10
16 11
end_mutex_group
begin_state
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
7
7
0
0
0
0
0
0
0
0
1
1
1
1
1
1
0
end_state
begin_goal
12
9 0
14 0
17 0
18 0
19 0
21 0
22 0
24 0
26 0
28 0
29 0
30 0
end_goal
59
begin_operator
fe1-feed-letter sheet1
1
0 0
2
0 15 7 0
0 25 -1 0
224
end_operator
begin_operator
fe1-feed-letter sheet2
1
0 0
2
0 16 7 0
0 27 -1 0
224
end_operator
begin_operator
fe1-feedmsi-letter sheet1
1
0 0
2
0 15 7 0
0 25 -1 0
125
end_operator
begin_operator
fe1-feedmsi-letter sheet2
1
0 0
2
0 16 7 0
0 27 -1 0
125
end_operator
begin_operator
hw1-bottomrightentrytobottomleftexit-letter sheet1
1
1 0
1
0 15 5 3
8999
end_operator
begin_operator
hw1-bottomrightentrytobottomleftexit-letter sheet2
1
1 0
1
0 16 5 3
8999
end_operator
begin_operator
hw1-bottomrightentrytorightexit-letter sheet1
1
1 0
1
0 15 5 1
1499
end_operator
begin_operator
hw1-bottomrightentrytorightexit-letter sheet2
1
1 0
1
0 16 5 1
1499
end_operator
begin_operator
hw1-bottomrightentrytotopleftexit-letter sheet1
1
1 0
1
0 15 5 9
8999
end_operator
begin_operator
hw1-bottomrightentrytotopleftexit-letter sheet2
1
1 0
1
0 16 5 9
8999
end_operator
begin_operator
hw1-leftentrytobottomleftexit-letter sheet1
1
1 0
1
0 15 0 3
1499
end_operator
begin_operator
hw1-leftentrytobottomleftexit-letter sheet2
1
1 0
1
0 16 0 3
1499
end_operator
begin_operator
hw1-leftentrytotopleftexit-letter sheet1
1
1 0
1
0 15 0 9
1499
end_operator
begin_operator
hw1-leftentrytotopleftexit-letter sheet2
1
1 0
1
0 16 0 9
1499
end_operator
begin_operator
hw1-toprightentrytobottomleftexit-letter sheet1
1
1 0
1
0 15 2 3
8999
end_operator
begin_operator
hw1-toprightentrytobottomleftexit-letter sheet2
1
1 0
1
0 16 2 3
8999
end_operator
begin_operator
hw1-toprightentrytorightexit-letter sheet1
1
1 0
1
0 15 2 1
1499
end_operator
begin_operator
hw1-toprightentrytorightexit-letter sheet2
1
1 0
1
0 16 2 1
1499
end_operator
begin_operator
hw1-toprightentrytotopleftexit-letter sheet1
1
1 0
1
0 15 2 9
8999
end_operator
begin_operator
hw1-toprightentrytotopleftexit-letter sheet2
1
1 0
1
0 16 2 9
8999
end_operator
begin_operator
initialize 
0
8
0 0 -1 0
0 1 -1 0
0 2 -1 0
0 3 -1 0
0 4 -1 0
0 5 -1 0
0 6 -1 0
0 31 0 1
0
end_operator
begin_operator
lc1-fromime-letter sheet1
1
2 0
1
0 15 4 5
4999
end_operator
begin_operator
lc1-fromime-letter sheet2
1
2 0
1
0 16 4 5
4999
end_operator
begin_operator
lc1-invertfromime-letter sheet1 back front
1
2 0
3
0 15 4 5
0 25 0 1
0 26 -1 0
9999
end_operator
begin_operator
lc1-invertfromime-letter sheet1 front back
1
2 0
3
0 15 4 5
0 25 -1 0
0 26 0 1
9999
end_operator
begin_operator
lc1-invertfromime-letter sheet2 back front
1
2 0
3
0 16 4 5
0 27 0 1
0 28 -1 0
9999
end_operator
begin_operator
lc1-invertfromime-letter sheet2 front back
1
2 0
3
0 16 4 5
0 27 -1 0
0 28 0 1
9999
end_operator
begin_operator
lc1-inverttoime-letter sheet1 back front
1
2 0
3
0 15 3 6
0 25 0 1
0 26 -1 0
9999
end_operator
begin_operator
lc1-inverttoime-letter sheet1 front back
1
2 0
3
0 15 3 6
0 25 -1 0
0 26 0 1
9999
end_operator
begin_operator
lc1-inverttoime-letter sheet2 back front
1
2 0
3
0 16 3 6
0 27 0 1
0 28 -1 0
9999
end_operator
begin_operator
lc1-inverttoime-letter sheet2 front back
1
2 0
3
0 16 3 6
0 27 -1 0
0 28 0 1
9999
end_operator
begin_operator
lc1-toime-letter sheet1
1
2 0
1
0 15 3 6
4999
end_operator
begin_operator
lc1-toime-letter sheet2
1
2 0
1
0 16 3 6
4999
end_operator
begin_operator
lime-simplex-letter sheet1 back image-1
2
3 0
25 0
3
0 7 -1 0
0 15 6 4
0 18 0 1
212790
end_operator
begin_operator
lime-simplex-letter sheet1 front image-1
2
3 0
26 0
3
0 9 -1 0
0 15 6 4
0 20 0 1
212790
end_operator
begin_operator
lime-simplex-letter sheet2 back image-1
2
3 0
27 0
3
0 11 -1 0
0 16 6 4
0 22 0 1
212790
end_operator
begin_operator
lime-simplex-letter sheet2 front image-1
2
3 0
28 0
3
0 13 -1 0
0 16 6 4
0 24 0 1
212790
end_operator
begin_operator
lime-simplexmono-letter sheet1 back image-2
2
3 0
25 0
3
0 8 -1 0
0 15 6 4
0 17 0 1
212790
end_operator
begin_operator
lime-simplexmono-letter sheet1 front image-2
2
3 0
26 0
3
0 10 -1 0
0 15 6 4
0 19 0 1
212790
end_operator
begin_operator
lime-simplexmono-letter sheet2 back image-2
2
3 0
27 0
3
0 12 -1 0
0 16 6 4
0 21 0 1
212790
end_operator
begin_operator
lime-simplexmono-letter sheet2 front image-2
2
3 0
28 0
3
0 14 -1 0
0 16 6 4
0 23 0 1
212790
end_operator
begin_operator
sys-stack-letter sheet1 dummy-sheet
1
4 0
2
0 15 1 8
0 29 -1 0
1499
end_operator
begin_operator
sys-stack-letter sheet2 sheet1
2
4 0
15 8
2
0 16 1 8
0 30 -1 0
1499
end_operator
begin_operator
uc1-fromime-letter sheet1
1
5 0
1
0 15 11 2
2999
end_operator
begin_operator
uc1-fromime-letter sheet2
1
5 0
1
0 16 11 2
2999
end_operator
begin_operator
uc1-invertfromime-letter sheet1 back front
1
5 0
3
0 15 11 2
0 25 0 1
0 26 -1 0
8000
end_operator
begin_operator
uc1-invertfromime-letter sheet1 front back
1
5 0
3
0 15 11 2
0 25 -1 0
0 26 0 1
8000
end_operator
begin_operator
uc1-invertfromime-letter sheet2 back front
1
5 0
3
0 16 11 2
0 27 0 1
0 28 -1 0
8000
end_operator
begin_operator
uc1-invertfromime-letter sheet2 front back
1
5 0
3
0 16 11 2
0 27 -1 0
0 28 0 1
8000
end_operator
begin_operator
uc1-inverttoime-letter sheet1 back front
1
5 0
3
0 15 9 10
0 25 0 1
0 26 -1 0
8000
end_operator
begin_operator
uc1-inverttoime-letter sheet1 front back
1
5 0
3
0 15 9 10
0 25 -1 0
0 26 0 1
8000
end_operator
begin_operator
uc1-inverttoime-letter sheet2 back front
1
5 0
3
0 16 9 10
0 27 0 1
0 28 -1 0
8000
end_operator
begin_operator
uc1-inverttoime-letter sheet2 front back
1
5 0
3
0 16 9 10
0 27 -1 0
0 28 0 1
8000
end_operator
begin_operator
uc1-toime-letter sheet1
1
5 0
1
0 15 9 10
2999
end_operator
begin_operator
uc1-toime-letter sheet2
1
5 0
1
0 16 9 10
2999
end_operator
begin_operator
uime-simplex-letter sheet1 back image-2
2
6 0
25 0
3
0 8 -1 0
0 15 10 11
0 17 0 1
127790
end_operator
begin_operator
uime-simplex-letter sheet1 front image-2
2
6 0
26 0
3
0 10 -1 0
0 15 10 11
0 19 0 1
127790
end_operator
begin_operator
uime-simplex-letter sheet2 back image-2
2
6 0
27 0
3
0 12 -1 0
0 16 10 11
0 21 0 1
127790
end_operator
begin_operator
uime-simplex-letter sheet2 front image-2
2
6 0
28 0
3
0 14 -1 0
0 16 10 11
0 23 0 1
127790
end_operator
0
