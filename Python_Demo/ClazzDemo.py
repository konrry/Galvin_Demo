#coding:utf-8
import MySQLdb
import time,datetime



class Person:
    "Person"
    name = "Galvin";

    def __init__(self):
        print "Hello World ... ";
        self.sex = "male";

    def sleep(self):
        print " I am sleep ... ";

print Person.__doc__;
print time.strftime('%Y-%m-%d %H:%M:%S');
print datetime.datetime.now();
start = time.time();
time.sleep(0.2);
end = time.time();
print (end - start) * 1000;
person = Person();
person.sleep();
print person.sex;
