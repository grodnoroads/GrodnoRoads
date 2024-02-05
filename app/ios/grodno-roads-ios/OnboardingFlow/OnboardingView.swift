//
//  OnboardingView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct OnboardingView: View {
    private let component: OnboardingComponent
    
    init(_ component: OnboardingComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Button {
                component.finishOnboarding()
            } label: {
                Text("Complete onboarding")
            }.buttonStyle(.borderedProminent)
        }
    }
}

#Preview {
    OnboardingView(
        OnboardingComponentImplKt.buildOnboardingComponent(
            componentContext: .context(),
            onFinishOnboarding: {}
        )
    )
}
