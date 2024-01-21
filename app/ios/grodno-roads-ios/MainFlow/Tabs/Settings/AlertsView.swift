//
//  AlertsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 22.12.23.
//

import SwiftUI
import Root

struct AlertsView: View {
    private let component: AlertsComponent
    
    init(_ component: AlertsComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("AlertsView")
        }
    }
}

#Preview {
    AlertsView(AlertsComponentPreview())
}
