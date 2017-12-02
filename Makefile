COMPONENT=TinySmartAutomationAppC
TOSMAKE_PRE_EXE_DEPS += javaUI/TinySmartAutomation.class
TOSMAKE_CLEAN_EXTRA = javaUI/*.class

javaUI/TinySmartAutomation.class: $(wildcard javaUI/*.java) javaUI/TinySmartAutomationMsg.java
	javac -target 1.4 -source 1.4 javaUI/*.java

javaUI/TinySmartAutomationMsg.class: javaUI/TinySmartAutomationMsg.java
	nescc-mig java $(CFLAGS) -java-classname=javaUI/TinySmartAutomationMsg utils.h serial_msg -o $@


# radio Channel emission
CC2420_CHANNEL=16
# manage radio power emission
CFlags = -DCC2420_DEF_RFPower= 3
TINYOS_ROOT_DIR?=../..

BUILT_SOURCES = utils.c utils.h
include $(TINYOS_ROOT_DIR)/Makefile.include

PFLAGS += -I$(TINYOS_ROOT_DIR)/tos/lib/printf
