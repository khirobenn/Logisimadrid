# Compiler & Runner
JAVAC = javac
JAVA = java

# Chemins
MODULE_PATH = /home/amir/Téléchargements/javafx-sdk-23.0.1/lib/
MODULES = javafx.controls,javafx.fxml,javafx.swing
SRC_DIR = src
OUT_DIR = out
LIB_DIR = lib
PICTURES_DIR = src/pictures

# Dépendances
JSON_SIMPLE_JAR = $(LIB_DIR)/json-simple-1.1.1.jar
JUNIT_JAR = $(LIB_DIR)/junit-platform-console-standalone-1.9.3.jar
CLASSPATH = $(OUT_DIR):$(JSON_SIMPLE_JAR):$(JUNIT_JAR)

# Classe principale
MAIN_CLASS = Application.App

# Règles principales
.PHONY: all compile run test clean

all: compile run

compile:
	@echo "Compilation des sources principales..."
	$(JAVAC) --module-path $(MODULE_PATH) --add-modules $(MODULES) \
	-cp "$(CLASSPATH)" \
	-d $(OUT_DIR) $$(find $(SRC_DIR) -name "*.java" -not -name "*Test.java")
	@cp -r $(PICTURES_DIR) $(OUT_DIR)/
	@echo "Compilation principale terminée."

compile-tests: compile
	@echo "Compilation des tests..."
	$(JAVAC) --module-path $(MODULE_PATH) --add-modules $(MODULES) \
	-cp "$(CLASSPATH)" \
	-d $(OUT_DIR) $$(find $(SRC_DIR) -name "*Test.java")
	@echo "Compilation des tests terminée."

run: compile
	$(JAVA) --enable-preview \
	--module-path $(MODULE_PATH) \
	--add-modules $(MODULES) \
	-cp "$(CLASSPATH)" \
	$(MAIN_CLASS)

test: compile-tests
	@echo "Lancement des tests..."
	$(JAVA) --module-path $(MODULE_PATH) \
	--add-modules $(MODULES) \
	-jar $(JUNIT_JAR) \
	--class-path "$(CLASSPATH)" \
	--scan-classpath \
	--details tree

clean:
	@rm -rf $(OUT_DIR)
	@echo "Nettoyage terminé."