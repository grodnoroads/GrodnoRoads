//
//  ChangelogView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct ChangelogView: View {
    private let component: ChangelogComponent
    
    @StateFlow
    private var state: ChangelogStoreState
    
    init(_ component: ChangelogComponent) {
        self.component = component
        _state = StateFlow(component.state)
    }
    
    var body: some View {
        ScrollView {
            VStack {
                Text("ChangelogView").padding()
                Divider()
                ForEach(state.releaseNotes) { notes in
                    Text(notes.notes)
                    Divider()
                }
            }
        }
    }
}

extension ReleaseNotes: Identifiable {}

#Preview {
    ChangelogView(ChangelogComponentPreview())
}
