GRADLE=./gradlew
RIFTC=riftc
JAR=riftc-java-all.jar
SRCS=$(shell find src -name "*.java")

$(RIFTC): $(SRCS)
	$(GRADLE) clean build
	cp build/libs/$(JAR) $(RIFTC)
	chmod +x $(RIFTC)

clean:
	rm $(RIFTC)
