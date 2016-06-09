cd /home/certi/CustomReportsTelefonica/
pkill -f Autentication
java -Dfile.encoding=utf-8 -jar ReportAutenticationTarget/Autentication-1.0-SNAPSHOT.jar server configAt.yml &
