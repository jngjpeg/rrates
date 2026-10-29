set src=C:\PROJECTS\RRates_Oracle\RRates4.2\checkout_rrates

@rem set WL_HOME=C:\Dev\Tools\Weblogic\12.2.1.3\Oracle_Home\wlserver
set WL_HOME=C:\DevTools\Weblogic\14.1.2.0\wlserver

set ant_home=C:\Dev\Tools\Ant1.9.4
set ext_lib=C:\Dev\Tools\ipslib\ipplib
set ips_shared_src=C:\PROJECTS\RRates_Oracle\RRates4.2\checkout_ipsshared
set otspsso_src=C:\PROJECTS\RRates_Oracle\RRates4.2\checkout_otspsso
set build.to.environment=DEV
set buildVersion=RRate_DEV_4.2
set weblogicVersion=Weblogic 14.1.2.0
set jdkVersion=JDK17
@rem set JAVA_HOME=C:\Program Files\java\jdk1.8.0_181
set JAVA_HOME=C:\DevTools\JDK17

set path=%JAVA_HOME%\bin;%ant_home%\bin;%path%

set classpath=.;%ant_home%\lib\ant.jar;%WL_HOME%\server\lib\weblogic.jar;%classpath%


call ant

pause