//
//  RoundButtonView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 5.01.24.
//

import SwiftUI

struct RoundButtonView: View {
    
    private let image: String
    private let action: (() -> Void)
    
    init(image: String, action: @escaping () -> Void) {
        self.image = image
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            Image(systemName: image)
                .tint(.black)
        }
        .buttonStyle(.borderedTransparent)
    }
}

#Preview {
    RoundButtonView(image: "questionmark.circle.fill") {}
}


struct BorderedTransparentButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.all, 12)
            .foregroundStyle(.tint)
            .background(
                RoundedRectangle(
                    cornerRadius: 24,
                    style: .continuous
                )
                .stroke(.tint, lineWidth: 1)
            )
    }
}

extension ButtonStyle where Self == BorderedTransparentButtonStyle {
    static var borderedTransparent: Self {
        return .init()
    }
}
