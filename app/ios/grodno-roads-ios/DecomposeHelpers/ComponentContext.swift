//
//  ComponentContext.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import Root

extension ComponentContext where Self == DefaultComponentContext {
    static func context() -> Self {
        Self(lifecycle: ApplicationLifecycle())
    }
}
