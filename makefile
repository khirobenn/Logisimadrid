JAVAC=javac
JAVA=java
#Chemin a modifier selon votre repertoire
MODULE_PATH=../Downloads/javafx-sdk-23.0.2/lib/
MODULES=javafx.controls,javafx.fxml
SRC=src/**/*.java
OUT_DIR=out
MAIN_CLASS=Test.App


all:compile


compile:
	$(JAVAC) --module-path $(MODULE_PATH) --add-modules $(MODULES) --enable-preview --release 23 -d $(OUT_DIR) $(SRC)

run: compile
	$(JAVA) --enable-preview --module-path $(MODULE_PATH) --add-modules $(MODULES) -cp $(OUT_DIR) $(MAIN_CLASS)


clean :
	rm -rf $(OUT_DIR)
