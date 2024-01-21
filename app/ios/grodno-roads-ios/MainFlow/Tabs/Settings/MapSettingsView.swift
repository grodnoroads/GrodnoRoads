//
//  MapSettingsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import SwiftUI
import Root

struct MapSettingsView: View {
    private let component: MapSettingsComponent
    
    init(_ component: MapSettingsComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("MapSettingsView")
        }
    }
}

#Preview {
    MapSettingsView(MapSettingsComponentPreview())
}
