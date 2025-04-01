JAVAC=javac
JAVA=java
#Chemin a modifier selon votre repertoire
<<<<<<< HEAD
MODULE_PATH=/home/khiro/Files/javafx-sdk-23.0.2/lib
=======
MODULE_PATH=/home/samy/Téléchargements/javafx-sdk-23.0.2/lib
>>>>>>> f15da4a2fab5c3a5a14d1f6ee606e5bbf513b4fd
MODULES=javafx.controls,javafx.fxml
SRC=src/**/*.java
OUT_DIR=out
MAIN_CLASS=Application.App


all:compile run


compile:
	$(JAVAC) --module-path $(MODULE_PATH) --add-modules $(MODULES) -d $(OUT_DIR) $(SRC) && cp -r src/pictures/ $(OUT_DIR)

# cp: cp -r src/pictures/ $(OUT_DIR)

run: compile
	$(JAVA) --enable-preview --module-path $(MODULE_PATH) --add-modules $(MODULES) -cp $(OUT_DIR) $(MAIN_CLASS)


clean :
	rm -rf $(OUT_DIR)
