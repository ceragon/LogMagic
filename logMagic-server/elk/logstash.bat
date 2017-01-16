@echo off
title logstash
SetLocal EnableDelayedExpansion
set APP_HOME=%cd%\logstash-2.4.1
set APP_CONF=%APP_HOME%\config\simple.conf
%APP_HOME%\bin\logstash agent -f %APP_CONF%