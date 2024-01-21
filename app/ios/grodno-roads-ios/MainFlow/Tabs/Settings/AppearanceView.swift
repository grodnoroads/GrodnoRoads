//
//  AppearanceView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct AppearanceView: View {
    private let component: AppearanceComponent
    
    init(_ component: AppearanceComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("AppearanceView")
        }
    }
}

#Preview {
    AppearanceView(AppearanceComponentPreview())
}
