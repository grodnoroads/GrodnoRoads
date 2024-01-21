//
//  FaqView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 21.12.23.
//

import SwiftUI
import Root

struct FaqScreenView: View {
    private let component: FaqComponent
    
    @StateFlow
    private var state: FaqStoreState
    
    init(_ component: FaqComponent) {
        self.component = component
        _state = StateFlow(component.state)
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                ScrollView {
                    LazyVStack(spacing: 16) {
                        ForEach(state.faq) { faq in
                            FaqView(question: faq.question, answer: faq.answer)
                        }
                    }
                    .padding(.horizontal, 16)
                }
                if state.isLoading {
                    LoadingView()
                }
            }
        }
        .navigationBarTitle("FAQ")
    }
}

private struct FaqView: View {
    var question: String
    var answer: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10)
                .fill(.white)
                .shadow(radius: 2)
            
            VStack(alignment: .leading) {
                Text(question)
                    .font(.headline)
                    .bold()
                    .padding([.bottom], 4)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(answer)
                    .font(.body)
            }
            .padding()
        }
        .fixedSize(horizontal: false, vertical: true)
    }
}

extension FAQ: Identifiable {}

#Preview {
    FaqScreenView(FaqComponentPreview())
}
