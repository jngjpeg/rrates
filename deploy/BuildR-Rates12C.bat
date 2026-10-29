set src=C:\SVN\DIPS-IPP\tags\Test\R-RATES
set WL_HOME=C:\Dev\Tools\Weblogic\12.2.1.3\Oracle_Home\wlserver
@rem set ant_home=C:\ApacheAnt\apache-ant-1.8.2
@rem set ant_home=C:\Users\DANG_C\Desktop\wls1212_dev\wls12120\oracle_common\modules\org.apache.ant_1.8.4
set ant_home=C:\ApacheAnt\apache-ant-1.8.2
set JAVA_HOME=C:\Program Files\java\jdk1.8.0_181

set path=%JAVA_HOME%\bin;%ant_home%\bin;%path%

set classpath=.;%ant_home%\lib\ant.jar;%WL_HOME%\server\lib\weblogic.jar;%classpath%


call ant

pause