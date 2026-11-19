all:
	javac -d classes -sourcepath src src/org/ioopm/calculator/Calculator.java

run:
	java -cp classes org.ioopm.calculator.Calculator

test_run:
	javac -d classes_test -sourcepath src src/org/ioopm/calculator/Test.java
	java -enableassertions:org.ioopm.calculator.Test -cp classes_test org.ioopm.calculator.Test

clean:
	rm -rf classes