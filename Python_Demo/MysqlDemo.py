#coding:utf-8
import MySQLdb
import time


class EventProcessor:
    "EventProcessor"

    def __init__(self):
        print " 初始化数据库 ..."
        self.conn = MySQLdb.connect(host="10.200.3.178",port=3306,user="lvmm_log",passwd="12345678",db="lvmm_log",charset="utf8");
        self.cursor = self.conn.cursor();
        print " 初始化数据库完毕 ..."

    def detroy(self):
        self.conn.commit();
        self.cursor.close();
        self.conn.close();

    def execute(self, start, end):
        insert_sql = 'INSERT INTO COM_LOG_ORD_ORDER_A'\
                        '(LOG_MSG_ID,PARENT_ID,PARENT_TYPE,OBJECT_ID,OBJECT_TYPE,LOG_TYPE,LOG_NAME,CONTENT,' \
                        'CONTENT_TYPE,MENO,OPERATOR_NAME,CREATE_TIME,RECEIVE_TIME,LOG_TIME)'\
                      'VALUES'\
                        '("ASJDFIOQEYRTQPJFAJSDOFASLDF[10.115.4.95]",%s,"PARENT_TYPE",%s,"OBJECT_TYPE","LOG_TYPE",' \
                        '"LOG_NAME","驴妈妈旅游网日志",' \
                        '"VARCHAR","MENO","GALVIN",SYSDATE(),SYSDATE(),SYSDATE())';
        values = [];
        while(start <= end):
            start = start + 1;
            values.append((start,start));
            if values.__len__() >= 10:
                try:
                    self.cursor.executemany(insert_sql,values);
                    self.conn.commit();
                except BaseException, pams:
                    print 'exception ... ';
                    print pams;
                else:
                    values = [];
                    print "保存 ===>>> " + str(start);

    def yzMysqlQuery(self):
        queryId = 1;
        querySqlList = [];
        while(queryId <= 1000):
            querySqlList.append("SELECT * FROM COM_LOG_ORD_ORDER_A WHERE OBJECT_ID = "+str(queryId)+" AND OBJECT_TYPE = \"OBJECT_TYPE\"");
            queryId = queryId + 1;

        sumScope = 0;
        count = 0;
        while(count < 20):
            for querySql in querySqlList:
                start = time.time();
                self.cursor.execute(querySql);
                end = time.time();
                scope = end - start
                sumScope = sumScope + scope;
            print "count: " + str(count);
            print "sumScope: " + str(sumScope);

            count = count + 1;

        print sumScope/20000;




print " 程序开始 ... " + time.strftime('%Y-%m-%d %H:%M:%S');
eventProcessor = EventProcessor();
#eventProcessor.execute(3000000,4000000);
eventProcessor.yzMysqlQuery();
eventProcessor.detroy();
print " 程序结束 ... " + time.strftime('%Y-%m-%d %H:%M:%S');