//
//  RootView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct RootView: View {
    private let root: RootComponent
    
    init(root: RootComponent) {
        self.root = root
    }

    var body: some View {
        StackView(
            stackValue: StateValue(root.childStack),
            getTitle: { child in
                switch child {
                case is RootComponentChild.MainFlow: "MainFlow"
                case is RootComponentChild.Onboarding: "Onboarding"
                default: "Default"
                }
            },
            onBack: { _ in },
            childContent: { child in
                switch child {
                case let child as RootComponentChild.MainFlow: MainFlowView(child.component)
                case let child as RootComponentChild.Onboarding: OnboardingView(child.component)
                default: EmptyView()
                }
            }
        )
    }
}
