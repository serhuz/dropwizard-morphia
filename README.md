# dropwizard-morphia [![Build Status](https://travis-ci.org/serhuz/dropwizard-morphia.svg?branch=master)](https://travis-ci.org/serhuz/dropwizard-morphia)

Morphia integration for Dropwizard

## Add Dependency

Create `settings.xml` in your project dir
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
          xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
    
    <profiles>
        <profile>
            <repositories>
                <repository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-serhuz-maven</id>
                    <name>bintray</name>
                    <url>http://dl.bintray.com/serhuz/maven</url>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-serhuz-maven</id>
                    <name>bintray-plugins</name>
                    <url>http://dl.bintray.com/serhuz/maven</url>
                </pluginRepository>
            </pluginRepositories>
            <id>bintray</id>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>bintray</activeProfile>
    </activeProfiles>
</settings>
```

Add dependency to `pom.xml`

```xml
<dependency>
  <groupId>xyz.randomcode</groupId>
  <artifactId>dropwizard-morphia</artifactId>
  <version>1.0.5-1</version>
</dependency>
```

## Usage
Add the following to your Dropwizard `Configuration` class
```java
@Valid
@NotNull
private MongoConfiguration mongo;

@JsonProperty
public MongoConfiguration getMongo() {
    return mongo;
}

@JsonProperty
public void setMongo(MongoConfiguration mongo) {
    this.mongo = mongo;
}
```
Add the following to your Dropwizard `Application` class
```java
private MorphiaBundle<ExampleConfiguration> morphiaBundle =
        new MorphiaBundle<ExampleConfiguration>(ExampleEntity.class) {
            @Override
            protected MongoConfiguration getMongo(ExampleConfiguration configuration) {
                return configuration.getMongo();
            }
        };
        
@Override
public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    bootstrap.addBundle(morphiaBundle);
}


@Override
public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
    Datastore datastore = morphiaBundle.getDatastore();
}
```

It is also possible to create an instance of `MorphiaPackageBundle`, which accepts package name as constructor parameter.
```java
private static MorphiaPackageBundle<ExampleConfiguration> morphiaBundle = 
        new MorphiaPackageBundle<ExampleConfiguration>("some.package.with.entities", false) {
            @Override
            protected MongoConfiguration getMongo(DummyConfiguration configuration) {
                return configuration.getMongo();
            }
        };
```

## Configuration
Minimal configuration requires the following
```yaml
mongo:
  type: uriConfig
  uri: ${MONGODB_URI}
```

Currently the only available type is `uriConfig`. A `mongodb://` uri is used to connect to Mongo. `dbName` can be retrived from uri, if it is specified. Otherwise you'll have to set `dbName` inside `mongo` explicitly.

Available Morhpia settings:
- storeNulls (boolean) Enables/disable storing `null` values in Mongo
- storeEmpties (boolean) Enables/disable storing empty values in Mongo
- enableValidationExtension (boolean) Add Hibernate Validator interceptor to Morphia
 
## Example
Example app with a bit of Morphia usage is available [here](https://github.com/serhuz/dropwizard-morhia-example)
