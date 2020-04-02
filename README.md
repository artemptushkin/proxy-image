### Description

Image proxy server based on spring cloud gateway filters provides opportunity to modify images on fly.
<br>There are two ways to customize response image:
* By passing additional query parameter at the request, see tables below
* By setting up default filters parameters at the spring application properties

It is available to use several filters for a specific url and enable an image filter by customizing settings of proxied server
response (see `io.github.aptushkin.proxy.image.modify.predicate.ImageModifierPredicate` and `responseHeaderName` property). 

<br>Usage:
1. As standalone http (https in todo list) proxy server to modify image responses from proxied servers
2. As dependency, it is possible to fetch `proxy-image-starter` to get image modifications filters for your custom spring cloud gateway server
3. TODO: As part of spring cloud microservices infrastructure (eureka, config server)
4. TODO: As part of your Spring REST API server by fetching a starter

### How to use inside your spring-cloud-gateway server

Fetch dependency from `https://repo1.maven.org/maven2`:
<br>Maven:
```xml
<dependency>
    <groupId>io.github.artemptushkin.proxy.image</groupId>
    <artifactId>proxy-image-starter</artifactId>
    <version>1.0.3</version>
</dependency>
```
Gradle:
```groovy
compile group: 'io.github.aptushkin.proxy.image', name: 'proxy-image-starter', version: '0.0.1-SNAPSHOT'
```

Use spring properties to activate custom image filters: 
```yaml
proxy.image:
    enalbed: true
```

#### Customization of gateway filters properties

1. Enable default filter to proxy every request to the target destination
    ```
    spring:
      cloud:
        gateway:
          default-filters:
            - ProxyForward
    ```
2. Customize routing properties to specify image modification filters
    ```yaml
     spring.cloud.gateway.routes:
       - id: modify_image_router
       uri: no://op
        predicates:
         - Path=/**
    ```

3. Specify required filters
    ```yaml
    spring.cloud.gateway:
     filters:
       name: ModifyImageSize
       args:
         responseHeaderName: Content-Type
         regexp: image/.*
    ```
   
### Available image filters

## Resize filter

Spring properties name: ResizeImage

**Default properties:**

| Property           	| Required 	| Description                                                                                                            	|
|:--------------------:	|:----------:	|------------------------------------------------------------------------------------------------------------------------	|
| defaultWidth       	| N        	| Default width value of resize method                                                                                   	|
| defaultHeight      	| N        	| Default height value of resize method                                                                                  	|
| defaultMode        	| N        	| Default mode value of resize method; See http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Mode.html 	|
| defaultMethod      	| N        	| Default method value of resize method; http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Method.html 	|
| responseHeaderName 	| Y        	| The header name of proxied server response                                                                             	|
| regexp             	| Y        	| Regex to verify response header value. If matched then current filter should be applied                                	|
| onNotExistedHeader 	| N        	| If true, current filter will be applied; otherwise not                                                                 	|
| defaultFormat      	| N        	| default file format for image modification                                                                             	|                                                                   |

**Query parameters:**

| Property 	| Description                                                                                                      	|
|----------	|------------------------------------------------------------------------------------------------------------------	|
| width    	| Width value of target image                                                                                      	|
| height   	| Height value of target image                                                                                     	|
| format   	| File format for image modification                                                                               	|
| mode     	| Mode value of resize method; See http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Mode.html   	|
| method   	| Method value of resize method; See http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Mode.html 	|

Curl example:
```
curl --location --request GET 'http://github.githubassets.com/images/modules/open_graph/github-octocat.png?width=100&height=300&format=jpg'
```

<br>

## Crop filter

Spring properties name: CropImage

**Default properties:**

| Property           	| Required 	| Description                                                                             	|
|--------------------	|----------	|-----------------------------------------------------------------------------------------	|
| defaultWidth       	| N        	| Default width value of crop method                                                    	|
| defaultHeight      	| N        	| Default height value of crop method                                                   	|
| defaultX           	| N        	| Default X value of crop method; Used to crop the proxy image from the top-left corner   	|
| defaultY           	| N        	| Default Y value of crop method; Used to crop the proxy image from the top-left corner   	|
| responseHeaderName 	| Y        	| The header name of proxied server response                                              	|
| regexp             	| Y        	| Regex to verify response header value. If matched then current filter should be applied 	|
| onNotExistedHeader 	| N        	| If true, current filter will be applied; otherwise not                                  	|
| defaultFormat      	| N        	| default file format for image modification                                              	|

**Query parameters:**

| Property 	| Description                                                                   	|
|----------	|-------------------------------------------------------------------------------	|
| width    	| Width value of target image                                                   	|
| height   	| Height value of target image                                                  	|
| format   	| File format for image modification                                            	|
| x        	| X value of crop method; Used to crop the proxy image from the top-left corner 	|
| y        	| Y value of crop method; Used to crop the proxy image from the top-left corner 	|

Curl example:
```
curl --location --request GET 'http://github.githubassets.com/images/modules/open_graph/github-octocat.png?width=600&height=500&x=400&y=100&format=jpg'
```

<br>

### Rotate filter

Spring properties name: RotateImage

**Default properties:**

| Property           	| Required 	| Description                                                                                                                       	|
|--------------------	|----------	|-----------------------------------------------------------------------------------------------------------------------------------	|
| rotation           	| N        	| Default rotation value for rotation method; See http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Rotation.html 	|
| responseHeaderName 	| Y        	| The header name of proxied server response                                                                                        	|
| regexp             	| Y        	| Regex to verify response header value. If matched then current filter should be applied                                           	|
| onNotExistedHeader 	| N        	| If true, current filter will be applied; otherwise not                                                                            	|
| defaultFormat      	| N        	| default file format for image modification                                                                                        	|

**Query parameters:**

| Property 	| Description                                                                                                               	|
|----------	|---------------------------------------------------------------------------------------------------------------------------	|
| rotation 	| Rotation value for rotation method; See http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.Rotation.html 	|
| format   	| File format for image modification                                                                                        	|

Curl example:
```
curl --location --request GET 'http://github.githubassets.com/images/modules/open_graph/github-octocat.png?rotation=CW_180'
```

# Development

### How to build

```
mvn clean install
```

### How to run

Run proxy server with classpath config file
```
java -jar ./proxy-image-server/target/proxy-image-server-0.0.1-SNAPSHOT.jar
```

### How deploy current build to sonartype nexus

```
mvn clean deploy
```

### How to release deploy to sonartype nexus

```
mvn clean release:prepare release:perform -Prelease
```

#### TODO

1. Enabled HTTPS proxy requests for image-server
2. Make starter for common web applications to use it with internal response filters for a specific url
3. To add dependencies of spring cloud infrastructure: eureka-client, config-client