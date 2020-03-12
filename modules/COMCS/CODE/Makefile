NAME = walled_server
BIN_FOLDER = bin/
DEPS_FOLDER = "src:json-simple-1.1.1.jar:mariadb-java-client-2.5.4.jar:"

SOURCES = sources.txt

MAIN_PATH_MANIFEST = "Main.Main"

MANIFEST = "MANIFEST.MF"
JAR = $(NAME).jar

all:
	@mkdir -p $(BIN_FOLDER)
	#@find -name "*.java" > sources.txt
	javac -cp $(DEPS_FOLDER) -d $(BIN_FOLDER) @$(SOURCES)
	@echo -e "Class-Path: ./json-simple-1.1.1.jar\n ./mariadb-java-client-2.5.4.jar\nMain-Class: "$(MAIN_PATH_MANIFEST) > $(MANIFEST)
	jar -cmf $(MANIFEST) $(JAR) -C $(BIN_FOLDER) ./

run: all
	java -jar $(JAR)

clean:
	rm -rf $(BIN_FOLDER)
	rm -rf $(MANIFEST)
	rm -rf $(SOURCES)

fclean: clean
	rm -rf $(JAR)
