//
//  MapViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 25.12.23.
//

import SwiftUI

struct MapViewControllerBridge: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> MapViewController {
        MapViewController()
    }
    
    func updateUIViewController(_ uiViewController: MapViewController, context: Context) {
    }
}

