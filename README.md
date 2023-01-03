[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Build](https://github.com/klcodanr/com.danklco.blog.servletdemo/actions/workflows/maven.yml/badge.svg)](https://github.com/klcodanr/com.danklco.blog.servletdemo/actions/workflows/maven.yml)

# Sling Servlet Demo

This repository demonstrates three different methods for implementing servlets in AEM / Apache Sling including performance testing to compare the different methods.

Note all code should be considered sample and non-production ready.

## Dependencies

This project requires:

 - Java 11+
 - Maven 3+
 - Apache Benchmark
 - Make

## Use

To run the tests yourself, you will need an AEM instance running on port 4502. Then run the command `make` and it will:

 - Build and install the testing code
 - Execute the tests
 - Create a report at `target/timing.csv`
