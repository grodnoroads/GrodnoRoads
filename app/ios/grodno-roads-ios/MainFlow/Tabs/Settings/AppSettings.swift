//
//  AppSettings.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct AppSettings: View {
    private let component: AppSettingsComponent
    
    init(_ component: AppSettingsComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingsHeaderView(title: "Main")
            
            SettingsItemView(
                leadingIcon: "swatchpalette.fill",
                headlineText: "Appearance",
                action: { component.open(page: .appearance) }
            ).padding(.leading, 12)
            
            SettingsItemView(
                leadingIcon: "map.fill",
                headlineText: "Map",
                action: { component.open(page: .mapsettings) }
            ).padding(.leading, 12)
            
            SettingsItemView(
                leadingIcon: "speaker.wave.2.bubble.fill",
                headlineText: "Alerts",
                action: { component.open(page: .alerts) }
            ).padding(.leading, 12)
            
            SettingsHeaderView(title: "Other")
                .padding(.top, 16)
            
            SettingsItemView(
                leadingIcon: "newspaper.fill",
                headlineText: "Changelog",
                action: { component.open(page: .changelog) }
            ).padding(.leading, 12)
            
            SettingsItemView(
                leadingIcon: "quote.bubble.fill",
                headlineText: "FAQ",
                action: { component.open(page: .faq) }
            ).padding(.leading, 12)
            
            SettingsItemView(
                leadingIcon: "doc.fill",
                headlineText: "Privacy Policy",
                action: { 
                    openUrl("https://github.com/grodnoroads/GrodnoRoads/blob/release/PrivacyPolicy.md")
                }
            ).padding(.leading, 12)
            
            SettingsItemView(
                leadingIcon: "info.circle.fill",
                headlineText: "Terms of Service",
                action: {
                    openUrl("https://github.com/grodnoroads/GrodnoRoads/blob/release/TermsConditions.md")
                }
            ).padding(.leading, 12)
            
            Spacer()
            
            HStack {
                Spacer()
                SettingsRoundItem("Chat", image: "questionmark.circle.fill") {
                    openUrl("https://t.me/grodnoroads_chat")
                }
                Spacer()
                SettingsRoundItem("Channel", image: "paperplane.fill") {
                    openUrl("https://t.me/grodno_roads")
                }
                Spacer()
                SettingsRoundItem("Share", image: "square.and.arrow.up.fill", action: { shareAppLink() })
                Spacer()
            }
            
            Text("Version: \(component.appVersion)")
                .bold()
                .font(.system(size: 12))
                .frame(maxWidth: .infinity, alignment: .center)
                .padding()
        }
        .padding()
    }
    
    private func shareAppLink() {
        guard
            let vc = UIApplication.shared.connectedScenes
                .compactMap({$0 as? UIWindowScene}).first?
                .windows.first?.rootViewController,
            let url = URL(string: "https://t.me/grodno_roads")
        else {
            return
        }
        
        let shareActivity = UIActivityViewController(activityItems: [url], applicationActivities: nil)
        shareActivity.popoverPresentationController?.sourceView = vc.view
        shareActivity.popoverPresentationController?.sourceRect = CGRect(x: UIScreen.main.bounds.width / 2, y: UIScreen.main.bounds.height, width: 0, height: 0)
        shareActivity.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.down
        vc.present(shareActivity, animated: true, completion: nil)
    }
    
    private func openUrl(_ url: String) {
        guard let url = URL(string: url) else { return }
        UIApplication.shared.open(url)
    }
}

#Preview {
    AppSettings(AppSettingsComponentPreview())
}
