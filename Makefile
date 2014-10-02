SRC = src
SOURCES = $(shell ls $(SRC)/*.scala)
S = scala
SC = scalac
TARGET = build
CP = $(TARGET):scalirc.jar
SPEC = scala.RomanSpec
 
compile: $(SOURCES:.scala=.class)
 
%.class: %.scala
	@echo "Compiling $*.scala.."
	@$(SC) -cp $(CP) -d $(TARGET) $*.scala
 
test: compile
	@$(S) -cp $(CP) uk.skelty.ScalIRC.Main -p . -o -s $(SPEC)
 
clean:
	@$(RM) $(SRC)/*.class