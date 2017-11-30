COMPONENT=TinySmartAutomationAppC
TOSMAKE_PRE_EXE_DEPS += javaUI/TinySmartAutomation.class
TOSMAKE_CLEAN_EXTRA = javaUI/*.class javaUI/TinySmartAutomationMsg.java

TinySmartAutomation.class: $(wildcard *.java) javaUI/TinySmartAutomationMsg.java
	javac -target 1.4 -source 1.4 *.java

TinySmartAutomationMsg.java:
	nescc-mig java $(CFLAGS) -java-classname=javaUI/TinySmartAutomationMsg TinySmartAutomation.h serial_msg -o $@

# radio Channel emission
CC2420_CHANNEL=16
# manage radio power emission
CFlags = -DCC2420_DEF_RFPower= 3
TINYOS_ROOT_DIR?=../..

BUILT_SOURCES = TinySmartAutomation.c TinySmartAutomation.h
include $(TINYOS_ROOT_DIR)/Makefile.include

PFLAGS += -I$(TINYOS_ROOT_DIR)/tos/lib/printf
