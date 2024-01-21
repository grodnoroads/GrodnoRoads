import SwiftUI
import Root

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableValue<T>

    var wrappedValue: T { obj.value }

    init(_ value: Value<T>) {
        obj = ObservableValue(value)
    }
}

@propertyWrapper struct StateFlow<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableFlow<T>

    var wrappedValue: T { obj.value }

    init(_ value: CStateFlow<T>) {
        obj = ObservableFlow(value)
    }
}
