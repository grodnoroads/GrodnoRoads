//
//  MainApp.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 3.12.23.
//

import SwiftUI

@main
struct MainApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var delegate
    
    var body: some Scene {
        WindowGroup {
            RootView(root: delegate.root)
        }
    }
}
