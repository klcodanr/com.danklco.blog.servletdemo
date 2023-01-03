
AB_PARAMS = -n 5000 -c 5
AB_AUTH_HEADER = -H "Authorization: Basic YWRtaW46YWRtaW4="

.DEFAULT_GOAL := run

run: clean install test deps report

deps:
		mvn dependency:copy -Dartifact=commons-io:commons-io:2.4 -DoutputDirectory=target/lib -Dmdep.stripVersion=true
		mvn dependency:copy -Dartifact=commons-lang:commons-lang:2.4 -DoutputDirectory=target/lib -Dmdep.stripVersion=true
		mvn dependency:copy -Dartifact=org.apache.commons:commons-csv:1.1 -DoutputDirectory=target/lib -Dmdep.stripVersion=true
		mvn dependency:copy -Dartifact=com.sixdimensions.coe.util:ab-parser:0.1.0 -DoutputDirectory=target/lib -Dmdep.stripVersion=true

clean:
		mvn clean

test:
		mkdir -p target/res
		echo "Running HelloWorldPathServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/bin/test/hello > target/res/HelloWorldPathServlet.txt
		sleep 10
		echo "Running HelloWorldResourceServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/content/test/hello.txt > target/res/HelloWorldResourceServlet.txt
		sleep 10
		echo "Running HelloWorldOsgiServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/test/hello > target/res/HelloWorldOsgiServlet.txt
		sleep 10

		echo "Running ServiceUserPathServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/bin/test/serviceuser > target/res/ServiceUserPathServlet.txt
		sleep 10
		echo "Running ServiceUserdResourceServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/content/test/serviceuser.txt > target/res/ServiceUserResourceServlet.txt
		sleep 10
		echo "Running ServiceUserOsgiServlet test"
		ab $(AB_PARAMS) http://127.0.0.1:4502/test/serviceuser > target/res/ServiceUserOsgiServlet.txt
		sleep 10

		echo "Running AuthPathServlet test"
		ab $(AB_PARAMS) $(AB_AUTH_HEADER) http://127.0.0.1:4502/bin/test/auth > target/res/AuthPathServlet.txt
		sleep 10
		echo "Running AuthdResourceServlet test"
		ab $(AB_PARAMS) $(AB_AUTH_HEADER) http://127.0.0.1:4502/content/test/auth.txt > target/res/AuthResourceServlet.txt
		sleep 10
		echo "Running AuthOsgiServlet test"
		ab $(AB_PARAMS) $(AB_AUTH_HEADER) http://127.0.0.1:4502/test/auth > target/res/AuthOsgiServlet.txt

report:
		java -cp "target/lib/*" com.sixd.coe.util.Main target/res target/timing.csv

install:
		mvn install sling:install