# Writing drivers

## Library guideline

+ The project folder should be 'measure-driver-vendorName', e.g: 'measure-driver-keysight', 'measure-driver-tektronix'

+ The project name should driver-vendorName, which must be included in the root project settings.gradle file.

+ The project level build.gradle file should have a description field

## Device guideline

### Important

+ Devices should implement interfaces found inside `org.jtmc.core.instrument`, for example a Mixed Signal Oscilloscope (MSO) would implement the Oscilloscope and LogicAnalyzer interfaces.

+ Devices should have functionality not covered by the interfaces as public methods and they should use already builtin types as much as possible.

+ For every vendor there should be at least one factory class.

### Less important

+ Add your factory class as Bean in the Spring Adapter.

+ Usually there are different variants of a product, it's recommended to implement most of the functionality using a single class. This generic device should have the model's name/number replaced with X's or 0's accordingly, while still being clear that it's the common driver for multiple devices. Then inside this class using the device identifier parameter you can differentiate between the models.
