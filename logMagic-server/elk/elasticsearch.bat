@echo off
title elasticsearch
SetLocal EnableDelayedExpansion
set APP_HOME=%cd%\elasticsearch-2.4.4
set APP_CONF=%APP_HOME%\config\simple.conf
%APP_HOME%\bin\elasticsearch