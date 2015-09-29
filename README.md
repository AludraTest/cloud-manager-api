## Build Status
[![Build Status](https://travis-ci.org/AludraTest/cloud-manager-api.svg?branch=master)](https://travis-ci.org/AludraTest/cloud-manager-api)

# cloud-manager-api
API for the AludraTest Cloud Manager.

Include a dependency to this module in your custom Resource Module for AludraTest Cloud Manager.

The entry class CloudManagerApp and its static accessor getInstance() allows access to most of the ACM functionality.

Documentation on how to create your custom Resource Module will follow. For the time being, you can examine the 
cloud-manager-selenium module for an example for a Resource Module. Note the src/main/resources/META-INF/plexus/components.xml file,
which is the "hook" to register the module within ACM.


## Generate Architecture Report

A ConQAT compatible architecture is included in this project. To generate the HTML report, execute

```
mvn clean compile site -P run-conqat
```

This will download the ConQAT binaries into `./conqat/engine`, if they are not already present (around 50 MB, please ensure you have an internet connection active), 
and execute ConQAT during site generation. You will find the ConQAT report in

```
target/site/conqat/index.html
```
