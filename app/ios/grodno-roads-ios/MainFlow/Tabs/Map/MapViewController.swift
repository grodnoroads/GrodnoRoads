//
//  MapViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 25.12.23.
//

import GoogleMaps
import UIKit

class MapViewController: UIViewController {
    
    private lazy var map = GMSMapView()
    
    override func loadView() {
        super.loadView()
        self.view = map
    }
}
