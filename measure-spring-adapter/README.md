# Spring Adapter for JMeasure

Integrates JMeasure into the Spring framework by providing autoconfiguration of device and socket factories.

## Usage

```xml
<dependency>
    <groupId>org.jtmc</groupId>
    <artifactId>measure-spring-adapter</artifactId>
</dependency>
```

## Example

```java
@RestController
class TestRunner {

    @Autowired
    private CompositeSCPIDeviceFactory devFactory;

    @Autowired
    private CompositeSCPISocketFactory socketFactory;

    @PostMapping("/run")
    public void runTests() {
        SCPISocket socket = socketFactory.create("TCP0::192.168.1.2::5025::SOCKET");
        SCPIDevice device = devFactory.create(socket);

        //device.send(..);
        //..
    }

}
```
