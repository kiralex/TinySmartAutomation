COMPONENT=TinySmartAutomation
# radio Channel emission
CC2420_CHANNEL=16
# manage radio power emission
CFlags = -DCC2420_DEF_RFPower= 3
TINYOS_ROOT_DIR?=../..

BUILT_SOURCES = utils.c utils.h
include $(TINYOS_ROOT_DIR)/Makefile.include

PFLAGS += -I$(TINYOS_ROOT_DIR)/tos/lib/printf
# PFLAGS+=-I$(TINYOS_ROOT_DIR)/apps/Test01/utils.c
