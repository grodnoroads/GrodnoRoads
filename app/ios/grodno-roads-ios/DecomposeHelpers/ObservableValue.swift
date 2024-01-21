import SwiftUI
import Root

public class ObservableValue<T: AnyObject>: ObservableObject {
    @Published
    private(set) var value: T

    private var cancellation: Cancellation?
    
    init(_ value: Value<T>) {
        self.value = value.value
        self.cancellation = value.observe { [weak self] value in self?.value = value }
    }

    deinit {
        cancellation?.cancel()
    }
}

public class ObservableFlow<T: AnyObject>: ObservableObject {
    @Published
    private(set) var value: T
    
    private var cancelable: Cancellation?
    
    init(_ state: CStateFlow<T>) {
        value = state.value

        cancelable = FlowWrapper<T>(flow: state).collect(
            consumer: { [weak self] value in
                guard let value else { return }
                self?.value = value
            }
        )
    }
    
    func recreate(_ state: CStateFlow<T>) {
        cancelable?.cancel()
        
        cancelable = FlowWrapper<T>(flow: state).collect(
            consumer: { [weak self] value in
                if let value {
                    self?.value = value
                }
            }
        )
    }
    
    deinit {
        cancelable?.cancel()
    }
}
