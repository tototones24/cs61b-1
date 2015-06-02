import sqlite3, sys
conn = sqlite3.connect(':memory:')
c = conn.cursor();
c.execute("create table data (string text primary key, weight real)");

with open(sys.argv[1]) as f:
    #skip first line
    f.readline()
    content = f.readlines()

k = int(sys.argv[3])
content = [(line.split("\t")[1][:-1], float(line.split("\t")[0])) for line in content]
c.executemany("insert into data values (?, ?)", content)

c.execute("select weight, string from data where instr(string, ?)=1 order by weight desc limit ?", (sys.argv[2], k))

for s in c.fetchall():
    print(s[0], s[1])
